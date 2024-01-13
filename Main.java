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
	static int vel = 3;
	static String direction = "down";

	// GAME STATE
	static int gameState = 0;

	// MAP
	static String[][] map = new String[13][15];

	// IMAGES
	static BufferedImage wallImg, unbreakableWallImg;
	static BufferedImage backgroundImg, highscoreImg, rulesImg, aboutImg, backImg;
	static BufferedImage[] characterSprites;
	static BufferedImage playerImg;
	static int spriteNum = 1;
	static int spriteCounter = 0;

	// POWER UPS
	static PowerUps speed = new PowerUps(5, 0);
	static PowerUps slow = new PowerUps(5, 0);

	// BOMBS
	static ArrayList<Bomb> bombArray = new ArrayList<Bomb>();


// --------------------------------------------------------------------------------------------------------------------------------------------------------


	// INITIALIZE
	public Main() throws IOException
	{
		// Set the size and colour of the game window
		setPreferredSize(new Dimension(600, 520));
		setBackground(new Color(252, 177, 3));

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
		// Play
		g.setColor(new Color(160, 50, 168));
		g.fillRect(214, 290, 177, 60);
		
		// Highscore
		g.setColor(new Color(160, 50, 168));
		g.fillRect(214, 369, 179, 48);
		
		// Rules
		g.setColor(new Color(160, 50, 168));
		g.fillRect(214, 438, 117, 40);
		
		// Exit
		g.setColor(new Color(160, 50, 168));
		g.fillRect(339, 438, 55, 40);
		*/

		// Draw background image
		g.drawImage(backgroundImg, 0, 0, null);

		// Game state 1, main game
		if(gameState == 1)
		{
			// CLEAR SCREEN
			super.paintComponent(g);

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
					else if(map[j/40][i/40].equals("-"))
					{
						g.drawImage(wallImg, i, j, null);
					}
					else if(map[j/40][i/40].equals("1"))
					{
						g.drawImage(unbreakableWallImg, i, j, null);
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
		// Game state 2, high score page
		else if(gameState == 2)
        {
            super.paintComponent(g);
			g.drawImage(highscoreImg, 0, 0, null);
			g.drawImage(backImg, 15, 15, null);
        }
		// Game state 3, rules page
        else if(gameState == 3)
        {
            super.paintComponent(g);
			g.drawImage(backImg, 15, 15, null);
        }
		// Game state 4, about page
        else if(gameState == 4)
        {
            super.paintComponent(g);
			g.drawImage(aboutImg, 0, 0, null);
			g.drawImage(backImg, 15, 15, null);
        }
		// Game state 5, exit
		else if(gameState == 5)
        {
            super.paintComponent(g);
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
	}


// --------------------------------------------------------------------------------------------------------------------------------------------------------


	// MOUSE INPUT
    public void mousePressed(MouseEvent e) {
		xPos = e.getX();
		yPos = e.getY();
		
		if(gameState == 0)
		{
			if(xPos >= 214 && xPos <= 391 && yPos >= 290 && yPos <= 350)
			{
				gameState = 1;
			}

			else if(xPos >= 214 && xPos <= 393 && yPos >= 369 && yPos <= 417)
			{
				gameState = 2;
			}

			else if(xPos >= 214 && xPos <= 331 && yPos >= 438 && yPos <= 478)
			{
                gameState = 3;
			}

			else if(xPos >= 339 && xPos <= 394 && yPos >= 438 && yPos <= 478)
			{
				gameState = 4;
			}
			else if(xPos >= 339 && xPos <= 394 && yPos >= 438 && yPos <= 478)
			{
				gameState = 5;
			}
		}
		else if(gameState == 1)
		{
			repaint();
		}
		else if(gameState == 2)
		{
			repaint();
			if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
			{
				gameState = 0;
			}
		}
		else if(gameState == 3)
		{
			repaint();
			if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
			{
				gameState = 0;
			}
		}
		else if(gameState == 4)
		{
			repaint();
			if(xPos >= 15 && xPos <= 115 && yPos >= 15 && yPos <= 61)
			{
				gameState = 0;
			}
		}
		repaint();
	}


// --------------------------------------------------------------------------------------------------------------------------------------------------------


	// KEYBOARD INPUT
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();

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
			Bomb bomb = new Bomb(xPosPlayer, yPosPlayer);
			bombArray.add(bomb);
		}

		else if(key == KeyEvent.VK_CONTROL)
		{
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
		else if(key == KeyEvent.VK_M)
		{
			vel += speed.getSpeedPowerUp();
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
		if(left || right || up || down)
		{
			if(left && xPosPlayer > 40)
			{
				xPosPlayer -= vel;
				direction = "left";
			}
			else if(right && xPosPlayer < 520)
			{
				xPosPlayer += vel;
				direction = "right";
			}
			if(up && yPosPlayer > 40)
			{
				yPosPlayer -= vel;
				direction = "up";
			}
			else if(down && yPosPlayer < 440)
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


// --------------------------------------------------------------------------------------------------------------------------------------------------------


	// THREADING
	public void run() {
        while(true) {
			move();
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
					line = line.substring(1);
				}
			}
			br.close();
		}
		catch(IOException e)
		{
			System.out.println("Input / Output Error");
		}

		// LOADING IMAGES
		try
		{
			backgroundImg = ImageIO.read(new File("Images/Background.png"));
			highscoreImg = ImageIO.read(new File("Images/highscore.png"));
			// rulesImg = ImageIO.read(new File("Images/rulesImg.png"));
			aboutImg = ImageIO.read(new File("Images/about.png"));
			backImg = ImageIO.read(new File("Images/back.png"));

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
		}
		catch(IOException e)
		{
			System.out.println("Input / Output Error");
		}
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