package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;


/*
 * This is the main class where the application for sieve analysis initiates
 * and takes launch  
 */
@SuppressWarnings("serial")
public class SieveAnalysisWindow extends JFrame{

	/*
	 * headerPanel - Will have title and text field for who its from, and sample no. 
	 * dataPanel - the table for sieve analysis
	 * fmPanel - the panel that will show the fineness modulus
	 * graphPanel - the panel that will show the semi-log graph
	 * cuPanel - the panel that will show the uniform coefficient
	 */
	JPanel headerPanel, dataPanel, fmPanel, graphPanel, cuPanel, graphBox;
	JLabel titleLabel, testMethodLabel, totalWtTextLabel, totalWtLabel, fmLabel, d60Label, d10Label, cuLabel;
	Graphics graphArea;
	SemiLogGraph SLG; 
	// dataPanel is set as an [][]
	ArrayList<ArrayList<Object>> sieveData; //needs to be set properly with JLabel, JTextField
	// xCoordinates and yCoordinates 
	double[] xCords = {
		4.750,
		2.360,
		1.180,
		0.600,
		0.300,
		0.150,
		0.075
	};
	double[] yCords = new double[7];
	
	// button which when pressed will indicate all data required is entered
	JButton submitButton;
	
	// TODO will add a export button which will export all data (given and calculated) into a csv file, for later use 
	// JButton exportButton; 
	// TODO will add a import button which will collect data from a csv file
	// JButton importButton
	// The above could also be set as menu options 
	
	// private function for setting up sieve data
	private void setUpData() {
		sieveData = new ArrayList<ArrayList<Object>>();
		
		ArrayList<Object> header = new ArrayList<Object>();
		header.add(new JLabel("Sieve No."));
		header.add(new JLabel("Sieve Opening(mm)"));
		header.add(new JLabel("Weight Retained"));
		header.add(new JLabel("Percent Retained"));
		header.add(new JLabel("Cumulative Percent Retained"));
		header.add(new JLabel("Percent Finer"));
		sieveData.add(header);
		
		ArrayList<Object> seveNo4 = new ArrayList<Object>();
		seveNo4.add(new JLabel("4"));
		seveNo4.add(new JLabel("4.750"));
		seveNo4.add(new JTextField(10));
		seveNo4.add(new JLabel(""));
		seveNo4.add(new JLabel(""));
		seveNo4.add(new JLabel(""));
		sieveData.add(seveNo4);
		
		ArrayList<Object> sieveNo8 = new ArrayList<Object>();
		sieveNo8.add(new JLabel("8"));
		sieveNo8.add(new JLabel("2.36"));
		sieveNo8.add(new JTextField(10));
		sieveNo8.add(new JLabel(""));
		sieveNo8.add(new JLabel(""));
		sieveNo8.add(new JLabel(""));
		sieveData.add(sieveNo8);
		
		ArrayList<Object> seiveNo16 = new ArrayList<Object>();
		seiveNo16.add(new JLabel("16"));
		seiveNo16.add(new JLabel("1.18"));
		seiveNo16.add(new JTextField(10));
		seiveNo16.add(new JLabel(""));
		seiveNo16.add(new JLabel(""));
		seiveNo16.add(new JLabel(""));
		sieveData.add(seiveNo16);
		
		ArrayList<Object> seiveNo30 = new ArrayList<Object>();
		seiveNo30.add(new JLabel("30"));
		seiveNo30.add(new JLabel("0.6"));
		seiveNo30.add(new JTextField(10));
		seiveNo30.add(new JLabel(""));
		seiveNo30.add(new JLabel(""));
		seiveNo30.add(new JLabel(""));
		sieveData.add(seiveNo30);
		
		ArrayList<Object> seiveNo50 = new ArrayList<Object>();
		seiveNo50.add(new JLabel("50"));
		seiveNo50.add(new JLabel("0.3"));
		seiveNo50.add(new JTextField(10));
		seiveNo50.add(new JLabel(""));
		seiveNo50.add(new JLabel(""));
		seiveNo50.add(new JLabel(""));
		sieveData.add(seiveNo50);
		
		ArrayList<Object> seiveNo100 = new ArrayList<Object>();
		seiveNo100.add(new JLabel("100"));
		seiveNo100.add(new JLabel("0.15"));
		seiveNo100.add(new JTextField(10));
		seiveNo100.add(new JLabel(""));
		seiveNo100.add(new JLabel(""));
		seiveNo100.add(new JLabel(""));
		sieveData.add(seiveNo100);
		
		ArrayList<Object> seiveNo200 = new ArrayList<Object>();
		seiveNo200.add(new JLabel("200"));
		seiveNo200.add(new JLabel("0.075"));
		seiveNo200.add(new JTextField(10));
		seiveNo200.add(new JLabel(""));
		seiveNo200.add(new JLabel(""));
		seiveNo200.add(new JLabel(""));
		sieveData.add(seiveNo200);
		
		ArrayList<Object> pan = new ArrayList<Object>();
		pan.add(new JLabel("Pan"));
		pan.add(new JLabel(""));
		pan.add(new JTextField(10));
		pan.add(new JLabel(""));
		pan.add(new JLabel(""));
		pan.add(new JLabel(""));
		sieveData.add(pan);
		
	}
	
