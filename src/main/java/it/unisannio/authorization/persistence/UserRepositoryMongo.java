package it.unisannio.authorization.persistence;

import com.mongodb.client.model.IndexOptions;
import it.unisannio.authorization.data.User;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import it.unisannio.authorization.persistence.mapper.DocumentToUserMapper;
import it.unisannio.authorization.persistence.mapper.UserToDocumentMapper;
import it.unisannio.authorization.exception.UserNotFoundException;
import org.bson.Document;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.mongodb.client.model.Filters.eq;

public class UserRepositoryMongo implements UserRepository {
    private final MongoClient mongoClient;
    private final Function<User, Document> userToDocumentMapper=new UserToDocumentMapper();
    private final Function<Document,User> documentToUserMapper=new DocumentToUserMapper();

    private static UserRepositoryMongo INSTANCE;

    private UserRepositoryMongo() {
        String host =System.getenv("MONGO_ADDRESS")!=null?
                System.getenv("MONGO_ADDRESS"):
                "localhost";
        String port = System.getenv("MONGO_PORT")!=null?
                System.getenv("MONGO_PORT"):
                "27017";
        mongoClient = MongoClients.create("mongodb://" + host + ":" + port);
        getUserCollection().createIndex(new Document("username", 1),
                new IndexOptions().unique(true));
    }

    public static UserRepositoryMongo getInstance() {
        if(INSTANCE == null) {
            INSTANCE = new UserRepositoryMongo();
        }

        return INSTANCE;
    }


    @Override
    public User createUser(User user) {
        Document toBeInserted = this.userToDocumentMapper.apply(user);
        InsertOneResult result = this.getUserCollection().insertOne(toBeInserted);
        if(!result.wasAcknowledged()){
            throw new RuntimeException("Insert failed for citizen with username: " + user.getUsername());
        }
        return user;
    }

    @Override
    public User findUser(String username) {
        return Optional.ofNullable(
                this.getUserCollection()
                        .find(eq(UserRepository.USERNAME,username))
                        .map(documentToUserMapper::apply).first()
        ).orElseThrow(()->new UserNotFoundException("Citizen with id: "+username+" not found"));
    }

    @Override
    public User updateUser(String username, User user) {
        Document updatedDocument = this.userToDocumentMapper.apply(user);
        updatedDocument.put(UserRepository.USERNAME,username);
        Document updateQuery = new Document("$set", updatedDocument);
        UpdateResult result= this.getUserCollection().updateOne(eq(UserRepository.USERNAME,username),updateQuery);

        if (result.getMatchedCount() == 0) {
            throw new UserNotFoundException("Citizen with id " + username + " not found");
        }
        return this.findUser(user.getUsername());
    }

    @Override
    public boolean deleteUser(String username) {
        Document deleted = this.getUserCollection().findOneAndDelete(eq(UserRepository.USERNAME,username));
        return deleted!=null;
    }

    @Override
    public List<User> findall() {
        return getUserCollection()
                .find()
                .map(documentToUserMapper::apply)
                .into(new java.util.ArrayList<>());
    }

    public User findUserByEmail(String email) {
        return Optional.ofNullable(
                this.getUserCollection()
                        .find(eq(UserRepository.EMAIL,email))
                        .map(documentToUserMapper::apply).first()
        ).orElseThrow(()->new UserNotFoundException("Citizen with email: "+email+" not found"));
    }


    private MongoCollection<Document> getUserCollection() {
        return this.mongoClient.getDatabase(UserRepository.DB)
                .getCollection(UserRepository.COLLECTION);
    }


}
