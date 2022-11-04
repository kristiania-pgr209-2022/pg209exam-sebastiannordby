package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.json.Json;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.entities.User;

import java.io.StringReader;

@Path("/user")
public class UserEndpoint {
    @Inject
    public UserDao userDao;

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws Exception {
        var result = Json.createArrayBuilder();
        var users = userDao.list();

        for(var user : users) {
            result.add(Json.createObjectBuilder()
                .add("id", user.getId())
                .add("name", user.getName())
                .add("emailAddress", user.getEmailAddress())
                .add("nickname", user.getNickname())
                .add("bio", user.getBio())
            );
        }

        return Response.ok(result.build().toString()).build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(String body) throws Exception {
        if(body == null && body.length() == 0) {
            throw new IllegalArgumentException();
        }

        var userJson = Json.createReader(new StringReader(body)).readObject();
        var userId = userDao.insertUser(
            new User(
                userJson.getString("name"),
                userJson.getString("emailAddress"),
                userJson.getString("nickname"),
                userJson.getString("bio"))
        );

        return Response.ok().build();
    }
}
