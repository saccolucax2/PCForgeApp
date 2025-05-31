package it.unisannio.chat.presentation;

import it.unisannio.chat.application.ChatService;
import it.unisannio.chat.data.Message;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static jakarta.annotation.Resource.AuthenticationType.APPLICATION;

@Path("/rest")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatController {

    @Autowired
    private ChatService chatService;


    @POST
    @Path("/send")
    public Response sendMessage(Message message) {
        chatService.sendMessage(message);
        return Response.ok("Message sent").build();
    }

    @GET
    @Path("/inbox/{userId}")
    public Response getInbox(@PathParam("userId") String userId) {
        List<Message> msgs = chatService.getInbox(userId);
        return Response.ok(msgs).build();
    }

    @DELETE
    public Response deleteChat(Message msg){
        boolean result=chatService.deleteMessage(msg);
        if(result==true) return Response.ok().build();
        else return Response.serverError().build();
    }
}

