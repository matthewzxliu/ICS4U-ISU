/*
 * INTRODUCTORY COMMENTS:
 *
 * NAME: Jeevesh Balendra
 *
 * DATE: January 21st, 2024
 *
 * DESCRIPTION: This is the class for enemy objects.
 */

// IMPORT
import java.awt.*;

// ENEMY/LAPTOP OBJECT CLASS
public class Enemy {

    // Variables
    private int x;
    private int y;
    private String direction;
    private double speed = 2;

    // Getters
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // Get Slow Power Up
    // Description: Sets the speed of the enemies to 1
    // Parameters: N/A.
    // Return: Speed value (double)
    public double getSlowPowerUp()
    {
        return speed = 1;
    }

    // Remove Slow Power Up
    // Description: Sets the speed of the enemies to 2
    // Parameters: N/A.
    // Return: Speed value (double)
    public double removeSlowPowerUp()
    {
        return speed = 2;
    }

    // Constructor
    public Enemy(int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    // Enemy movement
    // Description: updates the position of the enemy based on their current direction and speed
    // calls the change direction method if there is collision (invalid move for the enemy)
    // Parameters: 2D array of the map the check the tiles around it
    // Return: N/A.
    public void move(String[][] map) {
        // Move in the current direction with the specified speed
        int newX = x;
        int newY = y;

        // Update the temporary position based on the current direction
        if(direction.equals("up")) {
            newY -= speed;
            Rectangle enemyBox = new Rectangle(newX, newY, 40, 40);
            int xTile = (newX + 20) / 40;
            int yTile = (newY + 20) / 40;
            Rectangle upEnemy = new Rectangle((xTile) * 40, (yTile - 1) * 40, 40, 40);
            // checks if the tile above is invalid and if it is being intersected
            if(!map[yTile - 1][xTile].equals("-") && enemyBox.intersects(upEnemy)) {
                // changes direction if intersection
                changeDirection();
            } else {
                // otherwise it updates the coordinates
                x = newX;
                y = newY;
            }
        }
        if(direction.equals("down")) {
            // checks if tile below is being intersected and is invalid
            newY += speed;
            Rectangle enemyBox = new Rectangle(newX, newY, 40, 40);
            int xTile = (newX + 20) / 40;
            int yTile = (newY + 20) / 40;
            Rectangle downEnemy = new Rectangle((xTile) * 40, (yTile + 1) * 40, 40, 40);
            // updates coordinates if valid, otherwise it changes direction
            if(!map[yTile + 1][xTile].equals("-") && enemyBox.intersects(downEnemy)) {
                changeDirection();
            } else {
                x = newX;
                y = newY;
            }
        }
        if(direction.equals("left")) {
            // checks if the tile to the left is invalid and being intersected
            newX -= speed;
            Rectangle enemyBox = new Rectangle(newX, newY, 40, 40);
            int xTile = (newX + 20) / 40;
            int yTile = (newY + 20) / 40;
            Rectangle leftEnemy = new Rectangle((xTile-1) * 40, yTile * 40, 40, 40);
            // if it is being intersected it changes direction otherwise it updates the coordinates
            if(!map[yTile][xTile - 1].equals("-") && enemyBox.intersects(leftEnemy)) {
                changeDirection();
            } else {
                x = newX;
                y = newY;
            }
        }
        if(direction.equals("right")) {
            // checks if the tile to the right is invlaid and being intersected
            newX += speed;
            Rectangle enemyBox = new Rectangle(newX, newY, 40, 40);
            int xTile = (newX + 20) / 40;
            int yTile = (newY + 20) / 40;
            Rectangle rightEnemy = new Rectangle((xTile + 1) * 40, yTile * 40, 40, 40);
            // if it is making invalid movement it changes direction, otherwise coordinates are updated
            if(!map[yTile][xTile + 1].equals("-") && enemyBox.intersects(rightEnemy)) {
                changeDirection();
            } else {
                x = newX;
                y = newY;
            }
        }
    }

    // Change direction
    // Description: method takes the current direction and randomly chooses one of the 
    // other three directions for the enemy to move in
    // Parameters: N/A.
    // Return: N/A.
    private void changeDirection() {
        // generates a random number from 0 to 2
        int num = (int) (Math.random() * 3);
        // generates one of the other three directions given the original direction
        if(direction.equals("up")) {
            if(num == 0) {
                direction = "right";
            }
            if(num == 1) {
                direction = "down";
            }
            if(num == 2) {
                direction = "left";
            }
        }
        // generates one of the other three directions given the original direction
        else if(direction.equals("down")) {
            if(num == 0) {
                direction = "up";
            }
            if(num == 1) {
                direction = "right";
            }
            if(num == 2) {
                direction = "left";
            }
        }
        // generates one of the other three directions given the original direction
        else if(direction.equals("right")) {
            if(num == 0) {
                direction = "up";
            }
            if(num == 1) {
                direction = "down";
            }
            if(num == 2) {
                direction = "left";
            }
        }
        // generates one of the other three directions given the original direction
        else if(direction.equals("left")) {
            if(num == 0) {
                direction = "up";
            }
            if(num == 1) {
                direction = "down";
            }
            if(num == 2) {
                direction = "right";
            }
        }
    }
}
