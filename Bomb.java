import java.awt.*;

public class Bomb {
    
    private int x;
    private int y;
    private int damage;
    private int timer;
    private boolean exploded;

    public Bomb(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g)
    {
        // Round
        g.setColor(new Color(255, 0, 0));
        g.fillOval(x, y, 25, 25);
    }
}
