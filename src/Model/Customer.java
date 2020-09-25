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

}
