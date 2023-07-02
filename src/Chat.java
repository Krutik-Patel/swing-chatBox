import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.io.*;
import javax.swing.border.*;

import javafx.scene.layout.Border;

import java.util.ArrayList;

public class Chat {
    private JFrame frame;
    private JPanel mainPanel, textPanel, sendPanel, topPanel;
    private String chatBoxTitle = "ChatBox";
    private JTextField messageField;
    private JScrollPane scrollPane;
    private JButton sendButton;
    private ArrayList<MessageItem> msgList;
    private String newMessage;
    private final Object monitor = new Object();
    private String USER_IMG_PATH = "./images/Usr.png";

    public enum Author {
        USER,
        GRETA
    }

    public static Chat init() {
        Chat thisChat = new Chat();
        return thisChat;
    }

    public void pushMessageToUser(String message) {
        Box msgFromGreta = createMessageArea(message, Author.GRETA);
        textPanel.add(msgFromGreta);
        frame.validate();
        scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
    }

    public synchronized String getMessageFromUser() throws Exception {
        wait();
        return newMessage;
    }

    private Chat() {
        msgList = new ArrayList<>();

        frame = new JFrame();
        mainPanel = new JPanel();

        frame.setResizable(false);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(chatBoxTitle);
        frame.setLocationRelativeTo(null);
        mainPanel.setLayout(new BorderLayout());

        addTextPanel();
        addSendPanel();
        addTopPanel();

        frame.add(mainPanel);

        frame.setVisible(true);

    }

    private void addTextPanel() {
        textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.decode("#E2E2E2"));
        textPanel.setBorder(new EmptyBorder(5, 10, 5, 10));

        scrollPane = new JScrollPane(textPanel);
        scrollPane.setBorder(null);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
    }

    private void addTopPanel() {
        ImageIcon i1 = new ImageIcon(USER_IMG_PATH);
        Image i2 = i1.getImage().getScaledInstance(42, 42, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(new EmptyBorder(5, 10, 5, 10));
        topPanel.setBackground(Color.decode("#255c99"));
        JLabel greta = new JLabel("GRETA", i3, SwingConstants.LEADING);
        greta.setFont(new Font("SANS-SERIF", Font.BOLD, 24));
        greta.setForeground(Color.WHITE);
        topPanel.add(greta, BorderLayout.CENTER);
        topPanel.setPreferredSize(new Dimension(1000, 80));

        mainPanel.add(topPanel, BorderLayout.NORTH);
    }

    private void addSendPanel() {
        sendPanel = new JPanel();
        sendPanel.setLayout(new BorderLayout());
        sendPanel.setBackground(Color.decode("#ADC5E0"));
        sendPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.decode("#ACACAC")));
        sendPanel.setPreferredSize(new Dimension(1000, 60));

        addSendButton();

        mainPanel.add(sendPanel, BorderLayout.SOUTH);
    }

    private void addSendButton() {

        messageField = new JTextField();
        messageField.setFont(new Font("SANS-SERIF", Font.PLAIN, 20));
        messageField.setText("Message");
        messageField.setForeground(Color.GRAY);
        messageField.setPreferredSize(new Dimension(875, 60));
        messageField.setBorder(new EmptyBorder(0, 15, 0, 15));
        messageField.addKeyListener(new KeyListener() {
            private boolean noText = true;

            public void keyPressed(KeyEvent k) {
            }

            public void keyTyped(KeyEvent k) {
                if (messageField.getText().equals("")) {
                    messageField.setText("Message");
                    messageField.setForeground(Color.GRAY);
                    messageField.setCaretPosition(0);
                    noText = true;
                } else if (noText && !k.isActionKey()) {
                    noText = false;
                    messageField.setText("");
                    messageField.setForeground(Color.BLACK);
                }
            }

            public void keyReleased(KeyEvent k) {
            }
        });
        sendPanel.add(messageField, BorderLayout.WEST);

        sendButton = new JButton("Send");
        sendButton.setFont(new Font("SANS-SERIF", Font.BOLD, 20));
        sendButton.setBackground(Color.decode("#255c99"));
        sendButton.setForeground(Color.WHITE);
        sendButton.setBorder(null);
        frame.getRootPane().setDefaultButton(sendButton);
        sendButton.addActionListener(new ActionListener() {
            public synchronized void actionPerformed(ActionEvent e) {

                String msg = messageField.getText();
                if (msg.equals("")) {
                    return;
                }
                // System.out.println(msg);

                Box newMsgArea = createMessageArea(msg, Author.USER);
                textPanel.add(newMsgArea);
                frame.validate();
                scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());

                messageField.setText("");

                newMessage = msg;
                try {
                    callNotify();
                } catch (Exception e1) {
                    System.out.println("Oops");
                    e1.printStackTrace();
                }
            }
        });

        sendButton.setPreferredSize(new Dimension(125, 60));
        sendPanel.add(sendButton, BorderLayout.EAST);

    }

    private synchronized void callNotify() throws Exception {
        notify();
    }

    private Box createMessageArea(String msg, Author author) {
        Color textAreaColour = Color.WHITE, foregroundColour = Color.WHITE;
        float textAlignment = Component.LEFT_ALIGNMENT;
        if (author == Author.USER) {
            textAreaColour = Color.decode("#255c99");
            foregroundColour = Color.WHITE;
            textAlignment = Component.RIGHT_ALIGNMENT;

        } else if (author == Author.GRETA) {
            textAreaColour = Color.WHITE;
            foregroundColour = Color.BLACK;
            textAlignment = Component.LEFT_ALIGNMENT;
        }

        JPanel msgArea = new JPanel();
        msgArea.setLayout(new BoxLayout(msgArea, BoxLayout.Y_AXIS));
        msgArea.setBackground(null);

        JLabel msgText = new JLabel(msg);
        msgText.setBackground(textAreaColour);
        msgText.setForeground(foregroundColour);
        msgText.setOpaque(true);
        msgText.setBorder(new EmptyBorder(5, 10, 5, 10));
        msgText.setFont(new Font("SANS-SERIF", Font.PLAIN, 18));
        msgText.setAlignmentX(textAlignment);

        JLabel timeLabel = new JLabel();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat time = new SimpleDateFormat("HH:MM");
        Date msgTime = cal.getTime();
        timeLabel.setText(time.format(msgTime));
        timeLabel.setAlignmentX(textAlignment);
        msgList.add(new MessageItem(msg, Author.USER, msgTime));

        msgArea.add(msgText);
        msgArea.add(timeLabel);
        msgArea.add(Box.createVerticalStrut(10));
        msgArea.add(Box.createHorizontalGlue());
        return justify(msgArea, textAlignment);
    }

    private Box justify(JPanel panel, float alignment) {
        Box b = Box.createHorizontalBox();
        b.add(panel);
        if (alignment == Component.LEFT_ALIGNMENT) {
            b.add(Box.createHorizontalGlue());
        }
        return b;
    }

    public static void main(String[] args) throws Exception {

        new Chat();


    }

}

class MessageItem {
    private String messageText;
    private Chat.Author author;
    private Date time;

    public MessageItem(String msg, Chat.Author author, Date time) {
        this.messageText = msg;
        this.author = author;
        this.time = time;
    }

    public String getMessageText() {
        return messageText;
    }

    public Chat.Author isFromUser() {
        return author;
    }

    public Date getTime() {
        return time;
    }
}