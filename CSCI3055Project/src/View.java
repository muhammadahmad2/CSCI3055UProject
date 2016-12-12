/**
 * Created by 100518917 on 12/9/2016.
 */
//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class View {

    public static void main(String[] args) {

        new View();
    }

    public View()
    {
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("ATM");
        guiFrame.setSize(600,650);
        guiFrame.setLocationRelativeTo(null);

        //Login Panel
        final JPanel loginPanel = new JPanel();
        loginPanel.setVisible(true);
        JLabel usernameLabel = new JLabel("Account #:");
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JLabel messageLabel = new JLabel("");

        JButton createAccountButton = new JButton("Create Account");
        JButton loginButton = new JButton("Login");

        loginPanel.add(usernameLabel);
        loginPanel.add(usernameField);
        loginPanel.add(passwordLabel);
        loginPanel.add(passwordField);
        loginPanel.add(messageLabel);
        loginPanel.add(createAccountButton);
        loginPanel.add(loginButton);


        // Options Panel
        final JPanel optionsPanel = new JPanel();
        optionsPanel.setVisible(false);

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferFundsButton = new JButton("Transfer Funds");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton logoutButton = new JButton("Logout");

        optionsPanel.add(checkBalanceButton);
        optionsPanel.add(depositButton);
        optionsPanel.add(withdrawButton);
        optionsPanel.add(transferFundsButton);
        optionsPanel.add(deleteAccountButton);
        optionsPanel.add(logoutButton);



        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                loginPanel.setVisible(false);
                optionsPanel.setVisible(true);

            }
        });


        guiFrame.add(loginPanel, BorderLayout.NORTH);
        guiFrame.add(optionsPanel, BorderLayout.CENTER);
        guiFrame.add(loginButton,BorderLayout.SOUTH);

        guiFrame.setVisible(true);
    }

}
