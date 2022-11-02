package no.kristiania.messenger;

import java.io.IOException;

public class Program {
    public static void main(String[] args) throws Exception {
        new MessengerServer(8080).start();
    }
}
