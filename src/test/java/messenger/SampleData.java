package messenger;

import no.kristiania.messenger.entities.User;

public class SampleData {

    static User sampleUser(){
        var user = new User("testson", "test@hotmail.test");
        return user;
    }
}
