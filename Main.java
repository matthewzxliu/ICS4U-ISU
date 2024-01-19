// IMPORT
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;

// MAIN CLASS
public class Main extends JPanel implements MouseListener, KeyListener, Runnable
{

    // GLOBAL VARIABLES
    // GRAPHICS VARIABLES
    static JFrame frame;

    // MOUSE INPUT
    static int xPos, yPos;

    // CHARACTER VARIABLES
    static int xPosPlayer = 40, yPosPlayer = 40;
    // static int xPosEnemy = 535, yPosEnemy = 455;
    static boolean up, left, down, right;
    static boolean blockUp, blockLeft, blockDown, blockRight;
    static int vel = 3;
    static String direction = "down";

    // GAME STATE
    static int gameState = 0;

    // MAP
    static String[][] map = new String[13][15];
    static ArrayList<Integer> xBlocks = new ArrayList<>();
    static ArrayList<Integer> yBlocks = new ArrayList<>();
    static int xPosNear, yPosNear;

    // IMAGES
    static BufferedImage wallImg, unbreakableWallImg;
    static BufferedImage backgroundImg, highscoreImg, rulesImg, aboutImg, backImg, gameOverImg, bombImg, bombExplosionImg, bombAndExplosionImg, powerUpSpeedImg, doorImg;
    static BufferedImage[] characterSprites;
    static BufferedImage playerImg;
    static int spriteNum = 1;
    static int spriteCounter = 0;

    // BOMBS
    static ArrayList<Bomb> bombArray = new ArrayList<Bomb>();
    static int timer = 0;

	// POWERUPS
    static PowerUp powerUp1;
    static PowerUp powerUp2;
    static boolean powerUp1Claimed = false;
    static boolean powerUp2Claimed = false;
    static int powerUp1Duration = 300;
    static int powerUp2Duration = 300;

    // DOOR
    static int xPosDoor;
    static int yPosDoor;

