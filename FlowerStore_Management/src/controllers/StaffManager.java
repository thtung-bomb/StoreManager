package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import models.Account;
import models.Staff;

public class StaffManager {

    private final File file = new File("data\\staffs.dat");
//    String filePath = "data\\";
    private final AccountManager am;
    private ArrayList<Staff> staffList;
    private final CustomerManager cm;

    public StaffManager() throws IOException {
        am = new AccountManager();
        staffList = loadFromStaffFile();
        cm = new CustomerManager();
    }

    public ArrayList<Staff> loadFromStaffFile() {

        File file = new File("data\\staffs.dat");

        ArrayList<Staff> listStaff = new ArrayList<>();

        if (!file.exists()) {
            System.out.println("FILE NOTS EXISTS!");
        }

        try {

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                String staffId = data[0].trim();
                String staffName = data[1].trim();
                String staffAddress = data[2].trim();
                String staffPhone = data[3].trim();

                listStaff.add(new Staff(staffId, staffName, staffAddress, staffPhone));

            }

            br.close();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return (ArrayList<Staff>) listStaff;

    }

    public boolean checkExists(String id) throws Exception {
        try ( BufferedReader br = new BufferedReader(new FileReader(file))) {
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

    public String takeStaffId(String id) throws ParseException { //customer manager use to get ID

        Account account = cm.loadFromAccountFile(id);
        String staff;
        staff = account.getUserId();
        return staff;

    }

    public ArrayList<Staff> updateProfile(String id, String name, String address, String phone) throws ParseException, Exception {

        String staffId = takeStaffId(id);

        if (!checkExists(staffId)) {
            System.out.println("Profile with ID " + staffId + " does not exist.");
            return null;
        }

        for (Staff staff : staffList) {
            if (staff.getStaffId().equals(staffId)) {
                staff.setName(name);
                staff.setAddress(address);
                staff.setPhone(phone);
            }
        }
        System.out.println("Successfully Update Account");
        return staffList;
    }

    public void saveStafftoFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));

            for (Staff staff : staffList) {
                String str = "";
                str += staff;
                str += "\n";
                bw.write(str);
            }
            bw.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException, Exception {
        StaffManager sm = new StaffManager();
        sm.loadFromStaffFile();
        System.out.println(sm.updateProfile("A004", "Tung", "ddddd", "111222333"));
        for (Staff staff : sm.staffList) {
            System.out.println(staff);
        }
        sm.saveStafftoFile();
    }

}
