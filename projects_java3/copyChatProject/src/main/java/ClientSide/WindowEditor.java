//package ClientSide;
//
//import com.sun.java.swing.action.ExitAction;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//
//public class WindowEditor {
//    package ClientSide;
//
//import com.sun.java.swing.action.ExitAction;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.*;
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
////
////
//    public class Window extends JFrame {
////        private final static String IP_ADDRESS = "localhost"; //127.0.0.1
////        private final static int SERVER_PORT = 8081;
////        private Socket socket;
////        private DataInputStream dis;
////        private DataOutputStream dos;
//        public JTextArea jTextArea;
//        public JTextField jTextField;
////        private boolean isAuthorized;
//
//
//        public static void main(String[] args) {
//
//            SwingUtilities.invokeLater(() -> {
//                new ClientSide.Window();
//            });
//        }
//
//        public Window() {
//            connection();
//            prepareWindow();
//        }



//        public void setIsAuthorized(boolean authorized) {
//            isAuthorized = authorized;
//        }


//        private void connection() {
//            try {
//                //сокет, вход, выход
//                socket = new Socket(IP_ADDRESS, SERVER_PORT);
//                dis = new DataInputStream(socket.getInputStream());
//                dos = new DataOutputStream(socket.getOutputStream());
//                setIsAuthorized(false);
//                Thread tr = new Thread(() -> {
//                    while (true) {
//                        try {
//                            String serverMessage = dis.readUTF();
//                            if (serverMessage.startsWith("/authok")) {
//                                String arr[] = serverMessage.split(" ", 2);
//                                setIsAuthorized(true);
//                                jTextArea.append("Вы вошли в чат, " + arr[1] + "\n");
//                                break;
//                            }
//                            jTextArea.append(serverMessage + "\n");
//                        } catch (IOException ignored) {
//                        }
//                    }
//                    while (true) {
//                        try {
//                            String serverMessage = dis.readUTF();
//                            if (serverMessage.equals("/q")) {
//                                break;
//                            }
//                            jTextArea.append(serverMessage + "\n");
//                        } catch (IOException ignored) {
//                        }
//                    }
//                    closeConnection();
//                });
////            tr.setDaemon(true);
//                tr.start();
//            } catch (IOException ignored) {
//            }
//        }

