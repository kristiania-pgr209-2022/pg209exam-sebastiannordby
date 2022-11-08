package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageThreadDao;
import no.kristiania.messenger.dtos.commands.CreateUserMessageThreadCommandDto;
import no.kristiania.messenger.dtos.commands.InsertNewMessageIntoThreadCommandDto;

@Path("/message")
public class MessageEndpoint {
    @Inject
    public MessageDao messageDao;

    @GET
    @Path("/{messageThreadId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllThreads(@PathParam("messageThreadId") int messageThreadId) throws Exception {
        return Response
                .ok(messageDao.findMessageViewsInThread(messageThreadId))
                .build();
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertMessageIntoThread(InsertNewMessageIntoThreadCommandDto command) throws Exception {
        if(command == null)
            return Response.status(404).build();

        if(command.message == null || command.message.length() == 0)
            return Response.status(400).entity("Message must be specified.").build();

        messageDao.newMessage(command.userId, command.messageThreadId, command.message);

        return Response.ok(true).build();
    }
}
