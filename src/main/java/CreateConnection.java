import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/Employee_db";
    private static final String USER = "root";  // ប្ដូរតាម MySQL របស់អ្នក
    private static final String PASSWORD = ""; // បញ្ចូលពាក្យសម្ងាត់របស់អ្នក

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PreparedStatement prepareStatement(String sql) {
        return null;
    }
}
