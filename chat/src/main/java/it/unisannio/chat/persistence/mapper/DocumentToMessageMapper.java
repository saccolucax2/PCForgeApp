package it.unisannio.chat.persistence.mapper;

import it.unisannio.chat.data.Message;
import it.unisannio.chat.persistence.ChatRepository;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.function.Function;

public class DocumentToMessageMapper implements Function<Document, Message> {
    @Override
    public Message apply(Document document) {
        ObjectId objectId = document.getObjectId("_id");
        Date timestampDate = document.getDate("timestamp");
        LocalDateTime timestamp = timestampDate != null
                ? timestampDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                : null;

        return new Message(
                objectId.toHexString(),
                document.getString("fromUserId"),
                document.getString("toUserId"),
                document.getString("content"),
                timestamp
        );
    }
}