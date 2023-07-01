import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;
import javax.swing.border.*;
import java.util.ArrayList;

public class Chat implements ActionListener {
    private JFrame frame;
    private JPanel textPanel, sendPanel, topPanel;
    private String chatBoxTitle = "ChatBox";
    private JTextField messageField;
    private JButton sendButton;
    private ArrayList<MessageItem> msgList;
    private Box box = Box.createVerticalBox();

    public Chat() {
        msgList = new ArrayList<>();

        frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(chatBoxTitle);

        textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBounds(0, 80, 1000, 700);
        textPanel.setBackground(Color.decode("#F3FFFD"));
        JScrollPane textScrollPane = new JScrollPane(textPanel);

        
        topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 1000, 80);
        topPanel.setBackground(Color.DARK_GRAY);
        JLabel greta = new JLabel("GRETA");
        greta.setFont(new Font("SANS-SERIF", Font.BOLD, 24));
        greta.setBounds(10, 18, 100, 50);
        greta.setForeground(Color.WHITE);
        topPanel.add(greta);
        
        sendPanel = new JPanel();
        sendPanel.setLayout(null);
        sendPanel.setBackground(Color.decode("#ADC5E0"));
        sendPanel.setBounds(0, 705, 1000, 60);
        
        frame.add(sendPanel);
        frame.add(textPanel);
        frame.add(topPanel);
        
        addSendEvent();

        frame.setVisible(true);

    }

    private void addSendEvent() {

        messageField = new JTextField(80);
        messageField.setBounds(8, 8, 860, 46);
        messageField.setFont(new Font("SANS-SERIF", Font.PLAIN, 20));
        messageField.setBorder(new EmptyBorder(0, 10, 0, 0));
        sendPanel.add(messageField);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("SANS-SERIF", Font.BOLD, 20));

        sendButton.setBounds(875, 0, 125, 60);
        sendButton.setBackground(Color.decode("#255c99"));
        sendButton.setForeground(Color.WHITE);
        frame.getRootPane().setDefaultButton(sendButton);
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                String msg = messageField.getText();
                if (msg.equals("")) { return; }
                System.out.println(msg);
                JPanel newMsg = addToList(msg, true, e);
                textPanel.setLayout(new BorderLayout());
                
                JPanel right = new JPanel(new BorderLayout());

                right.add(newMsg, BorderLayout.LINE_END);
                right.setBackground(Color.decode("#F3FFFD"));
                box.add(right);
                box.add(Box.createRigidArea(new Dimension(0, 10)));
                textPanel.add(box, BorderLayout.PAGE_START);
                frame.validate();

                messageField.setText("");
            }
        });
        
        sendPanel.add(sendButton);

    }
    
    private JPanel addToList(String msg, boolean fromUser, ActionEvent e) {
        Color textAreaColor, foregroundColor;
        if (fromUser) {
            textAreaColor = Color.decode("#255c99");
            foregroundColor = Color.WHITE;
        } else {
            textAreaColor = Color.WHITE;
            foregroundColor = Color.BLACK;
        }
        
        msgList.add(new MessageItem(msg, fromUser));
        JPanel textArea = new JPanel();
        textArea.setLayout(new BoxLayout(textArea, BoxLayout.Y_AXIS));
        textArea.setBackground(Color.decode("#F3FFFD"));

        JLabel __label = new JLabel(msg);
        __label.setFont(new Font("SANS-SERIF", Font.PLAIN, 18));
        __label.setBackground(textAreaColor);
        __label.setOpaque(true);
        __label.setForeground(foregroundColor);
        __label.setBorder(new EmptyBorder(5, 10, 5, 10));

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH:MM");

        JLabel timeLabel = new JLabel(time.format(cal.getTime()));
        textArea.add(__label);
        textArea.add(timeLabel);

        return textArea;
    }

    public static void main(String[] args) throws Exception {

        new Chat();

    }

}

class MessageItem {
    private String messageText;
    private boolean fromUser;

    public MessageItem(String msg, boolean fromUser) {
        this.messageText = msg;
        this.fromUser = fromUser;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String msg) {
        this.messageText = msg;
    };

    public boolean isFromUser() {
        return fromUser;
    }

    public void setFromUser(boolean fromUsr) {
        this.fromUser = fromUsr;
    }
}