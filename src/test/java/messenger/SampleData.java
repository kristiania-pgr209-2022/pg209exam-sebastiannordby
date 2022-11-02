package messenger;

import no.kristiania.messenger.entities.User;

public class SampleData {

    static User sampleBook(){
        var user = new User();
        user.setName("Testson");
        user.setEmail("Test@hotmail.Test");
        return user;
    }
}
