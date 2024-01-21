/*
 * INTRO COMMENTS:
 *
 *
 *
 *
 */

// IMPORT
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;

// MAIN CLASS
public class Main extends JPanel implements MouseListener, KeyListener, Runnable
{

    // GLOBAL VARIABLES

    // GRAPHICS VARIABLES
    static JFrame frame;

    // MOUSE INPUT
    static int xPos, yPos;

    // CHARACTER VARIABLES
    static String nameEntered;
    static int xPosPlayer = 40, yPosPlayer = 40;
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

    // IMAGES
    static BufferedImage wallImg, unbreakableWallImg;
    static BufferedImage backgroundImg, highscoreImg, rulesImg, aboutImg, backImg, gameOverImg, winImg, bombImg, bombExplosionImg, bombAndExplosionImg, powerUpSpeedImg, powerUpSlowImg, doorImg, enemyImg;
    static BufferedImage[] characterSprites;
    static BufferedImage playerImg;
    static int spriteNum = 1;
    static int spriteCounter = 0;

    // BOMBS
    static ArrayList<Bomb> bombArray = new ArrayList<Bomb>();
    static int timer = 0;
    static int currentFrame;
    static int endFrame;
    static int xPosExploded;
    static int yPosExploded;
    static int fps = 0;

    // ENEMIES
    static ArrayList<Enemy> enemies = new ArrayList<>();

    // POWERUPS
    static PowerUp powerUp1;
    static PowerUp powerUp2;
    static PowerUp powerUp3;
    static PowerUp powerUp4;
    static boolean powerUp1Claimed = false;
    static boolean powerUp2Claimed = false;
    static boolean powerUp3Claimed = false;
    static boolean powerUp4Claimed = false;
    static int powerUp1Duration = 300;
    static int powerUp2Duration = 300;
    static int powerUp3Duration = 300;
    static int powerUp4Duration = 300;

    // DOOR
    static int xPosDoor;
    static int yPosDoor;

    // HIGHSCORE
    static PrintWriter outFile;
    static BufferedReader inFile;
    static HashMap<String, Integer> highscoreMap = new HashMap<>();
    static ArrayList<Score> allScores = new ArrayList<>();
    static boolean enterName = false;
    static Font font = new Font("SansSerif", Font.PLAIN, 18);
    static boolean timeStart = false;
    static long timeElapsedMsStart = 0;
    static long timeElapsedMsEnd = 0;
    static int timeElapsedSec = 0;
    static int timeElapsedMin = 0;
    static String timeString = "";
    static int countDown = 30;
    static int countDownPoints = 300;
    static String countDownString = "";
    static int score = 0;

