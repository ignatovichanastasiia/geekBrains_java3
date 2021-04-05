package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistoryWindow extends JFrame{
    public JTextArea jta;
    private static int lineCount;

    public HistoryWindow(){
        getHWindow();
    }

    public void histPrint(ArrayList<String> rH) {
        int a = getLineCount();
        for (int i = a; i < rH.size(); i++) {
            jta.append(rH.get(i)+"\n");
        }
    }



    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        HistoryWindow.lineCount = lineCount;
    }

    public void getHWindow() {
        JFrame jFrame = new JFrame();
        jFrame.setTitle("История");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(300, 100, 400, 500);
        jFrame.setResizable(false);
        jta = new JTextArea("История чата.\n");
        jta.setBackground(Color.lightGray);
        jta.setLineWrap(true);
        jta.setLayout(null);
        jFrame.add(jta, BorderLayout.CENTER);
        JScrollPane scrollBar = new JScrollPane(jta, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jFrame.add(scrollBar);
        jFrame.setVisible(true);

    }
}
