package it.unisannio.chat.persistence.mapper;

import it.unisannio.chat.data.Message;
import it.unisannio.chat.persistence.ChatRepository;
import org.bson.Document;

import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

public class MessageToDocumentMapper implements Function<Message, Document> {
    @Override
    public Document apply(Message message) {
        Date timestampDate = Date.from(message.getTimestamp().atZone(ZoneId.systemDefault()).toInstant());

        return new Document()
                .append("fromUserId", message.getFromUserId())
                .append("toUserId", message.getToUserId())
                .append("content", message.getContent())
                .append("timestamp", timestampDate);
    }
}