import java.sql.*;

public class Main {
    public static void main(String[] args) {

        try {
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
}