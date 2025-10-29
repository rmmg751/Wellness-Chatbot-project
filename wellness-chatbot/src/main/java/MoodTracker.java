import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MoodTracker {
    
    public void addMoodEntry(int userId, int moodRating, String entryText) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO MoodEntries (user_id, mood_rating, entry_text) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, moodRating);
            stmt.setString(3, entryText);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error adding mood entry: " + e.getMessage());
        }
    }
    
    public double getAverageMood(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT AVG(mood_rating) as avg_mood FROM MoodEntries WHERE user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("avg_mood");
            }
        } catch (SQLException e) {
            System.err.println("Error getting average mood: " + e.getMessage());
        }
        return 0.0;
    }
    
    public void printMoodHistory(int userId) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT mood_rating, entry_text, timestamp FROM MoodEntries WHERE user_id = ? ORDER BY timestamp DESC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("Recent Mood Entries:");
            while (rs.next()) {
                System.out.printf("%s - Rating: %d - %s%n", 
                    rs.getTimestamp("timestamp"),
                    rs.getInt("mood_rating"),
                    rs.getString("entry_text"));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving mood history: " + e.getMessage());
        }
    }
}