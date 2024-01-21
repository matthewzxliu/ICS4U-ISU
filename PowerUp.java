/*
 * INTRODUCTORY COMMENTS:
 *
 * NAME: Matthew Liu
 *
 * DATE: January 21st, 2024
 *
 * DESCRIPTION: This is the class for power up objects.
 */

// IMPORT
import java.awt.*;
import java.awt.image.*;

// POWERUP OBJECT CLASS
public class PowerUp {
    
    // Variables
    private int playerVel = 0;
    private int xPosPowerUp;
    private int yPosPowerUp;
    private BufferedImage powerUpImg;

    // Getters
    public int getX()
    {
        return (int) Math.round(xPosPowerUp / 40.0) * 40;
    }

    public int getY()
    {
        return (int) Math.round(yPosPowerUp / 40.0) * 40;
    }

    // Constructor
    // Description: Takes in the image of the power up and the x / y coordinate that it randomly spawns at
    // Parameters: Image of the power up, x coordinate, y coordinate
    // Return: N/A.
    public PowerUp(BufferedImage powerUpImg, int xPosPowerUp, int yPosPowerUp)
    {
        this.powerUpImg = powerUpImg;
        this.xPosPowerUp = xPosPowerUp;
        this.yPosPowerUp = yPosPowerUp;
    }

    // Draw
    // Description: Draw the image of the power up where it spawns
    // Parameters: Graphics g object to allow drawing
    // Return: N/A.
    public void draw(Graphics g)
    {
        g.drawImage(powerUpImg, xPosPowerUp, yPosPowerUp, null);
    }

    // Get Speed Power Up
    // Description: Increases the speed of the player by 2
    // Parameters: N/A.
    // Return: Speed increase amount (int)
    public int getSpeedPowerUp()
    {
        return playerVel += 2;
    }
    
    // Remove Speed Power Up
    // Description: Decreases the speed of the player by 2
    // Parameters: N/A.
    // Return: Speed decrease amount (int)
    public int removeSpeedPowerUp()
    {
        return playerVel -= 2;
    }
}
