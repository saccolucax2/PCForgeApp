package it.unisannio.authorization.application;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import it.unisannio.authorization.data.User;
import it.unisannio.authorization.persistence.UserRepository;
import it.unisannio.authorization.persistence.UserRepositoryMongo;

import java.util.List;

@Service
public class UserService {


    private final UserRepository userRepository = UserRepositoryMongo.getInstance();
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public String createUser(User user) {
            user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.createUser(user).getUsername();

    }

    public List<User> getAllUsers() {
        return userRepository.findall();
    }

    public User getUser(String username) {
        return userRepository.findUser(username);
    }

    public User updateUser(String username, User user) {
        if(user.getPassword()!=null && !user.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        return userRepository.updateUser(username,user);
    }

    public boolean deleteUser(String username) {
        return userRepository.deleteUser(username);
    }

    public boolean authenticateUser(String username, String rawPassword) {
        User user = userRepository.findUser(username);
        if (user == null) return false;

        // Verifica la password con BCrypt
        return encoder.matches(rawPassword, user.getPassword());
    }




}