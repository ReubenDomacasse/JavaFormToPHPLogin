import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class UserRegistration {
    public static void main(String[] args) {
        JPanel inputControls = new JPanel(new BorderLayout(5, 5));

        JPanel inputControlsLabels = new JPanel(new GridLayout(0, 1, 3, 3));
        JPanel inputControlsFields = new JPanel(new GridLayout(0, 1, 3, 3));
        inputControls.add(inputControlsLabels, BorderLayout.WEST);
        inputControls.add(inputControlsFields, BorderLayout.CENTER);

        inputControlsLabels.add(new JLabel("Username: "));
        JTextField username = new JTextField(10);
        inputControlsFields.add(username);

        inputControlsLabels.add(new JLabel("Password: "));
        JTextField password = new JTextField(10);
        inputControlsFields.add(password);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 2));

        JButton resetButton = new JButton("Reset");
        JButton submitButton = new JButton("Register");

        controls.add(resetButton);
        controls.add(submitButton);


        resetButton.addActionListener(ae -> {
            username.setText("");
            password.setText("");
        });

        submitButton.addActionListener(ae -> {
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con= DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/demo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
                Statement stmt =con.createStatement();
                String myuser = username.getText();
                String mypass =  password.getText();
                stmt.executeUpdate("INSERT INTO users (username, password,created_at) VALUES ('"+myuser+"','"+mypass+"',NULL);");
                con.close();
            }catch(Exception e){ System.out.println(e);}

            infoBox("User: "+username.getText()+" added to databse","User registered");
            username.setText("");
            password.setText("");
        });

        JPanel gui = new JPanel(new BorderLayout(10, 10));
        gui.setBorder(new TitledBorder("UserRegistration"));
        gui.add(inputControls, BorderLayout.CENTER);
        gui.add(controls, BorderLayout.SOUTH);

        JFrame f = new JFrame();
        f.setContentPane(gui);

        f.pack();
        f.setLocationByPlatform(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    private static void infoBox(String infoMessage, String titleBar)
    {
        JOptionPane.showMessageDialog(null, infoMessage, "InfoBox: " + titleBar, JOptionPane.INFORMATION_MESSAGE);
    }
}