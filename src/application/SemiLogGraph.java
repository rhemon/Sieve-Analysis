package application;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.BorderFactory;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SemiLogGraph extends JPanel{
	

	public SemiLogGraph() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
//
    public Dimension getPreferredSize() {
        return new Dimension(700, 250);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);       
        Graphics2D g = (Graphics2D) graphics;
        // Draw Text
        g.setStroke(new BasicStroke(3));
        g.draw(new Line2D.Double(20, 20, 20, 230));
        g.draw(new Line2D.Double(20, 230, 680, 230));
        g.setStroke(new BasicStroke(0));
        // Loop to draw the scaled graph
        final int XSTARTPOINT = 20;
        final int XENDPOINT = 680;
        final int YSTARTPOINT = 20;
        final int YENDPOINT = 230;
        final int GRAPHHEIGHT = YENDPOINT - YSTARTPOINT;
        final int GRAPHWIDTH = XENDPOINT - XSTARTPOINT;
        
        // Logarithmic scaled X Lines  
        int at = 1;
        int scale = 1;
        int startingPoint = 20;
        int WIDTH = (GRAPHWIDTH/4);
        int XPOINT = 20;
        for (int i=0; i<4; i++) {
        	int STARTPOINT = startingPoint;
            for (int j=0; j<9; j++) {
            	XPOINT = STARTPOINT + (int) (Math.log10(at)*WIDTH); 
            	graphics.drawLine(XPOINT, YSTARTPOINT, XPOINT, YENDPOINT);
            	at += scale; 
            }
            XPOINT = STARTPOINT + (int) (Math.log10(at)*WIDTH); 
            g.setStroke(new BasicStroke(3));
            g.draw(new Line2D.Double(XPOINT, YSTARTPOINT, XPOINT, YENDPOINT));
            g.setStroke(new BasicStroke(0));
        	startingPoint = XPOINT;
        	at = 1;
        }
        
        // Linear scaled Y Lines
        startingPoint = 230;
        double height = Math.round(GRAPHHEIGHT/10);
        double YPOINT = startingPoint;
    	g.setStroke(new BasicStroke(2));
        for (int i = 0; i<10; i++) {
        	YPOINT -= height;
        	g.draw(new Line2D.Double(20, YPOINT, 680, YPOINT));
        	at += scale;
        	System.out.println(YPOINT);
        }
        g.setStroke(new BasicStroke(0));
       
    }  
}
