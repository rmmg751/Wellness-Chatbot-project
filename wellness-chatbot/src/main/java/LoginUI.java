import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginUI extends JFrame {
    private static final Color DEEP_PURPLE = new Color(103, 58, 183);
    private static final Color LIGHT_PURPLE = new Color(159, 168, 218);
    private static final Color SOFT_BLUE = new Color(230, 245, 255);
    private static final Color ACCENT_GREEN = new Color(76, 175, 80);
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private boolean isRegistering = false;
    
    public LoginUI() {
        setTitle("Wellness Chatbot - Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
    }
    
    private void initializeComponents() {
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
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Title
        JLabel titleLabel = new JLabel("🌟 Welcome 🌟", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(DEEP_PURPLE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        formPanel.add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1; gbc.gridy++;
        JLabel userLabel = new JLabel("Username:");
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(userLabel, gbc);
        
        gbc.gridx = 1;
        usernameField = new JTextField(15);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_PURPLE, 2, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        formPanel.add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0; gbc.gridy++;
        JLabel passLabel = new JLabel("Password:");
        passLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        formPanel.add(passLabel, gbc);
        
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_PURPLE, 2, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        formPanel.add(passwordField, gbc);
        
        // Email (hidden initially)
        gbc.gridx = 0; gbc.gridy++;
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        emailLabel.setVisible(false);
        formPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        emailField = new JTextField(15);
        emailField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(LIGHT_PURPLE, 2, true),
            new EmptyBorder(8, 10, 8, 10)
        ));
        emailField.setVisible(false);
        formPanel.add(emailField, gbc);
        
        // Login Button
        gbc.gridx = 0; gbc.gridy++; gbc.gridwidth = 2;
        JButton loginButton = createStyledButton("Login 🚀");
        loginButton.addActionListener(e -> handleLogin());
        formPanel.add(loginButton, gbc);
        
        // Register Button
        gbc.gridy++;
        JButton registerButton = createStyledButton("Register 📝");
        registerButton.addActionListener(e -> toggleRegistration());
        formPanel.add(registerButton, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);
    }
    
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
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
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBorder(new EmptyBorder(12, 20, 12, 20));
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }
    
    private void toggleRegistration() {
        isRegistering = !isRegistering;
        Component[] components = ((JPanel) getContentPane().getComponent(0)).getComponents();
        JPanel formPanel = (JPanel) components[0];
        
        for (Component comp : formPanel.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals("Email:")) {
                comp.setVisible(isRegistering);
            }
        }
        emailField.setVisible(isRegistering);
        
        JButton registerBtn = null;
        for (Component comp : formPanel.getComponents()) {
            if (comp instanceof JButton && ((JButton) comp).getText().contains("Register")) {
                registerBtn = (JButton) comp;
                break;
            }
        }
        
        if (registerBtn != null) {
            registerBtn.setText(isRegistering ? "Back to Login 🔙" : "Register 📝");
        }
        
        setTitle(isRegistering ? "Wellness Chatbot - Register" : "Wellness Chatbot - Login");
        revalidate();
        repaint();
    }
    
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            showMessage("Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (isRegistering) {
            String email = emailField.getText().trim();
            if (email.isEmpty()) {
                showMessage("Please enter your email!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            registerUser(username, password, email);
        } else {
            loginUser(username, password);
        }
    }
    
    private void registerUser(String username, String password, String email) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Check if username exists
            String checkQuery = "SELECT username FROM Users WHERE username = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                showMessage("Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Insert new user
            String insertQuery = "INSERT INTO Users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
            insertStmt.setString(1, username);
            insertStmt.setString(2, hashPassword(password));
            insertStmt.setString(3, email);
            
            int result = insertStmt.executeUpdate();
            if (result > 0) {
                showMessage("Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                toggleRegistration(); // Switch back to login
                clearFields();
            }
        } catch (SQLException e) {
            showMessage("Registration failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loginUser(String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "SELECT user_id, password FROM Users WHERE username = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password");
                if (storedPassword.equals(hashPassword(password))) {
                    int userId = rs.getInt("user_id");
                    showMessage("Login successful! Welcome " + username + "! 🎉", "Success", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Open chatbot with user ID
                    SwingUtilities.invokeLater(() -> {
                        new WellnessChatbotUI(userId, username).setVisible(true);
                        dispose();
                    });
                } else {
                    showMessage("Invalid password!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                showMessage("Username not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            showMessage("Login failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return password; // Fallback (not recommended for production)
        }
    }
    
    private void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        emailField.setText("");
    }
    
    private void showMessage(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginUI().setVisible(true);
        });
    }
}