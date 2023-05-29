package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import models.Customer;
import models.Account;

public class CustomerManager {

    private File f;
    private AccountManager am;
    private String filePath;
    private ArrayList<Account> accountList;
    private List<String> thisCustomer;
    private ArrayList<Customer> listCustomer;

    public CustomerManager() throws IOException {
        am = new AccountManager();
        f = new File("data\\customers.dat");
        accountList = new ArrayList();
        filePath = "data\\accounts.dat";
        thisCustomer = new ArrayList();
        listCustomer = loadFromFile();
    }

    public String customerId(String id) { //order manager use it
        Customer customer = loadFromCustomerFile(f, id);
        if (customer.getcId().equals(id)) {
            return customer.getcId();
        } else {
            System.out.println("Customer not found!");
            return null; // or return a default value as per your requirement
        }
    }

    public Customer loadFromCustomerFile(File file, String id) {
        List<Customer> listCus = new ArrayList<>();
        if (!f.exists()) {
            System.out.println("FILE NOTS EXISTS!");
            return null;
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String cId = data[0].trim();
                String cName = data[1].trim();
                String cAddress = data[2].trim();
                String cPhone = data[3].trim();
                listCus.add(new Customer(cId, cName, cAddress, cPhone));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        for (Customer customer : listCus) {
            if (customer.getcId().equals(id)) {
                return customer; //take a line of customer find by Id, in file customers.dat
            }
        }
        return null;
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
                accountList.add(new Account(accountId, Ipassword, role, cId));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        Account result = null;
        for (Account account : accountList) {
            if (account.getAccountId().equals(id)) {
                result = account;
            }
        }
        return result; //accountId, pw, role, cID
    }

    public String takeCustomerId(String id) throws ParseException { //customer manager use to get ID

        Account account = loadFromAccountFile(id);
        String customer;
        customer = account.getUserId();
        return customer;

    }

    public String getName(String id) throws ParseException {
        String cid = "";
        for (Customer customer : listCustomer) {
            if (id.equals(customer.getcId())) {
                cid = customer.getName();
            }
        }
        return cid;
    }

    public boolean checkExists(String id) throws Exception {
        try ( BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains(id)) {
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public ArrayList<Customer> loadFromFile() throws FileNotFoundException, IOException {

        ArrayList<Customer> list = new ArrayList<>();

        File file = new File("data\\customers.dat");

        if (!file.exists()) {
            System.err.println("File does not exist!");
        }

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String CustomerId = data[0].trim();
                String Name = data[1].trim();
                String Address = data[2].trim();
                String Phone = data[3].trim();

                list.add(new Customer(CustomerId, Name, Address, Phone));

            }

            br.close();

        } catch (FileNotFoundException | NumberFormatException e) {
            System.err.println(e.getMessage());
        }
        return list;
    }

    public void updateProfile(String id, String name, String address, String phone) throws Exception {

        String customerId = takeCustomerId(id);

        for (Customer i : listCustomer) {
            if (i.getcId().equals(customerId)) {
                i.setAddress(address);
                i.setName(name);
                i.setPhone(phone);
            }
        }
        System.out.println("Successfully Update Account!");
    }
    
    public void saveToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("data\\customers.dat"));

            for (Customer i : listCustomer) {
                String str = "";
                str += i + "\n";
                bw.write(str);
            }
            bw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public static void main(String[] args) throws IOException, Exception {
        CustomerManager cm = new CustomerManager();
        cm.updateProfile("A000", "Thanh Tùng", "Xuyên Mộc", "096666666");
        cm.saveToFile();
    }

}
