import java.awt.*;
import java.awt.image.*;

public class PowerUp {
    
    private int playerVel = 0;
    private int enemyVel;
    private int xPosPowerUp;
    private int yPosPowerUp;
    private BufferedImage powerUpSpeedImg;

    public int getPlayerVel()
    {
        return playerVel;
    }

    public int getEnemyVel()
    {
        return enemyVel;
    }

    public int getX()
    {
        return (int) Math.round(xPosPowerUp / 40.0) * 40;
    }

    public int getY()
    {
        return (int) Math.round(yPosPowerUp / 40.0) * 40;
    }

    public PowerUp(BufferedImage powerUpSpeedImg, int xPosPowerUp, int yPosPowerUp)
    {
        // this.playerVel = playerVel;
        // this.enemyVel = enemyVel;
        this.powerUpSpeedImg = powerUpSpeedImg;
        this.xPosPowerUp = xPosPowerUp;
        this.yPosPowerUp = yPosPowerUp;
    }

    public void draw(Graphics g)
    {
        g.drawImage(powerUpSpeedImg, xPosPowerUp, yPosPowerUp, null);
    }

    public int getSpeedPowerUp()
    {
        return playerVel += 2;
    }
    
    public int removeSpeedPowerUp()
    {
        return playerVel -= 2;
    }
    
    public int getSlowPowerUp()
    {
        return enemyVel += 5;
    }

    public int removeSlowPowerUp()
    {
        return enemyVel -= 2;
    }
}
