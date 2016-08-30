package application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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
	JPanel headerPanel, dataPanel, fmPanel, graphPanel, cuPanel;
	JLabel titleLabel, testMethodLabel, fromLabel, sampleLabel, totalWtTextLabel, totalWtLabel, fmLabel;
	JTextField fromField, sampleField;
	Graphics graphArea;
	SemiLogGraph SLG; 
	// dataPanel is set as an [][]
	ArrayList<ArrayList<Object>> sieveData; //needs to be set properly with JLabel, JTextField
	
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
			
			
			cumulativePercentLabel.setText(String.format("%.2f", cumulativePercent[j-1]));
			percentFinerLabel.setText(String.format("%.2f", percentFiner));
			
			j++;		
		}
		totalWtLabel.setText(String.format("%.2f", totalWt));
		double finenessModulus = SieveAnalysisCalc.CalcFinenessModulus(Arrays.copyOfRange(cumulativePercent, 0, 6));
		fmLabel.setText("Fineness Modulus : " + String.format("%.2f", finenessModulus));
		
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
		fromLabel = new JLabel(" From : ");
		sampleLabel = new JLabel("Sample No. : ");
		fromField = new JTextField(10);
		sampleField = new JTextField(5);
		
		headerPanel = new JPanel();
		headerPanel.setLayout(new GridBagLayout());
		GridBagConstraints headerConstraints = new GridBagConstraints();
		headerConstraints.fill = GridBagConstraints.HORIZONTAL;
		headerConstraints.gridx = 2;
		headerConstraints.gridy = 0;
		headerPanel.add(titleLabel, headerConstraints);
		headerConstraints.gridy = 1;
		headerPanel.add(testMethodLabel, headerConstraints);
		headerConstraints.gridy = 2;
		headerConstraints.gridx = 0;
		headerPanel.add(fromLabel, headerConstraints);
		headerConstraints.gridx = 1;
		headerPanel.add(fromField, headerConstraints);
		headerConstraints.gridx = 3;
		headerPanel.add(sampleLabel, headerConstraints);
		headerConstraints.gridx = 4;
		headerPanel.add(sampleField, headerConstraints);
		
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
		
		constraints.gridy = 1;
		BodyPanel.add(dataPanel, constraints);//, BorderLayout.CENTER);
		
		// set up for graphPanel;
		graphPanel = new JPanel();
		graphPanel.setLayout(new FlowLayout());
		
		SLG = new SemiLogGraph();
		graphPanel.add(SLG);
		
		cuPanel = new JPanel();
		cuPanel.setLayout(new FlowLayout());
		JLabel cuLabel = new JLabel("Uniform Coefficient (D60/D10)");
		cuPanel.add(cuLabel);
		graphPanel.add(cuPanel);
		
		constraints.gridy = 2;
		BodyPanel.add(graphPanel, constraints);
		
		// ------------------------
		// set up for graphPanel  -
		//						  -
		// set up for cuPanel	  -
		// ------------------------
		
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
