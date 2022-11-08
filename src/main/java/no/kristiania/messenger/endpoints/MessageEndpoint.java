package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import no.kristiania.messenger.dao.MessageDao;
import no.kristiania.messenger.dao.MessageThreadDao;

@Path("/message")
public class MessageEndpoint {
    @Inject
    public MessageDao messageDao;

    @GET
    @Path("/{messageThreadId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAllThreads(@PathParam("messageThreadId") int messageThreadId) throws Exception {
        return Response
                .ok(messageDao.findMessagesInThread(messageThreadId))
                .build();
    }
}
