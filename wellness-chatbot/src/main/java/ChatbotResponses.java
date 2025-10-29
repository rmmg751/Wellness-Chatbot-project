import java.util.*;

public class ChatbotResponses {
    private static final Random random = new Random();
    
    private static final Map<String, List<String>> KEYWORDS = new HashMap<>();
    private static final Map<String, List<String>> RESPONSES = new HashMap<>();
    
    static {
        // Keywords for categorization
        KEYWORDS.put("GREETING", Arrays.asList("hello", "hi", "hey", "good morning", "good evening"));
        KEYWORDS.put("ANXIETY", Arrays.asList("worried", "nervous", "anxious", "panic", "fear", "scared"));
        KEYWORDS.put("DEPRESSION", Arrays.asList("sad", "hopeless", "lonely", "empty", "depressed", "down"));
        KEYWORDS.put("STRESS", Arrays.asList("overwhelmed", "pressure", "tired", "exhausted", "stressed", "busy"));
        KEYWORDS.put("CRISIS", Arrays.asList("suicide", "hurt", "die", "end", "kill", "harm"));
        KEYWORDS.put("POSITIVE", Arrays.asList("happy", "good", "great", "amazing", "wonderful", "excited"));
        
        // Multiple responses for each category
        RESPONSES.put("GREETING", Arrays.asList(
            "Hello! I'm so glad you're here. How are you feeling today? 😊",
            "Hi there! Welcome to our safe space. What's on your mind?",
            "Hey! It's great to see you. I'm here to listen and support you.",
            "Good to see you! How has your day been treating you?"
        ));
        
        RESPONSES.put("ANXIETY", Arrays.asList(
            "I hear that you're feeling anxious. Let's take a deep breath together. Can you tell me more about what's making you feel this way?",
            "Anxiety can be really overwhelming. You're brave for reaching out. What's been triggering these feelings?",
            "I understand you're feeling worried. Remember, anxiety is temporary. What usually helps you feel calmer?",
            "Those anxious feelings are valid. Let's work through this together. What's your biggest concern right now?"
        ));
        
        RESPONSES.put("DEPRESSION", Arrays.asList(
            "I'm sorry you're feeling down. Remember that you're not alone, and it's okay to feel this way. Would you like to talk about it?",
            "Thank you for sharing how you're feeling. Depression is tough, but you're tougher. What's been weighing on your heart?",
            "I hear the sadness in your words. You matter, and your feelings are important. Can you tell me more?",
            "It takes courage to express these feelings. I'm here with you. What's been the hardest part lately?"
        ));
        
        RESPONSES.put("STRESS", Arrays.asList(
            "It sounds like you're under a lot of pressure. Let's break this down into smaller, manageable pieces. What's the biggest stressor right now?",
            "Stress can feel overwhelming, but you're handling more than you realize. What's been piling up for you?",
            "I can sense you're feeling stretched thin. You're doing your best. What's been most challenging?",
            "Being overwhelmed is exhausting. Let's find some ways to lighten your load. What's causing the most stress?"
        ));
        
        RESPONSES.put("CRISIS", Arrays.asList(
            "I'm very concerned about what you're saying. Please know that you matter and help is available. Would you like the number for a crisis helpline? They're available 24/7.",
            "Your life has value and meaning. I'm worried about you. Please reach out to a crisis counselor - they're trained to help. Can I provide some resources?",
            "I hear you're in a lot of pain right now. You don't have to go through this alone. There are people who want to help you. Can we talk about getting you some immediate support?"
        ));
        
        RESPONSES.put("POSITIVE", Arrays.asList(
            "That's wonderful to hear! I'm so happy you're feeling good. What's been going well for you?",
            "It's great that you're in a positive space! Those good feelings are important to celebrate. What's bringing you joy?",
            "I love hearing when you're doing well! Keep nurturing those positive feelings. What's been the highlight?",
            "Your positive energy is contagious! It's beautiful to see you thriving. What's been working for you?"
        ));
        
        RESPONSES.put("GENERAL", Arrays.asList(
            "I'm here to support you. How can I help you today?",
            "Thank you for sharing with me. What would be most helpful right now?",
            "I'm listening. What's been on your mind lately?",
            "You have my full attention. What would you like to talk about?",
            "I'm glad you're here. How are you taking care of yourself today?"
        ));
    }
    
    public static String categorizeInput(String input) {
        String lowerInput = input.toLowerCase();
        for (Map.Entry<String, List<String>> entry : KEYWORDS.entrySet()) {
            for (String keyword : entry.getValue()) {
                if (lowerInput.contains(keyword)) {
                    return entry.getKey();
                }
            }
        }
        return "GENERAL";
    }
    
    public static String getResponse(String input, String category) {
        List<String> responses = RESPONSES.get(category);
        if (responses != null && !responses.isEmpty()) {
            return responses.get(random.nextInt(responses.size()));
        }
        return RESPONSES.get("GENERAL").get(random.nextInt(RESPONSES.get("GENERAL").size()));
    }
}