/*
 * INTRODUCTORY COMMENTS:
 *
 * NAME: Matthew Liu
 *
 * DATE: January 21st, 2024
 *
 * DESCRIPTION: This is the class for bomb objects.
 */

// IMPORT
import java.awt.*;
import java.awt.image.*;

// BOMB CLASS
public class Bomb {

    // Variables
    private BufferedImage bombImg;
    private int x;
    private int y;

    // Getters
    public int getX()
    {
        return (int) Math.round(x / 40.0) * 40;
    }

    public int getY()
    {
        return (int) Math.round(y / 40.0) * 40;
    }

    // Constructor
    // Description: Takes in the image of the bomb, x coordinate of the player, and y coordinate of the player to make a bomb object
    // Parameters: Image of the bomb, x coordinate of player, y coordinate of player
    // Return: N/A.
    public Bomb(BufferedImage bombImg, int x, int y)
    {
        this.bombImg = bombImg;
        this.x = x;
        this.y = y;
    }

    // Draw
    // Description: Draw the image of the bomb where it is placed
    // Parameters: Graphics g object to allow drawing
    // Return: N/A.
    public void draw(Graphics g)
    {
        // Round the x and y positions of the bomb to the nearest multiple of 40 (ensures that the bomb will be centered in each block because each block is 40x40)
        int roundedX = (int) Math.round(x / 40.0) * 40;
        int roundedY = (int) Math.round(y / 40.0) * 40;
        // Draw the image of the bomb where it is placed
        g.drawImage(bombImg, roundedX, roundedY, null);
    }
}
