package no.kristiania.messenger;

import no.kristiania.messenger.entities.MessageThread;
import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.entities.User;

import java.util.Date;
import java.util.Random;

public class SampleData {
    private static Random random = new Random();

    public static User sampleUser(){
        return new User(
            pick("Mats", "Sebastian", "Leif"),
            pick("csharp@gmail.com", "javalord@oracle.net", "ndc@code.com"),
            pick("Cyllon", "Ridens", "Spolker"),
            pick("Happy, living life.", "Programming my way away.", "Looking for coding job. Hit me up", "Hello World")
        );
    }


    public static Message sampleMessage(User sender, User receiver){
        Date date = new Date(System.currentTimeMillis());

        return new Message(
            pick(
        "Hei, skal vi spille tennis etter backend timen?",
                "Når kommer du over?",
                "Byen i kveld?"),
                sender.getId(), receiver.getId(), date);

    }

    public static MessageThread sampleGroup(){
        return new MessageThread(
                pick("Football"
                        , "Tennis",
                        "Gaming")
        );
    }

    public static String pick(String... parameters) {
        return parameters != null && parameters.length > 0 ?
            parameters[random.nextInt(parameters.length - 1)] : null;
    }


}
//java.sql.Date.valueOf(df.format(date))