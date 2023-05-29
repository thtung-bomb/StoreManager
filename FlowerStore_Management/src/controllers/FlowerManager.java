package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import models.Flower;
    
public final class FlowerManager {
    
    ArrayList<Flower> flowerList;
    private String filePath;

    public FlowerManager() throws ParseException {
        flowerList = loadContentFromFile();
        filePath = "data\\flowers.dat";
    }
    
    public boolean checkIdInList(ArrayList<Flower> list, String id) {
        for (Flower flower : list) {
            if (flower.getFlowerId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    
    public String nameFlower(String id) {
        for (Flower flower : flowerList) {
            if (id.equals(flower.getFlowerId())) {
                return flower.getName();
            }
        }
        return null;
    }
    
    public ArrayList<Flower> loadContentFromFile() throws ParseException {
        ArrayList<Flower> list = new ArrayList<>();
        File file = new File("data\\flowers.dat");
        try ( BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String flowerId = data[0].trim();
                String name = data[1].trim();
                double price = Double.parseDouble(data[2]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
                Date importDate = formatter.parse(data[3]);
                
                list.add(new Flower(flowerId, name, price, importDate));
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }   
    
    public void addFlower(String name, double price, Date date) throws ParseException {
//        loadDataToFlowerlist();
        int number = flowerList.size();
        String id = "";
        if (number >= 0 && number < 10) {
            id = "F00" + number;
        } else if (number < 100) {
            id = "F0" + number;
        } else {
            id = "F" + number;
        }
        flowerList.add(new Flower(id, name, price, date));
    }
    
    public void viewFlowerList() throws ParseException {
        for (int i = 0; i < 108; i++) {
            System.out.print("#");
        }
        System.out.println();
        System.out.printf("#%25s#%25s#%25s#%28s#\n", "id", "name", "price", "importDate");
        for (int i = 0; i < 108; i++) {
            System.out.print("#");
        }
        System.out.println();
        for (Flower flowers : flowerList) {
            System.out.print(flowers.print());
        }
        for (int i = 0; i < 108; i++) {
            System.out.print("#");
        }
        System.out.println();
        System.out.printf("#%82s", " ");
        System.out.printf("TOTAL: " + flowerList.size() + " flower types[s]#\n");
        for (int i = 0; i < 108; i++) {
            System.out.print("#");
        }
        System.out.println();
    }
    
    public void modifyFlower(String id, String fName, double fPrice, Date fDate) throws ParseException {
        if (!checkIdInList(flowerList, id)) {
            System.out.println("This Id does not exist!");
        }
        
        for (Flower flower : flowerList) {
            if (flower.getFlowerId().equals(id)) {
                flower.setName(fName);
                flower.setUnitPrice(fPrice);
                flower.setImportDate(fDate);
            }
        }
        System.out.println("Successfull modify flower");
    }
        
    public void removeFlower(String flowerId) throws ParseException {
        if (checkIdInList(flowerList, flowerId)) {
            Flower foundFlower = null;
            for (Flower flower : flowerList) {
                if (flower.getFlowerId().equals(flowerId)) {
                    foundFlower = flower;
                    break;
                }
            }
            if (foundFlower != null) {
                flowerList.remove(foundFlower);
                System.out.println("Flower removed successfully.");
            } else {
                System.out.println("Flower not found.");
            }
        } else {
            System.out.println("Flower not found.");
        }
    }
        
    //T/F -> save or not       
    public void saveToFile() {
        try {
            
            BufferedWriter bw = new BufferedWriter(new FileWriter("data\\flowers.dat"));

            for (Flower flower : flowerList) {
                String str = "";
                str += flower + "\n";
                bw.write(str);
            }

            bw.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }
    
    public double totalPrice(String id, int quantity) throws ParseException {
        double result = 0;
        for (Flower flower : flowerList) {
            if (flower.getFlowerId().equals(id)) {
                result = flower.getUnitPrice() * quantity;
            }
        }
        return result;
    }
    
}
