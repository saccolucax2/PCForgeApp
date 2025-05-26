package it.unisannio.authorization.presentation;

import it.unisannio.authorization.application.UserService;
import it.unisannio.authorization.data.User;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URI;
import java.util.List;


@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/users")
public class UserController {

    @Autowired
    private UserService userService;


    // Endpoint per autenticazione (accessibile senza token)
    @POST
    @Path("/authenticate")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response authenticateUserJson(User user) {
        boolean authenticated = userService.authenticateUser(user.getUsername(), user.getPassword());
        if (authenticated) {
            return Response.ok().build(); // oppure puoi restituire un token
        } else {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }



    // Endpoint per creare un nuovo utente (puoi decidere se renderlo pubblico o protetto)
    @POST
    public Response createUser(User user, @Context UriInfo uriInfo) {
        String username = userService.createUser(user);
        if (username != null) {
            URI uri = UriBuilder.fromUri(uriInfo.getAbsolutePath()).path(username).build();
            return Response.created(uri).build();
        } else {
            return Response.serverError().build();
        }
    }

    // Gli altri metodi sono protetti e quindi richiedono token (per esempio):
    @GET
    @Path("/{username}")
    public Response getUser(@PathParam("username") String username) {
        User user = userService.getUser(username);
        if (user != null) {
            return Response.ok(user).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/all")
    public Response getAllUsers() {
        List<User> users = userService.getAllUsers();
        if (users != null && !users.isEmpty()) {
            return Response.ok(users).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @PUT
    @Path("/{username}")
    public Response updateUser(@PathParam("username") String username, User user) {
        User updatedUser = userService.updateUser(username, user);
        return Response.ok(updatedUser).build();
    }

    @DELETE
    @Path("/{username}")
    public Response deleteUser(@PathParam("username") String username) {
        if (userService.deleteUser(username)) {
            return Response.ok().build();
        } else {
            return Response.serverError().build();
        }
    }
}

