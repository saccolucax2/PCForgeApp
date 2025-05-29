package it.unisannio.chat.data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "messages")

public class Message {

    @Id
    private String id;
    private String fromUserId;
    private String toUserId;
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();;


    public Message(){}
    // Getters/setters
    public Message(String fromUserId, String toUserId, String content) {
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
    }

    // Costruttore completo (usato dal mapper)
    public Message(String id, String fromUserId, String toUserId, String content, LocalDateTime timestamp) {
        this.id = id;
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
        this.content = content;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getFromUserId() {
        return fromUserId;
    }
    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }
    public String getToUserId() {
        return toUserId;
    }
    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(LocalDateTime now) {
        this.timestamp = now;
    }
}
