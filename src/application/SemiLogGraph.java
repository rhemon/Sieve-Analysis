package application;

import java.awt.*;
import java.awt.geom.Line2D;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class SemiLogGraph extends JPanel{
	
	private double[] xCoordinates, yCoordinates;
	private double D60, D10;
	JLabel d60, d10, cu;
	
	public SemiLogGraph() {
        setBorder(BorderFactory.createLineBorder(Color.black));
    }
	
    public Dimension getPreferredSize() {
        return new Dimension(700, 250);
    }

    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);       
        
        Graphics2D g = (Graphics2D) graphics;
        g.setFont(new Font("TimesRoman", Font.BOLD, 10));
        
        // Draws the Axis and 
        g.setStroke(new BasicStroke(3));
        g.draw(new Line2D.Double(20, 20, 20, 230));
        g.draw(new Line2D.Double(20, 230, 680, 230));
        g.drawString("10", 20-5, 245);
        g.drawString("0", 4, 230+5);
        g.setStroke(new BasicStroke(0));
        
        // constant variables about graph
        final int XSTARTPOINT = 20;
        final int XENDPOINT = 680;
        final int YSTARTPOINT = 20;
        final int YENDPOINT = 230;
        final int GRAPHHEIGHT = YENDPOINT - YSTARTPOINT;
        final int GRAPHWIDTH = XENDPOINT - XSTARTPOINT;
        
        // Logarithmic scaled X Lines  
        int at = 1;
        int scale = 1;
        int STARTPOINTX = 20;
        int WIDTH = (GRAPHWIDTH/4);
        int XPOINT = 20;
        double value = 10;
        for (int i=0; i<4; i++) {
        	g.setColor(Color.GRAY);
            for (int j=0; j<9; j++) {
            	XPOINT = STARTPOINTX + (WIDTH - (int) (Math.log10(at)*WIDTH)); 
            	graphics.drawLine(XPOINT, YSTARTPOINT, XPOINT, YENDPOINT);
            	at += scale; 
            }
            XPOINT = STARTPOINTX + (WIDTH - (int) (Math.log10(at)*WIDTH)); 
            g.setStroke(new BasicStroke(3));
            g.setColor(Color.BLACK);
            if (i > 0){
	            value /= 10;
	            g.drawString(Double.toString(value), (int) XPOINT-5, 245);
            }
            g.draw(new Line2D.Double(XPOINT, YSTARTPOINT, XPOINT, YENDPOINT));
            g.setStroke(new BasicStroke(0));
            STARTPOINTX += (int) Math.log10(10)*WIDTH;
        	at = 1;
        }
        
        // Linear scaled Y Lines
        int STARTPOINTY = 230;
        double height = Math.round(GRAPHHEIGHT/10);
        double YPOINT = STARTPOINTY;
        int valueY = 0;
    	g.setStroke(new BasicStroke(2));
    	g.setColor(Color.GRAY);
        for (int i = 0; i<10; i++) {
        	YPOINT -= height;
        	valueY += 10;
        	g.draw(new Line2D.Double(20, YPOINT, 680, YPOINT));
        	g.drawString(Integer.toString(valueY), 4, (int) YPOINT+5); 
        	at += scale;
        }
        g.setStroke(new BasicStroke(0));
        
        
        // Plotting Values
        g.setStroke(new BasicStroke(2));
		
        if (xCoordinates != null && yCoordinates != null) {
        	STARTPOINTX = 20;
        	STARTPOINTY = 230;
        	double prevXPoint = xCoordinates[0];
        	prevXPoint = STARTPOINTX + WIDTH - (Math.log10(prevXPoint) * WIDTH);
        	double prevYPoint = yCoordinates[0];
        	prevYPoint = STARTPOINTY - (height * prevYPoint/10);
        	double presXPoint;
        	double presYPoint;
        	for (int i=1; i<xCoordinates.length; i++) {
        		presXPoint = xCoordinates[i];
        		presYPoint = yCoordinates[i];
        		if (presXPoint >= 1) {
            		STARTPOINTX = 20;
            	} else if (presXPoint >= 0.1) {
            		STARTPOINTX = 20 + (int) (Math.log10(10)*WIDTH);
            		presXPoint *= 10;
            	} else if (presXPoint >= 0.01) {
            		STARTPOINTX = 20 + (int) (Math.log10(100)*WIDTH);
            		presXPoint *= 100;
            	}
        		presXPoint = STARTPOINTX + (WIDTH - (Math.log10(presXPoint) * WIDTH));
        		presYPoint = STARTPOINTY - (height * presYPoint/10);
        		
        		Line2D segment = new Line2D.Double(prevXPoint, prevYPoint, presXPoint, presYPoint);
        		
        		// Finding D60 and D10 
        		double FROM  =20;
        		if (yCoordinates[i-1] >= 60 && yCoordinates[i] <=60) {
        			double gradientOfSegment = (presYPoint-prevYPoint)/(presXPoint-prevXPoint);
    				// y = gradientOfSegment(x - prevXPoint)+ prevYPoint
    				// rearranged to find x when y is 60
    				double x = ((STARTPOINTY-(height*6) - prevYPoint)/gradientOfSegment)+prevXPoint;
    				double divisionBy = 1;
    				if (x > 20+(Math.log10(10)*WIDTH) && x < 20+(Math.log10(100)*WIDTH)){
    					FROM = 20 + (int) (Math.log10(10)*WIDTH);
    					divisionBy = 10;
    				} else if (x > 20+(Math.log10(100)*WIDTH) && x < 20+(Math.log10(1000)*WIDTH)) {
    					FROM = 20 + (int) (Math.log10(100)*WIDTH);
    					divisionBy = 100;
    				}
    				D60 = Math.pow(10,(((FROM + WIDTH - x)/WIDTH)))/divisionBy;
    				System.out.println("D60 = " + String.format("%.2f", D60));
    				
        		} else if (yCoordinates[i-1] >= 10 && yCoordinates[i] <=10) {
        			double gradientOfSegment = (presYPoint-prevYPoint)/(presXPoint-prevXPoint);
    				// y = gradientOfSegment(x - prevXPoint)+ prevYPoint
    				// rearranged to find x when y is 10
    				double x = ((STARTPOINTY-(height*1) - prevYPoint)/gradientOfSegment)+prevXPoint;
    				STARTPOINTX = 20;
    				double divisionBy = 1;
    				if (x > 20+(Math.log10(10)*WIDTH) && x < 20+(Math.log10(100)*WIDTH)){
    					STARTPOINTX = 20 + (int) (Math.log10(10)*WIDTH);
    					divisionBy = 10;
    				} else if (x > 20+(Math.log10(100)*WIDTH) && x < 20+(Math.log10(1000)*WIDTH)) {
    					STARTPOINTX = 20 + (int) (Math.log10(100)*WIDTH);
    					divisionBy = 100;
    				}
    				
    				D10 = Math.pow(10, (((STARTPOINTX + WIDTH - x)/WIDTH)))/divisionBy;
    				System.out.println("D10 = " + String.format("%.2f", D10));
    				
    				d60.setText("D60 : " + String.format("%.2f", D60));
    				d10.setText("D10 : "+ String.format("%.2f", D10));
    				cu.setText("Uniform Coefficient (D60/D10) = " + String.format("%.2f", SieveAnalysisCalc.CalcUniformityCoefficient(Double.parseDouble(String.format("%.2f", D60)),Double.parseDouble(String.format("%.2f", D10)))));
    		        
        		}
        		
        		g.setColor(Color.RED);
        		g.draw(segment);
        		prevXPoint = presXPoint;
        		prevYPoint = presYPoint;
        	}
        }
        		
    }  
    
    // this is called on submit 
    public void repaint(double[] xCords, double[] yCords, JLabel d60Label, JLabel d10Label, JLabel cuLabel) {
    	xCoordinates = xCords;
        yCoordinates = yCords;
        // sets the coordinate variable and paints the panel again
        super.repaint();
        d60 = d60Label;
        d10 = d10Label;
        cu = cuLabel;
		
    }
    
    
}
