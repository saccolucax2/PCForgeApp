package it.unisannio.authorization.persistence.mapper;

import org.bson.Document;
import it.unisannio.authorization.data.User;
import it.unisannio.authorization.persistence.UserRepository;

import java.util.function.Function;

public class UserToDocumentMapper implements Function<User, Document> {
    @Override
    public Document apply(User user) {
        Document doc=new Document(
                UserRepository.USERNAME, user.getUsername())
                .append(UserRepository.NAME, user.getName())
                .append(UserRepository.SURNAME, user.getSurname())
                .append(UserRepository.EMAIL,user.getEmail())
                .append(UserRepository.PASSWORD,user.getPassword());

        return doc;
    }
}
