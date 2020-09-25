package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Customer {

    private int id;
    private String name;
    private String address;
    private String phone;

    public Customer(int id, String name, String address, String phone) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
     public String getPhone() {
        return phone;
     }

     public void setId(int id) {
        this.id = id;
     }

     public void setName(String name) {
        this.name = name;
     }

     public void setAddress(String address) {
        this.address = address;
     }

     public void setPhone(String phone) {
        this.phone = phone;
     }

}
