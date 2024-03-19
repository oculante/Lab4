package Lab3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Score {
    private int score = 0;
    private Game game;

    // Define point values for different box types
    private static final int RED_BOX_POINTS = 3;
    private static final int BLUE_BOX_POINTS = 2;
    private static final int GREEN_BOX_POINTS = 1;

    public Score(Game game) {
        this.game = game;
    }

    public void incrementScore(Bricks box) {
        if (box instanceof RedBox) {
            score += RED_BOX_POINTS;
        } else if (box instanceof BlueBox) {
            score += BLUE_BOX_POINTS;
        } else if (box instanceof GreenBox) {
            score += GREEN_BOX_POINTS;
        }
    }

    public int getScore() {
        return score;
    }

    public void draw(Graphics2D graphics) {
        // Draw other game elements (bricks, bat, balls, etc.)

        // Draw score
        graphics.setColor(Color.white);
        Font font = new Font("Arial", Font.BOLD, 20);
        graphics.setFont(font);
        graphics.drawString("Score: " + score, 10, 600 - 20);

    }

    // Handle restart logic when 'R' is pressed
    public void restart() {
        // Reset score
        score = 0;
    }
}
