import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatHistoryManager {
    
    public static class ChatEntry {
        public String message;
        public String response;
        public String category;
        public String timestamp;
        
        public ChatEntry(String message, String response, String category, String timestamp) {
            this.message = message;
            this.response = response;
            this.category = category;
            this.timestamp = timestamp;
        }
    }
    
    public static class UserInfo {
        public int userId;
        public String username;
        public String email;
        public String createdAt;
        public int totalMessages;
        
        public UserInfo(int userId, String username, String email, String createdAt, int totalMessages) {
            this.userId = userId;
            this.username = username;
            this.email = email;
            this.createdAt = createdAt;
            this.totalMessages = totalMessages;
        }
    }
    
    public static List<UserInfo> getAllUsers() {
        List<UserInfo> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT u.user_id, u.username, u.email, u.created_at, COUNT(c.chat_id) as total_messages " +
                          "FROM Users u LEFT JOIN ChatLogs c ON u.user_id = c.user_id " +
                          "GROUP BY u.user_id ORDER BY total_messages DESC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                users.add(new UserInfo(
                    rs.getInt("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("created_at"),
                    rs.getInt("total_messages")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting users: " + e.getMessage());
        }
        return users;
    }
    
    public static List<ChatEntry> getChatHistory(int userId, int limit) {
        List<ChatEntry> history = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT message, response, category, timestamp FROM ChatLogs " +
                          "WHERE user_id = ? ORDER BY timestamp DESC LIMIT ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                history.add(new ChatEntry(
                    rs.getString("message"),
                    rs.getString("response"),
                    rs.getString("category"),
                    rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting chat history: " + e.getMessage());
        }
        return history;
    }
    
    public static List<ChatEntry> getAllChats(int limit) {
        List<ChatEntry> chats = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT u.username, c.message, c.response, c.category, c.timestamp " +
                          "FROM ChatLogs c JOIN Users u ON c.user_id = u.user_id " +
                          "ORDER BY c.timestamp DESC LIMIT ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                chats.add(new ChatEntry(
                    rs.getString("username") + ": " + rs.getString("message"),
                    rs.getString("response"),
                    rs.getString("category"),
                    rs.getString("timestamp")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all chats: " + e.getMessage());
        }
        return chats;
    }
}