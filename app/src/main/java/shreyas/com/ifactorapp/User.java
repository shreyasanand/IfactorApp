package shreyas.com.ifactorapp;

import java.io.Serializable;

/**
 * Created by shreyas on 4/22/2016.
 */
public class User implements Serializable {

    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;

    public User(int id, String name, String username, String email, Address address){
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
