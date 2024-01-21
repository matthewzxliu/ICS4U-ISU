import java.awt.*;
import java.awt.image.*;

// bomb class

public class Bomb {

    // variables
    private BufferedImage bombImg;
    private int x;
    private int y;

    // getters
    public int getX()
    {
        return (int) Math.round(x / 40.0) * 40;
    }

    public int getY()
    {
        return (int) Math.round(y / 40.0) * 40;
    }

    // constructor
    public Bomb(BufferedImage bombImg, int x, int y)
    {
        this.bombImg = bombImg;
        this.x = x;
        this.y = y;
    }

    // Purpose: drawing the bombs
    // Parameter: graphics object to allow drawing
    // Return: n/a
    public void draw(Graphics g)
    {
        // gets location of bomb and draws it
        int roundedX = (int) Math.round(x / 40.0) * 40;
        int roundedY = (int) Math.round(y / 40.0) * 40;
        g.drawImage(bombImg, roundedX, roundedY, null);
    }
}
