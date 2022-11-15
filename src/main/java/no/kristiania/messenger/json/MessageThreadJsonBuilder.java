package no.kristiania.messenger.json;

import jakarta.json.Json;
import no.kristiania.messenger.entities.MessageThread;

import javax.json.JsonObjectBuilder;
import java.util.Enumeration;
import java.util.List;

public class MessageThreadJsonBuilder {
    public static String getJson(MessageThread messageThread) {
        return Json.createObjectBuilder()
            .add("topic", messageThread.getTopic())
            .add("id", messageThread.getId())
            .build()
            .toString();
    }

    public static String getJson(List<MessageThread> messageThreads) {
        var stringBuilder = new StringBuilder();
        var arrayBuilder = Json.createArrayBuilder();
        stringBuilder.append("[");
        messageThreads.forEach(x -> stringBuilder.append(getJson(x)));
        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}
