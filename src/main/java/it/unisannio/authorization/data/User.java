package it.unisannio.authorization.data;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


public class User {

    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;

    public User(String username, String name, String surname, String email, String password) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }


    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "User{" +  ", username='" + username +", name='" + name + ", surname='" + surname + ", email='" + email + ", password='" + password + '}';
    }
}