/*
 * INTRODUCTORY COMMENTS:
 *
 * NAME: Matthew Liu and Jeevesh Balendra
 *
 * DATE: January 21st, 2024
 *
 * DESCRIPTION: This is the main class for our ISU game a Bomberman Spinoff.
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
    // GRAPHICS VARIABLES
    private static JFrame frame;

    // CHARACTER VARIABLES
    private static String nameEntered;
    private static int xPosPlayer = 40, yPosPlayer = 40;
    private static boolean up, left, down, right;
    private static boolean blockUp, blockLeft, blockDown, blockRight;
    private static int vel = 3;
    private static String direction = "down";

    // GAME STATE
    private static int gameState = 0;

    // MAP
    private static String[][] map = new String[13][15];
    private static ArrayList<Integer> xBlocks = new ArrayList<>();
    private static ArrayList<Integer> yBlocks = new ArrayList<>();

    // IMAGES
    private static BufferedImage wallImg, unbreakableWallImg;
    private static BufferedImage backgroundImg, highscoreImg, rulesImg, aboutImg, backImg, gameOverImg, winImg, bombImg, powerUpSpeedImg, powerUpSlowImg, bedImg, enemyImg;
    private static BufferedImage[] characterSprites;
    private static BufferedImage playerImg;
    private static int spriteNum = 1;
    private static int spriteCounter = 0;

    // BOMBS
    private static ArrayList<Bomb> bombArray = new ArrayList<>();
    private static int timer = 0;
    private static int currentFrame;
    private static int endFrame;
    private static int xPosExploded;
    private static int yPosExploded;
    private static int fps = 0;

    // ENEMIES
    private static ArrayList<Enemy> enemies = new ArrayList<>();

    // POWERUPS
    private static PowerUp powerUp1;
    private static PowerUp powerUp2;
    private static PowerUp powerUp3;
    private static PowerUp powerUp4;
    private static boolean powerUp1Claimed = false;
    private static boolean powerUp2Claimed = false;
    private static boolean powerUp3Claimed = false;
    private static boolean powerUp4Claimed = false;
    private static int powerUp1Duration = 300;
    private static int powerUp2Duration = 300;
    private static int powerUp3Duration = 300;
    private static int powerUp4Duration = 300;

    // BED
    private static int xPosBed;
    private static int yPosBed;

    // HIGHSCORE
    private static HashMap<String, Integer> highscoreMap = new HashMap<>();
    private static ArrayList<Score> allScores = new ArrayList<>();
    private static boolean enterName = false;
    private static Font font = new Font("SansSerif", Font.PLAIN, 18);
    private static boolean timeStart = false;
    private static long timeElapsedMsStart = 0;
    private static long timeElapsedMsEnd = 0;
    private static int timeElapsedSec = 0;
    private static int timeElapsedMin = 0;
    private static String timeString = "";
    private static int countDown = 30;
    private static int countDownPoints = 300;
    private static String countDownString = "";
    private static int score = 0;

    // MUSIC / AUDIO
    private static Clip clip;

// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // INITIALIZE
    // Description: This method intializes the program
    // Parameters: N/A.
    // Return: N/A.
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
    // Description: This method draws the graphics of the game
    // Parameters: Graphics g
    // Return: N/A.
    public void paintComponent(Graphics g)
    {
        // Clear screen
        super.paintComponent(g);

        // Draw background image
        g.drawImage(backgroundImg, 0, 0, null);

        // Game state 1, about page
        if(gameState == 1)
        {
            // Clear screen, draw about page, and draw back button
            super.paintComponent(g);
            g.drawImage(aboutImg, 0, 0, null);
            g.drawImage(backImg, 15, 15, null);
        }

        // Game state 2, main game
        else if(gameState == 2)
        {
            // Clear screen
            super.paintComponent(g);

            // Draw Bed
            g.drawImage(bedImg, xPosBed, yPosBed, null);

            // If player has not claimed power up
            if(powerUp1Claimed == false)
            {
                // Draw the power up
                powerUp1.draw(g);

                // If player has claimed powerup
                if(xPosPlayer >= powerUp1.getX()-10 && xPosPlayer <= powerUp1.getX()+10 && yPosPlayer >= powerUp1.getY()-10 && yPosPlayer <= powerUp1.getY()+10)
                {
                    // Play sound effect
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Apply the power up
                    vel += powerUp1.getSpeedPowerUp();
                    powerUp1Claimed = true;
                }
            }
            // If player has not claimed power up
            if(powerUp2Claimed == false)
            {
                // Draw the power up
                powerUp2.draw(g);

                // If player has claimed powerup
                if(xPosPlayer >= powerUp2.getX()-10 && xPosPlayer <= powerUp2.getX()+10 && yPosPlayer >= powerUp2.getY()-10 && yPosPlayer <= powerUp2.getY()+10)
                {
                    // Play sound effect
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Apply the power up
                    vel += powerUp2.getSpeedPowerUp();
                    powerUp2Claimed = true;
                }
            }
            // If player has not claimed power up
            if(powerUp3Claimed == false)
            {
                // Draw the power up
                powerUp3.draw(g);

                // If player has claimed powerup
                if(xPosPlayer >= powerUp3.getX()-10 && xPosPlayer <= powerUp3.getX()+10 && yPosPlayer >= powerUp3.getY()-10 && yPosPlayer <= powerUp3.getY()+10)
                {
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Apply the power up
                    for(int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).getSlowPowerUp();
                    }
                    powerUp3Claimed = true;
                }
            }
            // If player has not claimed power up
            if(powerUp4Claimed == false)
            {
                // Draw the power up
                powerUp4.draw(g);

                // If player has claimed powerup
                if(xPosPlayer >= powerUp4.getX()-10 && xPosPlayer <= powerUp4.getX()+10 && yPosPlayer >= powerUp4.getY()-10 && yPosPlayer <= powerUp4.getY()+10)
                {
                    try {
                        playMusic("collect");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // Apply the power up
                    for(int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).getSlowPowerUp();
                    }
                    powerUp4Claimed = true;
                }
            }
            // If power up is claimed and duration is more than 0
            if(powerUp1Claimed == true && powerUp1Duration >= 0)
            {
                // Decrease duration of the power up
                powerUp1Duration--;

                // If the duration of the power up is done
                if(powerUp1Duration <= 0)
                {
                    // Remove the power up
                    vel += powerUp1.removeSpeedPowerUp();
                }
            }
            // If power up is claimed and duration is more than 0
            if(powerUp2Claimed == true && powerUp2Duration >= 0)
            {
                // Decrease duration of the power up
                powerUp2Duration--;

                // If the duration of the power up is done
                if(powerUp2Duration <= 0)
                {
                    // Remove the power up
                    vel += powerUp2.removeSpeedPowerUp();
                }
            }
            // If power up is claimed and duration is more than 0
            if(powerUp3Claimed == true && powerUp3Duration >= 0)
            {
                // Decrease duration of the power up
                powerUp3Duration--;

                // If the duration of the power up is done
                if(powerUp3Duration <= 0)
                {
                    // Remove the power up
                    for(int i = 0; i < enemies.size(); i++)
                    {
                        enemies.get(i).removeSlowPowerUp();
                    }
                }
            }
            // If power up is claimed and duration is more than 0
            if(powerUp4Claimed == true && powerUp4Duration >= 0)
            {
                // Decrease duration of the power up
                powerUp4Duration--;

                // If the duration of the power up is done
                if(powerUp4Duration <= 0)
                {
                    // Remove the power up
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
                    // "-" is for the empty spaces that the player can walk on, nothing is drawn on those tiles
                    // "x" is for the outer wall / boundary
                    if(map[j/40][i/40].equals("x"))
                    {
                        g.setColor(new Color(0, 0, 0));
                        g.fillRect(i, j, 40, 40);
                    }
                    // "W" is for the blocks on the map that the player cannot go through, but can break
                    else if(map[j/40][i/40].equals("W"))
                    {
                        g.drawImage(wallImg, i, j, null);
                    }
                    // "1" is for the blocks on the map that the player cannot go through and cannot break
                    else if(map[j/40][i/40].equals("1"))
                    {
                        g.drawImage(unbreakableWallImg, i, j, null);
                    }
                }
            }

            // Set colour and font of text
            g.setColor(new Color(255, 255, 255));
            g.setFont(font);
            // Draw the timer on the top
            timeString = String.format("Time: %02d:%02d", timeElapsedMin, timeElapsedSec);
            g.drawString(timeString, 400, 27);
            g.drawString("Score: " + score, 80, 27);
            // Draw the countdown timer on the bottom
            countDownString = String.format("Find the bed within %d seconds for %d bonus points!", countDown, countDownPoints);
            // If the countdown timer for bonus points has not run out, draw the countdown timer
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

            // Display the bombs that the player places and start the timer for the bomb explosion
            for(int i = 0; i < bombArray.size(); i++)
            {
                bombArray.get(i).draw(g);
                timer++;
            }
            // Explode the bomb if the timer is up and if the array size is more than or equal to 1 (if there are bombs to explode)
            if(timer >= 60 && bombArray.size() >= 1)
            {
                // Explode the bomb
                explodeBomb();

                // Get x and y position of the bomb
                xPosExploded = bombArray.get(0).getX();
                yPosExploded = bombArray.get(0).getY();

                // Find the current frame number and the frame number that the bomb should exploded on
                currentFrame = fps;
                endFrame = currentFrame + 10;

                // Remove the bomb from the array and reset the explosion timer
                bombArray.remove(0);
                timer = 0;

                // Play sound effect
                try {
                    playMusic("explosion");
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            // Increase the current frame
            currentFrame++;
            // If the current frame has not reached the end frame yet, draw the radius of the bombs explosion
            if(currentFrame <= endFrame)
            {
                // Set the colour of the explosion to red
                g.setColor(new Color(255, 0, 0));
                // Draw the explosion radius at the bombs location
                g.fillRect(xPosExploded, yPosExploded, 40, 40);
                // If the block above the bomb is an empty space or breakable wall, draw the explosion radius
                if(!map[(yPosExploded/40)-1][(xPosExploded/40)].equals("x") && !map[(yPosExploded/40)-1][(xPosExploded/40)].equals("1"))
                {
                    g.fillRect(xPosExploded, yPosExploded-40, 40, 40);
                }
                // If the block below the bomb is an empty space or breakable wall, draw the explosion radius
                if(!map[(yPosExploded/40)+1][(xPosExploded/40)].equals("x") && !map[(yPosExploded/40)+1][(xPosExploded/40)].equals("1"))
                {
                    g.fillRect(xPosExploded, yPosExploded+40, 40, 40);
                }
                // If the block on the left of the bomb is an empty space or breakable wall, draw the explosion radius
                if(!map[(yPosExploded/40)][(xPosExploded/40)-1].equals("x") && !map[(yPosExploded/40)][(xPosExploded/40)-1].equals("1"))
                {
                    g.fillRect(xPosExploded-40, yPosExploded, 40, 40);
                }
                // If the block on the right of the bomb is an empty space or breakable wall, draw the explosion radius
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

            // gets the top 5 scores 
            // sorts arraylist of score objects by score
            Collections.sort(allScores);
            for(int i = 0; i < 5; i++) {
                // if their name is too long it gets cut off
                if(allScores.get(i).getName().length() > 7) {
                    g.drawString((i+1) + ") " + allScores.get(i).getName().substring(0, 7) + ".. : " + allScores.get(i).getScore(), 125, 205 + 50 * i);
                } else {
                    g.drawString((i+1) + ") " + allScores.get(i).getName() + ": " + allScores.get(i).getScore(), 125, 205 + 50 * i);
                }
            }

            // if they didn't play a game and entered their name there won't be user info
            if(nameEntered != null) {
                // Name and Highest Score
                g.drawString("Player Information", 300, 250);
                // name cutoff if too long
                if(nameEntered.length() > 7) {
                    g.drawString("Name: " + nameEntered.substring(0, 7) + "...", 300, 275);
                } else {
                    g.drawString("Name: " + nameEntered, 300, 275);
                }
                g.drawString("Highest Score: "  + highscoreMap.get(nameEntered), 300, 300);

                // Number of times played
                // sorts by name alphabetically
                Collections.sort(allScores, new CompareByName());
                // searches for the position of their name using binary search
                int index = Collections.binarySearch(allScores, new Score(nameEntered, 0), new CompareByName());
                int lowest = -1;
                int highest = -1;
                // checks the lowest and highest index containing their name
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
                
                // subtracts to find the number of times they played
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
            // Clear screen, draw rules page, and draw back button
            super.paintComponent(g);
            g.drawImage(rulesImg, 0, 0, null);
            g.drawImage(backImg, 15, 15, null);
        }

        // Game state 5, exit game
        else if(gameState == 5)
        {
            // Clear the screen and exit the game
            super.paintComponent(g);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }

        // Game State 6, game over
        else if(gameState == 6)
        {
            // Clear screen, draw game over page, draw back button, display your score, and allow user to enter in their username when the user presses back
            super.paintComponent(g);
            g.drawImage(gameOverImg, 0, 0, null);
            g.drawImage(backImg, 250, 290, null);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("Your Score: " + score, 235, 200);
            enterName = true;
        }

        else if(gameState == 7)
        {
            // Clear screen, draw win page, draw back button, display your score, and allow user to enter in their username when the user presses back
            super.paintComponent(g);
            g.drawImage(winImg, 0, 0, null);
            g.drawImage(backImg, 250, 290, null);
            g.setColor(Color.white);
            g.setFont(font);
            g.drawString("Your Score: " + score, 235, 200);
            enterName = true;
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // MOUSE INPUT
    // Description: This method gets the x and y positions of where the user clicks and determines what happens when the user clicks in specific locations
    // Parameters: MouseEvent e
    // Return: N/A.
    public void mousePressed(MouseEvent e) {
        // Get the x and y position of where the user clicks
        int xPos = e.getX();
        int yPos = e.getY();

        // If the user is on the main menu
        if(gameState == 0)
        {
            // If the user clicks on the about button
            if(xPos >= 211 && xPos <= 388 && yPos >= 260 && yPos <= 310)
            {
                // Change the game state to 1 (about page), play a click sound effect, and play a different background song
                gameState = 1;
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                clip.stop();
                try {
                    playBackground("menuMusic");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // If the user clicks on the play button
            else if(xPos >= 211 && xPos <= 388 && yPos >= 320 && yPos <= 370)
            {
                // Reset the game and starts the timer
                reset();
                timeStart = true;

                // Change the game state to 2 (play game), play a click sound effect, and play a different background song
                gameState = 2;
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                clip.stop();
                try {
                    playBackground("gameMusic");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // If the user clicks on the highscore button
            else if(xPos >= 211 && xPos <= 388 && yPos >= 380 && yPos <= 430)
            {
                //
                allScores.clear();
                readHighscore();

                // Change the game state to 3 (highscore page), play a click sound effect, and play a different background song
                gameState = 3;
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                clip.stop();
                try {
                    playBackground("menuMusic");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // If the user clicks on the rules button
            else if(xPos >= 211 && xPos <= 327 && yPos >= 440 && yPos <= 490)
            {
                // Change the game state to 4 (rules page), play a click sound effect, and play a different background song
                gameState = 4;
                try {
                    playMusic("click");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                clip.stop();
                try {
                    playBackground("menuMusic");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            // If the user clicks on the exit button
            else if(xPos >= 336 && xPos <= 388 && yPos >= 440 && yPos <= 490)
            {
                // Change the game state to 5 (exit game) and play a click sound effect
                gameState = 5;
                try {
                    playMusic("back");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // If the user is on the about page
        else if(gameState == 1)
        {
            // If the user clicks on the back button
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                // Change the game state to 0 (main menu), play a click sound effect, and play a different background song
                gameState = 0;
                clip.stop();
                try {
                    playMusic("back");
                    playBackground("title");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // If the user is on the highscore page
        else if(gameState == 3)
        {
            // If the user clicks on the back button
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                // Change the game state to 0 (main menu), play a click sound effect, and play a different background song
                gameState = 0;
                clip.stop();
                try {
                    playMusic("back");
                    playBackground("title");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // If the user is on the rules page
        else if(gameState == 4)
        {
            // If the user clicks on the back button
            if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
            {
                // Change the game state to 0 (main menu), play a click sound effect, and play a different background song
                gameState = 0;
                clip.stop();
                try {
                    playMusic("back");
                    playBackground("title");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // If the user is on the game over screen
        else if(gameState == 6)
        {
            // Allow the user to enter in their username
            if(enterName == true)
            {
                enterHighscoreName();
            }

            // If the user clicks on the back button
            if(xPos >= 250 && xPos <= 350 && yPos >= 290 && yPos <= 336)
            {
                // Change the game state to 0 (main menu), play a click sound effect, and play a different background song
                gameState = 0;
                try {
                    playMusic("back");
                    playBackground("title");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // If the user is on the victory screen
        else if(gameState == 7)
        {
            // Allow the user to enter in their username
            if(enterName == true)
            {
                enterHighscoreName();
            }

            // If the user clicks on the back button
            if(xPos >= 250 && xPos <= 350 && yPos >= 290 && yPos <= 336)
            {
                // Change the game state to 0 (main menu), play a click sound effect, and play a different background song
                gameState = 0;
                try {
                    playMusic("back");
                    playBackground("title");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // KEYBOARD INPUT
    // Description: This method gets the keys that the user presses and determines what happens when the user presses those keys
    // Parameters: KeyEvent e
    // Return: N/A.
    public void keyPressed(KeyEvent e)
    {
        // Get the key that the user presses
        int key = e.getKeyCode();

        // Create player and bed rectangle for opening the bed to win
        Rectangle player = new Rectangle(xPosPlayer, yPosPlayer, 40, 40);
        Rectangle bed = new Rectangle(xPosBed, yPosBed, 40, 40);

        // If the user presses A set left to true and right to false. If the user presses D set right to true and left to false. If the user presses W set up to true and down to false. If the user presses S set down to true and up to false.
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

        // If the user presses space
        else if(key == KeyEvent.VK_SPACE)
        {
            // If the game state is 2 (in game)
            if(gameState == 2)
            {
                // Create a new bomb object and add the bomb object to an arraylist of bomb objects
                Bomb bomb = new Bomb(bombImg, xPosPlayer, yPosPlayer);
                bombArray.add(bomb);

                // Play a sound effect
                try {
                    playMusic("placeBomb");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        // Shortcut for Ms. Wong to lose: Press "-" to lose.
        else if(key == KeyEvent.VK_MINUS)
        {
            // Stop the background music, play a death tune, and change the game state to 6 (game over)
            gameState = 6;
            clip.stop();
            try {
                playMusic("death");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        // If the player is intersecting with the bed and the user presses enter. Shortcut for Ms. Wong to win: Press "=" to win.
        else if((player.intersects(bed) && (key == KeyEvent.VK_ENTER)) || key == KeyEvent.VK_EQUALS)
        {
            // Play a sound effect and a victory tune
            clip.stop();
            try {
                playMusic("door");
                playMusic("victory");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // Determine how many points the user gets from entering the bed
            // If the user enters the bed within 30 seconds: 300 points
            // If the user enters the bed after 30 seconds, but within 1 minute: 200 points
            // If the user enters the bed after 1 minute, but within 1 minute and 30 seconds: 100 points
            // If the user enters the bed after 1 minute and 30 seconds: 50 points
            if(timeElapsedSec <= 30)
                score += 300;
            else if(timeElapsedSec > 30 && timeElapsedMin <= 1)
                score += 200;
            else if(timeElapsedMin > 1 && timeElapsedSec <= 30)
                score += 100;
            else if(timeElapsedSec > 30 && timeElapsedMin > 1)
                score += 50;

            // Set the gamestate to 7 (victory screen)
            gameState = 7;
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // KEYBOARD INPUT
    // Description: This method gets the keys that the user releases and determines what happens when the user releases those keys
    // Parameters: KeyEvent e
    // Return: N/A.
    public void keyReleased(KeyEvent e)
    {
        // Get the key that the user releases
        int key = e.getKeyCode();

        // If the user releases A set left to false. If the user releases D set right to false. If the user releases W set up to false. If the user releases S set down to false
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
    // Description: This method moves the player based on the users inputs
    // Parameters: N/A.
    // Return: N/A.
    public static void move()
    {
        // If the game stat is 2 (in game)
        if(gameState == 2)
        {
            // If the user is trying to go left, right, up, or down, move the player if possible, and play the sprite animations
            if(left || right || up || down)
            {
                // If the user is trying to move left and if the player is not blocked on the left, move left. Set direction to left for sprite animations.
                if(left && xPosPlayer > 40 && !blockLeft)
                {
                    xPosPlayer -= vel;
                    direction = "left";
                }
                // If the user is trying to move right and if the player is not blocked on the right, move right. Set direction to right for sprite animations.
                else if(right && xPosPlayer < 520 && !blockRight)
                {
                    xPosPlayer += vel;
                    direction = "right";
                }
                // If the user is trying to move up and if the player is not blocked on top, move up. Set direction to up for sprite animations.
                if(up && yPosPlayer > 40 && !blockUp)
                {
                    yPosPlayer -= vel;
                    direction = "up";
                }
                // If the user is trying to move down and if the player is not blocked below, move down. Set direction to down for sprite animations.
                else if(down && yPosPlayer < 440 && !blockDown)
                {
                    yPosPlayer += vel;
                    direction = "down";
                }

                // Increase the sprite counter. If the sprite counter is more than 8, change the current sprite to the other sprite. Reset the sprite counter to 0. Play a footstep sound effect.
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

    // COLLISION DETECTION
    // Description: This method checks collision with boxes and enemies. It checks if the player is colliding with walls.
    // Parameters: N/A.
    // Return: N/A.
    public static void checkCollision()
    {
        if(gameState == 2) {
            // Player and Tile Collision
            // head tile/feet/left/right tile is the location of those body parts on the player
            // since they can be in different tiles
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

            // Checks left block(s) of player
            if(!map[yFeetTile][xFeetTile-1].equals("-") || !map[yHeadTile][xHeadTile-1].equals("-")) {
                Rectangle tile = new Rectangle((xTile-1) * 40, yTile * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockLeft = true;
                }
            }
            // Checks right block(s)
            if(!map[yFeetTile][xFeetTile+1].equals("-") || !map[yHeadTile][xHeadTile+1].equals("-")) {
                Rectangle tile = new Rectangle((xTile + 1) * 40, (yTile) * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockRight = true;
                }
            }
            // Checks up block(s) of player
            if(!map[yLeftHandTile-1][xLeftHandTile].equals("-") || !map[yRightHandTile-1][xRightHandTile].equals("-")) {
                Rectangle tile = new Rectangle((xTile) * 40, (yTile-1) * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockUp = true;
                }
            }
            // Checks down block(s)
            if(!map[yLeftHandTile+1][xLeftHandTile].equals("-") || !map[yRightHandTile+1][xRightHandTile].equals("-")) {
                Rectangle tile = new Rectangle((xTile) * 40, (yTile + 1) * 40, 40, 40);
                if(player.intersects(tile)) {
                    blockDown = true;
                }
            }

            // Player and Enemy Collision
            // goes through all the enemies to see if they intersect with the player box
            for(Enemy enemy : enemies) {
                Rectangle enemyBox = new Rectangle(enemy.getX() + 10, enemy.getY() + 10, 20, 20);
                if(enemyBox.intersects(player)) {
                    clip.stop();
                    try {
                        playMusic("death");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    gameState = 6;
                }
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // BOMB EXPLOSION
    // Description: This method allows the bomb to break blocks on top, below, left, and right. It also checks if the bomb hits the user or enemies.
    // Parameters: N/A.
    // Return: N/A.
    public static void explodeBomb()
    {
        // Get the x and y positions of the bomb
        int bombX = bombArray.get(0).getX();
        int bombY = bombArray.get(0).getY();

        // Create a rectangle object for the bomb's tile, the tile above the bomb, the tile below the bomb, the tile on the left of the bomb, and the tile on the right of the bomb
        Rectangle bomb = new Rectangle(bombX, bombY, 40, 40);
        Rectangle bombUp = new Rectangle(bombX, bombY - 40, 40, 40);
        Rectangle bombDown = new Rectangle(bombX, bombY + 40, 40, 40);
        Rectangle bombLeft = new Rectangle(bombX - 40, bombY, 40, 40);
        Rectangle bombRight = new Rectangle(bombX + 40, bombY, 40, 40);

        // Create a rectangle object for the player
        Rectangle player = new Rectangle(((int)Math.round(xPosPlayer/40.0)*40), ((int)Math.round(yPosPlayer/40.0)*40), 40, 40);

        // If the block above the bomb is a breakable wall
        if(map[(bombY/40) - 1][(bombX/40)].equals("W")) {
            // Break the wall. Change the "W" in the 2d map array to a "-".
            map[(bombY/40) - 1][(bombX/40)] = "-";

            // Play a sound effect for the wall breaking
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Increase the score by 5 for breaking the wall
            score += 5;
        }

        // If the block below the bomb is a breakable wall
        if(map[(bombY/40) + 1][(bombX/40)].equals("W")) {
            // Break the wall. Change the "W" in the 2d map array to a "-".
            map[(bombY/40) + 1][(bombX/40)] = "-";

            // Play a sound effect for the wall breaking
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Increase the score by 5 for breaking the wall
            score += 5;
        }

        // If the block on the left of the bomb is a breakable wall
        if(map[(bombY/40)][(bombX/40) - 1].equals("W")) {
            // Break the wall. Change the "W" in the 2d map array to a "-".
            map[(bombY/40)][(bombX/40) - 1] = "-";

            // Play a sound effect for the wall breaking
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Increase the score by 5 for breaking the wall
            score += 5;
        }

        // If the block on the right of the bomb is a breakable wall
        if(map[(bombY/40)][(bombX/40) + 1].equals("W")) {
            // Break the wall. Change the "W" in the 2d map array to a "-".
            map[(bombY/40)][(bombX/40) + 1] = "-";

            // Play a sound effect for the wall breaking
            try {
                playMusic("break");
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Increase the score by 5 for breaking the wall
            score += 5;
        }

        // If the bomb's explosion (rectangle) intersects the player (rectangle) in any direction
        if(bomb.intersects(player) || bombUp.intersects(player) || bombDown.intersects(player) || bombLeft.intersects(player) || bombRight.intersects(player))
        {
            // Stop the background music, play a death tune, and change the game state to 6 (game over)
            clip.stop();
            try {
                playMusic("death");
            } catch (IOException e) {
                e.printStackTrace();
            }
            gameState = 6;
        }

        // For each of the enemies
        for(int i = 0; i < enemies.size(); i++) {
            // Create a rectangle object for the enemy
            Rectangle enemy = new Rectangle(enemies.get(i).getX(), enemies.get(i).getY(), 40, 40);

            // If the bomb's explosion (rectangle) intersects the enemy (rectangle) in any direction
            if(bomb.intersects(enemy) || bombUp.intersects(enemy) || bombDown.intersects(enemy) || bombLeft.intersects(enemy) || bombRight.intersects(enemy)) {
                // play a sound effect for killing an enemy
                try {
                    playMusic("enemyDie");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // Remove the enemy from the arraylist of enemies
                enemies.remove(i);

                // If the enemy was killed within 30 seconds: 20 points
                // If the enemy was killed after 30 seconds: 10 points
                if(timeElapsedSec <= 30)
                    score += 20;
                else
                    score += 10;
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------

    // LOAD MAPS
    // Description: This method reads "map.txt" and puts one of the maps from the text file into a 2d array so that it can be drawn when the game starts
    // Parameters: N/A.
    // Return: N/A.
    public static void loadMaps() throws IOException
    {
        // Try catch incase there is an error reading "map.txt"
        try
        {
            // Buffered reader to read the map
            BufferedReader br = new BufferedReader(new FileReader("map.txt"));

            // Randomly choose one of three maps to read
            int mapNumber = (int)(Math.random()*(3)) + 1;
            // Finds how many lines need to be skipped to reach the desired map
            int end = 13 * (mapNumber - 1);
            // Skips the lines that need to be skipped in order to reach the desired map
            for(int i = 0; i < end; i++)
            {
                br.readLine();
            }

            // Each map is 15x13 (15 columns, 13 rows)
            // Go thorugh each row
            for(int i = 0; i < 13; i++)
            {
                // Read the row
                String line = br.readLine();
                // Go through each column
                for(int j = 0; j < 15; j++)
                {
                    // Put each letter/symbol into the 2d map array
                    map[i][j] = line.substring(0, 1);

                    // If the letter is a "W", add the x and y coordinates of it into an arraylist (this keeps track of where breakable blocks will be)
                    if(map[i][j].equals("W")) {
                        xBlocks.add(j * 40);
                        yBlocks.add(i * 40);
                    }

                    line = line.substring(1);
                }
            }

            // Close the buffered reader
            br.close();

            // Create the bed
            int bedBlock = (int) (Math.random() * xBlocks.size());
            xPosBed = xBlocks.get(bedBlock);
            yPosBed = yBlocks.get(bedBlock);
            System.out.println("Bed: " + xPosBed/40 + ", " + yPosBed/40);

            // Call a method to generate enemies based on the map number
            generateEnemies(mapNumber);
        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error");
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------

    // GENERATE POWER UPS
    // Description: This method generates power ups. It generates a random x and y coordinate to spawn the power up on. When creating a power up object,
    //              the sprite that should be drawn as well as the x and y coordinates of the power up are passed in
    // Parameters: N/A.
    // Return: N/A.
    public static void generatePowerUps()
    {
        // Generate x and y positions for power up 1
        int xPosPowerUp1 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp1 = (int)(Math.random()*(11)) +1;
        // While the x and y positions are not valid (if it is under an indestructable block, on the bed, or on the spawn tile), generate another set of x and y positions for power up 1
        while(map[yPosPowerUp1][xPosPowerUp1].equals("1") || (xPosPowerUp1 == xPosBed && yPosPowerUp1 == yPosBed) || (xPosPowerUp1 == 1 && yPosPowerUp1 == 1))
        {
            xPosPowerUp1 = (int)(Math.random()*(13)) +1;
            yPosPowerUp1 = (int)(Math.random()*(11)) +1;
        }
        // Create the power up 1 object
        powerUp1 = new PowerUp(powerUpSpeedImg, xPosPowerUp1*40, yPosPowerUp1*40);

        // Generate x and y positions for power up 2
        int xPosPowerUp2 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp2 = (int)(Math.random()*(11)) +1;
        // While the x and y positions are not valid (if it is under an indestructable block, on the bed, on the spawn tile, or on power up 1), generate another set of x and y positions for power up 2
        while(map[yPosPowerUp2][xPosPowerUp2].equals("1") || (xPosPowerUp1 == xPosPowerUp2 && yPosPowerUp1 == yPosPowerUp2) || (xPosPowerUp2 == xPosBed && yPosPowerUp2 == yPosBed) || (xPosPowerUp2 == 1 && yPosPowerUp2 == 1))
        {
            xPosPowerUp2 = (int)(Math.random()*(13)) +1;
            yPosPowerUp2 = (int)(Math.random()*(11)) +1;
        }
        // Create the power up 2 object
        powerUp2 = new PowerUp(powerUpSpeedImg, xPosPowerUp2*40, yPosPowerUp2*40);

        // Generate x and y positions for power up 3
        int xPosPowerUp3 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp3 = (int)(Math.random()*(11)) +1;
        // While the x and y positions are not valid (if it is under an indestructable block, on the bed, on the spawn tile, on power up 1, or on power up 2), generate another set of x and y positions for power up 3
        while(map[yPosPowerUp3][xPosPowerUp3].equals("1") || (xPosPowerUp1 == xPosPowerUp3 && yPosPowerUp1 == yPosPowerUp3) || (xPosPowerUp2 == xPosPowerUp3 && yPosPowerUp2 == yPosPowerUp3) || (xPosPowerUp3 == xPosBed && yPosPowerUp3 == yPosBed) || (xPosPowerUp3 == 1 && yPosPowerUp3 == 1))
        {
            xPosPowerUp3 = (int)(Math.random()*(13)) +1;
            yPosPowerUp3 = (int)(Math.random()*(11)) +1;
        }
        // Create the power up 3 object
        powerUp3 = new PowerUp(powerUpSlowImg, xPosPowerUp3*40, yPosPowerUp3*40);

        // Generate x and y positions for power up 4
        int xPosPowerUp4 = (int)(Math.random()*(13)) +1;
        int yPosPowerUp4 = (int)(Math.random()*(11)) +1;
        // While the x and y positions are not valid (if it is under an indestructable block, on the bed, on the spawn tile, on power up 1, on power up 2, or on power up 3), generate another set of x and y positions for power up 4
        while(map[yPosPowerUp4][xPosPowerUp4].equals("1") || (xPosPowerUp1 == xPosPowerUp4 && yPosPowerUp1 == yPosPowerUp4) || (xPosPowerUp2 == xPosPowerUp4 && yPosPowerUp2 == yPosPowerUp4) || (xPosPowerUp3 == xPosPowerUp4 && yPosPowerUp3 == yPosPowerUp4) || (xPosPowerUp4 == xPosBed && yPosPowerUp4 == yPosBed) || (xPosPowerUp4 == 1 && yPosPowerUp4 == 1))
        {
            xPosPowerUp4 = (int)(Math.random()*(13)) +1;
            yPosPowerUp4 = (int)(Math.random()*(11)) +1;
        }
        // Create the power up 4 object
        powerUp4 = new PowerUp(powerUpSlowImg, xPosPowerUp4*40, yPosPowerUp4*40);

        System.out.println("Power up 1 (speed): " + xPosPowerUp1 + ", " + yPosPowerUp1);
        System.out.println("Power up 2 (speed): " + xPosPowerUp2 + ", " + yPosPowerUp2);
        System.out.println("Power up 3 (slow): " + xPosPowerUp3 + ", " + yPosPowerUp3);
        System.out.println("Power up 4 (slow): " + xPosPowerUp4 + ", " + yPosPowerUp4);
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------

    // GENERATE ENEMIES
    // Description: This method generates 5 enemies based on the map number.
    // Parameters: The map number (int) that was randomly selected in the loadMaps() method
    // Return: N/A.
    public static void generateEnemies(int mapNumber) {
        // If the map number is 1, create 5 enemy objects and add them to the enemies arraylist
        if(mapNumber == 1) {
            enemies.add(new Enemy(160, 120, "left"));
            enemies.add(new Enemy(400, 40, "left"));
            enemies.add(new Enemy(280, 200, "left"));
            enemies.add(new Enemy(280, 280, "left"));
            enemies.add(new Enemy(400, 440, "left"));
        }
        // If the map number is 2, create 5 enemy objects and add them to the enemies arraylist
        if(mapNumber == 2) {
            enemies.add(new Enemy(320, 40, "left"));
            enemies.add(new Enemy(160, 120, "left"));
            enemies.add(new Enemy(280, 200, "left"));
            enemies.add(new Enemy(280, 280, "left"));
            enemies.add(new Enemy(400, 440, "left"));
        }
        // If the map number is 3, create 5 enemy objects and add them to the enemies arraylist
        if(mapNumber == 3) {
            enemies.add(new Enemy(440, 40, "left"));
            enemies.add(new Enemy(120, 120, "left"));
            enemies.add(new Enemy(280, 200, "left"));
            enemies.add(new Enemy(280, 320, "left"));
            enemies.add(new Enemy(400, 440, "left"));
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // ENTER USERNAME FOR HIGHSCORE
    // Description: This method allows the user to enter in a username so that their score can be saved and viewed on the highscore page
    // Parameters: N/A.
    // Return: N/A.
    public static void enterHighscoreName()
    {
        // If enterName is true
        if(enterName == true)
        {
            // Try catch incase there is an error when writing to the "highscore.txt" file
            try
            {
                // Create a new print writer
                PrintWriter outFile = new PrintWriter(new FileWriter("highscore.txt", true));

                // Show a JOptionPane that allows the user to enter their name for the highscore
                nameEntered = JOptionPane.showInputDialog("Enter your name for the highscore.");

                // If the user presses the X button
                if(nameEntered == null)
                {
                    // Close the print writer and return back - the highscore will not be saved
                    outFile.close();
                    return;
                }

                // If the user tries to enter an empty name
                if(nameEntered.length() <= 0)
                {
                    // Display that it is invalid and call the method again so that the user can enter their name again
                    JOptionPane.showMessageDialog(null, "No name given.", "Invalid Entry", JOptionPane.ERROR_MESSAGE);
                    enterHighscoreName();
                }

                // If the name entered is valid
                else
                {
                    // Display that the name entered was sucessfully added
                    JOptionPane.showMessageDialog(null, "Highscore added.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

                // Write the name entered into the outfile along with the user's score
                outFile.println(nameEntered + "," + score);

                // Close the print writer
                outFile.close();
            }
            catch(IOException e)
            {
                System.out.println("Input / Output Error.");
            }
        }

        // Set enterName to false
        enterName = false;
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // READ HIGHSCORE
    // Description: This method reads the highscores from "highscore.txt" and creates a score object. The score object is added to an arraylist for score objects. And the hashmap if conditions are met (explained below)
    // Parameters: N/A.
    // Return: N/A.
    public static void readHighscore()
    {
        // Try catch incase there is an error when reading from the "highscore.txt" file
        try
        {
            // Buffered reader to read the highscores
            BufferedReader inFile = new BufferedReader(new FileReader("highscore.txt"));
            String line = "";
            while((line = inFile.readLine()) != null)
            {
                // creates a score object for each score in the textfile
                Score score = new Score(line.substring(0, line.indexOf(",")), Integer.parseInt(line.substring(line.indexOf(",") + 1)));
                // adds score to the arraylist
                allScores.add(score);
                // if the hashmap does not contain this player or they beat their score, the new score is added to the hashmap
                if(highscoreMap.get(line.substring(0, line.indexOf(","))) == null || highscoreMap.get(line.substring(0, line.indexOf(","))) < Integer.parseInt(line.substring(line.indexOf(",") + 1))) {
                    highscoreMap.put(line.substring(0, line.indexOf(",")), Integer.parseInt(line.substring(line.indexOf(",") + 1)));
                }
            }

            // Close the buffered reader
            inFile.close();
        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error.");
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // RESET GAME
    // Description: This method resets all the variables, clears all the arraylists, loads a new map, and generates new powerups when the user presses play
    //              again after finishing a game.
    // Parameters: N/A.
    // Return: N/A.
    public void reset() {
        // Reset all varables and clear all arraylists
        xPosPlayer = 40;
        yPosPlayer = 40;
        direction = "down";
        enemies.clear();
        xBlocks.clear();
        yBlocks.clear();
        bombArray.clear();
        allScores.clear();
        timer = 0;
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

        // Try catch incase there is an input / output error when loading in a new map
        try
        {
            // Call the loadMaps() method to read in a new map
            loadMaps();
        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error.");
        }

        // Call the generatePowerUps() method to generate new power ups
        generatePowerUps();
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // THREADING
    // Description: This method calls the methods that must be constantly called. This method also keeps track of the in game time.
    // Parameters: N/A.
    // Return: N/A.
    public void run() {
        // While true
        while(true) {
            // Call the move(), checkCollsion(), and repaint() methods
            move();
            checkCollision();
            repaint();
            // Set the frame rate to 60 frames per second
            try {
                Thread.sleep(1000/60);
            } catch(Exception e) {}

            // If the game state is 2 (in game)
            if(gameState == 2)
            {
                // If the time start is true
                if(timeStart == true)
                {
                    // Get the current time and save it to a variable
                    timeElapsedMsStart = System.currentTimeMillis();
                    // Set the time start to false
                    timeStart = false;
                }
                // Constantly get the current time and save it to a variable
                timeElapsedMsEnd = System.currentTimeMillis();
                // If 1000 ms or more has passed
                if(timeElapsedMsEnd - timeElapsedMsStart >= 1000)
                {
                    // Set time start to true to repeat the process
                    timeStart = true;
                    // Increase the time in seconds by 1
                    timeElapsedSec++;
                    // Decrease the count down timer by 1
                    countDown--;
                }
                // If the time in seconds reaches 60 seconds
                if(timeElapsedSec >= 60)
                {
                    // Increase the time in minutes by 1
                    timeElapsedMin++;
                    // Reset the time in seconds to 0
                    timeElapsedSec = 0;
                }
                // If the count down timer is 0
                if(countDown <= 0)
                {
                    // If there are still count down points to be earned
                    if(countDownPoints > 0)
                    {
                        // Reduce the count down points that can be earned by 100
                        countDownPoints -= 100;
                    }
                    // Reset the count down timer to 30 seconds
                    countDown = 30;
                }
            }
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // PLAY MUSIC
    // Description: This method plays music / sound effects.
    // Parameters: The name of the song
    // Return: N/A.
    public static void playMusic(String song) throws IOException
    {
        // Create a new File object (songs are stored in a music folder and all of the songs are .wav's)
        File file = new File("Music/" + song + ".wav");
        // Try catch in case there is an error when playing the song
        try
        {
            // Play the song
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(file));
            clip.start();
            // Turn down the volume of the song
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(-5f);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // PLAY BACKGROUND MUSIC
    // Description: This method plays background music.
    // Parameters: The name of the song
    // Return: N/A.
    public static void playBackground(String song) throws IOException
    {
        // Create a new File object (songs are stored in a music folder and all of the songs are .wav's)
        File titleSong = new File("Music/" + song + ".wav");
        // Try catch in case there is an error when playing the song
        try
        {
            // Play the song and loop the song infinitly
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(titleSong));
            clip.loop(-1);
            clip.start();
            // Turn down the volume of the song
            FloatControl fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            fc.setValue(-20f);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // MAIN METHOD
    // Description: This is the main method of the program. It helps intialize the graphics, loads music, and loads images.
    // Parameters: String[] args
    // Return: N/A.
    public static void main(String[] args) throws IOException {
        // Initialize graphics
        frame = new JFrame("Assignment Assassin");
        Main myPanel = new Main();
        frame.add(myPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Loads music by calling the playBackground() method and passing in the song to be played
        playBackground("title");

        // Try catch in case there is an error when reading in the images from the "Images" folder
        try
        {
            // Load the images for the menu pages
            backgroundImg = ImageIO.read(new File("Images/Background.png"));
            highscoreImg = ImageIO.read(new File("Images/highscore.png"));
            rulesImg = ImageIO.read(new File("Images/rules.png"));
            aboutImg = ImageIO.read(new File("Images/about.png"));
            backImg = ImageIO.read(new File("Images/back.png"));
            gameOverImg = ImageIO.read(new File("Images/gameOver.png"));
            winImg = ImageIO.read(new File("Images/win.png"));

            // Load the images for the unbreakable walls and the breakable walls
            wallImg = ImageIO.read(new File("Images/wall.png"));
            unbreakableWallImg = ImageIO.read(new File("Images/unbreakable.png"));

            // Load the player sprites into a BufferedImage Array
            characterSprites = new BufferedImage[8];
            characterSprites[0] = ImageIO.read(new File("Images/boy_down_1.png"));
            characterSprites[1] = ImageIO.read(new File("Images/boy_down_2.png"));
            characterSprites[2] = ImageIO.read(new File("Images/boy_left_1.png"));
            characterSprites[3] = ImageIO.read(new File("Images/boy_left_2.png"));
            characterSprites[4] = ImageIO.read(new File("Images/boy_right_1.png"));
            characterSprites[5] = ImageIO.read(new File("Images/boy_right_2.png"));
            characterSprites[6] = ImageIO.read(new File("Images/boy_up_1.png"));
            characterSprites[7] = ImageIO.read(new File("Images/boy_up_2.png"));

            // Load the image of the bomb
            bombImg = ImageIO.read(new File("Images/bomb.png"));

            // Load the images of the power ups
            powerUpSpeedImg = ImageIO.read(new File("Images/powerUpSpeed.png"));
            powerUpSlowImg = ImageIO.read(new File("Images/powerUpSlow.png"));

            // Load the image of the bed
            bedImg = ImageIO.read(new File("Images/bed.png"));

            // Load the image of the enemy
            enemyImg = ImageIO.read(new File("Images/laptop.png"));

        }
        catch(IOException e)
        {
            System.out.println("Input / Output Error");
        }
    }


// --------------------------------------------------------------------------------------------------------------------------------------------------------


    // USELESS - UNUSED METHODS
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
}