/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package businessLogic;

import javafx.beans.property.SimpleStringProperty;

/**
 * This is the User JavaBean class to hold user information, where information
 * is retrieved/updated from/into USERS database
 *
 * @author Timothy
 */
public class User {

    public User(String firstName, String lastName, String email, String password, String address) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.address = new SimpleStringProperty(address);
    }

    public User() {
        this.firstName = new SimpleStringProperty();
        this.lastName = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
    }

    private SimpleStringProperty firstName;
    private SimpleStringProperty lastName;
    private SimpleStringProperty email;
    private SimpleStringProperty password;
    private SimpleStringProperty address;

    public String getFirstName() {
        return firstName.get();
    }

    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public String getLastName() {
        return lastName.get();
    }

    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public String getEmail() {
        return email.get();
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public String getAddress() {
        return address.get();
    }

    public void setAddress(String address) {
        this.address.set(address);
    }
}
