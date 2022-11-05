package no.kristiania.messenger;

import no.kristiania.messenger.entities.Message;
import no.kristiania.messenger.entities.User;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

public class SampleData {
    private static Random random = new Random();

    public static User sampleUser(){
        return new User(
            pick("Mats", "Sebastian", "Leif"),
            pick("csharp@gmail.com", "javalord@oracle.net", "ndc@code.com"),
            pick("Cyllon", "Ridens", "Spolker"),
            pick("Happy, living life.", "Programming my way away.", "Looking for coding job. Hit me up")
        );
    }

    public static Message sampleMessage(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Europe/Oslo"));
        Date currentDate = java.sql.Date.valueOf(df.format(date));
        return new Message(
                pick("Hei, skal vi spille tennis etter backend timen?"), 1, 0, currentDate

        );
    }

    public static String pick(String... parameters) {
        return parameters != null && parameters.length > 0 ?
            parameters[random.nextInt(parameters.length - 1)] : null;
    }
}
