import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationTest {
    public static void main(String[] args) {
        System.out.println("Testing Registration System...\n");
        
        // Test 1: Database Connection
        System.out.println("1. Testing database connection...");
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database connected successfully!");
                
                // Test 2: Check if Users table exists
                System.out.println("\n2. Checking Users table...");
                String checkTable = "SELECT COUNT(*) FROM Users";
                PreparedStatement stmt = conn.prepareStatement(checkTable);
                ResultSet rs = stmt.executeQuery();
                rs.next();
                System.out.println("✅ Users table exists with " + rs.getInt(1) + " users");
                
                // Test 3: Try to register a test user
                System.out.println("\n3. Testing user registration...");
                String testUsername = "testuser_" + System.currentTimeMillis();
                String testPassword = "test123";
                String testEmail = "test@example.com";
                
                String insertQuery = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setString(1, testUsername);
                insertStmt.setString(2, testPassword);
                insertStmt.setString(3, testEmail);
                
                int result = insertStmt.executeUpdate();
                if (result > 0) {
                    System.out.println("✅ Test user registered successfully!");
                    System.out.println("Username: " + testUsername);
                    
                    // Test 4: Verify the user was inserted
                    String verifyQuery = "SELECT username FROM Users WHERE username = ?";
                    PreparedStatement verifyStmt = conn.prepareStatement(verifyQuery);
                    verifyStmt.setString(1, testUsername);
                    ResultSet verifyRs = verifyStmt.executeQuery();
                    
                    if (verifyRs.next()) {
                        System.out.println("✅ User verification successful!");
                    } else {
                        System.out.println("❌ User not found after insertion!");
                    }
                } else {
                    System.out.println("❌ Failed to register test user!");
                }
                
            } else {
                System.out.println("❌ Database connection failed!");
            }
        } catch (SQLException e) {
            System.out.println("❌ Database error: " + e.getMessage());
            System.out.println("\nPossible issues:");
            System.out.println("1. MySQL server not running");
            System.out.println("2. Database 'Wellness_Chatbot' doesn't exist");
            System.out.println("3. Wrong username/password in database.properties");
            System.out.println("4. Tables not created");
        } catch (Exception e) {
            System.out.println("❌ Connection error: " + e.getMessage());
            System.out.println("\nPossible issues:");
            System.out.println("1. database.properties file not found");
            System.out.println("2. MySQL connector JAR not in classpath");
        }
        
        System.out.println("\n" + "=".repeat(50));
        System.out.println("If you see errors above, run the demo version:");
        System.out.println("java LoginUIDemo");
    }
}