//        private void prepareWindow() {
//            //окно
//            JFrame jFrame = new JFrame();
//            jFrame.setTitle("Чат");
//            jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//            jFrame.setBounds(300, 100, 400, 500);
//            jFrame.setResizable(false);
//            jFrame.addWindowListener(new WindowAdapter() {
//                @Override
//                public void windowClosing(WindowEvent e) {
//                    super.windowClosing(e);
//                    try {
//                        dos.writeUTF("/q");
//                        closeConnection();
//                    } catch (IOException z) {
//                        z.printStackTrace();
//                    }
//                }
//            });
//
//            //меню-бар
////        JMenuBar menuBar = new JMenuBar();
////        menuBar.add(createFileMenu());
////        menuBar.add(createViewMenu());
//
//            //текстовое поле
//            jTextArea = new JTextArea("Чат - комната.\n");
//            jTextArea.setBackground(Color.lightGray);
//            jTextArea.setLineWrap(true);
//            jTextArea.setLayout(null);
//            jFrame.add(jTextArea, BorderLayout.CENTER);
//
//            //панель
//            JPanel panel = new JPanel();
//            jTextField = new JTextField();
//            panel.setLayout(new GridLayout(1, 2));
//            jTextField.setSize(20, 20);
//            jTextField.setBackground(Color.white);
//            panel.add(jTextField, BorderLayout.PAGE_START);
//            jTextField.addKeyListener(new KeyListener() {
//                @Override
//                public void keyTyped(KeyEvent e) {
//
//                }
//
//                @Override
//                public void keyPressed(KeyEvent e) {
//
//                }
//
//                @Override
//                public void keyReleased(KeyEvent e) {
//                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                        sendMessageToServer();
//                    }
//                }
//            });
//
//            //кнопка
//            JButton answerButton = new JButton("Написать");
//            panel.add(answerButton, BorderLayout.CENTER);
//            answerButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    sendMessageToServer();
//                }
//            });
//            jFrame.add(panel, BorderLayout.SOUTH);
//            JScrollPane scrollBar = new JScrollPane(jTextArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
//            jFrame.add(scrollBar);
//            jFrame.setVisible(true);
//        }
//
//        //менюшка
//        private JMenu createViewMenu() {
//            // создадим выпадающее меню
//            JMenu viewMenu = new JMenu("Вид");
//// Создание выпадающего меню
//            JMenu file = new JMenu("Файл");
//            // Пункт меню "Открыть" с изображением
//            JMenuItem open = new JMenuItem("Открыть",
//                    new ImageIcon("images/open.png"));
//            // Пункт меню из команды с выходом из программы
//            JMenuItem exit = new JMenuItem(new ExitAction());
//            // Добавление к пункту меню изображения
//            exit.setIcon(new ImageIcon("images/exit.png"));
//            // Добавим в меню пункта open
//            file.add(open);
//            // Добавление разделителя
//            file.addSeparator();
//            file.add(exit);
//
//            open.addActionListener(new ActionListener()
//            {
//                @Override
//                public void actionPerformed(ActionEvent arg0) {
//                    System.out.println ("ActionListener.actionPerformed : open");
//                }
//            });
//            return file;
//        }
//
//        private JMenu createFileMenu() {
//            {
//                JMenu text = new JMenu("Текст");
//                // и несколько вложенных меню
//                JMenu style = new JMenu("Стиль");
//                JMenuItem bold = new JMenuItem("Жирный");
//                JMenuItem italic = new JMenuItem("Курсив");
//                JMenu font = new JMenu("Шрифт");
//                JMenuItem arial = new JMenuItem("Arial");
//                JMenuItem times = new JMenuItem("Times");
//                font.add(arial); font.add(times);
//                // размещаем все в нужном порядке
//                style.add(bold);
//                style.add(italic);
//                style.addSeparator();
//                style.add(font);
//                text.add(style);
//                return text;
//
//
//                // создадим выпадающее меню
//                JMenu viewMenu = new JMenu("Файл");
//                // меню-флажки
//                JCheckBoxMenuItem line  = new JCheckBoxMenuItem("Линейка");
//                JCheckBoxMenuItem grid  = new JCheckBoxMenuItem("Сетка");
//                JCheckBoxMenuItem navig = new JCheckBoxMenuItem("Навигация");
//                // меню-переключатели
//                JRadioButtonMenuItem one = new JRadioButtonMenuItem("Одна страница");
//                JRadioButtonMenuItem two = new JRadioButtonMenuItem("Две страницы");
//                // организуем переключатели в логическую группу
//                ButtonGroup bg = new ButtonGroup();
//                bg.add(one);
//                bg.add(two);
//                // добавим все в меню
//                viewMenu.add(line);
//                viewMenu.add(grid);
//                viewMenu.add(navig);
//                // разделитель можно создать и явно
//                viewMenu.add( new JSeparator());
//                viewMenu.add(one);
//                viewMenu.add(two);
//                return viewMenu;
//            }
//
//            //закрывашка
//            private void closeConnection() {
//                try {
//                    if (dos != null) dos.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (dis != null) dis.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (dos != null) dos.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    if (socket != null) socket.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            //отправка сообщений в чат и на сервер - формат: только сообщение без ника
//            public void sendMessageToServer() {
//                if (!jTextField.getText().trim().isEmpty()) {
//                    try {
//                        String messageToServer = jTextField.getText();
//                        dos.writeUTF(messageToServer);
//                        jTextField.setText("");
//                    } catch (IOException ignored) {
//                    }
//                }
//            }
//        }
//
//
//    }
//
