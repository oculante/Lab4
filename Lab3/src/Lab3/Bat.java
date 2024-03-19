package Lab3;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.List;

public class Bat extends Sprite {
    private char[] initials = {'A', 'S'};
    private List<Ball> balls;

    public Bat(int x, int y, int width, int height, List<Ball> balls) {
        super(x, y, width, height);
        this.balls = balls;
    }

    public void update(Keyboard keyboard) {
        if (keyboard.isKeyDown(Key.Left)) {
            setX(getX() - Const.Speed);
        }
        if (keyboard.isKeyDown(Key.Right)) {
            setX(getX() + Const.Speed);
        }

        for (Ball ball : balls) {
            if (getBounds().intersects(ball.getBounds())) {
                int ballCenterX = ball.getX() + ball.getWidth() / 2;
                int ballCenterY = ball.getY() + ball.getHeight() / 2;

                int batCenterX = getX() + getWidth() / 2;
                int batCenterY = getY() + getHeight() / 2;

                int dx = ballCenterX - batCenterX;
                int dy = ballCenterY - batCenterY;

                int halfWidth = getWidth() / 2;

                if (dx < 0 && dx > -halfWidth) {
                    // Ball hits the left half of the bat, bounce normally
                    ball.setDy(-Math.abs(ball.getDy())); // Ensure the direction is upward
                    ball.setDx(-Math.abs(ball.getDx())); // Ensure the direction is towards the left
                } else if (dx >= 0 && dx < halfWidth) {
                    // Ball hits the right half of the bat, bounce normally
                    ball.setDy(-Math.abs(ball.getDy())); // Ensure the direction is upward
                    ball.setDx(Math.abs(ball.getDx())); // Ensure the direction is towards the right
                }
            }
        }
    }


    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.white);
        graphics.fillRect(getX(), getY(), getWidth(), getHeight());

        // Set font for drawing initials
        Font font = new Font("Arial", Font.BOLD, 14);
        graphics.setFont(font);
        graphics.setColor(Color.red);

        // Draw initials in the middle of the bat
        int centerX = getX() + getWidth() / 2;
        int centerY = getY() + getHeight() / 2;

        for (int i = 0; i < initials.length; i++) {
            char initial = initials[i];
            graphics.drawString(Character.toString(initial), centerX - 10 + i * 10, centerY + 5);
        }
    }
}
