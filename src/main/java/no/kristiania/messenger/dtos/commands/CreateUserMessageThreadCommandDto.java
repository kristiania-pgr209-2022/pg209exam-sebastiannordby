package no.kristiania.messenger.dtos.commands;

import java.util.List;

public class CreateUserMessageThreadCommandDto {
    public String topic;
    public String message;
    public int senderId;
    public List<Integer> receivers;
}
