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

public class Main extends JPanel implements MouseListener, KeyListener
{

    static int xPos;
    static int yPos;
    static int gameState = 0;
    public static BufferedImage player;


    public Main() throws IOException{
        setPreferredSize(new Dimension(450,720));
        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);
        Thread t = new Thread((Runnable) this);
        t.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(255, 0, 0));
        g.drawRect(50, 50, 100, 50);
        g.drawImage(player, 10, 10, null);
     }

    public void mousePressed(MouseEvent e) {
        xPos = e.getX();
		yPos = e.getY();

        if(gameState == 0)
		{
			if(xPos >= 150 && xPos <= 350 && yPos >= 375 && yPos <= 425)
			{
				gameState = 1;
				repaint();
			}
		}
		else if(gameState == 1)
		{
			repaint();
		}
		repaint(); // This calls the paintComponent method
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

    public static void main(String[] args) throws IOException{
        JFrame frame = new JFrame("BomberMan");
        frame.setPreferredSize(new Dimension(1080, 600));

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
}