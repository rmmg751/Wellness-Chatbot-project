import java.sql.Connection;
import java.sql.SQLException;

public class TestLogin {
    public static void main(String[] args) {
        System.out.println("Testing database connection...");
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connection successful!");
                System.out.println("Database: " + conn.getCatalog());
            }
        } catch (SQLException e) {
            System.out.println("❌ Database connection failed:");
            System.out.println("Error: " + e.getMessage());
            System.out.println("\nMake sure:");
            System.out.println("1. MySQL server is running");
            System.out.println("2. Database 'Wellness_Chatbot' exists");
            System.out.println("3. Username/password are correct in database.properties");
        }
        
        System.out.println("\nStarting login UI...");
        LoginUI.main(args);
    }
}