package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;

import org.math.plot.Plot2DPanel;

import geneticAlgorithm.GeneticAlgorithm;
import utils.FunctionType;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSlider;

public class Interface extends JFrame {

	private JPanel contentPane;
	private Plot2DPanel panelMathPlot;
	private JLabel parametersLabel;
	private GeneticAlgorithm gA;
	private FunctionType f_type;
	private int numGenerations; 
	private boolean elitism;
	private double eliPercentage;
	
	JSlider sliderElitism;
	JCheckBox elitismCheckBox;
	JComboBox functionDropdown;
	JSpinner spinnerNumGenerations;
	JSpinner spinnerSizePopulation;
	JComboBox selectionDropdown;
	
	/**
	 * Create the frame.
	 */
	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();

		String title = "Practica 1 - Programacion Evolutiva           |            Jose Daniel Rave Robayo - Samuel Blázquez";
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		double[] x = { 1, 2, 3, 4, 5, 6 };
		double[] y = { 45, 89, 6, 32, 63, 12 };
		panelMathPlot = new Plot2DPanel();
		panelMathPlot.addLegend("SOUTH");
		panelMathPlot.addLinePlot("Best", x, y);
		
		parametersLabel = new JLabel("Parameters");
		parametersLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JLabel graphTitle = new JLabel("Graph Evolution");
		graphTitle.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		selectionPanel.setBackground(new Color(192, 192, 192));
		
		
		
