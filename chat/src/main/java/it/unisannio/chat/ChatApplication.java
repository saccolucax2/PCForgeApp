package it.unisannio.chat;

import it.unisannio.chat.presentation.ChatController;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ApplicationPath("/chat")
public class ChatApplication extends ResourceConfig {

    public ChatApplication() {
        register(ChatController.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

}
