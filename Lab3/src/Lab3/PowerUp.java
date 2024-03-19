package Lab3;
import java.awt.Color;
import java.awt.Graphics2D;

public class PowerUp extends Sprite {
    private int speed;

    public PowerUp(int x, int y, int diameter, int speed) {
        super(x, y, diameter, diameter);
        this.speed = 3;
    }

    @Override
    public void update(Keyboard keyboard) {
        setY(getY() + speed); // Make the power-up float down
    }

    @Override
    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.cyan);
        graphics.fillOval(getX(), getY(), getWidth(), getHeight());
    }

	public void applyEffect() {
	}
}