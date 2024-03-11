package Lab3;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class SquareCollection {
    private ArrayList<Bricks> coloredBoxes;
    private ArrayList<Ball> balls;
    private Score score;
    private ScoreManager scoreManager;
    private Iterator<Bricks> iterator;
    private Random r;

    private static final int MAX_X_COORDINATE = 799;
    private static final int BOX_WIDTH = 50;
    private static final int BOX_HEIGHT = 20;
    private static final int COLUMN_SPACING = 60;
    private static final int ROW_SPACING = 30;

    public SquareCollection(ArrayList<Ball> balls, Score score, ScoreManager scoreManager) {
        this.balls = balls;
        this.score = score;
        this.scoreManager = scoreManager;
        coloredBoxes = new ArrayList<>();
        int numberOfColumns = 13;
        int numberOfRows = 7;
        int initialX = 15;
        int initialY = 10;
        r = new Random();

        for (int col = 1; col < numberOfColumns - 1; col++) {
            for (int row = 0; row < numberOfRows; row++) {
                int colorType = r.nextInt(100); // Increase range to 100
                if (colorType < 20) { // 20% probability for red bricks
                    coloredBoxes.add(new RedBox(initialX + col * COLUMN_SPACING, initialY + row * ROW_SPACING, BOX_WIDTH, BOX_HEIGHT));
                } else if (colorType < 50) { // 30% probability for blue bricks
                    coloredBoxes.add(new BlueBox(initialX + col * COLUMN_SPACING, initialY + row * ROW_SPACING, BOX_WIDTH, BOX_HEIGHT));
                } else { // 50% probability for green bricks
                    coloredBoxes.add(new GreenBox(initialX + col * COLUMN_SPACING, initialY + row * ROW_SPACING, BOX_WIDTH, BOX_HEIGHT));
                }
            }
        }

        iterator = coloredBoxes.iterator();
    }

    public void update(Keyboard keyboard) {
        while (iterator.hasNext()) {
            Bricks box = iterator.next();
            box.update(keyboard);

            for (Ball ball : balls) {
                if (!ball.isCollisionDetectionDisabled() && box.getBounds().intersects(ball.getBounds())) {
                    handleBoxCollision(box, ball);
                }
            }

            if (box.getX() > MAX_X_COORDINATE) {
                iterator.remove();
            }
        }

        iterator = coloredBoxes.iterator();
    }
    
    private void handleBoxCollision(Bricks box, Ball ball) {
        int ballCenterX = ball.getX() + ball.getWidth() / 2;
        int ballCenterY = ball.getY() + ball.getHeight() / 2;
        int boxCenterX = box.getX() + box.getWidth() / 2;
        int boxCenterY = box.getY() + box.getHeight() / 2;
        int dx = ballCenterX - boxCenterX;
        int dy = ballCenterY - boxCenterY;
        double aspectRatio = (double) box.getWidth() / box.getHeight();

        if (Math.abs(dx / aspectRatio) > Math.abs(dy)) {
            // Reflect horizontally
            ball.setDx(-ball.getDx());
            // Move the ball out of collision with the box
            if (dx < 0) {
                ball.setX(box.getX() - ball.getWidth());
            } else {
                ball.setX(box.getX() + box.getWidth());
            }
        } else {
            // Reflect vertically
            ball.setDy(-ball.getDy());
            // Move the ball out of collision with the box
            if (dy < 0) {
                ball.setY(box.getY() - ball.getHeight());
            } else {
                ball.setY(box.getY() + box.getHeight());
            }
        }

        // Reduce box health
        if (box instanceof RedBox) {
            ((RedBox) box).reduceHp();
        } else if (box instanceof BlueBox) {
            ((BlueBox) box).reduceHp();
        } else if (box instanceof GreenBox) {
            ((GreenBox) box).reduceHp();
        }

        // Handle box destruction
        if (box.isDestroyed()) {
            iterator.remove();
            score.incrementScore(box);
        } else {
            // Change box color based on health
            if (box instanceof RedBox) {
                Color boxColor = ((RedBox) box).determineBoxColor();
                box.setColor(boxColor);
            } else if (box instanceof BlueBox) {
                Color boxColor = ((BlueBox) box).determineBoxColor();
                box.setColor(boxColor);
            } else if (box instanceof GreenBox) {
                Color boxColor = ((GreenBox) box).determineBoxColor();
                box.setColor(boxColor);
            }
        }
    }



    public void draw(Graphics2D graphics) {
        for (Bricks box : coloredBoxes) {
            box.draw(graphics);
        }
    }
}