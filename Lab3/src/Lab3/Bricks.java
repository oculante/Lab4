package Lab3;

import java.awt.Color;
import java.awt.Graphics2D;
public abstract class Bricks extends Sprite {
	
	public abstract boolean isDestroyed();
	private Color color;
	public Bricks(int x, int y, int width, int height, Color color) {
	super(x, y, width, height);
	this.color = color;
	}
	
	@Override
	public void update(Keyboard keyboard) {	
	}
	
	@Override
	public void draw(Graphics2D graphics) {
		graphics.setColor(color);
		graphics.fillRect(getX (), getY(), getWidth(), getHeight());
	}
	public void setColor(Color color) {
	    this.color = color;
	}
}