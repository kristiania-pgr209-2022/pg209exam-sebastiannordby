package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import no.kristiania.messenger.dao.MessageReadDao;
import no.kristiania.messenger.dtos.commands.MarkMessagesInMessageThreadAsReadCommandDto;

@Path("/message-read")
public class MessageReadEndpoint {

    @Inject
    public MessageReadDao messageReadDao;

    @GET
    @Path("/{messageId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserViewsWhichHasReadMessage(@PathParam("messageId") int messageId) throws Exception {
        return Response
                .ok(messageReadDao.getUserViewsWhichHasReadMessage(messageId))
                .build();
    }

    @POST
    @Path("/")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response markMessageAsRead(MarkMessagesInMessageThreadAsReadCommandDto command) throws Exception {
        if(command == null)
            return Response.status(404).build();

        messageReadDao.markMessagesInThreadAsRead(command.userId, command.messageThreadId);

        return Response.ok().build();
    }
}
