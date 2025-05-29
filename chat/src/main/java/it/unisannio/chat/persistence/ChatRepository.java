package it.unisannio.chat.persistence;

import it.unisannio.chat.data.Message;

import java.util.List;

public interface ChatRepository {
    static String ID = "id";
    static String MESSAGE = "message";
    static String FROM = "from";
    static String TO = "to";
    static String TIMESTAMP="timestamp";
    static String DB = "chatdb";
    static String COLLECTION = "messages";


    Message createMessage(Message message);

    Message findMessage(String id);

    boolean deleteMessage(Message msg);

    List<Message> findAll();
    // ... altri metodi esistenti ...
    
    List<Message> findByToUserId(String toUserId);
}