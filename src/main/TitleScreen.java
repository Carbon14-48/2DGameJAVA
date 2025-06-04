package main;
import java.awt.*;

public class TitleScreen {
    private GamePanel gp;
    private Graphics2D g2;
    
    // Animation variables
    private int titleScreenCounter = 0;
    private float titlePulse = 0;
    private int starCount = 50;
    private int[] starX = new int[starCount];
    private int[] starY = new int[starCount];
    private int[] starSpeed = new int[starCount];
    private boolean titleScreenInitialized = false;
    
    public TitleScreen(GamePanel gp) {
        this.gp = gp;
    }
    
    // Initialize stars for background
    private void initializeTitleScreen() {
        if (!titleScreenInitialized) {
            for (int i = 0; i < starCount; i++) {
                starX[i] = (int)(Math.random() * gp.screenWidth);
                starY[i] = (int)(Math.random() * gp.screenHeight);
                starSpeed[i] = (int)(Math.random() * 3) + 1;
            }
            titleScreenInitialized = true;
        }
    }
    
    public void drawTitleScreen(Graphics2D g2, int commandNum) {
        this.g2 = g2;
        
        initializeTitleScreen();
        titleScreenCounter++;
        titlePulse += 0.1f;
        
        // Animated starfield background
        drawStarfield();
        
        // Gradient background overlay
        drawGradientOverlay();
        
        // Main title with glow effect
        drawMainTitle();
        
        // Subtitle
        drawSubtitle();
        
        // Menu options (only 3 options now)
        drawMenuOptions(commandNum);
        
        // Pulsing "Press Enter" text
        drawPressEnter();
        
        // Cool border effect
        drawBorder();
    }
    
    private void drawStarfield() {
        g2.setColor(Color.WHITE);
        for (int i = 0; i < starCount; i++) {
            // Move stars
            starX[i] -= starSpeed[i];
            if (starX[i] < 0) {
                starX[i] = gp.screenWidth;
                starY[i] = (int)(Math.random() * gp.screenHeight);
            }
            
            // Draw stars with different sizes
            if (starSpeed[i] == 1) {
                g2.fillRect(starX[i], starY[i], 1, 1);
            } else if (starSpeed[i] == 2) {
                g2.fillRect(starX[i], starY[i], 2, 2);
            } else {
                g2.fillRect(starX[i], starY[i], 3, 3);
                // Add glow to fast stars
                g2.setColor(new Color(255, 255, 255, 100));
                g2.fillRect(starX[i]-1, starY[i]-1, 5, 5);
                g2.setColor(Color.WHITE);
            }
        }
    }
    
    private void drawGradientOverlay() {
        // Create a dark gradient overlay
        for (int i = 0; i < gp.screenHeight; i++) {
            int alpha = (int)(100 * (1.0 - (float)i / gp.screenHeight));
            if (alpha > 0) {
                g2.setColor(new Color(0, 0, 0, alpha));
                g2.fillRect(0, i, gp.screenWidth, 1);
            }
        }
    }
    
