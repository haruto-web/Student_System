import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginScreen extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel errorLabel;


    public LoginScreen() {
        setTitle("Admin Login");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);




        Color backgroundColor = new Color(30, 30, 30);
        Color fieldColor = new Color(60, 60, 60);
        Color textColor = Color.WHITE;
        Color buttonColor = new Color(100, 100, 100);




        getContentPane().setBackground(backgroundColor);




        usernameField = new JTextField(20);
        usernameField.setBackground(fieldColor);
        usernameField.setForeground(textColor);
        usernameField.setCaretColor(textColor);
        usernameField.setBorder(BorderFactory.createLineBorder(textColor));


        passwordField = new JPasswordField(20);
        passwordField.setBackground(fieldColor);
        passwordField.setForeground(textColor);
        passwordField.setCaretColor(textColor);
        passwordField.setBorder(BorderFactory.createLineBorder(textColor));


        loginButton = new JButton("Login");
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(textColor);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setFocusPainted(false);


        errorLabel = new JLabel("", JLabel.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 16));




        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;




        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Admin Login", JLabel.CENTER);
        titleLabel.setForeground(textColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 36));
        add(titleLabel, gbc);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(new JLabel("Username:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        add(usernameField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        add(passwordField, gbc);


        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);


        gbc.gridx = 0;
        gbc.gridy = 4;
        add(errorLabel, gbc);




        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());




                if (username.isEmpty() || password.isEmpty()) {
                    errorLabel.setText("Username and Password cannot be empty!");
                    return;
                }




                boolean isValid = db.validateAdmin(username, password);


                if (isValid) {
                    errorLabel.setText("");
                    JOptionPane.showMessageDialog(LoginScreen.this,
                            "Login Successful! Welcome, Admin.",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // Close the login screen




                    UserCart userCart = new UserCart();
                    new AdminDashboard(userCart).setVisible(true);
                } else {
                    errorLabel.setText("Invalid credentials! Please try again.");
                }
            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginScreen().setVisible(true);
            }
        });
    }
}
