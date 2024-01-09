import java.util.*;
import java.awt.*;
import java.awt.image.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Main extends JPanel implements MouseListener, KeyListener, Runnable
{

    static JFrame frame;
	static int xPos;
	static int yPos;
	static int gameState = 0;
    public static BufferedImage player;

	public Main() throws IOException
	{
		setPreferredSize(new Dimension(1080, 600));
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
		
		if(gameState == 1)
		{
			super.paintComponent(g);
            g.setColor(new Color(0, 0, 0));
            for(int i = 0; i < 1080; i += 72)
            {
                g.drawRect(i, 0, 72, 72);
                g.drawRect(i, 528, 72, 72);
            }
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
			if(xPos >= 410 && xPos <= 670 && yPos >= 310 && yPos <= 390)
			{
				gameState = 1;
				repaint();
			}
			
			else if(xPos >= 410 && xPos <= 670 && yPos >= 400 && yPos <= 480)
			{
				gameState = 2;
				repaint();
			}
			
			else if(xPos >= 410 && xPos <= 570 && yPos >= 490 && yPos <= 570)
			{
                gameState = 3;
                repaint();
			}
			
			else if(xPos >= 580 && xPos <= 670 && yPos >= 490 && yPos <= 570)
			{
				gameState = 4;
				repaint();
			}
		}

		else if(gameState == 1)
		{
			repaint();
		}
		repaint(); // This calls the paintComponent method
	}

    public static void main(String[] args) throws IOException{
        frame = new JFrame("Bomberman");
        Main myPanel = new Main();
        frame.add(myPanel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setVisible(true);
        
        player = ImageIO.read(new File("boy_down_1.png"));
    }

    public void run() {
        while(true) {
            repaint();
            try {
                Thread.sleep(17);
            } catch(Exception e) {}
        }
    }

















    public void mouseClicked(MouseEvent e) {		
    }
    public void mouseReleased(MouseEvent e) {		
    }
    public void mouseEntered(MouseEvent e) {		
    }
    public void mouseExited(MouseEvent e) {		
    }
    public void keyTyped(KeyEvent e) {
    }
    public void keyPressed(KeyEvent e) {
    }
    public void keyReleased(KeyEvent e) {
    }
}