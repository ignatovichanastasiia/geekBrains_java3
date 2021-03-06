package ServerSide;

import ClientSide.HistoryWindow;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;

    private String nick;
    private boolean authBoolean;
    private final static long time = 360000;
    private final int historyMax = 10;

    private static File history;
    public ArrayList<String> rHistory;


    //конструктор хэндлера + стримы и поток;
    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.nick = "";
            this.history = getHistory();

//            Экзекутор.
            ExecutorService executor = Executors.newFixedThreadPool(2);
            try {
                executor.execute(() -> {
                    setAuthBoolean(false);
                    while (getAuthBoolean()) {
                        Thread.sleep(time);
                        timeOfAuth();
                    }
                    authentication();
                    readMessage();
                });
            } catch (InterruptedException ignored) {
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeChat();
                executor.shutdown();
            }
        } catch (IOException e) {
            System.out.println("Server problem");
        }

//        Старые потоки.
//            new Thread(() -> {
//                try {
//                    setAuthTimer(false);
//                    new Thread(() -> {
//                        try {
//                            Thread.sleep(time);
//                            timeOfAuth();
//                        } catch (InterruptedException ignored) {
//                        }
//                    }).start();
//                    authentication();
//                    readMessage();
//                } catch (IOException i) {
//                    i.printStackTrace();
//                } finally {
//                    closeChat();
//                }
//            }).start();
//    } catch(
//    IOException e)
//
//    {
//        System.out.println("Server problem");
//    }
//
//}

        //геттер хэндлера
        public String getNick () {
            return nick;
        }

        public File getHistory () {
            if (history == null) {
                history = new File("history.txt");
            }
            return history;
        }

        //геттер и сеттер на логин
        public boolean getAuthBoolean () {
            return authBoolean;
        }

        public void setAuthBoolean ( boolean authBoolean){
            this.authBoolean = authBoolean;
        }

        //логинимся
        // /auth login password - для входа, /reg login password nick - для регистрации в чат.
        private void authentication () {
            while (true) {
                try {
                    String authStr = dis.readUTF();
                    if (authStr.startsWith("/auth")) {
                        String[] arr = authStr.split("\\s");
                        if (arr.length == 3) {
                            String nick = myServer.getAuthService().getNickByLoginAndPassword(arr[1], arr[2]);
                            if (nick != null) {
                                if (!myServer.isNickBusy(nick)) {
                                    setAuthBoolean(true);
                                    sendMessage("/authok " + nick);
                                    this.nick = nick;
                                    myServer.sendMessageToClients("Админ---" + this.nick + " вошел в чат. ");
                                    myServer.subscrible(this);
                                    return;
                                } else {
                                    sendMessage("Админ: " + this.nick + " - ник уже авторизован. ");
                                }
                            } else {
                                sendMessage("Админ: неверный логин или пароль. ");
                            }
                        } else {
                            sendMessage("Админ: неверный запрос. Напишите: /auth login password");
                        }
                    }
                    if (authStr.startsWith("/reg")) {
                        String[] arr = authStr.split("\\s");
                        if (arr.length == 4) {
                            if (myServer.getAuthService().registration(arr[1], arr[2], arr[3])) {
                                sendMessage("Админ: пользователь зарегистрировался. ");
                            } else {
                                sendMessage("Админ: что-то пошло не так. Попробуйте другое имя. ");
                            }
                        } else {
                            sendMessage("Админ: неверный запрос. Напишите: /reg login password nick. ");
                        }
                    }
                } catch (IOException ignored) {
                }
            }

        }

        //отправка сообщения на сервер
        public void sendMessage (String message){
            try {
                if (message.startsWith("/hist")) {
                    readHistory();
                    return;
                }
                dos.writeUTF(message);
                writeHistory(message);
            } catch (IOException ignored) {
            }
        }

        //чтение сообщения с сервера
        private void readMessage () throws IOException {
            while (true) {
                String messageFromClient = dis.readUTF();
                if (messageFromClient.equals("/q")) {
                    sendMessage(messageFromClient);
                    return;
                }

                myServer.sendMessageToClients(nick + "---" + messageFromClient);
            }
        }

        //проверка на время
        private void timeOfAuth () {
            if (!getAuthBoolean()) {
                sendMessage("Админ: время на авторизацию закончилось. ");
                setAuthBoolean(true);
                closeConnection();
            }
        }

        //запись истории - история с командами и приватами!
        private void writeHistory (String mes){
            try (BufferedOutputStream buf = new BufferedOutputStream(new FileOutputStream(getHistory(), true))) {
                String message = new String(mes + "\n");
                buf.write(message.getBytes());
            } catch (IOException e) {
                System.out.println("Ошибка с записью истории.");
                e.printStackTrace();
            }

        }

        //чтение истории, фильтр сообщений на вывод, команда на создание окна под историю+вывод
        public void readHistory () {
            int count = 0;
            rHistory = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(getHistory()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.startsWith("/") && !line.startsWith("!")) {
                        rHistory.add(line);
                        count++;
                    }
                }
                HistoryWindow hw = new HistoryWindow();
                hw.setLineCount(count - historyMax);
                hw.histPrint(rHistory);
                sendMessage("Админ: окно истории загрузилось.");
            } catch (
                    FileNotFoundException e) {
                e.printStackTrace();
            } catch (
                    IOException e) {
                e.printStackTrace();
            }

        }

        //закрывашка для выхода клиента
        private void closeChat () {
            myServer.unsubscrible(this);
            myServer.sendMessageToClients("Админ---" + nick + " покинул чат. ");
        }

        //закрывашка
        private void closeConnection () {
            try {
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
