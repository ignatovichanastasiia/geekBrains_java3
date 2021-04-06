package ServerSide;

public interface AuthenticationService {
    void start();
    void stop();
    String getNickByLoginAndPassword(String login,String password);
    boolean registration(String login,String password,String nick);
}
