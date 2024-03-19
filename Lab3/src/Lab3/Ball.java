package Lab3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.Random;

public class Ball extends Sprite {

    private Color color;
    private int dx;
    private int dy;
    private Random r;
    private int tickCounter = 0;
    public boolean isOutOfBoard = false;
    private boolean disableCollisionDetection = false;

    public Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        r = new Random();
        
        color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
        dx = 1; // Initial horizontal speed
        dy = -3; // Initial vertical speed
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    @Override
    public void update(Keyboard keyboard) {
        tickCounter++;
        if (tickCounter % 20 == 0)
            color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));

        setX(getX() + dx);
        setY(getY() + dy);

        // Check for collision with the roof
        if (getY() <= 0) {
            dy = -dy;
        }
        // Check if the ball goes out of the board
        if (getY() + getHeight() < 0 || getY() > 600) {
            isOutOfBoard = true;
        }
    }

    public void disableCollisionDetectionTemporarily() {
        disableCollisionDetection = true;
        // You can set a timer here to re-enable collision detection after a certain time
    }

    public boolean isCollisionDetectionDisabled() {
        return disableCollisionDetection;
    }

    public boolean isOutOfBoard() {
        return isOutOfBoard;
    }

    public void reset(int x, int y) {
        setX(x);
        setY(y);
        isOutOfBoard = false; // Reset the flag
        disableCollisionDetection = false;
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.white);
        graphics.fillOval(getX(), getY(), getWidth(), getHeight());
    }
}
