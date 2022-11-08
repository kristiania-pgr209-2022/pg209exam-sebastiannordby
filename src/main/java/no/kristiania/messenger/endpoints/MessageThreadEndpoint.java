package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.messenger.dao.MessageThreadDao;
import no.kristiania.messenger.entities.MessageThread;

import java.util.List;

@Path("/message-thread")
public class MessageThreadEndpoint {
    @Inject
    public MessageThreadDao messageThreadDao;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<MessageThread> listAllThreads(@PathParam("userId") int userId) throws Exception {
        var messageThreads = messageThreadDao.listThreadsByUserId(userId);

        return messageThreads;
    }
}
