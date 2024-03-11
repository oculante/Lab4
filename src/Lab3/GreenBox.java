package Lab3;
import java.awt.Color;
import java.awt.Graphics2D;
public class GreenBox extends Bricks
{
    private int hp;
	public GreenBox(int x, int y, int width, int height) {
		super(x, y, width, height, Color.green);	
	}


public void reduceHp() {
    if (hp > 0) {
        hp--;
    }
}

public int getHp() {
    return hp;
}

@Override
public void draw(Graphics2D graphics) {
    // Set the color based on the remaining hit points
    Color boxColor = determineBoxColor();
    graphics.setColor(boxColor);

    // Draw the box with the determined color
    super.draw(graphics);
	}
	public Color determineBoxColor() {
    // Choose color based on remaining hit points
    switch (hp) {
        case 2:
            return Color.blue;
        case 1:
            return Color.cyan;  // Change this to the desired color for hp = 2
        default:
            return Color.white;   // Change this to the desired color for hp = 0 or any other cases
    }
}


	@Override
	public boolean isDestroyed() {

		return hp <=0;
	}
}

