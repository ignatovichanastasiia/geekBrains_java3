package ServerSide;

import java.sql.*;


public class AuthenticationServiceImpl implements AuthenticationService {
    private static Connection connection;
    private static Statement statement;
    private Object ResultSet;


    public AuthenticationServiceImpl() {
        try {
            statement.execute("create table if not exists users (login text primary key not null,password text not null,nick text not null);");
            statement.execute("insert into users (login,password,nick) values ('A','A','A');");
            statement.execute("insert into users (login,password,nick) values ('B','B','B');");
            statement.execute("insert into users (login,password,nick) values ('C','C','C');");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        connectDB();
        System.out.println("Start");
    }

    @Override
    public void stop() {
        disconnectDB();
        System.out.println("Stop");
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        try {
            PreparedStatement psq = connection.prepareStatement("select nick from users where name = '?' and password = '?'");
            psq.setString(1, login);
            psq.setString(2, password);
            ResultSet = psq.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return (String) ResultSet;
        }
    }

    @Override
    public boolean registration(String login,String password,String nick){
        try {
            PreparedStatement psn = connection.prepareStatement("select from users nick where login = '?'");
            psn.setString(1,login);
            ResultSet = psn.executeQuery();
            if(ResultSet.equals(null)) {
                PreparedStatement psq = connection.prepareStatement("insert into users (login,password,nick) values ('?','?','?');");
                psq.setString(1, login);
                psq.setString(2, password);
                psq.setString(3, nick);
                psq.execute();
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void connectDB() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:db_fail.db");
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void disconnectDB() {
        try {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
