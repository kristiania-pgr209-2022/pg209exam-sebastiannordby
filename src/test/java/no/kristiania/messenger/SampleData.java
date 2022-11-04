package no.kristiania.messenger;

import no.kristiania.messenger.entities.User;
import java.util.Random;

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

    public static String pick(String... parameters) {
        return parameters != null && parameters.length > 0 ?
            parameters[random.nextInt(parameters.length - 1)] : null;
    }
}
