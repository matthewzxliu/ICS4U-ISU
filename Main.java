import java.util.*;
import java.io.*;
import java.nio.Buffer;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import javax.swing.*;
import javax.imageio.ImageIO;

public class Main extends JPanel implements MouseListener, KeyListener, Runnable
{

    static JFrame frame;

	static int xPos, yPos;
	static int xPosPlayer = 40, yPosPlayer = 40;
	// static int xPosEnemy = 535, yPosEnemy = 455;
	static boolean up, left, down, right;
	static int vel = 5;

	static int gameState = 0;

	static String[][] map = new String[13][15];

    public static BufferedImage player;

	static BufferedImage wallImg;
	static BufferedImage[] characterSprites;

	public Main() throws IOException
	{
		setPreferredSize(new Dimension(600, 520));
		setBackground(new Color(252, 177, 3));

        setFocusable(true);
        addKeyListener(this);
		addMouseListener(this);

        Thread t = new Thread((Runnable) this);
        t.start();
	}

    public void paintComponent(Graphics g)
	{
		super.paintComponent(g);

        /*
		// Play
		g.setColor(new Color(160, 50, 168));
		g.fillRect(410, 310, 260, 80);
		
		// About
		g.setColor(new Color(160, 50, 168));
		g.fillRect(410, 400, 260, 80);
		
		// Exit
		g.setColor(new Color(160, 50, 168));
		g.fillRect(410, 490, 160, 80);
		
		// Settings
		g.setColor(new Color(160, 50, 168));
		g.fillRect(580, 490, 90, 80);
		*/

        // Play
		g.setColor(new Color(160, 50, 168));
		g.fillRect(205, 270, 170, 50);
		
		// About
		g.setColor(new Color(160, 50, 168));
		g.fillRect(205, 330, 170, 50);
		
		// Exit
		g.setColor(new Color(160, 50, 168));
		g.fillRect(205, 390, 110, 50);
		
		// Settings
		g.setColor(new Color(160, 50, 168));
		g.fillRect(325, 390, 50, 50);

		if(gameState == 1)
		{
			super.paintComponent(g);
            g.setColor(new Color(0, 0, 0));
			for(int i = 0; i < 600; i += 40)
			{
				for(int j = 0; j < 520; j += 40)
				{
					if(map[j/40][i/40].equals("x"))
					{
						g.setColor(new Color(0, 0, 0));
						g.fillRect(i, j, 40, 40);
					}
					else if(map[j/40][i/40].equals("-"))
					{
						// g.setColor(new Color(0, 255, 0));
						// g.fillRect(i, j, 40, 40);
						g.drawImage(wallImg, i, j, null);
					}
				}
			}

			g.setColor(new Color(255, 0, 0));
			g.fillOval(xPosPlayer, yPosPlayer, 25, 25);

			// if(down == true)
			// {
			// 	g.drawImage(characterSprites[0], xPosPlayer, yPosPlayer, null);
			// 	g.drawImage(characterSprites[1], xPosPlayer, yPosPlayer, null);
			// }
			// else if(left == true)
			// {
			// }
			// else if(right == true)
			// {
			// }
			// else if(up == true)
			// {
			// }

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
        else if(gameState == 2)
        {
            super.paintComponent(g);
        }
        else if(gameState == 3)
        {
            super.paintComponent(g);
            frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
        }
        else if(gameState == 4)
        {
            super.paintComponent(g);
        }
	}

    public void mousePressed(MouseEvent e) {
		xPos = e.getX();
		yPos = e.getY();
		
		if(gameState == 0)
		{
			if(xPos >= 205 && xPos <= 375 && yPos >= 270 && yPos <= 320)
			{
				gameState = 1;
				repaint();
			}
			
			else if(xPos >= 205 && xPos <= 375 && yPos >= 330 && yPos <= 380)
			{
				gameState = 2;
				repaint();
			}
			
			else if(xPos >= 205 && xPos <= 315 && yPos >= 390 && yPos <= 440)
			{
                gameState = 3;
                repaint();
			}
			
			else if(xPos >= 325 && xPos <= 375 && yPos >= 390 && yPos <= 440)
			{
				gameState = 4;
				repaint();
			}
		}

		else if(gameState == 1)
		{
			repaint();
		}
		repaint();
	}

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
		else if(key == KeyEvent.VK_CONTROL)
		{
			frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		}
	}

	public void keyReleased(KeyEvent e)
	{
		int key = e.getKeyCode();

		if(key == KeyEvent.VK_A)
		{
			left = false;
		}
		else if(key == KeyEvent.VK_D)
		{
			right = false;
		}
		else if(key == KeyEvent.VK_W)
		{
			up = false;
		}
		else if(key == KeyEvent.VK_S)
		{
			down = false;
		}
	}

	public void move()
	{
		if(left && xPosPlayer > 40)
			xPosPlayer -= vel;
		else if(right && xPosPlayer < 535)
			xPosPlayer += vel;
		if(up && yPosPlayer > 40)
			yPosPlayer -= vel;
		else if(down && yPosPlayer < 455)
			yPosPlayer += vel;
	}

	public void run() {
        while(true) {
			move();
            repaint();
            try {
                Thread.sleep(17);
            } catch(Exception e) {}
        }
    }

    public static void main(String[] args) throws IOException {
        frame = new JFrame("Bomberman");
        Main myPanel = new Main();
        frame.add(myPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
        frame.pack();
		frame.setVisible(true);
     
		try
		{
			// Read Maps
			BufferedReader br = new BufferedReader(new FileReader("map.txt"));

			int mapNumber = (int)(Math.random()*(4-1+1)) + 1;

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

		try
		{
			wallImg = ImageIO.read(new File("Images/wall.png"));

			characterSprites = new BufferedImage[10];
			characterSprites[0] = ImageIO.read(new File("boy_down_1.png"));
			characterSprites[1] = ImageIO.read(new File("boy_down_2.png"));
			characterSprites[2] = ImageIO.read(new File("boy_left_1.png"));
			characterSprites[3] = ImageIO.read(new File("boy_left_2.png"));
			characterSprites[4] = ImageIO.read(new File("boy_right_1.png"));
			characterSprites[5] = ImageIO.read(new File("boy_right_2.png"));
			characterSprites[6] = ImageIO.read(new File("boy_up_1.png"));
			characterSprites[7] = ImageIO.read(new File("boy_up_2.png"));
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

        // player = ImageIO.read(new File("wall.png"));
    }

















    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void keyTyped(KeyEvent e) {}
}