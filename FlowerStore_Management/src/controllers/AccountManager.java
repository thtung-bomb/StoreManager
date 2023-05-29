package controllers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import models.Account;
import models.Staff;
import views.CommonMenu;
import views.CustomerMenu;
import views.DevMenu;

public class AccountManager {

    private final List<Account> contentList;
    private final CommonMenu cm;
    private String filePath;

    public AccountManager() {
        cm = new CommonMenu();
        this.contentList = new ArrayList<>();
        filePath = "data\\accounts.dat";
    }

    public boolean checkId(String id) {
        return id.matches("A[0-9]{3}");
    }

    public boolean checkPassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=])(?=.*[!-~]).{8,}$";
        return password.matches(regex);
    }

    public void checkAccounts(File f, String id, String password) throws Exception {
        List<String[]> dataArray = new ArrayList<>();
        CustomerMenu user = new CustomerMenu();
        DevMenu dev = new DevMenu();

        if (!f.exists()) {
            System.out.println(f + " not exits");
            return;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                dataArray.add(data);
            }
        } catch (IOException e) {
            e.getStackTrace();
        }

        boolean idFound = false;
        boolean passwordFound = false;

        for (String[] data : dataArray) {
            String accountId = data[0].trim();
            String pw = data[1].trim();

            if (accountId.equals(id)) {
                idFound = true;
                if (pw.equals(password)) {
                    passwordFound = true;

                    System.out.println("Succesful login!");

                    if (data[2].equals("USER")) {
                        user.MenuOfCustomers(data[0], data[1]);
                    } else if (data[2].equals("DEV")) {
                        dev.MenuOfDev(data[0], data[1]);
                    }
                    break; // Exit the loop once a match is found
                }
            }
        }

        if (!idFound || !passwordFound) {
            System.out.println("Invalid AccountId or Password! Please try again!");
            cm.LogIn();
        }

    }

    public Account loadFromAccountFile(String id) throws ParseException {
        try ( BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String accountId = data[0].trim();
                String Ipassword = data[1].trim();
                String role = data[2].trim();
                String cId = data[3].trim();
                contentList.add(new Account(accountId, Ipassword, cId, role));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Account result = null;
        for (Account account : contentList) {
            if (account.getUserId().equals(id)) {
                result = account;
            }
        }
        return result; //accountId, pw, role, cID
    }
    
}
