import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import javafx.scene.image.Image;

public class Chat implements ActionListener{
    private JFrame frame;
    private JPanel textPanel, sendPanel, topPanel;
    private String chatBoxTitle = "ChatBox";
    private JTextField messageField;
    private JButton sendButton;

    public Chat() {
        frame = new JFrame();
        frame.setResizable(false);
        frame.setSize(1000, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(chatBoxTitle);
        
        textPanel = new JPanel();
        textPanel.setLayout(null);
        textPanel.setBounds(0, 80, 1000, 700);
        textPanel.setBackground(Color.decode("#F3FFFD"));


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
        frame.add(topPanel);
        frame.add(textPanel);

        sendEvent();
        

        frame.setVisible(true);

    }

    private void sendEvent() {
     
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
                System.out.println(msg);
                messageField.setText("");
            }
        });

        sendPanel.add(sendButton);

    }

    public static void main(String[] args) throws Exception {

        new Chat();
        
    }
}
