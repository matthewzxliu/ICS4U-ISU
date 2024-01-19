import java.awt.*;
import java.awt.image.*;

public class Bomb {
    
    private BufferedImage bombImg;
    private int x;
    private int y;

    public int getX()
    {
        return (int) Math.round(x / 40.0) * 40;
    }

    public int getY()
    {
        return (int) Math.round(y / 40.0) * 40;
    }

    public Bomb(BufferedImage bombImg, int x, int y)
    {
        this.bombImg = bombImg;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g)
    {
        int roundedX = (int) Math.round(x / 40.0) * 40;
        int roundedY = (int) Math.round(y / 40.0) * 40;
        g.drawImage(bombImg, roundedX, roundedY, null);
    }
}