    // HIGHSCORE
    static ArrayList<String> highscore = new ArrayList<String>();
    boolean enterName = false;
    Font font = new Font("SansSerif", Font.PLAIN, 18);


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // INITIALIZE
    public Main() throws IOException
    {
        // Set the size and colour of the game window
        setPreferredSize(new Dimension(600, 520));
        setBackground(new Color(231, 238, 229));

        //
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        Thread t = new Thread((Runnable) this);
        t.start();
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // PAINT COMPONENT
    public void paintComponent(Graphics g)
    {
        // CLEAR SCREEN
        super.paintComponent(g);

		/* BUTTON HITBOXES
		// About
		g.setColor(new Color(160, 50, 168));
		g.fillRect(211, 260, 177, 50);

		// Play
		g.setColor(new Color(160, 50, 168));
		g.fillRect(211, 320, 177, 50);

		// Highscore
		g.setColor(new Color(160, 50, 168));
		g.fillRect(211, 380, 177, 50);

		// Rules
		g.setColor(new Color(160, 50, 168));
		g.fillRect(211, 440, 116, 50);

		// Exit
		g.setColor(new Color(160, 50, 168));
		g.fillRect(336, 440, 52, 50);
		*/

        // Draw background image
        g.drawImage(backgroundImg, 0, 0, null);

        // Game state 1, about page
        if(gameState == 1)
        {
            super.paintComponent(g);
            g.drawImage(aboutImg, 0, 0, null);
            g.drawImage(backImg, 15, 15, null);
        }
        // Game state 2, main game
        else if(gameState == 2)
        {
            // CLEAR SCREEN
            super.paintComponent(g);

            // Draw Door
            g.drawImage(doorImg, xPosDoor, yPosDoor, null);

            if(powerUp1Claimed == false)
            {
                powerUp1.draw(g);
                if(xPosPlayer >= powerUp1.getX()-10 && xPosPlayer <= powerUp1.getX()+10 && yPosPlayer >= powerUp1.getY()-10 && yPosPlayer <= powerUp1.getY()+10)
                {
                    vel += powerUp1.getSpeedPowerUp();
                    powerUp1Claimed = true;
                }
            }
            if(powerUp2Claimed == false)
            {
                powerUp2.draw(g);
                if(xPosPlayer >= powerUp2.getX()-10 && xPosPlayer <= powerUp2.getX()+10 && yPosPlayer >= powerUp2.getY()-10 && yPosPlayer <= powerUp2.getY()+10)
                {
                    vel += powerUp2.getSpeedPowerUp();
                    powerUp2Claimed = true;
                }
            }
            if(powerUp1Claimed == true && powerUp1Duration >= 0)
            {
                powerUp1Duration--;
                if(powerUp1Duration <= 0)
                {
                    vel += powerUp1.removeSpeedPowerUp();
                }
            }
            if(powerUp2Claimed == true && powerUp2Duration >= 0)
            {
                powerUp2Duration--;
                if(powerUp2Duration <= 0)
                {
                    vel += powerUp2.removeSpeedPowerUp();
                }
            }

            // Read map from map.txt and draw map onto the screen
            for(int i = 0; i < 600; i += 40)
            {
                for(int j = 0; j < 520; j += 40)
                {
                    // "x" is for the outer wall / boundary, "-" is for empty spaces player can walk on, "1" is for blocks on the map that player cannot go through
                    if(map[j/40][i/40].equals("x"))
                    {
                        g.setColor(new Color(0, 0, 0));
                        g.fillRect(i, j, 40, 40);
                    }
                    else if(map[j/40][i/40].equals("W"))
                    {
                        g.drawImage(wallImg, i, j, null);
                    }
                    else if(map[j/40][i/40].equals("1"))
                    {
                        g.drawImage(unbreakableWallImg, i, j, null);
                    }
                    else if(map[j/40][i/40].equals("P"))
                    {
                        g.setColor(new Color(255, 0, 0));
                        g.fillOval(i, j, 40, 40);
                    }
                }
            }

            // Sprite animation, if player is going down/left/right/up, set the player image to one of two sprites
            playerImg = null;
            if(direction.equals("down"))
            {
                if(spriteNum == 1)
                    playerImg = characterSprites[0];
                else
                    playerImg = characterSprites[1];
            }
            else if(direction.equals("left"))
            {
                if(spriteNum == 1)
                    playerImg = characterSprites[2];
                else
                    playerImg = characterSprites[3];
            }
            else if(direction.equals("right"))
            {
                if(spriteNum == 1)
                    playerImg = characterSprites[4];
                else
                    playerImg = characterSprites[5];
            }
            else if(direction.equals("up"))
            {
                if(spriteNum == 1)
                    playerImg = characterSprites[6];
                else
                    playerImg = characterSprites[7];
            }

            // Display bombs that player places
            for(int i = 0; i < bombArray.size(); i++)
            {
                bombArray.get(i).draw(g);
                timer++;
                // System.out.println("X: " + bombArray.get(i).getX());
                // System.out.println("Y: " + bombArray.get(i).getY());
            }
            // Bomb explosion
            if(timer >= 60 && bombArray.size() >= 1)
            {
                explodeBomb();

                bombArray.remove(0);
                timer = 0;
            }

            // Draw the player
            g.drawImage(playerImg, xPosPlayer, yPosPlayer, null);

            // ENEMY TEST
            // g.setColor(new Color(0, 0, 255));
            // g.fillOval(xPosEnemy, yPosEnemy, 25, 25);
            // if(xPosEnemy >= xPosPlayer)
            // {
            // 	xPosEnemy -= 2;
            // }
            // else if(xPosEnemy <= xPosPlayer)
            // {
            // 	xPosEnemy += 2;
            // }
            // if(yPosEnemy >= yPosPlayer)
            // {
            // 	yPosEnemy -= 2;
            // }
            // else if(yPosEnemy <= yPosPlayer)
            // {
            // 	yPosEnemy += 2;
            // }

        }
        // Game state 3, high score page
        else if(gameState == 3)
        {
            super.paintComponent(g);
            g.drawImage(highscoreImg, 0, 0, null);
            g.drawImage(backImg, 15, 15, null);

            for(int i = 0; i < highscore.size(); i++)
            {
                String name = highscore.get(i);
                g.setFont(font);
                g.drawString(name, 125, 195 + 45*i);
            }
        }
        // Game state 4, rules page
        else if(gameState == 4)
        {
            super.paintComponent(g);
            g.drawImage(backImg, 15, 15, null);
        }
        // Game state 5, exit
        else if(gameState == 5)
        {
            super.paintComponent(g);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        // Game State 6, game over
        else if(gameState == 6)
        {
            g.drawImage(gameOverImg, 0, 0, null);
            g.drawImage(backImg, 250, 290, null);
            enterName = true;
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // MOUSE INPUT
    public void mousePressed(MouseEvent e) {
        xPos = e.getX();
        yPos = e.getY();

        if(gameState == 0)
        {
            if(xPos >= 211 && xPos <= 388 && yPos >= 260 && yPos <= 310)
            {
                gameState = 1;
            }
            else if(xPos >= 211 && xPos <= 388 && yPos >= 320 && yPos <= 370)
            {
                gameState = 2;
            }
            else if(xPos >= 211 && xPos <= 388 && yPos >= 380 && yPos <= 430)
            {
                gameState = 3;
            }
            else if(xPos >= 211 && xPos <= 327 && yPos >= 440 && yPos <= 490)
            {
                gameState = 4;
            }
            else if(xPos >= 336 && xPos <= 388 && yPos >= 440 && yPos <= 490)
            {
                gameState = 5;
            }
        }
        else if(gameState == 1)
        {
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                gameState = 0;
            }
        }
        else if(gameState == 2)
        {
            ;
        }
        else if(gameState == 3)
        {
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                gameState = 0;
            }
        }
        else if(gameState == 4)
        {
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                gameState = 0;
            }
        }
        else if(gameState == 6)
        {
            if(enterName == true)
            {
                enterHighscoreName();
            }

            if(xPos >= 250 && xPos <= 350 && yPos >= 290 && yPos <= 336)
            {
                gameState = 0;
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // KEYBOARD INPUT
    public void keyPressed(KeyEvent e)
    {
        int key = e.getKeyCode();
        Rectangle player = new Rectangle(xPosPlayer, yPosPlayer, 40, 40);
        Rectangle door = new Rectangle(xPosDoor, yPosDoor, 40, 40);

        if(key == KeyEvent.VK_A)
        {
            left = true;
            right = false;
        }
        else if(key == KeyEvent.VK_D)
        {
            right = true;
            left = false;
        }
        else if(key == KeyEvent.VK_W)
        {
            up = true;
            down = false;
        }
        else if(key == KeyEvent.VK_S)
        {
            down = true;
            up = false;
        }
        else if(key == KeyEvent.VK_SPACE)
        {
            if(gameState == 2)
            {
                Bomb bomb = new Bomb(bombImg, xPosPlayer, yPosPlayer);
                bombArray.add(bomb);
            }
        }
        else if(key == KeyEvent.VK_L || (player.intersects(door) && (key == KeyEvent.VK_ENTER)))
        {
            gameState = 6;
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // KEYBOARD INPUT
    public void keyReleased(KeyEvent e)
    {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_A)
            left = false;
        else if(key == KeyEvent.VK_D)
            right = false;
        else if(key == KeyEvent.VK_W)
            up = false;
        else if(key == KeyEvent.VK_S)
            down = false;
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // PLAYER MOVEMENT
    public void move()
    {
        if(gameState == 2)
        {
            if(left || right || up || down)
            {
                if(left && xPosPlayer > 40 && !blockLeft)
                {
                    xPosPlayer -= vel;
                    direction = "left";
                }
                else if(right && xPosPlayer < 520 && !blockRight)
                {
                    xPosPlayer += vel;
                    direction = "right";
                }
                if(up && yPosPlayer > 40 && !blockUp)
                {
                    yPosPlayer -= vel;
                    direction = "up";
                }
                else if(down && yPosPlayer < 440 && !blockDown)
                {
                    yPosPlayer += vel;
                    direction = "down";
                }

                spriteCounter++;

                if(spriteCounter > 8)
                {
                    if(spriteNum == 1)
                    {
                        spriteNum = 2;
                    }
                    else if(spriteNum == 2)
                    {
                        spriteNum = 1;
                    }
                    spriteCounter = 0;
                }
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------

    // Checks collision with boxes
    public void checkCollision()
    {
        if(gameState == 2) {
            int xTile = (xPosPlayer + 20) / 40;
            int yTile = (yPosPlayer + 20) / 40;

            int xHeadTile = (xPosPlayer + 20) / 40;
            int yHeadTile = (yPosPlayer + 5) / 40;

            int xFeetTile = (xPosPlayer + 20) / 40;
            int yFeetTile = (yPosPlayer + 35) / 40;

            int xLeftHandTile = (xPosPlayer + 5) / 40;
            int yLeftHandTile = (yPosPlayer + 20) / 40;

            int xRightHandTile = (xPosPlayer + 35) / 40;
            int yRightHandTile = (yPosPlayer + 20) / 40;

            Rectangle player = new Rectangle(xPosPlayer, yPosPlayer, 40, 40);

            blockDown = false;
            blockUp = false;
            blockLeft = false;
            blockRight = false;

            // Checks left block of player
            if(!map[yFeetTile][xFeetTile-1].equals("-") || !map[yHeadTile][xHeadTile-1].equals("-")) {
                Rectangle tile = new Rectangle((xTile-1) * 40, yTile * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockLeft = true;
                }
            }
            // Checks right block
            if(!map[yFeetTile][xFeetTile+1].equals("-") || !map[yHeadTile][xHeadTile+1].equals("-")) {
                Rectangle tile = new Rectangle((xTile + 1) * 40, (yTile) * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockRight = true;
                }
            }
            // Checks up block of player
            if(!map[yLeftHandTile-1][xLeftHandTile].equals("-") || !map[yRightHandTile-1][xRightHandTile].equals("-")) {
                Rectangle tile = new Rectangle((xTile) * 40, (yTile-1) * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockUp = true;
                }
            }
            // Checks down block
            if(!map[yLeftHandTile+1][xLeftHandTile].equals("-") || !map[yRightHandTile+1][xRightHandTile].equals("-")) {
                Rectangle tile = new Rectangle((xTile) * 40, (yTile + 1) * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockDown = true;
                }
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public void explodeBomb()
    {
        int bombX = bombArray.get(0).getX();
        int bombY = bombArray.get(0).getY();

        if(map[(bombY/40)-1][(bombX/40)].equals("W") && map[(bombY/40)][(bombX/40)+1].equals("W") && map[(bombY/40)+1][(bombX/40)].equals("W"))
        {
            map[(bombY/40)-1][(bombX/40)] = "-";
            map[(bombY/40)][(bombX/40) + 1] = "-";
            map[(bombY/40)+1][(bombX/40)] = "-";
        }
        else if(map[(bombY/40)-1][(bombX/40)].equals("W") && map[(bombY/40)][(bombX/40) + 1].equals("W"))
        {
            map[(bombY/40)-1][(bombX/40)] = "-";
            map[(bombY/40)][(bombX/40)+ 1] = "-";
        }
        else if(map[(bombY/40)][(bombX/40)+1].equals("W") && map[(bombY/40)+1][(bombX/40)].equals("W") && map[(bombY/40)][(bombX/40)-1].equals("W"))
        {
            map[(bombY/40)][(bombX/40)+1] = "-";
            map[(bombY/40)+1][(bombX/40)] = "-";
            map[(bombY/40)][(bombX/40)-1] = "-";
        }
        else if(map[(bombY/40)][(bombX/40)+1].equals("W") && map[(bombY/40)+1][(bombX/40)].equals("W"))
        {
            map[(bombY/40)][(bombX/40)+1] = "-";
            map[(bombY/40)+1][(bombX/40)] = "-";
        }
        else if(map[(bombY/40)+1][(bombX/40)].equals("W") && map[(bombY/40)][(bombX/40)-1].equals("W") && map[(bombY/40)-1][(bombX/40)].equals("W"))
        {
            map[(bombY/40)+1][(bombX/40)] = "-";
            map[(bombY/40)][(bombX/40)-1] = "-";
            map[(bombY/40)-1][(bombX/40)] = "-";
        }
        else if(map[(bombY/40)+1][(bombX/40)].equals("W") && map[(bombY/40)][(bombX/40)-1].equals("W"))
        {
            map[(bombY/40)+1][(bombX/40)] = "-";
            map[(bombY/40)][(bombX/40)-1] = "-";
        }
        else if(map[(bombY/40)][(bombX/40)-1].equals("W") && map[(bombY/40)-1][(bombX/40)].equals("W") && map[(bombY/40)][(bombX/40)+1].equals("W"))
        {
            map[(bombY/40)][(bombX/40)-1] = "-";
            map[(bombY/40)-1][(bombX/40)] = "-";
            map[(bombY/40)][(bombX/40)+1] = "-";
        }
        else if(map[(bombY/40)][(bombX/40)-1].equals("W") && map[(bombY/40)-1][(bombX/40)].equals("W"))
        {
            map[(bombY/40)][(bombX/40)-1] = "-";
            map[(bombY/40)-1][(bombX/40)] = "-";
        }
        else if(map[(bombY/40)-1][(bombX/40)].equals("W") && map[(bombY/40)+1][(bombX/40)].equals("W"))
        {
            map[(bombY/40)-1][(bombX/40)] = "-";
            map[(bombY/40)+1][(bombX/40)] = "-";
        }
        else if(map[(bombY/40)][(bombX/40)-1].equals("W") && map[(bombY/40)][(bombX/40)+1].equals("W"))
        {
            map[(bombY/40)][(bombX/40)-1] = "-";
            map[(bombY/40)][(bombX/40)+1] = "-";
        }
        else if(map[(bombY/40)-1][(bombX/40)].equals("W"))
        {
            map[(bombY/40)-1][(bombX/40)] = "-";
        }
        else if(map[(bombY/40)][(bombX/40)+1].equals("W"))
        {
            map[(bombY/40)][(bombX/40)+1] = "-";
        }
        else if(map[(bombY/40)+1][(bombX/40)].equals("W"))
        {
            map[(bombY/40)+1][(bombX/40)] = "-";
        }
        else if(map[(bombY/40)][(bombX/40)-1].equals("W"))
        {
            map[(bombY/40)][(bombX/40)-1] = "-";
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public static void loadMaps() throws IOException
    {
        // LOADING MAPS
        try
        {
            // Read Maps
            BufferedReader br = new BufferedReader(new FileReader("map.txt"));

            int mapNumber = (int)(Math.random()*(3-1+1)) + 1;

            int end = 13 * (mapNumber - 1);
            for(int i = 0; i < end; i++)
            {
                br.readLine();
            }

            for(int i = 0; i < 13; i++)
            {
                String line = br.readLine();
                for(int j = 0; j < 15; j++)
                {
                    map[i][j] = line.substring(0, 1);

                    if(map[i][j].equals("W")) {
                        xBlocks.add(j * 40);
                        yBlocks.add(i * 40);
                    }

                    line = line.substring(1);
                }
            }
            br.close();

            int doorBlock = (int) (Math.random() * xBlocks.size()) + 1;
            xPosDoor = xBlocks.get(doorBlock);
            yPosDoor = yBlocks.get(doorBlock);
            System.out.println("Door: " + xPosDoor + ", " + yPosDoor);

        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error");
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public static void generatePowerUps()
    {
        int xPosPowerUp = (int)(Math.random()*(14)) +1;
		int yPosPowerUp = (int)(Math.random()*(12)) +1;
        while(map[yPosPowerUp][xPosPowerUp].equals("1"))
        {
            xPosPowerUp = (int)(Math.random()*(14)) +1;
            yPosPowerUp = (int)(Math.random()*(12)) +1;
        }
        powerUp1 = new PowerUp(powerUpSpeedImg, xPosPowerUp*40, yPosPowerUp*40);

        xPosPowerUp = (int)(Math.random()*(14)) +1;
        yPosPowerUp = (int)(Math.random()*(12)) +1;
        while(map[yPosPowerUp][xPosPowerUp].equals("1"))
        {
            xPosPowerUp = (int)(Math.random()*(14)) +1;
            yPosPowerUp = (int)(Math.random()*(12)) +1;
        }
        powerUp2 = new PowerUp(powerUpSpeedImg, xPosPowerUp*40, yPosPowerUp*40);
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public void enterHighscoreName()
    {
        if(enterName == true)
        {
            String nameEntered = JOptionPane.showInputDialog("Enter your name for the highscore.");

            // If the user presses the X button, return back
            if(nameEntered == null)
            {
                return;
            }

            // If the user tries to enter an empty file name
            if(nameEntered.length() <= 0)
            {
                // Display that it is invalid and return
                JOptionPane.showMessageDialog(null, "No name given.", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if the file entered is a valid file
            else
            {
                // If it is a valid file, display that it was sucessfully added
                JOptionPane.showMessageDialog(null, "Highscore added.", "Success", JOptionPane.INFORMATION_MESSAGE);
                // Add the new file to the arraylist of files names and the combo box so that the user can select it
                highscore.add(nameEntered);
            }
        }
        enterName = false;
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // THREADING
    public void run() {
        while(true) {
            move();
            checkCollision();
            repaint();
            try {
                Thread.sleep(17);
            } catch(Exception e) {}
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // MAIN METHOD
    public static void main(String[] args) throws IOException {
        // INITIALIZE FRAME
        frame = new JFrame("Bomberman");
        Main myPanel = new Main();
        frame.add(myPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

        loadMaps();

        // LOADING IMAGES
        try
        {
            backgroundImg = ImageIO.read(new File("Images/Background.png"));
            highscoreImg = ImageIO.read(new File("Images/highscore.png"));
            // rulesImg = ImageIO.read(new File("Images/rulesImg.png"));
            aboutImg = ImageIO.read(new File("Images/about.png"));
            backImg = ImageIO.read(new File("Images/back.png"));
            gameOverImg = ImageIO.read(new File("Images/gameOver.png"));

            wallImg = ImageIO.read(new File("Images/wall.png"));
            unbreakableWallImg = ImageIO.read(new File("Images/unbreakable.png"));

            characterSprites = new BufferedImage[10];
            characterSprites[0] = ImageIO.read(new File("Images/boy_down_1.png"));
            characterSprites[1] = ImageIO.read(new File("Images/boy_down_2.png"));
            characterSprites[2] = ImageIO.read(new File("Images/boy_left_1.png"));
            characterSprites[3] = ImageIO.read(new File("Images/boy_left_2.png"));
            characterSprites[4] = ImageIO.read(new File("Images/boy_right_1.png"));
            characterSprites[5] = ImageIO.read(new File("Images/boy_right_2.png"));
            characterSprites[6] = ImageIO.read(new File("Images/boy_up_1.png"));
            characterSprites[7] = ImageIO.read(new File("Images/boy_up_2.png"));

            bombImg = ImageIO.read(new File("Images/bomb.png"));
            bombExplosionImg = ImageIO.read(new File("Images/bombExplosion.gif"));
            bombAndExplosionImg = ImageIO.read(new File("Images/bombAndExplosion.gif"));

			powerUpSpeedImg = ImageIO.read(new File("Images/powerUpSpeed.png"));

            doorImg = ImageIO.read(new File("Images/door.png"));
        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error");
        }

        generatePowerUps();

        // for (String[] x : map)
        // {
        // 	for (String y : x)
        // 	{
        // 			System.out.print(y);
        // 	}
        // 	System.out.println();
        // }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // USELESS
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
}