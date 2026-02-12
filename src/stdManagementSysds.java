import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import security.LoginCheck;


public class stdManagementSysds {
    private JLabel welcomeLab;
    private JLabel SysloginLab;
    private JTextField inputUsername;
    private JPasswordField inputPassword;
    private JLabel usernameLab;
    private JLabel pswrdLab;
    private JButton submitButton;
    private JPanel mainPanel;
    private JPanel loginPanel;
    private JTabbedPane tabbedpane;
    private JPanel systempanel;
    private String username;

    void checkLoginDetails(){
        try {
            username = "";
            File file = new File("data/security.txt");
            file.createNewFile();
            username = inputUsername.getText();
            String password = inputPassword.getText();
            if (username.equals("") || password.equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the fields");
                return;
            }
            if (LoginCheck.matchDetails(file, username, password)) {
                JOptionPane.showMessageDialog(null, "Login Successful\nWelcome " + username);
                loginPanel.setEnabled(false);
                systempanel.setEnabled(true);
                tabbedpane.setSelectedIndex(1);
            }else{
                JOptionPane.showMessageDialog(null, "Wrong Username or Password !");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        inputPassword.setText("");
        inputUsername.setText("");

    }
    stdManagementSysds(){
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLoginDetails();
            }
        });
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new stdManagementSysds().mainPanel);
        frame.pack();
        frame.setVisible(true);
    }
}
