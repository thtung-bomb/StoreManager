package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import models.FlowerCart;
import models.Order;

public class OrderManager {

    private ArrayList<Order> orderList;
    private ArrayList<FlowerCart> cartList;
    private ArrayList<Order> temp;
    Scanner sc;
    FlowerManager flowermanager;
    CustomerManager cm;

    public ArrayList<Order> loadList() {
        return temp = loadOrderFromFile();
    }

    public OrderManager() throws ParseException, IOException {
        orderList = new ArrayList<>();
        cartList = new ArrayList<>();
        flowermanager = new FlowerManager();
        cm = new CustomerManager();
        temp = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    public int checkIdOrder(String id) {
        int size = getSizeOrderList();
        for (int i = 0; i < size; i++) {
            if (id.equals(orderList.get(i).getCustomerId())) {
                return i;
            }
        }
        return -1;
    }

    public int checkIdFlowerCart(String id) {
        for (int i = 0; i < cartList.size(); i++) {
            if (id.equals(cartList.get(i).getFlowerId())) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<FlowerCart> loadCartFlower() {
        ArrayList<FlowerCart> cartFlower = new ArrayList<>();
        File file = new File("data/orders.dat");

        if (!file.exists()) {
            System.out.println("File not found!");
            return cartFlower;
        }

        try ( BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineOrder = line.split(",");
                for (int i = 4; i < lineOrder.length; i++) {
                    String[] fCart = lineOrder[i].trim().split(":");
                    String flowerId = fCart[0].trim();
                    int quantity = Integer.parseInt(fCart[1].trim());
                    double price = Double.parseDouble(fCart[2].trim());

                    cartFlower.add(new FlowerCart(flowerId, quantity, price));
                }
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        return cartFlower;
    }

    public ArrayList<Order> loadOrderFromFile() {
        ArrayList<Order> orders = new ArrayList<>();
        ArrayList<FlowerCart> cartFlower = new ArrayList<>();
        File file = new File("data\\orders.dat");

        if (!file.exists()) {
            System.out.println("File not found!");
            return orders;
        }

        try ( BufferedReader br = new BufferedReader(new FileReader(file))) {

            String line;
            while ((line = br.readLine()) != null) {

                String[] lineOrder = line.split(",");
                String orderId = lineOrder[0].trim();
                String date = lineOrder[1];
                double totalPrice = Double.parseDouble(lineOrder[2].trim());
                String customerId = lineOrder[3].trim();

                for (int i = 4; i < lineOrder.length; i++) {
                    String[] fCart = lineOrder[i].trim().split(":");
                    String flowerId = fCart[0].trim();
                    int quantity = Integer.parseInt(fCart[1].trim());
                    double price = Double.parseDouble(fCart[2].trim());

                    cartFlower.add(new FlowerCart(flowerId, quantity, price));

                }
                orders.add(new Order(orderId, date, totalPrice, customerId, cartFlower));
                cartFlower = new ArrayList<>();
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return orders;
    }

    public void saveCartToFile(ArrayList<Order> listOrderCart) {
        temp.add(listOrderCart.get(0));
    }

    public int getSizeOrderList() {
        this.loadList();
        return temp.size();
    }

    public String autoDate() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        // Define the desired format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        // Format the date and time
        String dateString = currentDateTime.format(formatter);
        return dateString;

    }

    public void addToCart(String accountId, String flowerid, int quantity) throws Exception {//menu user will pass flower id and quantity

        int size = getSizeOrderList();
//        Date date = checkDate();

        Double totalPrice = flowermanager.totalPrice(flowerid, quantity);//get total price (FlowerManager)

        String id = "";
        //print ID order
        if (size == 0) {
            id = "O000";
        } else if (size < 10) {
            id = "O00" + size;
        } else if (size < 100) {
            id = "O0" + size;
        } else {
            id = "O" + size;
        }

        String dateorder = autoDate();

        String nameFlower = flowermanager.nameFlower(flowerid);
        if (!(nameFlower == null)) {

            if (orderList.isEmpty()) {

                //OrderId, date, total, customer id,FlowerId1:quantity1:price1,FlowerId2:quantity2:price2,...
                cartList.add(new FlowerCart(flowerid, quantity, totalPrice));
                orderList.add(new Order(id, dateorder, totalPrice, cm.customerId(accountId), cartList));

            } else if (checkIdOrder(cm.customerId(accountId)) != -1) {

                int orderIndex = checkIdFlowerCart(flowerid);

                if (orderIndex != -1) {

                    quantity += cartList.get(orderIndex).getQuantity();
                    double price = cartList.get(orderIndex).getPrice() + totalPrice;
                    cartList.get(orderIndex).setQuantity(quantity);
                    cartList.get(orderIndex).setPrice(price);

                } else {

                    cartList.add(new FlowerCart(flowerid, quantity, totalPrice));

                }

                orderList.get(checkIdOrder(cm.customerId(accountId))).setCart(cartList);
                totalPrice += orderList.get(checkIdOrder(cm.customerId(accountId))).getTotalPrice();
                orderList.get(checkIdOrder(cm.customerId(accountId))).setTotalPrice(totalPrice);

            }
        } else {

            System.err.println("This flower not exits!");

        }
    }

    public void viewOrderList() {

        if (orderList.isEmpty()) {
            System.err.println("Your order is empty!!!");
            return;
        }

        for (int i = 0; i < 61; i++) {
            System.out.print("#");
        }
        System.out.println();
        System.out.printf("#%18s#%19s#%20s#", "Order ID", "Date", "Buyer ID");
        System.out.println();
        for (int i = 0; i < 61; i++) {
            System.out.print("#");
        }
        System.out.println();
        for (Order order : orderList) {
            System.out.printf("#%18s#%19s#%20s#\n", order.getOrderId(), order.getDate(), order.getCustomerId());

            for (int i = 0; i < 61; i++) {
                System.out.print("#");
            }
            System.out.println();
            System.out.printf("#%18s#%19s#%20s#", "Flower Name", "Quantity", "Price");
            System.out.println();
            for (int i = 0; i < 61; i++) {
                System.out.print("#");
            }
            System.out.println();
            for (FlowerCart flower : order.getCart()) {
                String nameFlower = flowermanager.nameFlower(flower.getFlowerId());
                System.out.printf("#%18s#%19s#%20s#\n", nameFlower, flower.getQuantity(), flower.getPrice());
            }
            for (int i = 0; i < 61; i++) {
                System.out.print("#");
            }
            System.out.println();
            String total = "TOTAL: $" + order.getTotalPrice() + "#";
            System.out.printf("#%60s\n", total);
            for (int i = 0; i < 61; i++) {
                System.out.print("#");
            }
            System.out.println();
        }
    }

    //sort by price
    public void sortByPrice(String type) {
        loadList();
        temp.sort((Order o1, Order o2) -> {
            if (type.equals("asc")) {
                return Double.compare(o1.getTotalPrice(), o2.getTotalPrice());
            } else if (type.equals("dsc")) {
                return Double.compare(o2.getTotalPrice(), o1.getTotalPrice());
            } else {
                return 0;
            }
        });
    }

    //sort by count
    public void sortByCount(String type) {
        loadList();
        temp.sort((Order o1, Order o2) -> {
            int quantity1 = 0;
            int quantity2 = 0;
            for (FlowerCart flower : o1.getCart()) {
                quantity1 += flower.getQuantity();
            }
            for (FlowerCart flower : o2.getCart()) {
                quantity2 += flower.getQuantity();
            }
            if (type.equals("asc")) {
                return quantity1 - quantity2;
            } else {
                return quantity2 - quantity1;
            }
        });
    }

    //sort by date
    public void sortByDate(String type) {
        loadList();
        temp.sort((Order o1, Order o2) -> {
            Date date1 = null;
            Date date2 = null;
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");

            try {

                date1 = format.parse(o1.getDate());
                date2 = format.parse(o2.getDate());

            } catch (ParseException e) {
                System.err.println(e.getMessage());
            }

            switch (type) {
                case "asc":
                    return date1.compareTo(date2);
                case "dsc":
                    return date2.compareTo(date1);
                default:
                    return 0;
            }
        });
    }

    public boolean checkDate(String dateString) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        format.setLenient(false);

        try {
            format.parse(dateString);
            return true;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean checkDateFrom(String dateList, String date) {
        try {
            SimpleDateFormat day = new SimpleDateFormat("yyyy/MM/dd");
            day.setLenient(false);
            return day.parse(dateList).after(day.parse(date));
        } catch (ParseException e) {
            return false;
        }
    }

    public boolean checkDateTo(String dateList, String date) {
        try {
            SimpleDateFormat day = new SimpleDateFormat("yyyy/MM/dd");
            day.setLenient(false);
            return day.parse(dateList).before(day.parse(date));
        } catch (ParseException e) {
            return false;
        }
    }

    public String inputDate() throws Exception {
        String date = "";

        do {
            date = sc.nextLine();
            if (!date.equals("")) {
                boolean check = checkDate(date); // Validate the date only if it's not empty
                if (!check) {
                    System.out.println("Enter the correct format yyyy/MM/dd!");
                }
            }
        } while (!date.equals("") && !checkDate(date)); // Continue loop only if date is not empty and not valid

        return date;

    }

    public void viewSortOrder(String term, String type, String aId) throws Exception {

        loadList();

        int no = 0;

        System.out.println("Enter fromDate (You can ignore by just enter): ");
        String fromDate = inputDate();
        System.out.println("Enter toDate (You can ignore by just enter)");
        String toDate = inputDate();

        switch (term) {
            case "count":
                sortByCount(type);
                break;
            case "date":
                sortByDate(type);
                break;
            case "price":
                sortByPrice(type);
                break;
        }

        for (int i = 0; i < 126; i++) {
            System.out.print("#");//above
        }
        System.out.println();
        System.out.printf("#%18s#%19s#%19s#%19s#%19s#%25s#\n", "No.", "Order Id", "Order date", "Customer", "Flower Count", "Order Total");
        for (int i = 0; i < 126; i++) {
            System.out.print("#");//under
        }
        System.out.println();
        String name;
        for (Order order : temp) {

            if (fromDate.equals("") && toDate.equals("")) {
                name = cm.getName(order.getCustomerId());
                int quantity = 0;
                for (FlowerCart flowerCart : order.getCart()) {
                    quantity += flowerCart.getQuantity();
                }
                System.out.printf("#%18d#%19s#%19s#%19s#%19d#%25s#\n", no, order.getOrderId(), order.getDate(), name, quantity, order.getTotalPrice());
                no++;

            } else if (!fromDate.equals("") && toDate.equals("")) {

                if (checkDateFrom(order.getDate(), fromDate)) {
                    name = cm.getName(order.getCustomerId());
                    int quantity = 0;
                    for (FlowerCart flc : order.getCart()) {
                        quantity += flc.getQuantity();
                    }
                    System.out.printf("#%18d#%19s#%19s#%19s#%19d#%25s#\n", no, order.getOrderId(), order.getDate(), name, quantity, order.getTotalPrice());
                    no++;
                }

            } else if (fromDate.equals("") && !toDate.equals("")) {
                if (checkDateTo(order.getDate(), toDate)) {
                    name = cm.getName(order.getCustomerId());
                    int quantity = 0;
                    for (FlowerCart flc : order.getCart()) {
                        quantity += flc.getQuantity();
                    }
                    System.out.printf("#%18d#%19s#%19s#%19s#%19d#%25s#\n", no, order.getOrderId(), order.getDate(), name, quantity, order.getTotalPrice());
                    no++;
                }
            } else {

                if (checkDateFrom(order.getDate(), fromDate) && checkDateTo(order.getDate(), toDate)) {
                    name = cm.getName(order.getCustomerId());
                    int quantity = 0;
                    for (FlowerCart flc : order.getCart()) {
                        quantity += flc.getQuantity();
                    }
                    System.out.printf("#%18d#%19s#%19s#%19s#%19d#%25s#\n", no, order.getOrderId(), order.getDate(), name, quantity, order.getTotalPrice());
                    no++;
                }

            }
        }

        for (int i = 0;
                i < 126; i++) {
            System.out.print("#");//under
        }
        System.out.println();
    }

    public void saveToFile(ArrayList<Order> list) {
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter("data\\orders.dat"));

            for (Order i : list) {
                String str = "";
                String flc = "";
                for (FlowerCart fl : i.getCart()) {
                    flc += fl + ",";
                }
                str += i.getOrderId() + "," + i.getDate() + "," + i.getTotalPrice() + "," + i.getCustomerId() + "," + flc.substring(0, flc.length() - 1) + "\n";
                bw.write(str);
            }
            
            bw.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public void saveOrderToFile() {
        if (orderList.isEmpty()) {
            System.err.println("Orderlist is empty!");
        } else {
            temp.add(orderList.get(0));
            saveToFile(temp);
            System.out.println("Successfully save data");
        }
    }
    
}
