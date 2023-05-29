package models;
    
public class Staff {
    
    private String staffId;
    private String name;
    private String address;
    private String phone;
    
    public Staff() {
    }
    
    public Staff(String staffId, String name, String address, String phone) {
        this.staffId = staffId;
        this.name = name;
        this.address = address;
        this.setPhone(phone);
    }
    
    public String getStaffId() {
        return staffId;
    }
    
    public void setStaffId(String staffId) {
        this.staffId = staffId;
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
        if (phone.matches("\\d{11}") || phone.matches("\\d{9}")) {
            this.phone = phone;
        }
    }
    
    @Override
    public String toString() {
        return staffId + ", " + name + ", " + address + ", " + phone;
    }
    
}
