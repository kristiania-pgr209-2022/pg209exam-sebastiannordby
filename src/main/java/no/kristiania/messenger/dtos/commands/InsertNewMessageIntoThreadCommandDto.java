package no.kristiania.messenger.dtos.commands;

public class InsertNewMessageIntoThreadCommandDto {
    public int userId;
    public int messageThreadId;
    public String message;

    public InsertNewMessageIntoThreadCommandDto() {

    }

    public InsertNewMessageIntoThreadCommandDto(int userId, int messageThreadId, String message) {
        this.userId = userId;
        this.messageThreadId = messageThreadId;
        this.message = message;
    }
}
