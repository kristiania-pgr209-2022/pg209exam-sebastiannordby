package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.json.Json;
import no.kristiania.messenger.dao.UserDao;
import no.kristiania.messenger.dtos.commands.CreateUserCommandDto;
import no.kristiania.messenger.dtos.models.UserDto;
import no.kristiania.messenger.entities.User;
import java.util.ArrayList;

@Path("/user")
public class UserEndpoint {
    @Inject
    public UserDao userDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() throws Exception {
        var result = new ArrayList<UserDto>();
        var users = userDao.list();

        for(var user : users) {
            result.add(new UserDto(
                user.getId(),
                user.getName(),
                user.getEmailAddress(),
                user.getNickname(),
                user.getBio()
            ));
        }

        return Response.ok(result).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") int id) throws Exception {
        var user = userDao.find(id);
        var userDto = new UserDto(
            user.getId(),
            user.getName(),
            user.getEmailAddress(),
            user.getNickname(),
            user.getBio()
        );

        return Response.ok(userDto).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(CreateUserCommandDto command) throws Exception {
        if(command == null) {
            return Response.status(404).build();
        }

        var createdUserId = userDao.insertUser(new User(
            command.name,
            command.emailAddress,
            command.nickName,
            command.bio
        ));

        var responseJson = Json.createObjectBuilder()
            .add("id", createdUserId);

        return Response.ok(responseJson.build().toString()).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(UserDto user) throws Exception {
        if(user == null) {
            return Response.status(404).build();
        }

        userDao.updateUser(user.id, user.name, user.emailAddress, user.nickName, user.bio);

        return Response.ok().build();
    }
}
