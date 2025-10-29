import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminPanel extends JFrame {
    private JTable userTable;
    private JTable chatTable;
    private DefaultTableModel userModel;
    private DefaultTableModel chatModel;
    private Timer refreshTimer;
    
    public AdminPanel() {
        setTitle("Wellness Chatbot - Admin Panel");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
        loadData();
    }
    
    private void initializeComponents() {
        setLayout(new BorderLayout());
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Users Tab
        JPanel usersPanel = new JPanel(new BorderLayout());
        String[] userColumns = {"ID", "Username", "Email", "Created", "Total Messages"};
        userModel = new DefaultTableModel(userColumns, 0);
        userTable = new JTable(userModel);
        JScrollPane userScroll = new JScrollPane(userTable);
        
        JButton refreshUsers = new JButton("Refresh Users");
        refreshUsers.addActionListener(e -> loadUsers());
        
        usersPanel.add(userScroll, BorderLayout.CENTER);
        usersPanel.add(refreshUsers, BorderLayout.SOUTH);
        
        // Chat History Tab
        JPanel chatPanel = new JPanel(new BorderLayout());
        String[] chatColumns = {"User/Message", "Response", "Category", "Timestamp"};
        chatModel = new DefaultTableModel(chatColumns, 0);
        chatTable = new JTable(chatModel);
        chatTable.getColumnModel().getColumn(0).setPreferredWidth(200);
        chatTable.getColumnModel().getColumn(1).setPreferredWidth(300);
        JScrollPane chatScroll = new JScrollPane(chatTable);
        
        JButton refreshChats = new JButton("Refresh Chats");
        refreshChats.addActionListener(e -> loadChats());
        
        chatPanel.add(chatScroll, BorderLayout.CENTER);
        chatPanel.add(refreshChats, BorderLayout.SOUTH);
        
        tabbedPane.addTab("Users", usersPanel);
        tabbedPane.addTab("Chat History", chatPanel);
        
        add(tabbedPane, BorderLayout.CENTER);
        
        // Auto-refresh every 5 seconds
        refreshTimer = new Timer(5000, e -> {
            loadUsers();
            loadChats();
        });
        refreshTimer.start();
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
                user.createdAt,
                user.totalMessages
            });
        }
    }
    
    private void loadChats() {
        chatModel.setRowCount(0);
        List<ChatHistoryManager.ChatEntry> chats = ChatHistoryManager.getAllChats(100);
        for (ChatHistoryManager.ChatEntry chat : chats) {
            chatModel.addRow(new Object[]{
                chat.message,
                chat.response,
                chat.category,
                chat.timestamp
            });
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new AdminPanel().setVisible(true);
        });
    }
}