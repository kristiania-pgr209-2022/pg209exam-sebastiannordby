package no.kristiania.messenger.dao;

import no.kristiania.messenger.entities.MessageThread;
import no.kristiania.messenger.views.MessageThreadView;

import java.util.List;

public interface MessageThreadViewDao {
    List<MessageThreadView> getListOfThreadsByRecieverId(int userReceiverId) throws Exception;
}
