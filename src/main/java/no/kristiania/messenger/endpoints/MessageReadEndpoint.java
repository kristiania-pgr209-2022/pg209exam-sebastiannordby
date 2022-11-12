package no.kristiania.messenger.endpoints;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import no.kristiania.messenger.dao.MessageReadDao;

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
}
