import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ViewUsers {
    public static void main(String[] args) {
        System.out.println("Current Users in Database:");
        System.out.println("=" .repeat(40));
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT user_id, username, email, created_at FROM Users ORDER BY created_at DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            int count = 0;
            while (rs.next()) {
                count++;
                System.out.printf("%d. %s (%s) - %s%n", 
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("created_at"));
            }
            
            if (count == 0) {
                System.out.println("No users found in database.");
            } else {
                System.out.println("\nTotal users: " + count);
            }
            
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}