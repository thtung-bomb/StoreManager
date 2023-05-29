package controllers;

import java.text.ParseException;
import java.util.Scanner;

public class Utilities {

    final Scanner sc;

    public Utilities() throws ParseException {
        sc = new Scanner(System.in);
    }

    public String checkToSaveData() {
        String result;
        do {
            System.out.print("Do you want to save data [1/0, Y/N, T/F]: ");
            result = sc.nextLine();
        } while (!(result.equalsIgnoreCase("1") || !result.equals("0") || !result.equals("Y") || !result.equals("y") || !result.equals("t") || !result.equals("T") || !result.equals("F") || !result.equals("f")));
        return result;
    }

}
