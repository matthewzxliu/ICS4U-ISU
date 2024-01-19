import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy {

    private int x;
    private int y;
    private String direction;
    private double speed = 2;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public double getSlowPowerUp()
    {
        return speed = 1;
    }

    public double removeSlowPowerUp()
    {
        return speed = 2;
    }

    public Enemy(BufferedImage enemyImg, int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

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
            if(!map[yTile - 1][xTile].equals("-") && enemyBox.intersects(upEnemy)) {
                changeDirection();
            } else {
                x = newX;
                y = newY;
            }
        }
        if(direction.equals("down")) {
            newY += speed;
            Rectangle enemyBox = new Rectangle(newX, newY, 40, 40);
            int xTile = (newX + 20) / 40;
            int yTile = (newY + 20) / 40;
            Rectangle downEnemy = new Rectangle((xTile) * 40, (yTile + 1) * 40, 40, 40);
            if(!map[yTile + 1][xTile].equals("-") && enemyBox.intersects(downEnemy)) {
                changeDirection();
            } else {
                x = newX;
                y = newY;
            }
        }
        if(direction.equals("left")) {
            newX -= speed;
            Rectangle enemyBox = new Rectangle(newX, newY, 40, 40);
            int xTile = (newX + 20) / 40;
            int yTile = (newY + 20) / 40;
            Rectangle leftEnemy = new Rectangle((xTile-1) * 40, yTile * 40, 40, 40);
            if(!map[yTile][xTile - 1].equals("-") && enemyBox.intersects(leftEnemy)) {
                changeDirection();
            } else {
                x = newX;
                y = newY;
            }
        }
        if(direction.equals("right")) {
            newX += speed;
            Rectangle enemyBox = new Rectangle(newX, newY, 40, 40);
            int xTile = (newX + 20) / 40;
            int yTile = (newY + 20) / 40;
            Rectangle rightEnemy = new Rectangle((xTile + 1) * 40, yTile * 40, 40, 40);
            if(!map[yTile][xTile + 1].equals("-") && enemyBox.intersects(rightEnemy)) {
                changeDirection();
            } else {
                x = newX;
                y = newY;
            }
        }
    }

    private void changeDirection() {
        // Change direction to a random value (0: UP, 1: DOWN, 2: LEFT, 3: RIGHT)
        int num = (int) (Math.random() * 3);
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
