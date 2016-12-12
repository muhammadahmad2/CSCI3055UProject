/**
 * Created by 100518917 on 12/9/2016.
 */
//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class View {

    private static final String FILENAME = "./src/accounts.txt";

    String currentAccount;

    List<Account> accountList = new ArrayList<Account>();

    class Account{
        String accountNumber;
        String password;
        double amount;
        Account (String accountNumber, String password,double amount) {
            this.accountNumber = accountNumber;
            this.password = password;
            this.amount = amount;
        }
    }

    public static void main(String[] args) {
        new View();
    }

    public View() {
        loadAccounts();
        JFrame guiFrame = new JFrame();
        guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiFrame.setTitle("ATM");
        guiFrame.setSize(400,450);
        guiFrame.setLocationRelativeTo(null);

        //Login Panel
        final JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setVisible(true);
        JLabel usernameLabel = new JLabel("Account #:");
        JTextField usernameField = new JTextField(10);
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
        optionsPanel.setLayout(new BoxLayout(optionsPanel, BoxLayout.Y_AXIS));
        optionsPanel.setVisible(false);


        JLabel topLabel = new JLabel("");
        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transferFundsButton = new JButton("Transfer Funds");
        JButton deleteAccountButton = new JButton("Delete Account");
        JButton logoutButton = new JButton("Logout");
        JLabel infoLabel = new JLabel("");

        optionsPanel.add(topLabel);
        optionsPanel.add(checkBalanceButton);
        optionsPanel.add(depositButton);
        optionsPanel.add(withdrawButton);
        optionsPanel.add(transferFundsButton);
        optionsPanel.add(deleteAccountButton);
        optionsPanel.add(logoutButton);
        optionsPanel.add(infoLabel);

        // Deposit Panel
        final JPanel depositPanel = new JPanel();
        depositPanel.setLayout(new BoxLayout(depositPanel, BoxLayout.Y_AXIS));
        depositPanel.setVisible(false);

        JLabel depositLabel = new JLabel("Deposit amount:");
        JTextField depositAmount = new JTextField();
        JButton depositConfirmButton = new JButton("Confirm");

        depositPanel.add(depositLabel);
        depositPanel.add(depositAmount);
        depositPanel.add(depositConfirmButton);


        // Withdraw Panel
        final JPanel withdrawPanel = new JPanel();
        withdrawPanel.setLayout(new BoxLayout(withdrawPanel, BoxLayout.Y_AXIS));
        withdrawPanel.setVisible(false);

        JLabel withdrawLabel = new JLabel("Withdrawal amount:");
        JTextField withdrawAmount = new JTextField();
        JButton withdrawConfirmButton = new JButton("Confirm");

        withdrawPanel.add(withdrawLabel);
        withdrawPanel.add(withdrawAmount);
        withdrawPanel.add(withdrawConfirmButton);


        // Tranfer Panel
        final JPanel transferPanel = new JPanel();
        transferPanel.setLayout(new BoxLayout(transferPanel, BoxLayout.Y_AXIS));
        transferPanel.setVisible(false);

        JLabel transferAccountLabel = new JLabel("Transfer to account:");
        JTextField transferAccount = new JTextField();
        JLabel transferLabel = new JLabel("Transfer amount:");
        JTextField transferAmount = new JTextField();
        JButton transferConfirmButton = new JButton("Confirm");

        transferPanel.add(transferAccountLabel);
        transferPanel.add(transferAccount);
        transferPanel.add(transferLabel);
        transferPanel.add(transferAmount);
        transferPanel.add(transferConfirmButton);


        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                boolean found = false;
                for (int i = 0; i < accountList.size(); i++) {
                    Account a = accountList.get(i);
                    if (usernameField.getText().equals(a.accountNumber)){
                        found = true;
                        if (passwordField.getText().equals(a.password)) {
                            currentAccount = a.accountNumber;
                            loginPanel.setVisible(false);
                            optionsPanel.setVisible(true);
                            topLabel.setText(currentAccount);
                            break;
                        }
                        else {
                            messageLabel.setText("Wrong password");
                            break;
                        }
                    }
                }
                if (!found) {
                    messageLabel.setText("Account does not exist. Create new account");
                }



            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                for (int i = 0; i < accountList.size(); i++) {
                    Account a = accountList.get(i);
                    if (usernameField.getText().equals(a.accountNumber)){
                        messageLabel.setText("Account already exists");
                        break;
                    }
                    else {
                        accountList.add(new Account(usernameField.getText(), passwordField.getText(),0));
                        currentAccount = usernameField.getText();
                        loginPanel.setVisible(false);
                        optionsPanel.setVisible(true);
                        topLabel.setText(currentAccount);
                        break;
                    }
                }

            }
        });

        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                infoLabel.setText(String.valueOf(getAccountBalance(currentAccount)));
            }
        });

        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                optionsPanel.setVisible(false);
                depositPanel.setVisible(true);
            }
        });

        depositConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                setAccountBalance(currentAccount,getAccountBalance(currentAccount)+Double.parseDouble(depositAmount.getText()));
                infoLabel.setText("Deposit confirmed: " + depositAmount.getText());
                optionsPanel.setVisible(true);
                depositPanel.setVisible(false);
            }
        });

        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                optionsPanel.setVisible(false);
                withdrawPanel.setVisible(true);
            }
        });

        withdrawConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                setAccountBalance(currentAccount,getAccountBalance(currentAccount)-Double.parseDouble(withdrawAmount.getText()));
                infoLabel.setText("Withdrawal confirmed: " + withdrawAmount.getText());
                optionsPanel.setVisible(true);
                withdrawPanel.setVisible(false);
            }
        });

        transferFundsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                optionsPanel.setVisible(false);
                transferPanel.setVisible(true);
            }
        });

        transferConfirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                setAccountBalance(currentAccount,getAccountBalance(currentAccount)-Double.parseDouble(transferAmount.getText()));
                setAccountBalance(transferAccount.getText(),getAccountBalance(transferAccount.getText())+Double.parseDouble(transferAmount.getText()));
                infoLabel.setText("Withdrawal confirmed to account: " + transferAccount.getText() + " in amount: " + transferAmount.getText());
                optionsPanel.setVisible(true);
                transferPanel.setVisible(false);
            }
        });

        deleteAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                setAccountBalance(currentAccount,0);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event)
            {
                currentAccount = "";
                optionsPanel.setVisible(false);
                loginPanel.setVisible(true);

            }
        });


        guiFrame.add(loginPanel, BorderLayout.NORTH);
        guiFrame.add(optionsPanel, BorderLayout.CENTER);
        guiFrame.add(depositPanel, BorderLayout.CENTER);
        guiFrame.add(withdrawPanel, BorderLayout.CENTER);


        guiFrame.setVisible(true);
    }

    public void loadAccounts() {
        try {
            Scanner scanner = new Scanner(new File(FILENAME));
            scanner.useDelimiter(" ");

            while (scanner.hasNext()) {
                accountList.add(new Account(scanner.next(),scanner.next(),Double.parseDouble(scanner.next())));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double getAccountBalance (String number) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).accountNumber. equals(number)) {
                return accountList.get(i).amount;
            }
        }
        return 0;
    }
    public void setAccountBalance (String number, double amount) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).accountNumber. equals(number)) {
                accountList.get(i).amount = amount;
            }
        }
    }
    public void setCurrentAccounPassword (String password) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).accountNumber. equals(currentAccount)) {
                accountList.get(i).password = password;
            }
        }
    }

}
