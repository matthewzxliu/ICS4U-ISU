import java.awt.*;
import java.awt.image.*;

public class Bomb {
    
    BufferedImage bombImg;
    private int x;
    private int y;
    private int damage;
    private int timer;
    private boolean exploded;

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public Bomb(BufferedImage bombImg, int x, int y)
    {
        this.bombImg = bombImg;
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g)
    {
        g.drawImage(bombImg, x, y, null);
    }
}
