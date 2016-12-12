/**
 * Created by 100518917 on 12/9/2016.
 */
//Imports are listed in full to show what's being used
//could just import javax.swing.* and java.awt.* etc..
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
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
        JButton passwordButton = new JButton("Change password");
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
        JTextField depositAmount = new JTextField(20);
        JButton depositConfirmButton = new JButton("Confirm");

        depositPanel.add(depositLabel);
        depositPanel.add(depositAmount);
        depositPanel.add(depositConfirmButton);


        // Withdraw Panel
        final JPanel withdrawPanel = new JPanel();
        withdrawPanel.setLayout(new BoxLayout(withdrawPanel, BoxLayout.Y_AXIS));
        withdrawPanel.setVisible(false);

        JLabel withdrawLabel = new JLabel("Withdrawal amount:");
        JTextField withdrawAmount = new JTextField(20);
        JButton withdrawConfirmButton = new JButton("Confirm");

        withdrawPanel.add(withdrawLabel);
        withdrawPanel.add(withdrawAmount);
        withdrawPanel.add(withdrawConfirmButton);


        // Transfer Panel
        final JPanel transferPanel = new JPanel();
        transferPanel.setLayout(new BoxLayout(transferPanel, BoxLayout.Y_AXIS));
        transferPanel.setVisible(false);

        JLabel transferAccountLabel = new JLabel("Transfer to account:");
        JTextField transferAccount = new JTextField(20);
        JLabel transferLabel = new JLabel("Transfer amount:");
        JTextField transferAmount = new JTextField(20);
        JButton transferConfirmButton = new JButton("Confirm");

        transferPanel.add(transferAccountLabel);
        transferPanel.add(transferAccount);
        transferPanel.add(transferLabel);
        transferPanel.add(transferAmount);
        transferPanel.add(transferConfirmButton);

        // Transfer Panel
        final JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.Y_AXIS));
        passwordPanel.setVisible(false);

        JLabel oldPasswordLabel = new JLabel("Old Password:");
        JPasswordField oldPassword = new JPasswordField(20);
        JLabel passwordChangeLabel = new JLabel("New Password:");
        JPasswordField passwordChange = new JPasswordField(20);
        JButton passwordConfirmButton = new JButton("Confirm");

        passwordPanel.add(oldPasswordLabel);
        passwordPanel.add(oldPassword);
        passwordPanel.add(passwordChangeLabel);
        passwordPanel.add(passwordChange);
        passwordPanel.add(passwordConfirmButton);


        loginButton.addActionListener(e ->{
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
                            messageLabel.setText("");
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
        });

        createAccountButton.addActionListener(e ->{
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
                        messageLabel.setText("");
                        break;
                    }
                }


        });

        checkBalanceButton.addActionListener(e ->{
                infoLabel.setText(String.valueOf(getAccountBalance(currentAccount)));

        });

        depositButton.addActionListener(e ->{
                optionsPanel.setVisible(false);
                depositPanel.setVisible(true);

        });

        depositConfirmButton.addActionListener(e ->{
                setAccountBalance(currentAccount,getAccountBalance(currentAccount)+Double.parseDouble(depositAmount.getText()));
                infoLabel.setText("Deposit confirmed: " + depositAmount.getText());
                optionsPanel.setVisible(true);
                depositPanel.setVisible(false);

        });

        withdrawButton.addActionListener(e ->{
                optionsPanel.setVisible(false);
                withdrawPanel.setVisible(true);

        });

        withdrawConfirmButton.addActionListener(e ->{
                setAccountBalance(currentAccount,getAccountBalance(currentAccount)-Double.parseDouble(withdrawAmount.getText()));
                infoLabel.setText("Withdrawal confirmed: " + withdrawAmount.getText());
                optionsPanel.setVisible(true);
                withdrawPanel.setVisible(false);

        });

        transferFundsButton.addActionListener(e ->{
                optionsPanel.setVisible(false);
                transferPanel.setVisible(true);

        });

        transferConfirmButton.addActionListener(e ->{
                setAccountBalance(currentAccount,getAccountBalance(currentAccount)-Double.parseDouble(transferAmount.getText()));
                setAccountBalance(transferAccount.getText(),getAccountBalance(transferAccount.getText())+Double.parseDouble(transferAmount.getText()));
                infoLabel.setText("Withdrawal confirmed to account: " + transferAccount.getText() + " in amount: " + transferAmount.getText());
                optionsPanel.setVisible(true);
                transferPanel.setVisible(false);

        });

        passwordButton.addActionListener(e ->{
                optionsPanel.setVisible(false);
                passwordPanel.setVisible(true);

        });

        passwordConfirmButton.addActionListener(e ->{
                for (int i = 0; i < accountList.size(); i++) {
                    if (accountList.get(i).accountNumber.equals(currentAccount)) {
                        if (oldPassword.getText().equals(accountList.get(i).password)) {
                            setCurrentAccountPassword(passwordChange.getText());
                            infoLabel.setText("Password changed");
                            break;
                        }
                        else {
                            infoLabel.setText("Old password incorrect;");
                            break;
                        }
                    }
                }

                optionsPanel.setVisible(true);
                transferPanel.setVisible(false);


        });

        deleteAccountButton.addActionListener(e ->{
                infoLabel.setText("Account closed. Amount: " + getAccountBalance(currentAccount));
                setAccountBalance(currentAccount,0);
                for (int i = 0; i < accountList.size(); i++) {
                    if (accountList.get(i).accountNumber.equals(currentAccount)) {
                        accountList.remove(i);
                    }
                }
                saveAccounts();
                currentAccount = "";
                infoLabel.setText("");
                optionsPanel.setVisible(false);
                loginPanel.setVisible(true);

        });
        logoutButton.addActionListener(e ->{
                saveAccounts();
                currentAccount = "";
                infoLabel.setText("");
                optionsPanel.setVisible(false);
                loginPanel.setVisible(true);


        });

        guiFrame.setLayout(null);
        guiFrame.add(loginPanel);
        loginPanel.setBounds(0,0,guiFrame.getWidth(),150);

        guiFrame.add(optionsPanel);
        optionsPanel.setBounds(0,0,guiFrame.getWidth(),guiFrame.getHeight());

        guiFrame.add(depositPanel);

        depositPanel.setBounds(0,0,guiFrame.getWidth(),100);
        guiFrame.add(withdrawPanel);

        withdrawPanel.setBounds(0,0,guiFrame.getWidth(),100);
        guiFrame.add(transferPanel);

        transferPanel.setBounds(0,0,guiFrame.getWidth(),120);
        guiFrame.add(passwordPanel);
        passwordPanel.setBounds(0,0,guiFrame.getWidth(),100);


        guiFrame.setVisible(true);
    }

    public void loadAccounts() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {

            String sCurrentLine;

            while ((sCurrentLine = br.readLine()) != null) {
                Scanner scanner = new Scanner(sCurrentLine);
                scanner.useDelimiter(" ");

                while (scanner.hasNext()) {
                    accountList.add(new Account(scanner.next(),scanner.next(),Double.parseDouble(scanner.next())));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public double getAccountBalance (String number) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).accountNumber.equals(number)) {
                return accountList.get(i).amount;
            }
        }
        return 0;
    }
    public void setAccountBalance (String number, double amount) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).accountNumber.equals(number)) {
                accountList.get(i).amount = amount;
            }
        }
    }
    public void setCurrentAccountPassword (String password) {
        for (int i = 0; i < accountList.size(); i++) {
            if (accountList.get(i).accountNumber.equals(currentAccount)) {
                accountList.get(i).password = password;
            }
        }
    }

    public void saveAccounts () {
        try{
            PrintWriter writer = new PrintWriter(FILENAME);
            for (int i = 0; i < accountList.size(); i++) {
                writer.println(accountList.get(i).accountNumber + " " + accountList.get(i).password + " " + String.valueOf(accountList.get(i).amount) + " ");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
