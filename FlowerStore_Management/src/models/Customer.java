package models;

public class Customer {

    private String cId;
    private String name;
    private String address;
    private String phone;

    public Customer() {
    }
    
    public Customer(String cId, String name, String address, String phone) {
        this.cId = cId;
        this.name = name;
        this.address = address;
        this.setPhone(phone);
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone.matches("\\d{9}") || phone.matches("\\d{11}")) {
            this.phone = phone;
        }
    }
    
    @Override
    public String toString() {
        return cId + ", " + name + ", " + address + ", " + phone;
    }
    
}
