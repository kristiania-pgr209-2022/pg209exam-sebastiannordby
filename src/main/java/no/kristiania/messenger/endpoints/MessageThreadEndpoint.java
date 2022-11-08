package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import no.kristiania.messenger.dao.MessageThreadDao;

@Path("/message-thread")
public class MessageThreadEndpoint {
    @Inject
    public MessageThreadDao messageThreadDao;

    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public void listAllThreads(@PathParam("userId") int userId) {
        messageThreadDao.li
    }
}
