package ServerSide;

import java.sql.*;


public class AuthenticationServiceImpl implements AuthenticationService {
    private static Connection connection;
    private static Statement statement;
    private ResultSet resultSet;
    private ResultSet resSet;
    private String nick;


    public AuthenticationServiceImpl() {
        connectDB();
    }

    @Override
    public void start() {
        try {
            statement.execute("create table if not exists users (login text primary key not null,password text not null,nick text not null);");
            statement.execute("delete from users;");

//            statement.execute("insert into users (login,password,nick) values ('A','A','A');");
//            statement.execute("insert into users (login,password,nick) values ('B','B','B');");
//            statement.execute("insert into users (login,password,nick) values ('C','C','C');");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Start");
    }

    @Override
    public void stop() {
        disconnectDB();
        System.out.println("Stop");
    }

    @Override
    public String getNickByLoginAndPassword(String login, String password) {
        try (PreparedStatement psq = connection.prepareStatement("select nick from users where login = ? and password = ?");) {
            psq.setString(1, login);
            psq.setString(2, password);
            resultSet = psq.executeQuery();
            nick = resultSet.getString("nick");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return nick;
        }
    }

    @Override
    public boolean registration(String login, String password, String nick) {
        try (PreparedStatement psn = connection.prepareStatement("select from users nick where login = ?");){
            psn.setString(1, login);
            resSet = psn.executeQuery();
            resSet.getString("nick");
        } catch (SQLException e) {
            resSet = null;
        }
        try {
            if (resSet == null) {
                PreparedStatement psq = connection.prepareStatement("insert into users (login,password,nick) values (?, ?, ?);");
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
