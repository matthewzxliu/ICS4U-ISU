import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

public class Main extends JPanel
{
    JFrame frame;
    JPanel panel;

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

    public static void main(String[] args) {
        new Main();
    }
}