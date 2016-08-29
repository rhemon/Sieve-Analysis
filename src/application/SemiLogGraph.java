package application;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


public class SemiLogGraph extends JPanel{
	
	public SemiLogGraph() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
//
    public Dimension getPreferredSize() {
        return new Dimension(700, 250);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);       

        // Draw Text
        g.drawLine(20, 20, 20, 230);
        g.drawLine(20, 230, 680, 230);
        
        // Loop to draw the scaled graph
        
        
    }  
}
