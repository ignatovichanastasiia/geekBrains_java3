package ServerSide;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String nick;
    private boolean authBoolean;
    //    private Timer timer;
//    private TimerTask task;
    private final static long time = 360000;

    //конструктор хэндлера + стримы и поток;
    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
            this.nick = "";

            new Thread(() -> {
                try{
                    authBoolean = false;
                    new Thread(() -> {
                        try {
                            Thread.sleep(time);
                            timeOfAuth();
                        } catch (InterruptedException ignored) {
                        }
                    }).start();
                    authentication();
                    readMessage();
                } catch (IOException i) {
                    i.printStackTrace();
                } finally {
                    closeChat();
                }
            }).start();

        } catch (IOException e) {
            System.out.println("Server problem");
        }
    }

    //геттер хэндлера
    public String getNick() {
        return nick;
    }

    //геттер и сеттер на логин
    public boolean getAuthBoolean() {
        return authBoolean;
    }

    public void setAuthTimer(boolean authBoolean) {
        this.authBoolean = authBoolean;
    }

    //логинимся
    // /auth login password - для входа, /reg login password nick - для регистрации в чат.
    private void authentication() {
        while (true) {
            try {
                String authStr = dis.readUTF();
                if (authStr.startsWith("/auth")) {
                    String[] arr = authStr.split("\\s");
                    if(arr.length==3) {
                        String nick = myServer.getAuthService().getNickByLoginAndPassword(arr[1], arr[2]);
                        if (nick!=null) {
                            if (!myServer.isNickBusy(nick)) {
                                authBoolean = true;
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
                    }else{
                        sendMessage("Админ: неверный запрос. Напишите: /auth login password");
                    }
                }
                if(authStr.startsWith("/reg")) {
                    String[] arr = authStr.split("\\s");
                    if(arr.length==4) {
                        if (myServer.getAuthService().registration(arr[1], arr[2], arr[3])) {
                            sendMessage("Админ: пользователь зарегистрировался. ");
                        } else {
                            sendMessage("Админ: что-то пошло не так. Попробуйте другое имя. ");
                        }
                    }else{
                        sendMessage("Админ: неверный запрос. Напишите: /reg login password nick. ");
                    }
                }
            } catch (IOException ignored) {
            }
        }

    }
    //отправка сообщения на сервер
    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
        } catch (IOException ignored) {
        }
    }

    //чтение сообщения с сервера
    private void readMessage() throws IOException {
        while (true) {
            String messageFromClient = dis.readUTF();
            if (messageFromClient.equals("/q")) {
                sendMessage(messageFromClient);
                return;
            }

            myServer.sendMessageToClients(nick +"---"+messageFromClient);
        }
    }

    //проверка на время
    private void timeOfAuth(){
        if(!authBoolean){
            sendMessage("Админ: время на авторизацию закончилось. ");
            closeConnection();
        }
    }

    //закрывашка для выхода клиента
    private void closeChat() {
        myServer.unsubscrible(this);
        myServer.sendMessageToClients("Админ---"+ nick + " покинул чат. ");
    }

    //закрывашка
    private void closeConnection(){
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
