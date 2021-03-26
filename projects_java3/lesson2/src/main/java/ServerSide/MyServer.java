package ServerSide;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MyServer {
    private final int PORT = 8081;
    private List<ClientHandler> clientsList;
    private AuthenticationService authService;

    public AuthenticationService getAuthService() {
        return this.authService;
    }

    //конструктор сервера, клиент лист
    public MyServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            this.authService = new AuthenticationServiceImpl();
            authService.start();
            clientsList = new ArrayList<>();
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (authService != null) {
                authService.stop();
            }
        }
    }

    //отправка сообщения на клиента/клиентов
    public synchronized void sendMessageToClients(String messageAllTypes) {

        //на вход все сообщения должены быть: ник отправляющего + --- + сообщение
        String[] arr = messageAllTypes.trim().split("---", 2);

        //проверка входящего формата
        if (arr.length < 2) {
            for (ClientHandler c : clientsList) {
                c.sendMessage("Неверный формат отправляемого сообщения");
            }
        }

        String nick = arr[0];
        String message = arr[1];

        //проверка сообщения на команды
        if (message.startsWith("/")) {
            String[] arr1 = message.trim().split(" ", 3);
            switch (arr1[0]) {

                //приват
                case ("/w"):
                    if (arr1.length < 3)
                        sendMessageByNick("Неверный формат приват-сообщения. Формат: /w nick сообщение.", nick);
                    String toNick = arr1[1];
                    String privateMessage = (nick + "(приватное сообщение для " + toNick + "): " + arr1[2]);
                    if (sendMessageByNick(privateMessage, toNick)) {
                        sendMessageByNick(privateMessage, nick);
                    } else {
                        sendMessageByNick("В чате нет такого пользователя. ", nick);
                    }
                    break;

                //онлайн пользователи - запрос
                case ("/ou"):
                    List<String> usersOnline = new ArrayList();
                    for (ClientHandler c : clientsList) {
                        usersOnline.add(c.getNick());
                    }
                    String ou = usersOnline.toString();
                    sendMessageByNick("Online: "+ou, nick);
                    break;

                //дефолт на ошибочную команду
                default:
                    sendMessageByNick("Команда неизвестна.\n /w nick - приватное собщение.\n /ou - ники онлайн. ", nick);
                    break;
            }
            //обычная отправка на всех
        } else {
            for (ClientHandler c : clientsList) {
                c.sendMessage(nick + ": " + message);
            }
        }
    }

    //отправка сообщения по нику: сообщение+кому
    public boolean sendMessageByNick(String message, String user) {
        for (ClientHandler c : clientsList) {
            if (c.getNick().equalsIgnoreCase(user)) {
                c.sendMessage(message);
                return true;
            }
        }
        return false;
    }

    //добавление авторизованного клиента
    public synchronized void subscrible(ClientHandler c) {
        clientsList.add(c);
    }

    //удаление авторизованного клиента
    public synchronized void unsubscrible(ClientHandler c) {
        clientsList.remove(c);
    }

    //проверка занятости ника
    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler c : clientsList) {
            if (c.getNick().equals(nick)) {
                return true;
            }
        }
        return false;
    }
}
