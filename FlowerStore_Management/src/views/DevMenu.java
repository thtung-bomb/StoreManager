package views;

import controllers.FlowerManager;
import controllers.OrderManager;
import controllers.StaffManager;
import controllers.Utilities;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class DevMenu {

    FlowerManager fm;
    final Scanner sc;
    boolean flag = true;
    OrderManager om;
    StaffManager sm;
    Utilities u;

    public DevMenu() throws ParseException, IOException {
        om = new OrderManager();
        fm = new FlowerManager();
        sc = new Scanner(System.in);
        sm = new StaffManager();
        u = new Utilities();
    }

    public void MenuOfDev(String aid, String password) throws Exception {
        int choice;
        do {
            try {
                System.out.println("DEV MENU:");
                System.out.print(
                        "1-Update Profile\n"
                        + "2-View Flower List\n"
                        + "3-Add Flower\n"
                        + "4-Modify Flower\n"
                        + "5-Remove Flower\n"
                        + "6-View Sorted Orders\n"
                        + "7-Remove Order\n"
                        + "8-Quit\n"
                        + "Enter your choice:");
                choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    
                    case 1:
                        String iName;
                        String address;
                        String number;
                        do {
                            System.out.print("Enter name: ");
                            iName = sc.nextLine();
                        } while (iName.equals(""));
                        do {
                            System.out.print("Enter address: ");
                            address = sc.nextLine();
                        } while (address.equals(""));
                        do {
                            System.out.print("Enter phone number: ");
                            number = sc.nextLine();
                        } while (!number.matches("^\\d{9}") && !number.matches("^\\d{11}"));

                        sm.updateProfile(aid, iName, address, number);
                        break;
                        
                    case 2://print list of flower
                        fm.viewFlowerList();
                        break;
                        
                    case 3://add flower
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                        System.out.print("Enter name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter price: ");
                        double price = Double.parseDouble(sc.nextLine());
                        System.out.print("Enter date: ");
                        Date date = formatter.parse(sc.nextLine());
                        fm.addFlower(name, price, date);
                        break;

                    case 4://Modify Flower
                        String fId;
                        String fName;
                        double fPrice;
                        Date fDate;
                        SimpleDateFormat format = new SimpleDateFormat("yyyy/mm/dd");
                        do {
                            System.out.print("Enter flower ID FXXX: ");
                            fId = sc.nextLine();
                        } while (!fId.matches("F\\d{3}"));
                        do {
                            System.out.print("Enter name: ");
                            fName = sc.nextLine();
                        } while (fName.equals(""));
                        do {
                            System.out.print("Enter price: ");
                            fPrice = Double.parseDouble(sc.nextLine());
                        } while (fPrice <= 0);
                        System.out.print("Enter date: ");
                        fDate = format.parse(sc.nextLine());

                        fm.modifyFlower(fId, fName, fPrice, fDate);
                        break;

                    case 5://Remove Flower
                        String flowerId;
                        do {
                            System.out.print("Enter flowerId FXXX: ");
                            flowerId = sc.nextLine();
                        } while (!flowerId.matches("F\\d{3}"));
                        fm.removeFlower(flowerId);
                        break;

                    case 6:
                        String term,
                         type;
                        do {
                            System.out.print("Enter sort term (count, date, or price): ");
                            term = sc.nextLine();
                        } while (!term.equals("count") && !term.equals("date") && !term.equals("price"));

                        do {
                            System.out.print("Enter type(asc/dsc): ");
                            type = sc.nextLine();
                        } while (!type.equals("asc") && !type.equals("dsc"));

                        om.viewSortOrder(term, type, aid);
                        break;
                        
                    case 7:
                        System.out.println("Removed Order !");
                        break;
                        
                    case 8:
                        flag = false;
                        String result = u.checkToSaveData();
                        if (result.equals("T") || result.equals("Y") || result.equals("1") || result.equals("y") || result.equals("t")) {
                            fm.saveToFile();
                            sm.saveStafftoFile();
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

        CommonMenu cm = new CommonMenu();
        cm.toContinue();

    }

    public static void main(String[] args) throws Exception {
        DevMenu d = new DevMenu();
        d.MenuOfDev("A004", "DEV@A004");
    }

}
