import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

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
    private JButton updateStudentButton;
    private JButton viewStudentsButton;
    private JButton removeStudentButton;
    private JButton addStudentButton;
    private JButton logoutButton;
    private JLabel welcomeUser;
    private JButton searchStudentButton;
    private JPanel AddStdPg;
    private JTextField nameInput;
    private JTextField FNameInput;
    private JTextField ContactInput;
    private JTextField programInput;
    private JButton submitButton1;
    private JTextField DOBinput;
    private JButton button1;
    private JPanel searchPg;
    private JTextField nameInputt;
    private JButton submitButton2;
    private JButton button2;
    private JPanel viewStdpg;
    private JScrollPane scrollPane;
    private JTable table1;
    private JButton button3;
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
                welcomeUser.setText("Welcome " + username);
            }else{
                JOptionPane.showMessageDialog(null, "Wrong Username or Password !");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,e.getMessage());
        }
        inputPassword.setText("");
        inputUsername.setText("");

    }
    void addTOStdFile(ArrayList <String> input){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter("data/students.txt",true))) {
            for (int i = 0 ; i < input.size(); i++) {
                bw.write(input.get(i) + "\n");
            }
            JOptionPane.showMessageDialog(null, "Student Added Successfully");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error occured while adding student to database");
        }
    }
    void inputField(){
        if (nameInput.getText().equals("") || DOBinput.getText().equals("") || FNameInput.getText().equals("")||
        programInput.getText().equals("") || ContactInput.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
            return;
        }
        ArrayList <String> input=new ArrayList<>();
        input.add(nameInput.getText()+", "+DOBinput.getText()+", "+ FNameInput.getText()+", "+ContactInput.getText()+", "+programInput.getText());
        addTOStdFile(input);
        nameInput.setText("");
        DOBinput.setText("");
        FNameInput.setText("");
        ContactInput.setText("");
        programInput.setText("");
    }
    ArrayList<String> loadData(){
        ArrayList <String> students = new ArrayList();
        try(BufferedReader br = new BufferedReader(new FileReader("data/students.txt"))){
            String line;
            while ((line = br.readLine()) != null){
                students.add(line);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"Error loading students from database");
        }return students;
    }
    void removeStd(){
        if (nameInputt.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Please fill all the fields");
        }
        ArrayList <String> students = loadData();
        boolean found = false;
        for (String s :  students){
            String [] input = s.split(", ");
            if (input[0].equalsIgnoreCase(nameInputt.getText())){
                found = true;
                continue;
            }
            students.add(input.toString());
        }
        nameInputt.setText("");
        if (! found){
            JOptionPane.showMessageDialog(null, "Student not found");
            return;
        }addTOStdFile(students);
    }
    void displayStd(ArrayList <String> students){
        tabbedpane.setSelectedIndex(4);
        String [] columns = {"Name","DOB","Father Name","Contact","Program"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        table1.setModel(model);
        String [] line;
        for (String s : students) {
            line = s.split(", ");
            Object[] row = {
                    line[0],
                    line[1],
                    line[2],
                    line[3],
                    line[4]
            };
            model.addRow(row);
        }
    }
    void showStd(){
        ArrayList <String> students = loadData();
        displayStd(students);
    }
    void searchStd(){
        ArrayList <String> students = loadData();
        if (students.size() == 0){
            JOptionPane.showMessageDialog(null, "Empty record ");
            tabbedpane.setSelectedIndex(1);
            return;
        }
        String [] check;
        boolean found = false;
        for (int i  = 0; i < students.size(); i++) {
            check =  students.get(i).split(", ");
            if (check[0].equalsIgnoreCase(nameInputt.getText())){
                tabbedpane.setSelectedIndex(4);
                found = true;
                break;
            }
        }if (! found){
            JOptionPane.showMessageDialog(null, "Student not found");
            return;
        }
        displayStd(students);
    }
    stdManagementSysds(){
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkLoginDetails();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginPanel.setEnabled(true);
                systempanel.setEnabled(false);
                tabbedpane.setSelectedIndex(0);
                username = "";
                welcomeUser.setText("Welcome ");
            }
        });
        addStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedpane.setSelectedIndex(2);
            }
        });
        submitButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputField();
            }
        });
        ActionListener commonListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                tabbedpane.setSelectedIndex(1);
            }
        };
        removeStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedpane.setSelectedIndex(3);
            }
        });
        button1.addActionListener(commonListener);
        button2.addActionListener(commonListener);
        button3.addActionListener(commonListener);
        submitButton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeStd();
            }
        });
        viewStudentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showStd();
            }
        });
        searchStudentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tabbedpane.setSelectedIndex(3);
                searchStd();
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
