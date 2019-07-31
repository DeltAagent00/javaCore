package Lesson_6.chat.server;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBService {
    private static Connection connection;
    private static Statement stmt;

    public static void connect() throws SQLException {
        try {
            // обращение к драйверу
            Class.forName("org.sqlite.JDBC");
            // установка подключения
            connection = DriverManager.getConnection("jdbc:sqlite:src/Lesson_6/chat/DBUsers.db");
            // создание Statement для возможности оправки запросов
            stmt = connection.createStatement();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getNickByLoginAndPass(String login, String pass) {
        // формирование запроса
        String sql = String.format("SELECT nickname FROM main where login = '%s' and password = '%s'", login, pass);

        try {
            // оправка запроса и получение ответа
            ResultSet rs = stmt.executeQuery(sql);

            // если есть строка возвращаем результат если нет то вернеться null
            if(rs.next()) {
                return rs.getString(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<String> getBlackList() {
        final String sql = "SELECT nickname FROM blackList";

        try {
            ResultSet rs = stmt.executeQuery(sql);

            final List<String> nicks = new ArrayList<>();
            if(rs.next()) {
                nicks.add(rs.getString(1));
            }
            return nicks;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public static boolean addToBlackList(String nick) {
        final String sql = "INSERT INTO blackList values (\'" + nick + "\')";

        try {
            int rs = stmt.executeUpdate(sql);

            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean removeFromBlackList(String nick) {
        final String sql = "DELETE FROM blackList WHERE 'nickname' = \'" + nick + "\')";

        try {
            int rs = stmt.executeUpdate(sql);

            return rs > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void disconnect() {
        try {
            // закрываем соединение
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
