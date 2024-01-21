import java.awt.*;
import java.awt.image.*;

// powerup class

public class PowerUp {
    
    // variables
    private int playerVel = 0;
    private int xPosPowerUp;
    private int yPosPowerUp;
    private BufferedImage powerUpImg;

    // getters
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

    // constructor
    public PowerUp(BufferedImage powerUpImg, int xPosPowerUp, int yPosPowerUp)
    {
        this.powerUpImg = powerUpImg;
        this.xPosPowerUp = xPosPowerUp;
        this.yPosPowerUp = yPosPowerUp;
    }

    // Purpose: drawing the powerup
    // Parameter: graphics object to allow drawing
    // Return: n/a
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
