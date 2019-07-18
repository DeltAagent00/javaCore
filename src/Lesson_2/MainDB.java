package Lesson_2;

import java.sql.*;

public class MainDB {

    private static Connection conn;
    private static Statement stmt;
    private static PreparedStatement pstmt;

    public static void main(String[] args) throws SQLException {

        try {
            connection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // работа с БД

        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS workers (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "lastname TEXT," +
                "surname TEXT," +
                "price DOUBLE)");

        stmt.executeUpdate("DELETE FROM workers");

        conn.setAutoCommit(false);

        stmt.executeUpdate("INSERT INTO workers (lastname, name, surname, price)\n" +
                "VALUES ('Иванов', 'Иван', 'Bdfyjdbx', 20412.89)");
        stmt.executeUpdate("INSERT INTO workers (lastname, name, surname, price)\n" +
                "VALUES ('Петров', 'Петр', 'Петрович', 15328.85)");
        stmt.executeUpdate("INSERT INTO workers (lastname, name, surname, price)\n" +
                "VALUES ('Сидоров', 'Кирилл', 'Гргорьевич', 8735.33)");

        conn.commit();
        conn.setAutoCommit(true);


        stmt.executeUpdate("UPDATE workers SET surname = 'Иванович' WHERE lastname = 'Иванов'");

        ResultSet rs = stmt.executeQuery("SELECT id, name, lastname, surname, price FROM workers");

        while (rs.next()) {
               System.out.println("id = " + rs.getString("id") +
                       " name = " + rs.getString("name") +
                       " lastname = " + rs.getString("lastname") +
                       " surname = " + rs.getString("surname") +
                       " price = " + rs.getDouble("price")
                       );
        }

        disconect();
    }

    public static void connection() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:src/Lesson_2/mainDB.db");
        stmt = conn.createStatement();
    }

    public static void disconect() throws SQLException {
        conn.close();
    }

}
