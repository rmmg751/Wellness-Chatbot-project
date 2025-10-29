import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WellnessChatbotUIDemo extends JFrame {
    private static final Color DEEP_PURPLE = new Color(103, 58, 183);
    private static final Color LIGHT_PURPLE = new Color(159, 168, 218);
    private static final Color SOFT_BLUE = new Color(230, 245, 255);
    private static final Color ACCENT_GREEN = new Color(76, 175, 80);
    
    private JTextArea chatArea;
    private JTextField inputField;
    private int currentUserId;
    private String currentUsername;
    
    public WellnessChatbotUIDemo(int userId, String username) {
        this.currentUserId = userId;
        this.currentUsername = username;
        setTitle("Wellness Chatbot - Welcome " + username + "! (Demo Mode)");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
    }

    private void initializeComponents() {
        // Main panel with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, SOFT_BLUE, 0, getHeight(), Color.WHITE);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Header panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(15, 0, 10, 0));
        
        JLabel titleLabel = new JLabel("🌟 Welcome " + currentUsername + "! (Demo) 🌟");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(DEEP_PURPLE);
        
        // Logout button
        JButton logoutBtn = new JButton("Logout 🚪");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        logoutBtn.setBackground(Color.RED);
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        logoutBtn.setFocusPainted(false);
        logoutBtn.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to logout?", "Logout", JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION) {
                new LoginUIDemo().setVisible(true);
                dispose();
            }
        });
        
        JPanel headerContainer = new JPanel(new BorderLayout());
        headerContainer.setOpaque(false);
        headerContainer.add(titleLabel, BorderLayout.CENTER);
        headerContainer.add(logoutBtn, BorderLayout.EAST);
        headerPanel.add(headerContainer);
        
        // Chat area with custom styling
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setBackground(Color.WHITE);
        chatArea.setBorder(new EmptyBorder(15, 15, 15, 15));
        
        JScrollPane scrollPane = new JScrollPane(chatArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
            new EmptyBorder(10, 20, 10, 20),
            BorderFactory.createLineBorder(LIGHT_PURPLE, 2, true)
        ));
        scrollPane.setBackground(Color.WHITE);
        
        // Input panel with enhanced styling
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setOpaque(false);
        inputPanel.setBorder(new EmptyBorder(10, 20, 20, 20));
        
        inputField = new JTextField();
        inputField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_PURPLE, 2, true),
            new EmptyBorder(10, 15, 10, 15)
        ));
        inputField.setBackground(Color.WHITE);
        
        // Enhanced send button
        JButton sendButton = new JButton("Send 💬") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gradient = new GradientPaint(0, 0, DEEP_PURPLE, 0, getHeight(), LIGHT_PURPLE);
                g2d.setPaint(gradient);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        sendButton.setForeground(Color.WHITE);
        sendButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        sendButton.setBorder(new EmptyBorder(12, 20, 12, 20));
        sendButton.setOpaque(false);
        sendButton.setContentAreaFilled(false);
        sendButton.setFocusPainted(false);
        
        // Quick response buttons
        JPanel quickPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        quickPanel.setOpaque(false);
        
        String[] quickResponses = {"😊 I'm good", "😔 Feeling down", "😰 Anxious", "😴 Tired", "❓ Need help"};
        for (String response : quickResponses) {
            JButton quickBtn = createQuickButton(response);
            quickPanel.add(quickBtn);
        }
        
        // Add action listeners
        sendButton.addActionListener(new SendMessageListener());
        inputField.addActionListener(new SendMessageListener());
        
        // Layout assembly
        JPanel inputContainer = new JPanel(new BorderLayout());
        inputContainer.setOpaque(false);
        inputContainer.add(inputField, BorderLayout.CENTER);
        inputContainer.add(sendButton, BorderLayout.EAST);
        
        inputPanel.add(quickPanel, BorderLayout.NORTH);
        inputPanel.add(inputContainer, BorderLayout.CENTER);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Welcome message with emoji
        appendBotMessage("Hi " + currentUsername + "! I'm here to listen and support you. How are you feeling today? 😊");
        appendBotMessage("(Demo Mode: Conversations won't be saved to database)");
    }
    
    private JButton createQuickButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setBackground(ACCENT_GREEN);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        btn.setFocusPainted(false);
        btn.addActionListener(e -> {
            String cleanText = text.replaceAll("[^a-zA-Z\\s]", "").trim();
            inputField.setText(cleanText);
            new SendMessageListener().actionPerformed(null);
        });
        return btn;
    }
    
    private void appendBotMessage(String message) {
        chatArea.append("🤖 Chatbot: " + message + "\n\n");
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }
    
    private void appendUserMessage(String message) {
        chatArea.append("👤 You: " + message + "\n");
    }
    
    private class SendMessageListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String userMessage = inputField.getText().trim();
            if (!userMessage.isEmpty()) {
                appendUserMessage(userMessage);
                
                // Add typing indicator
                chatArea.append("🤖 Chatbot is typing...\n");
                chatArea.setCaretPosition(chatArea.getDocument().getLength());
                
                // Simulate thinking time
                Timer timer = new Timer(1000, event -> {
                    // Remove typing indicator
                    String text = chatArea.getText();
                    int lastIndex = text.lastIndexOf("🤖 Chatbot is typing...\n");
                    if (lastIndex != -1) {
                        chatArea.setText(text.substring(0, lastIndex));
                    }
                    
                    String category = ChatbotResponses.categorizeInput(userMessage);
                    String response = ChatbotResponses.getResponse(userMessage, category);
                    
                    appendBotMessage(response);
                    
                    // In demo mode, just print to console instead of database
                    System.out.println("Demo Log - User: " + userMessage + " | Category: " + category);
                });
                timer.setRepeats(false);
                timer.start();
                
                inputField.setText("");
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            // Start with login screen
            new LoginUIDemo().setVisible(true);
        });
    }
}