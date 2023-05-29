package models;

import java.util.ArrayList;

public class Order {

    private String orderId;
    private String date;
    private double totalPrice;
    private String customerId;
    ArrayList<FlowerCart> cart;

    public Order(String orderId, String date, double totalPrice, String customerId, ArrayList<FlowerCart> cart) {
        this.orderId = orderId;
        this.date = date;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.cart = cart;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getDate() {
        return date;
    }
    
    public void setDate(String date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public ArrayList<FlowerCart> getCart() {
        return cart;
    }

    public void setCart(ArrayList<FlowerCart> cart) {
        this.cart = cart;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(orderId).append(", ").append(date).append(", ").append(totalPrice).append(", ").append(customerId).append(", ");

        for (int i = 0; i < cart.size(); i++) {
            sb.append(cart.get(i).toString());
            if (i != cart.size() - 1) {
                sb.append(",");
            }
        }

        return sb.toString();
    }
}
