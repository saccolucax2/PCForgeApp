package it.unisannio.authorization.persistence;

import it.unisannio.authorization.data.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    static String USERNAME = "username";
    static String NAME = "name";
    static String SURNAME= "surname";
    static String EMAIL = "email";
    static String PASSWORD = "password";
    static String DB = "accounting";
    static String COLLECTION = "users";

    public User createUser(User user);
    public User findUser(String username);
    public User updateUser(String username,User user);
    public boolean deleteUser(String username);
    public User findUserByEmail(String email);

    public List<User> findall();
}
