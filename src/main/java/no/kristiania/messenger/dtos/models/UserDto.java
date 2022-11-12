package no.kristiania.messenger.dtos.models;

public class UserDto {
    public int id;
    public String name;
    public String emailAddress;
    public String nickName;
    public String bio;

    public UserDto() {

    }

    public UserDto(
        int id,
        String name,
        String emailAddress,
        String nickName,
        String bio) {
        this.id = id;
        this.name = name;
        this.emailAddress = emailAddress;
        this.nickName = nickName;
        this.bio = bio;
    }
}
