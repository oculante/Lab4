package Lab3;

import java.awt.Color;
import java.awt.Graphics2D;

public class RedBox extends Bricks {
    private int hp;
   

    public RedBox(int x, int y, int width, int height) {
        super(x, y, width, height, Color.red);
        this.hp = 3;  // Set initial hit points
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

        // Example: Draw the remaining hit points as text on the box
        graphics.setColor(Color.black);
    }

    public Color determineBoxColor() {
        // Choose color based on remaining hit points
        switch (hp) {
            case 3:
                return Color.red;
            case 2:
                return Color.orange;  // Change this to the desired color for hp = 2
            case 1:
                return Color.yellow;  // Change this to the desired color for hp = 1
            default:
                return Color.white;   // Change this to the desired color for hp = 0 or any other cases
        }
    }

	@Override
	public boolean isDestroyed() {
		// TODO Auto-generated method stub
		return hp <=0;
	}
}
