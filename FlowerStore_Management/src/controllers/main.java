package controllers;

import java.util.Scanner;
import views.CommonMenu;

public class main {

    private Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        CommonMenu cm = new CommonMenu();
        cm.LogIn();
    }

}
