package it.unisannio.authorization.data;

public class User {

    private final String username;
    private final String name;
    private final String surname;
    private final String email;
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