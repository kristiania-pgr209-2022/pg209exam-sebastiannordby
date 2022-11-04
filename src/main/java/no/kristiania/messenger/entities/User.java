package no.kristiania.messenger.entities;

public class User {
    private int id;
    private String name;
    private String emailAddress;
    private String nickname;
    private String bio;

    public User(
        String name,
        String emailAddress,
        String nickname,
        String bio) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.nickname = nickname;
        this.bio = bio;
    }

    public User() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}
