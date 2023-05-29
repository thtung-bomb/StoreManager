/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author Administrator
 */
public class Flower {

    private String FlowerId;
    private String name;
    private double unitPrice;
    private Date importDate;

    public Flower(String flowerID, String name1, double unitPrice1, LocalDate importDate1) {
    }

    public Flower(String FlowerId, String name, double unitPrice, Date importDate) {
        this.FlowerId = FlowerId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.importDate = importDate;
    }

    public String getFlowerId() {
        return FlowerId;
    }

    public void setFlowerId(String FlowerId) {
        this.FlowerId = FlowerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Date getImportDate() {
        return importDate;
    }

    public void setImportDate(Date importDate) {
        this.importDate = importDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return String.format(Locale.ROOT, "%s,%s,%.2f,%s", FlowerId, name, unitPrice, formatter.format(importDate));
    }

    public String print() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        return String.format(Locale.ROOT, "#%25s#%25s#%25.2f#%28s#\n", FlowerId, name, unitPrice, formatter.format(importDate));
    }
}
