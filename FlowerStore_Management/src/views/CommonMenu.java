package views;

import controllers.AccountManager;
import java.io.File;
import java.util.Scanner;

public class CommonMenu {

    public void LogIn() throws Exception {
        AccountManager am = new AccountManager();
        String id;
        String password;
        boolean flag = true;
        File f = new File("data\\accounts.dat");
        Scanner sc = new Scanner(System.in);

        do {
            System.out.print("Enter id AXXX: ");
            id = sc.nextLine();
            if (am.checkId(id) == false) {
                System.out.println("Please input id start with A and followed by exactly 3 digits.!");
            }
        } while (am.checkId(id) == false);

        do {
            System.out.print("Enter password: ");
            password = sc.nextLine();
            if (am.checkPassword(password) == false) {
                System.out.println("Password must be at least 8 characters comrpised by at least one character, one digit, and one special symbol");
            }
        } while (am.checkPassword(password) == false);
        am.checkAccounts(f, id, password);
    }

    public void toContinue() throws Exception {
        Scanner sc = new Scanner(System.in);
        String op;
        boolean flag = true;
        do {
            System.out.println("\nDo you want to continues login!");
            System.out.print("yes/no - y/n: ");
            op = sc.nextLine();
            if (op.equalsIgnoreCase("yes") || op.equalsIgnoreCase("y")) {
                CommonMenu cm = new CommonMenu();
                cm.LogIn();
                flag = false;
            } else if (op.equalsIgnoreCase("no") || op.equalsIgnoreCase("n")) {
                System.out.println("Logged out!");
                flag = false;
            }
        } while (flag);
    }

}