    private void drawMainTitle() {
        // Glow effect for title
        String title = "FINDING MEOW";
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F)); // Much smaller!
        
        // Calculate position
        int x = getXToCenterText(title);
        int y = 80; // Fixed position instead of tileSize calculation
        
        // Pulsing size effect
        float pulse = (float)(Math.sin(titlePulse) * 0.05 + 1.0); // Smaller pulse
        Font pulsedFont = g2.getFont().deriveFont(48F * pulse);
        g2.setFont(pulsedFont);
        x = getXToCenterText(title); // Recalculate with new font size
        
        // Draw glow layers (fewer layers)
        for (int i = 4; i >= 1; i--) {
            int alpha = 30 / i;
            g2.setColor(new Color(0, 255, 255, alpha)); // Cyan glow
            g2.drawString(title, x - i, y - i);
            g2.drawString(title, x + i, y + i);
            g2.drawString(title, x - i, y + i);
            g2.drawString(title, x + i, y - i);
        }
        
        // Main title with gradient effect
        g2.setColor(new Color(255, 215, 0)); // Gold
        g2.drawString(title, x, y);
        
        // Add sparkle effects
        drawSparkles(x, y, title);
    }
    
    private void drawSparkles(int titleX, int titleY, String title) {
        // Random sparkles around the title
        g2.setColor(Color.WHITE);
        for (int i = 0; i < 5; i++) { // Fewer sparkles
            if ((titleScreenCounter + i * 10) % 60 < 30) {
                int sparkleX = titleX + (int)(Math.random() * g2.getFontMetrics().stringWidth(title));
                int sparkleY = titleY + (int)(Math.random() * 20) - 30; // Smaller area
                
                // Draw cross-shaped sparkle
                g2.fillRect(sparkleX, sparkleY, 3, 1);
                g2.fillRect(sparkleX + 1, sparkleY - 1, 1, 3);
            }
        }
    }
    
    private void drawSubtitle() {
        g2.setFont(g2.getFont().deriveFont(Font.ITALIC, 18F)); // Much smaller
        String subtitle = "~ An Epic Adventure Awaits ~";
        int x = getXToCenterText(subtitle);
        int y = 130; // Fixed position
        
        // Floating effect
        int floatOffset = (int)(Math.sin(titlePulse * 0.5) * 3);
        y += floatOffset;
        
        // Shadow
        g2.setColor(new Color(0, 0, 0, 150));
        g2.drawString(subtitle, x + 1, y + 1);
        
        // Main text
        g2.setColor(new Color(200, 200, 255));
        g2.drawString(subtitle, x, y);
    }
    private void drawMenuOptions(int commandNum) { // Add commandNum parameter
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 24F));
        
        // NEW GAME
        String text = "NEW GAME";
        int x = getXToCenterText(text);
        int y = 200;
        g2.setColor(Color.WHITE);
        if (commandNum == 0) {
            g2.setColor(Color.YELLOW); // Highlight selected option
            g2.drawString(">", x - 30, y); // Draw cursor
        }
        g2.drawString(text, x, y);
        
        // LOAD GAME
        text = "LOAD GAME";
        x = getXToCenterText(text);
        y += 60;
        g2.setColor(Color.WHITE);
        if (commandNum == 1) {
            g2.setColor(Color.YELLOW); // Highlight selected option
            g2.drawString(">", x - 30, y); // Draw cursor
        }
        g2.drawString(text, x, y);
        
        // QUIT
        text = "QUIT";
        x = getXToCenterText(text);
        y += 60;
        g2.setColor(Color.WHITE);
        if (commandNum == 2) {
            g2.setColor(Color.YELLOW); // Highlight selected option
            g2.drawString(">", x - 30, y); // Draw cursor
        }
        g2.drawString(text, x, y);
    }
    
    private void drawPressEnter() {
        if ((titleScreenCounter / 30) % 2 == 0) { // Blink every 30 frames
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 14F)); // Smaller text
            String text = "Press ENTER to Start Your Adventure";
            int x = getXToCenterText(text);
            int y = gp.screenHeight - 40; // Bottom of screen
            
            // Rainbow effect
            float hue = (titleScreenCounter * 0.02f) % 1.0f;
            Color rainbowColor = Color.getHSBColor(hue, 1.0f, 1.0f);
            
            g2.setColor(rainbowColor);
            g2.drawString(text, x, y);
        }
    }
    
    private void drawBorder() {
        // Animated border
        int thickness = 3; // Thinner border
        Color borderColor = Color.getHSBColor((titleScreenCounter * 0.01f) % 1.0f, 0.8f, 0.9f);
        g2.setColor(borderColor);
        
        // Top
        g2.fillRect(0, 0, gp.screenWidth, thickness);
        // Bottom  
        g2.fillRect(0, gp.screenHeight - thickness, gp.screenWidth, thickness);
        // Left
        g2.fillRect(0, 0, thickness, gp.screenHeight);
        // Right
        g2.fillRect(gp.screenWidth - thickness, 0, thickness, gp.screenHeight);
        
        // Corner decorations
        g2.setColor(Color.YELLOW);
        int cornerSize = 10; // Smaller corners
        // Top-left corner
        g2.fillRect(0, 0, cornerSize, cornerSize);
        // Top-right corner
        g2.fillRect(gp.screenWidth - cornerSize, 0, cornerSize, cornerSize);
        // Bottom-left corner
        g2.fillRect(0, gp.screenHeight - cornerSize, cornerSize, cornerSize);
        // Bottom-right corner
        g2.fillRect(gp.screenWidth - cornerSize, gp.screenHeight - cornerSize, cornerSize, cornerSize);
    }
    
    private int getXToCenterText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}