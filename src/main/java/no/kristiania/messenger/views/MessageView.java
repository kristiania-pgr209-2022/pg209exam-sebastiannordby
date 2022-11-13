package no.kristiania.messenger.views;

import java.time.LocalDateTime;
import java.util.Date;

public class MessageView {
    public int messageId;
    public String content;
    public int senderId;
    public int messageThreadId;
    public LocalDateTime sentDate;
    public String userNickname;
}