	// Updates data on submit 
	private void updateData() {
		double weightRetainedValues[] = new double[sieveData.size()-1], totalWt = 0;
		int j = 0;
		for (int i = 1; i < sieveData.size(); i++) {
			weightRetainedValues[j] = Double.parseDouble(String.format("%.2f", Double.parseDouble(((JTextField) sieveData.get(i).get(2)).getText())));
			totalWt += weightRetainedValues[j];
			j++;
		}
		JLabel percentRtLabel, cumulativePercentLabel, percentFinerLabel;
		double percentRt[] = new double[sieveData.size()-1], cumulativePercent[] = new double[sieveData.size()-1], percentFiner;
		j=0;
		
		for (ArrayList<Object> sieveComponent : sieveData) {
			if (j==0){
				j++;
				continue;
			}
			percentRtLabel = (JLabel) sieveComponent.get(3);
			cumulativePercentLabel = (JLabel) sieveComponent.get(4);
			percentFinerLabel = (JLabel) sieveComponent.get(5);
			percentRt[j-1] = SieveAnalysisCalc.CalcPerecentRetained(weightRetainedValues[j-1], totalWt);
			percentRtLabel.setText(String.format("%.2f", percentRt[j-1]));
			
			cumulativePercent[j-1] = SieveAnalysisCalc.CaclCumulativePercentRetained(percentRt);
			percentFiner = SieveAnalysisCalc.CalcPercentFiner(cumulativePercent[j-1]);
			if ((j-1) < 7) {
				yCords[j-1] = percentFiner;
			}
			
			cumulativePercentLabel.setText(String.format("%.2f", cumulativePercent[j-1]));
			percentFinerLabel.setText(String.format("%.2f", percentFiner));
			
			j++;		
		}
		totalWtLabel.setText(String.format("%.2f", totalWt));
		double finenessModulus = SieveAnalysisCalc.CalcFinenessModulus(Arrays.copyOfRange(cumulativePercent, 0, 6));
		fmLabel.setText("Fineness Modulus : " + String.format("%.2f", finenessModulus));
		
		SLG.repaint(xCords, yCords, d60Label, d10Label, cuLabel);
		
	}
	
	// Constructor function 
	public SieveAnalysisWindow() { 
		setUpData();
		setLayout(new FlowLayout());
		JPanel BodyPanel = new JPanel();
		BodyPanel.setLayout(new GridBagLayout());
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.HORIZONTAL;
		constraints.ipady = 2;
		constraints.gridy = 0;
		constraints.gridx = 0;
		
		// set up for headerPanel
		titleLabel = new JLabel("Sieve Analysis for Aggregate");
		testMethodLabel = new JLabel("   Test Method ASTM C136");
		
		headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());
		GridBagConstraints headerConstraints = new GridBagConstraints();
		headerConstraints.fill = GridBagConstraints.HORIZONTAL;
		headerConstraints.gridx = 2;
		headerConstraints.gridy = 0;
		headerPanel.add(titleLabel, headerConstraints);
		headerConstraints.gridy = 1;
		headerPanel.add(testMethodLabel, headerConstraints);
		
		// adding header panel to body
		BodyPanel.add(headerPanel, constraints);//, BorderLayout.PAGE_START);
		
