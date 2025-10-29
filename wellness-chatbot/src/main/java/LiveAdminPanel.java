import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class LiveAdminPanel extends JFrame {
    private JTable userTable;
    private JTable chatTable;
    private DefaultTableModel userModel;
    private DefaultTableModel chatModel;
    private Timer refreshTimer;
    private JLabel statusLabel;
    private int lastChatCount = 0;
    
    public LiveAdminPanel() {
        setTitle("Wellness Chatbot - Live Admin Panel");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
        startLiveUpdates();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        // Status bar
        statusLabel = new JLabel("🟢 Live Updates Active - Refreshing every 3 seconds");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        add(statusLabel, BorderLayout.NORTH);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Users Tab
        JPanel usersPanel = new JPanel(new BorderLayout());
        String[] userColumns = {"ID", "Username", "Email", "Total Messages", "Last Active"};
        userModel = new DefaultTableModel(userColumns, 0);
        userTable = new JTable(userModel);
        JScrollPane userScroll = new JScrollPane(userTable);
        usersPanel.add(userScroll, BorderLayout.CENTER);
        
        // Chat History Tab  
        JPanel chatPanel = new JPanel(new BorderLayout());
        String[] chatColumns = {"User", "Message", "Response", "Time"};
        chatModel = new DefaultTableModel(chatColumns, 0);
        chatTable = new JTable(chatModel);
        chatTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        chatTable.getColumnModel().getColumn(2).setPreferredWidth(300);
        JScrollPane chatScroll = new JScrollPane(chatTable);
        chatPanel.add(chatScroll, BorderLayout.CENTER);
        
        tabbedPane.addTab("Users (" + userModel.getRowCount() + ")", usersPanel);
        tabbedPane.addTab("Live Chats", chatPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void startLiveUpdates() {
        loadData();
        
        // Refresh every 3 seconds
        refreshTimer = new Timer(3000, e -> {
            updateData();
            updateStatus();
        });
        refreshTimer.start();
    }
    
    private void updateData() {
        SwingUtilities.invokeLater(() -> {
            loadUsers();
            loadChats();
        });
    }
    
    private void updateStatus() {
        List<ChatHistoryManager.ChatEntry> chats = ChatHistoryManager.getAllChats(1);
        int currentCount = chats.size();
        
        if (currentCount > lastChatCount) {
            statusLabel.setText("🔴 New message detected! Last update: " + java.time.LocalTime.now());
            // Flash the status
            Timer flashTimer = new Timer(1000, e -> {
                statusLabel.setText("🟢 Live Updates Active - Refreshing every 3 seconds");
                ((Timer)e.getSource()).stop();
            });
            flashTimer.start();
        }
        lastChatCount = currentCount;
    }
    
    private void loadData() {
        loadUsers();
        loadChats();
    }
    
    private void loadUsers() {
        userModel.setRowCount(0);
        List<ChatHistoryManager.UserInfo> users = ChatHistoryManager.getAllUsers();
        for (ChatHistoryManager.UserInfo user : users) {
            userModel.addRow(new Object[]{
                user.userId,
                user.username,
                user.email,
                user.totalMessages,
                user.createdAt
            });
        }
        
        // Update tab title
        Component parent = userTable.getParent().getParent().getParent();
        if (parent instanceof JTabbedPane) {
            ((JTabbedPane) parent).setTitleAt(0, "Users (" + users.size() + ")");
        }
    }
    
    private void loadChats() {
        chatModel.setRowCount(0);
        List<ChatHistoryManager.ChatEntry> chats = ChatHistoryManager.getAllChats(50);
        for (ChatHistoryManager.ChatEntry chat : chats) {
            String[] parts = chat.message.split(": ", 2);
            String username = parts.length > 1 ? parts[0] : "Unknown";
            String message = parts.length > 1 ? parts[1] : chat.message;
            
            chatModel.addRow(new Object[]{
                username,
                message,
                chat.response,
                chat.timestamp
            });
        }
    }
    
    @Override
    public void dispose() {
        if (refreshTimer != null) {
            refreshTimer.stop();
        }
        super.dispose();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LiveAdminPanel().setVisible(true);
        });
    }
}