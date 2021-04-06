package ServerSide;

public class User {

    private String login;
    private String password;
    private String nick;

    public User(String login, String password, String nick) {
        this.login =login;
        this.password = password;
        this.nick = nick;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNick() {
        return nick;
    }

}