    // MUSIC / AUDIO
    static Clip clip;


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // INITIALIZE
    public Main() throws IOException
    {
        // Set the size and colour of the game window
        setPreferredSize(new Dimension(600, 520));
        setBackground(new Color(231, 238, 229));

        // Add key and mouse listener
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        // Start thread
        Thread t = new Thread((Runnable) this);
        t.start();
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // PAINT COMPONENT
    public void paintComponent(Graphics g)
    {
        // Clear screen
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
            // Clear screen
            super.paintComponent(g);
            g.drawImage(aboutImg, 0, 0, null);
            g.drawImage(backImg, 15, 15, null);
        }

        // Game state 2, main game
        else if(gameState == 2)
        {
            // Clear screen
            super.paintComponent(g);

            // Draw Door
            g.drawImage(doorImg, xPosDoor, yPosDoor, null);

            // If player has not claimed power up
            if(powerUp1Claimed == false)
            {
                // Draw the power up
                powerUp1.draw(g);

                // If player has claimed powerup
                if(xPosPlayer >= powerUp1.getX()-10 && xPosPlayer <= powerUp1.getX()+10 && yPosPlayer >= powerUp1.getY()-10 && yPosPlayer <= powerUp1.getY()+10)
                {
                    // Apply the powerup
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    vel += powerUp1.getSpeedPowerUp();
                    powerUp1Claimed = true;
                }
            }
            if(powerUp2Claimed == false)
            {
                powerUp2.draw(g);
                if(xPosPlayer >= powerUp2.getX()-10 && xPosPlayer <= powerUp2.getX()+10 && yPosPlayer >= powerUp2.getY()-10 && yPosPlayer <= powerUp2.getY()+10)
                {
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    vel += powerUp2.getSpeedPowerUp();
                    powerUp2Claimed = true;
                }
            }
            if(powerUp3Claimed == false)
            {
                powerUp3.draw(g);
                if(xPosPlayer >= powerUp3.getX()-10 && xPosPlayer <= powerUp3.getX()+10 && yPosPlayer >= powerUp3.getY()-10 && yPosPlayer <= powerUp3.getY()+10)
                {
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    for(int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).getSlowPowerUp();
                    }
                    powerUp3Claimed = true;
                }
            }
            if(powerUp4Claimed == false)
            {
                powerUp4.draw(g);
                if(xPosPlayer >= powerUp4.getX()-10 && xPosPlayer <= powerUp4.getX()+10 && yPosPlayer >= powerUp4.getY()-10 && yPosPlayer <= powerUp4.getY()+10)
                {
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    for(int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).getSlowPowerUp();
                    }
                    powerUp4Claimed = true;
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
            if(powerUp3Claimed == true && powerUp3Duration >= 0)
            {
                powerUp3Duration--;
                if(powerUp3Duration <= 0)
                {
                    for(int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).removeSlowPowerUp();
                    }
                }
            }
            if(powerUp4Claimed == true && powerUp4Duration >= 0)
            {
                powerUp4Duration--;
                if(powerUp4Duration <= 0)
                {
                    for(int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).removeSlowPowerUp();
                    }
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

            g.setColor(new Color(255, 255, 255));
            g.setFont(font);
            timeString = String.format("Time: %02d:%02d", timeElapsedMin, timeElapsedSec);
            g.drawString(timeString, 400, 27);
            g.drawString("Score: " + score, 80, 27);
            countDownString = String.format("Find the door within %d seconds for %d bonus points!", countDown, countDownPoints);
            if(countDownPoints > 0)
            {
                g.drawString(countDownString, 80, 500);
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
            }
            // Bomb explosion
            if(timer >= 60 && bombArray.size() >= 1)
            {
                explodeBomb();

                currentFrame = fps;
                endFrame = currentFrame + 60;
                xPosExploded = bombArray.get(0).getX();
                yPosExploded = bombArray.get(0).getY();

                bombArray.remove(0);
                timer = 0;

                try
                {
                    playMusic("explosion");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
            currentFrame++;
            if(currentFrame <= endFrame)
            {
                g.setColor(new Color(255, 0, 0));
                g.fillRect(xPosExploded, yPosExploded, 40, 40);
                if(!map[(yPosExploded/40)-1][(xPosExploded/40)].equals("x") && !map[(yPosExploded/40)-1][(xPosExploded/40)].equals("1"))
                {
                    g.fillRect(xPosExploded, yPosExploded-40, 40, 40);
                }
                if(!map[(yPosExploded/40)+1][(xPosExploded/40)].equals("x") && !map[(yPosExploded/40)+1][(xPosExploded/40)].equals("1"))
                {
                    g.fillRect(xPosExploded, yPosExploded+40, 40, 40);
                }
                if(!map[(yPosExploded/40)][(xPosExploded/40)-1].equals("x") && !map[(yPosExploded/40)][(xPosExploded/40)-1].equals("1"))
                {
                    g.fillRect(xPosExploded-40, yPosExploded, 40, 40);
                }
                if(!map[(yPosExploded/40)][(xPosExploded/40)+1].equals("x") && !map[(yPosExploded/40)][(xPosExploded/40)+1].equals("1"))
                {
                    g.fillRect(xPosExploded+40, yPosExploded, 40, 40);
                }
            }

            // Draw the player
            g.drawImage(playerImg, xPosPlayer, yPosPlayer, null);

            // Draw the enemy
            for(int i = 0; i < enemies.size(); i++) {
                enemies.get(i).move(map);
                g.drawImage(enemyImg, enemies.get(i).getX(), enemies.get(i).getY(), null);
            }

        }
        // Game state 3, high score page
        else if(gameState == 3)
        {
            super.paintComponent(g);
            g.drawImage(highscoreImg, 0, 0, null);
            g.drawImage(backImg, 15, 15, null);
            g.setColor(Color.black);
            g.setFont(font);

            // top 5 scores
            Collections.sort(allScores);
            for(int i = 0; i < 5; i++) {
                if(allScores.get(i).getName().length() > 7) {
                    g.drawString((i+1) + ") " + allScores.get(i).getName().substring(0, 7) + ".. : " + allScores.get(i).getScore(), 125, 205 + 50 * i);
                } else {
                    g.drawString((i+1) + ") " + allScores.get(i).getName() + ": " + allScores.get(i).getScore(), 125, 205 + 50 * i);
                }
            }

            if(nameEntered != null) {
                // Name and Highest Score
                g.drawString("Player Information", 300, 250);
                if(nameEntered.length() > 7) {
                    g.drawString("Name: " + nameEntered.substring(0, 7) + "...", 300, 275);
                } else {
                    g.drawString("Name: " + nameEntered, 300, 275);
                }
                g.drawString("Highest Score: "  + highscoreMap.get(nameEntered), 300, 300);

                // Number of times played
                Collections.sort(allScores, new CompareByName());
                int index = Collections.binarySearch(allScores, new Score(nameEntered, 0), new CompareByName());
                int lowest = -1;
                int highest = -1;
                for(int i = index; i >= 0; i--) {
                    if(allScores.get(i).getName().equals(nameEntered)){
                        lowest = i;
                    }
                }
                for(int i = index; i < allScores.size(); i++) {
                    if(allScores.get(i).getName().equals(nameEntered)){
                        highest = i;
                    }
                }

                int numberOfPlays = highest - lowest + 1;
                g.drawString(String.format("Number of Plays: %d", numberOfPlays), 300, 325);

                // Tell them how to see their own info
                g.setColor(Color.yellow);
                g.fillRect(40, 450, 520, 30);
                g.setColor(Color.black);
                g.drawString("*Wrong Player Info? Start a New Game and Enter your Name!*", 42, 470);
            } else {
                // Tell them how to see their own info
                g.setColor(Color.yellow);
                g.fillRect(40, 450, 520, 30);
                g.setColor(Color.black);
                g.drawString("*Play a Game and Enter Your Username To See Your Stats!*", 52, 470);
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
        else if(gameState == 7)
        {
            g.drawImage(winImg, 0, 0, null);
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
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if(xPos >= 211 && xPos <= 388 && yPos >= 320 && yPos <= 370)
            {
                reset();
                timeStart = true;
                gameState = 2;
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if(xPos >= 211 && xPos <= 388 && yPos >= 380 && yPos <= 430)
            {
                allScores.clear();
                readHighscore();
                gameState = 3;
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if(xPos >= 211 && xPos <= 327 && yPos >= 440 && yPos <= 490)
            {
                gameState = 4;
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else if(xPos >= 336 && xPos <= 388 && yPos >= 440 && yPos <= 490)
            {
                gameState = 5;
                try {
                    playMusic("back");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else if(gameState == 1)
        {
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                gameState = 0;
                try {
                    playMusic("back");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                try {
                    playMusic("back");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else if(gameState == 4)
        {
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                gameState = 0;
                try {
                    playMusic("back");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                try {
                    playMusic("back");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        else if(gameState == 7)
        {
            if(enterName == true)
            {
                enterHighscoreName();
            }
            if(xPos >= 250 && xPos <= 350 && yPos >= 290 && yPos <= 336)
            {
                gameState = 0;
                try {
                    playMusic("back");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
        else if((player.intersects(door) && (key == KeyEvent.VK_ENTER)))
        {
            try {
                playMusic("door");
                playMusic("victory");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if(timeElapsedSec <= 30)
                score += 300;
            else if(timeElapsedSec > 30 && timeElapsedMin <= 1)
                score += 200;
            else if(timeElapsedMin > 1 && timeElapsedSec <= 30)
                score += 100;
            else if(timeElapsedSec > 30 && timeElapsedMin > 1)
                score += 50;

            gameState = 7;
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
    public static void move()
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
                    try {
                        playMusic("footstep");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------

    // Checks collision with boxes
    public static void checkCollision()
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


    public static void explodeBomb()
    {
        int bombX = bombArray.get(0).getX();
        int bombY = bombArray.get(0).getY();
        Rectangle bomb = new Rectangle(bombX, bombY, 40, 40);
        Rectangle bombUp = new Rectangle(bombX, bombY - 40, 40, 40);
        Rectangle bombDown = new Rectangle(bombX, bombY + 40, 40, 40);
        Rectangle bombLeft = new Rectangle(bombX - 40, bombY, 40, 40);
        Rectangle bombRight = new Rectangle(bombX + 40, bombY, 40, 40);
        Rectangle player = new Rectangle(((int)Math.round(xPosPlayer/40.0)*40), ((int)Math.round(yPosPlayer/40.0)*40), 40, 40);

        // Up
        if(map[(bombY/40) - 1][(bombX/40)].equals("W")) {
            map[(bombY/40) - 1][(bombX/40)] = "-";
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }
            score += 5;
        }
        // Down
        if(map[(bombY/40) + 1][(bombX/40)].equals("W")) {
            map[(bombY/40) + 1][(bombX/40)] = "-";
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }
            score += 5;
        }
        // Left
        if(map[(bombY/40)][(bombX/40) - 1].equals("W")) {
            map[(bombY/40)][(bombX/40) - 1] = "-";
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }
            score += 5;
        }
        // Right
        if(map[(bombY/40)][(bombX/40) + 1].equals("W")) {
            map[(bombY/40)][(bombX/40) + 1] = "-";
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }
            score += 5;
        }
        if(bomb.intersects(player) || bombUp.intersects(player) || bombDown.intersects(player) || bombLeft.intersects(player) || bombRight.intersects(player))
        {
            try {
                playMusic("death");
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameState = 6;
        }
        for(int i = 0; i < enemies.size(); i++) {
            Rectangle enemy = new Rectangle(enemies.get(i).getX(), enemies.get(i).getY(), 40, 40);

            if(bomb.intersects(enemy) || bombUp.intersects(enemy) || bombDown.intersects(enemy) || bombLeft.intersects(enemy) || bombRight.intersects(enemy)) {
                try {
                    playMusic("enemyDie");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
                enemies.remove(i);

                if(timeElapsedSec <= 30)
                {
                    score += 20;
                }
                else
                {
                    score += 10;
                }
            }
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

            int doorBlock = (int) (Math.random() * xBlocks.size());
            xPosDoor = xBlocks.get(doorBlock);
            yPosDoor = yBlocks.get(doorBlock);
            System.out.println("Door: " + xPosDoor + ", " + yPosDoor);

            generateEnemies(mapNumber);
        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error");
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public static void generatePowerUps()
    {
        int xPosPowerUp1 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp1 = (int)(Math.random()*(11)) +1;
        while(map[yPosPowerUp1][xPosPowerUp1].equals("1") || (xPosPowerUp1 == xPosDoor && yPosPowerUp1 == yPosDoor) || (xPosPowerUp1 == 1 && yPosPowerUp1 == 1))
        {
            xPosPowerUp1 = (int)(Math.random()*(13)) +1;
            yPosPowerUp1 = (int)(Math.random()*(11)) +1;
        }
        System.out.println("Power 1: " + xPosPowerUp1 + ", " + yPosPowerUp1);
        powerUp1 = new PowerUp(powerUpSpeedImg, xPosPowerUp1*40, yPosPowerUp1*40);

        int xPosPowerUp2 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp2 = (int)(Math.random()*(11)) +1;
        while(map[yPosPowerUp2][xPosPowerUp2].equals("1") || (xPosPowerUp1 == xPosPowerUp2 && yPosPowerUp1 == yPosPowerUp2) || (xPosPowerUp2 == xPosDoor && yPosPowerUp2 == yPosDoor) || (xPosPowerUp2 == 1 && yPosPowerUp2 == 1))
        {
            xPosPowerUp2 = (int)(Math.random()*(13)) +1;
            yPosPowerUp2 = (int)(Math.random()*(11)) +1;
        }
        System.out.println("Power 2: " + xPosPowerUp2 + ", " + yPosPowerUp2);
        powerUp2 = new PowerUp(powerUpSpeedImg, xPosPowerUp2*40, yPosPowerUp2*40);

        int xPosPowerUp3 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp3 = (int)(Math.random()*(11)) +1;
        while(map[yPosPowerUp3][xPosPowerUp3].equals("1") || (xPosPowerUp1 == xPosPowerUp3 && yPosPowerUp1 == yPosPowerUp3) || (xPosPowerUp2 == xPosPowerUp3 && yPosPowerUp2 == yPosPowerUp3) || (xPosPowerUp3 == xPosDoor && yPosPowerUp3 == yPosDoor) || (xPosPowerUp3 == 1 && yPosPowerUp3 == 1))
        {
            xPosPowerUp3 = (int)(Math.random()*(13)) +1;
            yPosPowerUp3 = (int)(Math.random()*(11)) +1;
        }
        System.out.println("Power 3: " + xPosPowerUp3 + ", " + yPosPowerUp3);
        powerUp3 = new PowerUp(powerUpSlowImg, xPosPowerUp3*40, yPosPowerUp3*40);

        int xPosPowerUp4 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp4 = (int)(Math.random()*(11)) +1;
        while(map[yPosPowerUp4][xPosPowerUp4].equals("1") || (xPosPowerUp1 == xPosPowerUp4 && yPosPowerUp1 == yPosPowerUp4) || (xPosPowerUp2 == xPosPowerUp4 && yPosPowerUp2 == yPosPowerUp4) || (xPosPowerUp3 == xPosPowerUp4 && yPosPowerUp3 == yPosPowerUp4) || (xPosPowerUp4 == xPosDoor && yPosPowerUp4 == yPosDoor) || (xPosPowerUp4 == 1 && yPosPowerUp4 == 1))
        {
            xPosPowerUp4 = (int)(Math.random()*(13)) +1;
            yPosPowerUp4 = (int)(Math.random()*(11)) +1;
        }
        System.out.println("Power 4: " + xPosPowerUp4 + ", " + yPosPowerUp4);
        powerUp4 = new PowerUp(powerUpSlowImg, xPosPowerUp4*40, yPosPowerUp4*40);
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public static void generateEnemies(int mapNumber) {
        if(mapNumber == 1) {
            enemies.add(new Enemy(enemyImg, 160, 120, "left"));
            enemies.add(new Enemy(enemyImg, 400, 40, "left"));
            enemies.add(new Enemy(enemyImg, 280, 200, "left"));
            enemies.add(new Enemy(enemyImg, 280, 280, "left"));
            enemies.add(new Enemy(enemyImg, 400, 440, "left"));
        }
        if(mapNumber == 2) {
            enemies.add(new Enemy(enemyImg, 320, 40, "left"));
            enemies.add(new Enemy(enemyImg, 160, 120, "left"));
            enemies.add(new Enemy(enemyImg, 280, 200, "left"));
            enemies.add(new Enemy(enemyImg, 280, 280, "left"));
            enemies.add(new Enemy(enemyImg, 400, 440, "left"));
        }
        if(mapNumber == 3) {
            enemies.add(new Enemy(enemyImg, 440, 40, "left"));
            enemies.add(new Enemy(enemyImg, 120, 120, "left"));
            enemies.add(new Enemy(enemyImg, 280, 200, "left"));
            enemies.add(new Enemy(enemyImg, 280, 320, "left"));
            enemies.add(new Enemy(enemyImg, 400, 440, "left"));
        }

    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public static void enterHighscoreName()
    {
        if(enterName == true)
        {
            try
            {
                outFile = new PrintWriter(new FileWriter("highscore.txt", true));

                nameEntered = JOptionPane.showInputDialog("Enter your name for the highscore.");

                // If the user presses the X button, return back
                if(nameEntered == null)
                {
                    return;
                }

                // If the user tries to enter an empty name
                if(nameEntered.length() <= 0)
                {
                    // Display that it is invalid and return
                    JOptionPane.showMessageDialog(null, "No name given.", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                    enterHighscoreName();
                }

                // Check if the file entered is a valid file
                else
                {
                    // If it is a valid file, display that it was sucessfully added
                    JOptionPane.showMessageDialog(null, "Highscore added.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    // Add the new file to the arraylist of files names and the combo box so that the user can select it
                }
                outFile.println(nameEntered + "," + score);
                outFile.close();
            }
            catch(IOException e)
            {
                System.out.println("Input / Output Error.");
            }
        }
        enterName = false;
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public static void readHighscore()
    {
        try
        {
            inFile = new BufferedReader(new FileReader("highscore.txt"));
            String line = "";
            while((line = inFile.readLine()) != null)
            {
                Score score = new Score(line.substring(0, line.indexOf(",")), Integer.parseInt(line.substring(line.indexOf(",") + 1)));
                allScores.add(score);
                if(highscoreMap.get(line.substring(0, line.indexOf(","))) == null || highscoreMap.get(line.substring(0, line.indexOf(","))) < Integer.parseInt(line.substring(line.indexOf(",") + 1))) {
                    highscoreMap.put(line.substring(0, line.indexOf(",")), Integer.parseInt(line.substring(line.indexOf(",") + 1)));
                }
            }
            inFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error.");
        }
    }

// --------------------------------------------------------------------------------------------------------------------------------------------------------

    public void reset() {
        xPosPlayer = 40;
        yPosPlayer = 40;
        direction = "down";
        enemies.clear();
        xBlocks.clear();
        yBlocks.clear();
        bombArray.clear();
        allScores.clear();
        currentFrame = 0;
        endFrame = 0;
        fps = 0;
        powerUp1Claimed = false;
        powerUp2Claimed = false;
        powerUp3Claimed = false;
        powerUp4Claimed = false;
        timeElapsedSec = 0;
        timeElapsedMin = 0;
        score = 0;
        countDown = 30;
        countDownPoints = 300;

        // Load new map
        try {
            loadMaps();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Generate new power-ups
        generatePowerUps();
    }

// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // THREADING
    public void run() {
        while(true) {
            move();
            checkCollision();
            repaint();
            try {
                Thread.sleep(1000/60);
            } catch(Exception e) {}
            if(gameState == 2)
            {
                if(timeStart == true)
                {
                    timeElapsedMsStart = System.currentTimeMillis();
                    timeStart = false;
                }
                timeElapsedMsEnd = System.currentTimeMillis();
                if(timeElapsedMsEnd - timeElapsedMsStart >= 1000)
                {
                    timeStart = true;
                    timeElapsedSec++;
                    countDown--;
                }
                if(timeElapsedSec >= 60)
                {
                    timeElapsedMin++;
                    timeElapsedSec = 0;
                }
                if(countDown <= 0)
                {
                    if(countDownPoints > 0)
                    {
                        countDownPoints -= 100;
                    }
                    countDown = 30;
                }
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    public static void playMusic(String song) throws IOException
    {
        File file = new File("Music/" + song + ".wav");
        try
        {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
        }
        catch (Exception e)
        {
            e.printStackTrace();
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

        // Load music
        // if(gameState == 0)
        // {
        //     File titleSong = new File("Music/title.wav");
        //     try
        //     {
        //         clip = AudioSystem.getClip();
        //         clip.open(AudioSystem.getAudioInputStream(titleSong));
        //         clip.loop(-1);
        //         clip.start();
        //         FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        //         fc.setValue(-20f);
        //     }
        //     catch(Exception e)
        //     {
        //         e.printStackTrace();
        //     }
        // }

        // LOADING IMAGES
        try
        {
            backgroundImg = ImageIO.read(new File("Images/Background.png"));
            highscoreImg = ImageIO.read(new File("Images/highscore.png"));
            // rulesImg = ImageIO.read(new File("Images/rulesImg.png"));
            aboutImg = ImageIO.read(new File("Images/about.png"));
            backImg = ImageIO.read(new File("Images/back.png"));
            gameOverImg = ImageIO.read(new File("Images/gameOver.png"));
            winImg = ImageIO.read(new File("Images/win.png"));

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
            powerUpSlowImg = ImageIO.read(new File("Images/powerUpSlow.png"));

            doorImg = ImageIO.read(new File("Images/door.png"));

            enemyImg = ImageIO.read(new File("Images/laptop.png"));

        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error");
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // USELESS
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
}