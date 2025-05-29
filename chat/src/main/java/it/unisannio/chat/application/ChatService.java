package it.unisannio.chat.application;

import it.unisannio.chat.data.Message;
import it.unisannio.chat.persistence.ChatRepository;
import it.unisannio.chat.persistence.ChatRepositoryMongo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    private  ChatRepository repository=ChatRepositoryMongo.getInstance();


    public Message sendMessage(Message msg) {
        msg.setTimestamp(LocalDateTime.now());
        return repository.createMessage(msg);
    }
    public List<Message> getInbox(String userId) {
        return repository.findByToUserId(userId);
    }

    public boolean deleteMessage(Message msg){
       return repository.deleteMessage(msg);
    }
}
