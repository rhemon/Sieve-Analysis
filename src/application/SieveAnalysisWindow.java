package application;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.*;
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
	JLabel titleLabel, testMethodLabel, totalWtTextLabel, totalWtLabel, fmLabel, d60Label, d10Label, cuLabel, errorLabel;
	
	// Menu bar, menu, menu item variable declaration 
	JMenuBar mainMenu;
	JMenu file;
	JMenuItem export, exportAndPrint;
	
	//TODO: Add a menu item for exporting to spreadsheet and printing that
	
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
	
	// Screen Informations 
	static double x, y, width, height;
	
	// private function for setting up sieve data array
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
	private void updateData() throws Exception{
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
		errorLabel.setText("");
		
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
		headerPanel.setBackground(Color.WHITE);
		headerPanel.setLayout(new GridBagLayout());
		GridBagConstraints headerConstraints = new GridBagConstraints();
		headerConstraints.fill = GridBagConstraints.HORIZONTAL;
		headerConstraints.gridx = 2;
		headerConstraints.gridy = 0;
		headerPanel.add(titleLabel, headerConstraints);
		headerConstraints.gridy = 1;
		headerPanel.add(testMethodLabel, headerConstraints);
		
		// adding header panel to body
		BodyPanel.add(headerPanel, constraints);
		
		// set up for dataPanel
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridBagLayout());
		dataPanel.setBackground(Color.WHITE);
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
				try {
					updateData();
				} catch (Exception exception) {
					errorLabel.setForeground(Color.RED);
					errorLabel.setText("Please ensure valid date is entered");
				}
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
		BodyPanel.add(dataPanel, constraints);
		
		// set up for graphPanel;
		graphPanel = new JPanel();
		graphPanel.setBackground(Color.WHITE);
		graphPanel.setLayout(new FlowLayout());
		
		graphBox = new JPanel();
		graphBox.setBackground(Color.WHITE);
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
				// Rotating the y-axis label
				Graphics2D g2 = (Graphics2D)g;
	            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                                RenderingHints.VALUE_ANTIALIAS_ON);
	            AffineTransform aT = g2.getTransform();
	            Shape oldshape = g2.getClip();
	            double x = 65;
	            double y = 0;
	            aT.rotate(Math.toRadians(270), x, y);
	            g2.setTransform(aT);
	            g2.setClip(oldshape);
	            super.paintComponent(g);
			}
		};
		
		graphBox.add(yLabel, boxConstraints);
		boxConstraints.gridx=1;
		SLG = new SemiLogGraph();
		SLG.setBackground(Color.WHITE);
		graphBox.add(SLG, boxConstraints); // graph added to graph box
		
		boxConstraints.fill = GridBagConstraints.CENTER;
		boxConstraints.gridy=2;
		boxConstraints.gridx=1;
		JLabel xLabel = new JLabel("Diameter (mm)");
		xLabel.setAlignmentX(CENTER_ALIGNMENT);
		graphBox.add(xLabel, boxConstraints);
		
		// graph box added to graph panel
		graphPanel.add(graphBox);
		
		
		// set up for cuPanel
		cuPanel = new JPanel();
		cuPanel.setBackground(Color.WHITE);
		cuPanel.setLayout(new GridBagLayout());
		GridBagConstraints cuConstraints = new GridBagConstraints();
		cuConstraints.fill = GridBagConstraints.HORIZONTAL;
		cuConstraints.gridx = 0;
		cuConstraints.ipady = 2;
		cuConstraints.gridy = 0;
		d60Label = new JLabel("D60 : ");
		cuPanel.add(d60Label, cuConstraints);
		cuConstraints.gridy = 1;
		d10Label = new JLabel("D10 : ");
		cuPanel.add(d10Label, cuConstraints);
		cuConstraints.gridy = 2;
		cuLabel = new JLabel("Uniform Coefficient (D60/D10) = ");
		cuPanel.add(cuLabel, cuConstraints);
		graphPanel.add(cuPanel);
		
		// adding graph panel to body
		constraints.gridy = 2;
		BodyPanel.add(graphPanel, constraints);
		
		// set up for error label 
		errorLabel = new JLabel("");
		constraints.gridx=0;
		constraints.gridy=3;
		// adding error label to body
		BodyPanel.add(errorLabel, constraints);
		
		BodyPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		BodyPanel.setBackground(Color.WHITE);
		add(BodyPanel); //body panel added to frame
		
		// setting up menu bar
		mainMenu = new JMenuBar();
		file = new JMenu("File");
		export = new JMenuItem("Export as Image");
		
		export.addActionListener(new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				takeScreenShot(false);
			}
			
		});
		
		exportAndPrint = new JMenuItem("Export and Print");
		exportAndPrint.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				takeScreenShot(true);		
			}
			
		});
		
		file.add(export);
		file.add(exportAndPrint);
		mainMenu.add(file);
		setJMenuBar(mainMenu);
		
	}
	
	// saves image
	/**
	 * Saves the screenshot taken
	 * @param screenShot The screenshot that was taken
	 * @param print      Should it print or not
	 */
	
	
	private void saveImage(BufferedImage screenShot, boolean print) {
		JFileChooser fs = new JFileChooser(new File("."));
		fs.setDialogTitle("Export Image (Please enter name)");
		int result = fs.showSaveDialog(null);
		if (result == JFileChooser.APPROVE_OPTION) {
			File fi = fs.getSelectedFile();
			String fileExtension = "jpg";
			try {
				String pathName = fi.getPath();
				// checks if any extension is given in the name, if it is then arranges the name according to it, and if invalid extension given
				// informs the user
				if (fi.getName().contains(".")){
					int numberOfOccurences =0;
					for (int i=0; i<fi.getName().length(); i++){
						if (fi.getName().charAt(i) == '.'){
							numberOfOccurences++;
						}
					}
					if (numberOfOccurences>1) {
						throw new Exception();
					} 
					
					fileExtension = fi.getName().substring(fi.getName().indexOf(".")+1, fi.getName().length());// continue here
					pathName = fi.getPath().substring(0, fi.getPath().length()-fileExtension.length()-1);
					
				}
				File imageFile = new File(pathName+"."+fileExtension);
				ImageIO.write(screenShot, fileExtension, imageFile);
				if (print) {
					printImage(imageFile);
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(fs,
					    "File Name extension cant be recongnized",
					    "Export Error!!!",
					    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	// prints image
	/**
	 * Functions prints the image using PrinterJob
	 * @param file image file 
	 */
	private void printImage(File file) {
		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pf = job.defaultPage();
		pf.setOrientation(PageFormat.LANDSCAPE);
		
		Printable painter = new Printable(){

			@Override
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
				if (pageIndex > 0) // we only print one page
		            return Printable.NO_SUCH_PAGE; // ie., end of job
				Image image;
				try {
					image = ImageIO.read((File) file);
					graphics.drawImage(image, 10, 30, (int)pf.getWidth()-25, (int)pf.getHeight()-40, null);// Graphics.class.VCENTER | Graphics.HCENTER)
					
				} catch (IOException e) {
					return Printable.NO_SUCH_PAGE;
				}
				return Printable.PAGE_EXISTS;
			}
			
		};
		
	    job.setPrintable(painter, pf);
	    boolean ok = job.printDialog();
	    if (ok) {
	      try {
	    	  job.print();
	      } catch (PrinterException ex) {
	        /* The job did not successfully complete */
	    	  JOptionPane.showMessageDialog(this,
					    "Error : " + ex.getMessage(),
					    "Print Error!!!",
					    JOptionPane.ERROR_MESSAGE);	  
	      }
	    }
	}
	
	// function takes screen shot 
	/**
	 * Takes a screenshot of the application
	 * @param print should it print or not 
	 */
	private void takeScreenShot(boolean print) {
		x = this.getLocation().getX();
		y = this.getLocation().getY();
		
		Robot robot;
		try {
			robot = new Robot();
			Rectangle rect = new Rectangle((int)width-23,(int)height-112);
			rect.setLocation((int)x+20, (int)y+95);
			BufferedImage screenShot = robot.createScreenCapture(rect);
			saveImage(screenShot, print);
		} catch (AWTException e) {
			JOptionPane.showMessageDialog(this,
				    "Error : " + e.getMessage(),
				    "Error!!!",
				    JOptionPane.ERROR_MESSAGE);
		}
		
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
					gui.setBackground(Color.WHITE);
					gui.setTitle("Sieve Analysis");
					gui.setResizable(false);
					
					// locating the window in the center of the screen
					Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
				    x = (double) ((dimension.getWidth() - gui.getWidth()) / 2);
				    y = (double) ((dimension.getHeight() - gui.getHeight()) / 2);
				    width = (double) gui.getWidth();
				    height = (double) gui.getHeight();
					gui.setLocation((int)x, (int) y);
					
					gui.setVisible(true);
			    }
			});
		} catch (InvocationTargetException e) {
			JOptionPane.showMessageDialog(new JFrame(),
				    "Error : " + e.getMessage(),
				    "Error!!!",
				    JOptionPane.ERROR_MESSAGE);
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(new JFrame(),
				    "Error : " + e.getMessage(),
				    "Error!!!",
				    JOptionPane.ERROR_MESSAGE);
		}
	}
}
