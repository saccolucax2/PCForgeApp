package it.unisannio.chat.persistence;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import it.unisannio.chat.data.Message;
import it.unisannio.chat.exception.MessageNotFoundException;
import it.unisannio.chat.persistence.mapper.DocumentToMessageMapper;
import it.unisannio.chat.persistence.mapper.MessageToDocumentMapper;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static com.mongodb.client.model.Filters.eq;

public class ChatRepositoryMongo implements ChatRepository {
    private final MongoClient mongoClient;
    private final Function<Message, Document> messageToDocumentMapper = new MessageToDocumentMapper();
    private final Function<Document, Message> documentToMessageMapper = new DocumentToMessageMapper();

    private static ChatRepositoryMongo INSTANCE;

    private ChatRepositoryMongo() {
        String host = System.getenv("MONGO_ADDRESS") != null ?
                System.getenv("MONGO_ADDRESS") :
                "localhost";
        String port = System.getenv("MONGO_PORT") != null ?
                System.getenv("MONGO_PORT") :
                "27017";
        mongoClient = MongoClients.create("mongodb://" + host + ":" + port);
    }

    public static ChatRepositoryMongo getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ChatRepositoryMongo();
        }
        return INSTANCE;
    }

    @Override
    public Message createMessage(Message message) {
        Document doc = messageToDocumentMapper.apply(message);
        InsertOneResult result = getMessageCollection().insertOne(doc);
        if (!result.wasAcknowledged()) {
            throw new RuntimeException("Insert failed for message with id: " + message.getId());
        }
        return message;
    }

    @Override
    public Message findMessage(String id) {
        try {
            return Optional.ofNullable(
                    getMessageCollection()
                            .find(eq("_id", new ObjectId(id)))
                            .map(documentToMessageMapper::apply)
                            .first()
            ).orElseThrow(() -> new MessageNotFoundException("Message with id: " + id + " not found"));
        } catch (MessageNotFoundException e) {
            // Gestisci l'eccezione qui
            e.printStackTrace();
            return null;
        }
    }


    @Override
    public List<Message> findAll() {
        return getMessageCollection()
                .find()
                .map(documentToMessageMapper::apply)
                .into(new java.util.ArrayList<>());
    }

    private MongoCollection<Document> getMessageCollection() {
        return this.mongoClient.getDatabase(ChatRepository.DB)
                .getCollection(ChatRepository.COLLECTION);
    }

    @Override
    public List<Message> findByToUserId(String toUserId) {
        return getMessageCollection()
                .find(eq("toUserId", toUserId))
                .map(documentToMessageMapper::apply)
                .into(new ArrayList<>());
    }

    @Override
    public boolean deleteMessage(Message msg){
     try {
        DeleteResult result = getMessageCollection().deleteOne(
                Filters.and(
                        Filters.eq("_id", new ObjectId(msg.getId())),
                        Filters.eq("fromUserId", msg.getFromUserId())
                )
        );
        return result.getDeletedCount() > 0;
    } catch (IllegalArgumentException e) {
        // Gestione errore per ID non valido
        e.printStackTrace();
        return false;
    }
    }

}