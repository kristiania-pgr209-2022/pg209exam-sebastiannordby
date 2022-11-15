package no.kristiania.messenger.dao;

import no.kristiania.messenger.views.MessageReadByUserView;
import no.kristiania.messenger.views.UnreadMessagesCountView;
import java.util.Date;
import java.util.List;

public interface MessageReadDao {
    List<UnreadMessagesCountView> getUnreadMessages(int userId, List<Integer> messageThreadIds) throws Exception;
    int getUnreadMessageCountByUserInThread(int userId, int messageThreadId) throws Exception;
    void markMessagesInThreadAsRead(int userId, int messageThreadId) throws Exception;
    Date find(int userId, int messageId) throws Exception;
    List<MessageReadByUserView> getUserViewsWhichHasReadMessage(int messageId) throws Exception;
}
