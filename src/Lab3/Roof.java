package Lab3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Roof extends Sprite {
    private ArrayList<Ball> balls;

    public Roof(int x, int y, int width, int height, ArrayList<Ball> balls) {
        super(x, y, width, height);
        this.balls = balls;
    }
    
    @Override
    public void update(Keyboard keyboard) {
        for (Ball ball : balls) {
            if (ball.getBounds().intersects(getBounds())) {
                // Adjust the ball's position to prevent overlapping with the roof
                ball.setY(getY() + getHeight());

                // Check the direction of the ball and adjust the vertical direction accordingly
                if (ball.getDy() < 0) {
                    // Ball is moving upwards, reverse the vertical direction
                    ball.setDy(-ball.getDy());
                }
            }
        }
    }
    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());
    }
}
