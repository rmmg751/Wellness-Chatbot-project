import javax.swing.*;

public class DemoLogin {
    public static void main(String[] args) {
        System.out.println("🚀 Starting Wellness Chatbot Demo...");
        System.out.println("Note: This demo runs without database connection");
        System.out.println("To enable full functionality, set up MySQL connector");
        System.out.println();
        
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // Show demo message
            JOptionPane.showMessageDialog(null, 
                "🌟 Welcome to Wellness Chatbot! 🌟\n\n" +
                "This is a demo version.\n" +
                "For full functionality with user accounts,\n" +
                "please set up MySQL connector.\n\n" +
                "Click OK to see the login interface!", 
                "Demo Mode", 
                JOptionPane.INFORMATION_MESSAGE);
            
            new LoginUI().setVisible(true);
        });
    }
}