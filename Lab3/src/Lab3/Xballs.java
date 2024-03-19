/*package Lab3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Xballs extends PowerUp {
    private static final int POWER_UP_SIZE = 10;
    private static final Color POWER_UP_COLOR = Color.cyan;
    private static final int POWER_UP_CHANCE = 25;
    private ArrayList<Ball> balls;
    
    public Xballs(int x, int y, ArrayList <Ball> balls) {
        super(x, y, POWER_UP_SIZE, POWER_UP_SIZE);
        this.balls = balls;
    }

    @Override
    public void applyEffect() {
        // Multiply the number of balls by 4
        for (int i = 0; i < 4; i++) { // Add 3 additional balls (since we already have one)
            balls.add(new Ball(getX(), getY(), Const.BallSize, Const.BallSize));
        }
    }
    public static void generatePowerUp() {
        int randomNumber = (int) (Math.random() * 100); 
        if (randomNumber < POWER_UP_CHANCE) {
            PowerUp Xballs = new PowerUp(Ball.getDx(), Ball.getDy(), POWER_UP_SIZE, POWER_UP_SIZE);
            
        }
    }

    @Override
    public void draw(Graphics2D graphics) {
        // Draw the power-up as a small cyan circle
        graphics.setColor(POWER_UP_COLOR);
        graphics.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}*/