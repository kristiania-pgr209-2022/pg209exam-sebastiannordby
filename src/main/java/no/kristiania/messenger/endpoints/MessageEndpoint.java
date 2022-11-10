package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageReadDao;
import no.kristiania.messenger.dtos.commands.InsertNewMessageIntoThreadCommandDto;
import no.kristiania.messenger.dtos.commands.MarkMessagesInMessageThreadAsReadCommandDto;
import no.kristiania.messenger.dtos.queries.MessageThreadsUnreadCountQuery;

import javax.print.attribute.standard.Media;

@Path("/message")
public class MessageEndpoint {
    @Inject
    public MessageDao messageDao;

    @Inject
    public MessageReadDao messageReadDao;

    @GET
    @Path("/{messageThreadId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllThreads(@PathParam("messageThreadId") int messageThreadId) throws Exception {
        return Response
                .ok(messageDao.findMessageViewsInThread(messageThreadId))
                .build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertMessageIntoThread(InsertNewMessageIntoThreadCommandDto command) throws Exception {
        if(command == null)
            return Response.status(404).build();

        if(command.message == null || command.message.length() == 0)
            return Response.status(400).entity("Message must be specified.").build();

        messageDao.newMessage(command.userId, command.messageThreadId, command.message);

        return Response.ok().build();
    }

    @POST
    @Path("/message-read")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response markMessageAsRead(MarkMessagesInMessageThreadAsReadCommandDto command) throws Exception {
        if(command == null)
            return Response.status(404).build();

        messageReadDao.markMessagesInThreadAsRead(command.userId, command.messageThreadId);

        return Response.ok().build();
    }

    @POST
    @Path("/unread")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response markMessageAsRead(MessageThreadsUnreadCountQuery query) throws Exception {
        if(query == null)
            return Response.status(404).build();

        var result = messageReadDao.getUnreadMessages(query.userId, query.messageThreadIds);

        return Response.ok(result).build();
    }
}
