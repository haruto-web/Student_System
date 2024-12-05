import java.sql.*;


public class db {
    private static final String URL = "jdbc:mysql://localhost:3306/java_db_connection";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";




    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
            throw e;
        }
    }




    public static boolean validateAdmin(String inputUsername, String inputPassword) {
        String query = "SELECT * FROM admins WHERE username = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {


            statement.setString(1, inputUsername);
            statement.setString(2, inputPassword);


            try (ResultSet resultSet = statement.executeQuery()) {


                return resultSet.next();
            }


        } catch (SQLException e) {
            System.out.println("Error occurred while validating admin credentials.");
            e.printStackTrace();
            return false;
        }
    }
}

