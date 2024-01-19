import java.awt.*;
import java.awt.image.BufferedImage;

public class Enemy {

    private BufferedImage enemyImg;
    private int x;
    private int y;
    private String direction;
    private double speed = 1;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Enemy(BufferedImage enemyImg, int x, int y, String direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemyImg = enemyImg;
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
            }
        }
        x = newX;
        y = newY;
//        switch (direction) {
//            case 0:
//                newY -= speed;
//                break;
//            case 1:
//                newY += speed;
//                break;
//            case 2:
//                newX -= speed;
//                break;
//            case 3:
//                newX += speed;
//                break;
//        }
    }

    private void changeDirection() {
        // Change direction to a random value (0: UP, 1: DOWN, 2: LEFT, 3: RIGHT)
        int num = (int) (Math.random() * 4);
        if(num == 0) {
            direction = "up";
        }
        if(num == 1) {
            direction = "down";
        }
        if(num == 2) {
            direction = "left";
        }
        if(num == 3) {
            direction = "right";
        }
    }
}
