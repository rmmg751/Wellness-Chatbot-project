import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class LoginUIDemo extends JFrame {
    private static final Color DEEP_PURPLE = new Color(103, 58, 183);
    private static final Color LIGHT_PURPLE = new Color(159, 168, 218);
    private static final Color SOFT_BLUE = new Color(230, 245, 255);
    
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;
    private boolean isRegistering = false;
    
    // Demo users storage (in-memory)
    private static Map<String, String> demoUsers = new HashMap<>();
    private static int nextUserId = 1;
    
    static {
        // Pre-populate with demo user
        demoUsers.put("demo", "demo123");
    }
    
    public LoginUIDemo() {
        setTitle("Wellness Chatbot - Login (Demo Mode)");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initializeComponents();
        
        // Show demo instructions
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                "🌟 Demo Mode Instructions 🌟\n\n" +
                "Try these demo credentials:\n" +
                "Username: demo\n" +
                "Password: demo123\n\n" +
                "Or register a new account!\n" +
                "(Data won't persist after closing)", 
                "Demo Mode", 
                JOptionPane.INFORMATION_MESSAGE);
        });
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
        JLabel titleLabel = new JLabel("🌟 Welcome (Demo) 🌟", SwingConstants.CENTER);
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
        
        setTitle(isRegistering ? "Wellness Chatbot - Register (Demo)" : "Wellness Chatbot - Login (Demo)");
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
        if (demoUsers.containsKey(username)) {
            showMessage("Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        demoUsers.put(username, password);
        showMessage("Registration successful! Please login.", "Success", JOptionPane.INFORMATION_MESSAGE);
        toggleRegistration();
        clearFields();
    }
    
    private void loginUser(String username, String password) {
        if (demoUsers.containsKey(username) && demoUsers.get(username).equals(password)) {
            showMessage("Login successful! Welcome " + username + "! 🎉", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            // Open chatbot with demo user ID
            SwingUtilities.invokeLater(() -> {
                new WellnessChatbotUIDemo(nextUserId++, username).setVisible(true);
                dispose();
            });
        } else {
            showMessage("Invalid username or password!", "Error", JOptionPane.ERROR_MESSAGE);
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
            new LoginUIDemo().setVisible(true);
        });
    }
}