		JButton startButton = new JButton("Evolute");
		startButton.setBackground(new Color(175, 238, 238));
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		startButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {			
				processData();		
			}
		});
		
		JPanel elitismPanel = new JPanel();
		elitismPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		elitismPanel.setBackground(Color.LIGHT_GRAY);
		
		elitismCheckBox = new JCheckBox("Elitism (%)");
		elitismCheckBox.setFont(new Font("Georgia", Font.PLAIN, 14));
		elitismCheckBox.setBackground(Color.LIGHT_GRAY);
		
		sliderElitism = new JSlider();
		sliderElitism.setFont(new Font("Georgia", Font.PLAIN, 10));
		sliderElitism.setPaintLabels(true);
		sliderElitism.setMinorTickSpacing(5);
		sliderElitism.setMajorTickSpacing(10);
		sliderElitism.setPaintTicks(true);
		sliderElitism.setBackground(Color.LIGHT_GRAY);
		
		GroupLayout gl_elitismPanel = new GroupLayout(elitismPanel);
		gl_elitismPanel.setHorizontalGroup(
			gl_elitismPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_elitismPanel.createSequentialGroup()
					.addGroup(gl_elitismPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_elitismPanel.createSequentialGroup()
							.addGap(36)
							.addComponent(sliderElitism, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_elitismPanel.createSequentialGroup()
							.addGap(126)
							.addComponent(elitismCheckBox)))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		gl_elitismPanel.setVerticalGroup(
			gl_elitismPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_elitismPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(elitismCheckBox)
					.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
					.addComponent(sliderElitism, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		elitismPanel.setLayout(gl_elitismPanel);
		
		JPanel generationsPanel = new JPanel();
		generationsPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		generationsPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel generationsLabel = new JLabel("# Generations");
		generationsLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		spinnerNumGenerations = new JSpinner();
		spinnerNumGenerations.setModel(new SpinnerNumberModel(100, 10, 100, 1));
		GroupLayout gl_generationsPanel = new GroupLayout(generationsPanel);
		gl_generationsPanel.setHorizontalGroup(
			gl_generationsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_generationsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(generationsLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addComponent(spinnerNumGenerations, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_generationsPanel.setVerticalGroup(
			gl_generationsPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_generationsPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_generationsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(generationsLabel)
						.addComponent(spinnerNumGenerations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		generationsPanel.setLayout(gl_generationsPanel);
		
		JPanel functionPanel = new JPanel();
		functionPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		functionPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel functionLabel = new JLabel("Function Type");
		functionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		functionDropdown = new JComboBox();
		functionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		
		functionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Function 1", "Function 2 Schubert", "Function 3 EggHolder", "Function 4 Michalewicz"}));
		
		GroupLayout gl_functionPanel = new GroupLayout(functionPanel);
		gl_functionPanel.setHorizontalGroup(
			gl_functionPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(functionLabel)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_functionPanel.setVerticalGroup(
			gl_functionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(functionLabel)
						.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		functionPanel.setLayout(gl_functionPanel);
		
		JPanel sizePanel = new JPanel();
		sizePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel sizeLabel = new JLabel("Size Population");
		sizeLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		spinnerSizePopulation = new JSpinner();
		spinnerSizePopulation.setModel(new SpinnerNumberModel(100, 10, 100, 1));
		GroupLayout gl_sizePanel = new GroupLayout(sizePanel);
		gl_sizePanel.setHorizontalGroup(
			gl_sizePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_sizePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(sizeLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
					.addComponent(spinnerSizePopulation, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_sizePanel.setVerticalGroup(
			gl_sizePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_sizePanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_sizePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(sizeLabel)
						.addComponent(spinnerSizePopulation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		sizePanel.setLayout(gl_sizePanel);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(parametersLabel)
									.addGap(93))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(startButton)
									.addGap(115))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
										.addComponent(sizePanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
										.addComponent(generationsPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
										.addComponent(functionPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 312, Short.MAX_VALUE)
										.addComponent(selectionPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
										.addComponent(elitismPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panelMathPlot, GroupLayout.PREFERRED_SIZE, 770, GroupLayout.PREFERRED_SIZE)
							.addGap(171))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(graphTitle)
							.addGap(300))))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(graphTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(parametersLabel)
							.addGap(39)
							.addComponent(sizePanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(generationsPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(functionPanel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addGap(154)
							.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(elitismPanel, GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(startButton)
							.addGap(23))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(panelMathPlot, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
							.addContainerGap())))
		);
		selectionDropdown = new JComboBox();
		selectionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		selectionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Ruleta", "Torneo Aleatorio", "Torneo Determinista", "Restos", "Truncamiento", "Estocástico Universal"}));
		
		JLabel selectionLabel = new JLabel("Selection Type");
		selectionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		GroupLayout gl_Selection = new GroupLayout(selectionPanel);
		gl_Selection.setHorizontalGroup(
			gl_Selection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Selection.createSequentialGroup()
					.addContainerGap()
					.addComponent(selectionLabel)
					.addGap(18)
					.addComponent(selectionDropdown, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(54, Short.MAX_VALUE))
		);
		gl_Selection.setVerticalGroup(
			gl_Selection.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_Selection.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_Selection.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectionDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectionLabel))
					.addContainerGap())
		);
		selectionPanel.setLayout(gl_Selection);
		
		contentPane.setLayout(gl_contentPane);
		
//		double[] x = { 1, 2, 3, 4, 5, 6 };
//		double[] y = { 45, 89, 6, 32, 63, 12 };
//		
//		panelMathPlot.setAlignmentY(Component.TOP_ALIGNMENT);
//		panelMathPlot.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
//		panelMathPlot.setLocation(new Point(0, 8));
//		BorderLayout borderLayout = (BorderLayout) panelMathPlot.getLayout();
//		borderLayout.setVgap(1);
//		panelMathPlot.setBounds(300, 0, 758, 601);
//		
//		// define the legend position
//		panelMathPlot.addLegend("SOUTH");
//		
//		// add a line plot to the PlotPanel
//		panelMathPlot.addLinePlot("my plot", x, y);
//		contentPane.setLayout(new GridLayout(0, 3, 3, 0));
//		panelMathPlot.plotCanvas.setLayout(new GridLayout(1, 0, 0, 0));
		
		
	}
	
	private void processData() {
		elitism = this.elitismCheckBox.isSelected(); 
		eliPercentage =	((int)this.sliderElitism.getValue())/100;
		
		
		getFunctionType();
		gA = new GeneticAlgorithm();
		gA.Evolute(f_type, numGenerations, elitism, eliPercentage);
	}
	
	private void getFunctionType() {
		int index = functionDropdown.getSelectedIndex();
		f_type = FunctionType.values()[index];
	}
	
	private void getSelectionType() {
		int index = selectionDropdown.getSelectedIndex();
		f_type = FunctionType.values()[index];
	}
}
