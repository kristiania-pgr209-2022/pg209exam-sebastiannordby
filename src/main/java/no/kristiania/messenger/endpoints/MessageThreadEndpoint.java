package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import no.kristiania.messenger.dao.MessageThreadDao;
import no.kristiania.messenger.dtos.commands.CreateUserMessageThreadCommandDto;

@Path("/message-thread")
public class MessageThreadEndpoint {
    @Inject
    public MessageThreadDao messageThreadDao;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllThreads(@PathParam("userId") int userId) throws Exception {
        var messageThreads = messageThreadDao.listThreadsByUserId(userId);

        return Response.ok(messageThreads).build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addThread(CreateUserMessageThreadCommandDto command) throws Exception {
        if(command == null)
            return Response.status(404).build();

        if(command.receivers == null || command.receivers.size() == 0)
            return Response.status(400).entity("Receivers must be specified.").build();

        if(command.topic == null || command.topic.length() == 0)
            return Response.status(400).entity("Topic must be specified.").build();

        if(command.message == null || command.message.length() == 0)
            return Response.status(400).entity("Message must be specified.").build();

        messageThreadDao.insert(command.topic, command.message, command.senderId, command.receivers);

        return Response.ok(true).build();
    }
}


