package views;

import controllers.CustomerManager;
import controllers.FlowerManager;
import controllers.OrderManager;
import controllers.Utilities;
import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class CustomerMenu {

    FlowerManager fm;
    Scanner sc = new Scanner(System.in);
    CommonMenu cm;
    OrderManager o;
    CustomerManager csm;
    Utilities u;

    public CustomerMenu() throws ParseException, IOException {
        fm = new FlowerManager();
        cm = new CommonMenu();
        o = new OrderManager();
        csm = new CustomerManager();
        u = new Utilities();
    }

    public void MenuOfCustomers(String aid, String password) throws Exception {
        int choice;
        boolean flag = true;
        do {
            try {
                System.out.println("USER MENU:");
                System.out.print(
                        "1-Update Profile\n"
                        + "2-View Flower List\n"
                        + "3-Add Flower to Cart\n"
                        + "4-View Order\n"
                        + "5-Cancel Order\n"
                        + "6-Quit\n"
                        + "Enter your choice:");
                choice = Integer.parseInt(sc.nextLine());
                try {

                } catch (Exception e) {
                }
                switch (choice) {

                    case 1:
                        String iName;
                        String address;
                        String number;
                        System.out.print("Enter name: ");
                        iName = sc.nextLine();
                        System.out.print("Enter address: ");
                        address = sc.nextLine();
                        System.out.print("Enter phone number: ");
                        number = sc.nextLine();

                        csm.updateProfile(aid, iName, address, number);
                        break;

                    case 2:
                        fm.viewFlowerList();
                        break;

                    case 3:
                        String flowerId;
                        int quantity;
                        do {
                            System.out.print("Enter flower ID FXXX: ");
                            flowerId = sc.nextLine();
                        } while (!flowerId.matches("^F\\d{3}$"));
                        do {
                            System.out.print("Enter quantities: ");
                            quantity = Integer.parseInt(sc.nextLine());
                        } while (quantity == 0);

                        String cId = csm.takeCustomerId(aid);
                        o.addToCart(cId, flowerId, quantity);
                        break;

                    case 4:
                        o.viewOrderList();
                        break;

                    case 5:
                        System.out.println("Cancel orders!");
                        break;

                    case 6:
                        flag = false;
                        String result = u.checkToSaveData();
                        if (result.equals("T") || result.equals("Y") || result.equals("1") || result.equals("y") || result.equals("t")) {
                            o.saveOrderToFile();
                            csm.saveToFile();
                        }
                        System.out.println("Successfully logout");

                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");

                }

            } catch (NumberFormatException | ParseException e) {
                System.out.println(e.getMessage());
            }
        } while (flag);
        cm.toContinue();
    }

}
