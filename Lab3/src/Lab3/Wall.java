package Lab3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Wall extends Sprite {
    private ArrayList<Wall> walls;
    private ArrayList<Ball> balls;

    public Wall(int x, int y, int width, int height, ArrayList<Wall> walls, ArrayList<Ball> balls) {
        super(x, y, width, height);
        this.walls = walls;
        this.balls = balls;
    }

    @Override
    public void update(Keyboard keyboard) {
        for (Ball ball : balls) {
            if (getBounds().intersects(ball.getBounds())) {
                // Check if the ball hits the horizontal walls (top or bottom)
                if (getHeight() == 1) {
                    // Reverse the vertical direction of the ball
                    ball.setDy(-ball.getDy());
                    // Adjust the ball's position to prevent overlapping with the wall
                    if (getY() == 0) {
                        ball.setY(getY() + getHeight());
                    } else {
                        ball.setY(getY() - ball.getHeight());
                    }
                } else {
                    // Reverse the horizontal direction of the ball
                    ball.setDx(-ball.getDx());
                    // Adjust the ball's position to prevent overlapping with the wall
                    if (getX() == 0) {
                        ball.setX(getX() + getWidth());
                    } else {
                        ball.setX(getX() - ball.getWidth());
                    }
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
