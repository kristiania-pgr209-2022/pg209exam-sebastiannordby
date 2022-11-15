package no.kristiania.messenger.views;

import java.time.LocalDateTime;
import java.util.Date;

public class MessageReadByUserView {
    public int userId;
    public String username;
    public LocalDateTime dateRead;

    public MessageReadByUserView() {

    }

    public MessageReadByUserView(int userId, String username, LocalDateTime dateRead) {
        this.userId = userId;
        this.username = username;
        this.dateRead = dateRead;
    }
}
