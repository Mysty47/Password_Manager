import javax.naming.ldap.Control;
import java.sql.*;

public class Passwords {

    public static int id_generator = 1;

    void checkId_Generator() {
        if(id_generator == 100) {
            id_generator = 1;
        }
    }

    void displayPasswords() {
        checkId_Generator();
        try {
            //Connection to SQL database
            Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/login_info",
                    "root",
                    "parola1");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while(resultSet.next()) {
                System.out.println(resultSet.getInt("id"));
                System.out.println(resultSet.getString("place"));
                System.out.println(resultSet.getString("password"));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    void addPassword(int index, String place, String password) {
        checkId_Generator();
        String sql = "INSERT INTO users (id, place, password) VALUES (?, ?, ?)";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/login_info",
                    "root",
                    "parola1");

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, index);
            stmt.setString(2, place);
            stmt.setString(3, password);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Data inserted successfully!");
            }

            id_generator++;
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    void deleteInfo(String place) {
        String sql = "DELETE FROM users WHERE place = ?";

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3307/login_info",
                    "root",
                    "parola1");

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, place);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Entry deleted successfully!");
            }

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}