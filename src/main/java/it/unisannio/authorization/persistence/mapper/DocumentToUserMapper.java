package it.unisannio.authorization.persistence.mapper;

import org.bson.Document;
import it.unisannio.authorization.data.User;
import it.unisannio.authorization.persistence.UserRepository;

import java.util.function.Function;

public class DocumentToUserMapper implements Function<Document, User> {
    @Override
    public User apply(Document document) {
        User user = new User(
                document.get(UserRepository.USERNAME, String.class),
                document.get(UserRepository.NAME,String.class),
                document.get(UserRepository.SURNAME,String.class),
                document.get(UserRepository.EMAIL,String.class),
                document.get(UserRepository.PASSWORD,String.class)

        );


        return user;

    }
}
