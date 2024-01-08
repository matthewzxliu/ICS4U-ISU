import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class Main extends JPanel implements MouseListener
{
    JFrame frame;
    JPanel panel;

    static int xPos;
    static int yPos;
    static int gameState = 0;

    public Main() {
        frame = new JFrame("BomberMan");
        frame.setPreferredSize(new Dimension(1080, 600));

        panel = new JPanel();
        frame.add(panel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
		frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(255, 0, 0));
        g.drawRect(50, 50, 100, 50);
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

    public static void main(String[] args) {
        new Main();
    }
}