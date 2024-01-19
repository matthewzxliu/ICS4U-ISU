import java.awt.*;
import java.awt.image.*;

public class PowerUp {
    
    private int playerVel = 0;
    private int xPosPowerUp;
    private int yPosPowerUp;
    private BufferedImage powerUpImg;

    public int getPlayerVel()
    {
        return playerVel;
    }

    public int getX()
    {
        return (int) Math.round(xPosPowerUp / 40.0) * 40;
    }

    public int getY()
    {
        return (int) Math.round(yPosPowerUp / 40.0) * 40;
    }

    public PowerUp(BufferedImage powerUpImg, int xPosPowerUp, int yPosPowerUp)
    {
        this.powerUpImg = powerUpImg;
        this.xPosPowerUp = xPosPowerUp;
        this.yPosPowerUp = yPosPowerUp;
    }

    public void draw(Graphics g)
    {
        g.drawImage(powerUpImg, xPosPowerUp, yPosPowerUp, null);
    }

    public int getSpeedPowerUp()
    {
        return playerVel += 2;
    }
    
    public int removeSpeedPowerUp()
    {
        return playerVel -= 2;
    }
}