		// set up for dataPanel
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridBagLayout());
		GridBagConstraints dataConstraints = new GridBagConstraints();
		dataConstraints.fill = GridBagConstraints.HORIZONTAL;
		dataConstraints.gridx = 0;
		dataConstraints.gridy = 0;
		dataConstraints.ipadx = 10;
		dataConstraints.ipady = 10;
		for (ArrayList<Object> currentSieveData : sieveData) {
			for (Object guiItem : currentSieveData) {
				dataPanel.add((Component) guiItem, dataConstraints);
				dataConstraints.gridx++;
			}
			dataConstraints.gridx = 0;
			dataConstraints.gridy++;
		}
		
		submitButton = new JButton("Submit Data");
		submitButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				updateData();
			}
			
		});
		dataConstraints.gridx = 8;
		dataPanel.add(submitButton, dataConstraints);
		
		totalWtTextLabel = new JLabel("Total Weight : ");
		totalWtLabel = new JLabel ("");
		dataConstraints.gridx=1;
		dataPanel.add(totalWtTextLabel, dataConstraints);
		dataConstraints.gridx=2;
		dataPanel.add(totalWtLabel, dataConstraints);
		
		fmLabel = new JLabel("Fineness Modulus : ");
		dataConstraints.gridx = 8;
		dataConstraints.gridy = 0;		
		dataPanel.add(fmLabel, dataConstraints);
		
		// adding data panel to body
		constraints.gridy = 1;
		BodyPanel.add(dataPanel, constraints);//, BorderLayout.CENTER);
		
		// set up for graphPanel;
		graphPanel = new JPanel();
		graphPanel.setLayout(new FlowLayout());
		
		graphBox = new JPanel();
		graphBox.setLayout(new GridBagLayout());
		GridBagConstraints boxConstraints = new GridBagConstraints();
		
		boxConstraints.fill = GridBagConstraints.CENTER;
		boxConstraints.gridx = 1;
		boxConstraints.gridy = 0;
		boxConstraints.ipadx = 2;
		boxConstraints.ipady = 2;
		JLabel graphTitle = new JLabel("Grain Size Distribution");
		graphTitle.setAlignmentX(CENTER_ALIGNMENT);
		graphBox.add(graphTitle, boxConstraints);
		
		boxConstraints.fill = GridBagConstraints.HORIZONTAL;
		boxConstraints.gridy=1;
		boxConstraints.gridx=0;
		JLabel yLabel = new JLabel("Percent Finer") {
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D)g;
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                                RenderingHints.VALUE_ANTIALIAS_ON);
	            AffineTransform aT = g2.getTransform();
	            Shape oldshape = g2.getClip();
	            double x = 65;
	            double y = 0;
	            aT.rotate(Math.toRadians(270), x, y);
//	            g2.transform(aT);
	            g2.setTransform(aT);
	            g2.setClip(oldshape);
	            super.paintComponent(g);
			}
		};
		
		graphBox.add(yLabel, boxConstraints);
		boxConstraints.gridx=1;
		SLG = new SemiLogGraph();
		graphBox.add(SLG, boxConstraints);
		
		boxConstraints.fill = GridBagConstraints.CENTER;
		boxConstraints.gridy=2;
		boxConstraints.gridx=1;
		JLabel xLabel = new JLabel("Diameter (mm)");
		xLabel.setAlignmentX(CENTER_ALIGNMENT);
		graphBox.add(xLabel, boxConstraints);
		
		graphPanel.add(graphBox);
		
		
		// set up for cuPanel
		cuPanel = new JPanel();
		cuPanel.setLayout(new GridBagLayout());
		GridBagConstraints cuConstraints = new GridBagConstraints();
		cuConstraints.fill = GridBagConstraints.HORIZONTAL;
		cuConstraints.gridx = 0;
		cuConstraints.ipady = 2;
		cuConstraints.gridy = 0;
		d60Label = new JLabel("D60 : ");
		cuPanel.add(d60Label, cuConstraints);
		cuConstraints.gridy = 1;
		d10Label = new JLabel("D10 : ");// + String.format("%.2f", SLG.D10));
		cuPanel.add(d10Label, cuConstraints);
		cuConstraints.gridy = 2;
		cuLabel = new JLabel("Uniform Coefficient (D60/D10) = ");// + String.format("%.2f", SieveAnalysisCalc.CalcUniformityCoefficient(SLG.D60, SLG.D10)));
		cuPanel.add(cuLabel, cuConstraints);
		graphPanel.add(cuPanel);
		
		// adding graph panel to body
		constraints.gridy = 2;
		BodyPanel.add(graphPanel, constraints);
		BodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(BodyPanel);
		
	}
	
	
	// Launcher (main) function
	public static void main(String[] args) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

			    @Override
			    public void run() {
			        final JFrame gui = new SieveAnalysisWindow();
			        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					gui.pack();
					
					gui.setTitle("Sieve Analysis");
					gui.setResizable(false);
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				    int x = (int) ((dimension.getWidth() - gui.getWidth()) / 2);
				    int y = (int) ((dimension.getHeight() - gui.getHeight()) / 2);
					gui.setLocation(x, y);;
					gui.setVisible(true);
					
			    }
			});
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
