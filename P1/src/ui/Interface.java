package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import org.math.plot.Plot2DPanel;

import geneticAlgorithm.GeneticAlgorithm;
import utils.*;
import javax.swing.SpinnerModel;

public class Interface extends JFrame {

	private JPanel contentPane;
	private Plot2DPanel panelMathPlot;
	private JLabel parametersLabel;
	private GeneticAlgorithm gA;
	private FunctionType f_type;
	private SelectionType s_type;
	private MutationType m_type;
	private CrossType c_type;
	JCheckBox elitismCheckBox;
	JComboBox functionDropdown;
	JSpinner spinnerNumGenerations;
	JSpinner spinnerSizePopulation;
	JComboBox selectionDropdown; 
	JComboBox crossDropdown;
	JComboBox mutationDropdown;
	JSpinner elitismSpinner;
	JSpinner crossSpinner;
	JSpinner mutationSpinner;
	JSpinner precisionSpinner;
	
	private int sizePop; 
	private int numGenerations; 
	private double crossProb; 
	private double mutProb; 
	private double precision; 
	private boolean elitism;
	private double eliPercentage;
	
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
		
		double min = 2.0;
        double value = 2.0;
        double max = 7.0;
        double stepSize = 0.1;
		SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, stepSize);
		elitismSpinner = new JSpinner(model);
		JSpinner.NumberEditor editor = (JSpinner.NumberEditor) elitismSpinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(1);
        
        
		GroupLayout gl_elitismPanel = new GroupLayout(elitismPanel);
		gl_elitismPanel.setHorizontalGroup(
			gl_elitismPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_elitismPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(elitismCheckBox)
					.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
					.addComponent(elitismSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_elitismPanel.setVerticalGroup(
			gl_elitismPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_elitismPanel.createSequentialGroup()
					.addContainerGap(67, Short.MAX_VALUE)
					.addGroup(gl_elitismPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(elitismCheckBox)
						.addComponent(elitismSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
		
		JPanel MutationPanel = new JPanel();
		MutationPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		MutationPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel mutationLabel = new JLabel("Mutation Type");
		mutationLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		mutationDropdown = new JComboBox();
		mutationDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		mutationDropdown.setModel(new DefaultComboBoxModel(new String[] {"Basic", "Basic_Double"}));
		GroupLayout gl_MutationPanel = new GroupLayout(MutationPanel);
		gl_MutationPanel.setHorizontalGroup(
			gl_MutationPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 312, Short.MAX_VALUE)
				.addGroup(gl_MutationPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(mutationLabel)
					.addGap(18)
					.addComponent(mutationDropdown, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_MutationPanel.setVerticalGroup(
			gl_MutationPanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 46, Short.MAX_VALUE)
				.addGroup(gl_MutationPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_MutationPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(mutationDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(mutationLabel))
					.addContainerGap())
		);
		MutationPanel.setLayout(gl_MutationPanel);
		
		JPanel crossPanel = new JPanel();
		crossPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		crossPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel crossLabel = new JLabel("Cross Type");
		crossLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		crossDropdown = new JComboBox();
		crossDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		crossDropdown.setModel(new DefaultComboBoxModel(new String[] {"Monopoint","Uniform"}));
		GroupLayout gl_crossPanel = new GroupLayout(crossPanel);
		gl_crossPanel.setHorizontalGroup(
			gl_crossPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 312, Short.MAX_VALUE)
				.addGroup(gl_crossPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(crossLabel)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(crossDropdown, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_crossPanel.setVerticalGroup(
			gl_crossPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 54, Short.MAX_VALUE)
				.addGroup(gl_crossPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_crossPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(crossLabel)
						.addComponent(crossDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		crossPanel.setLayout(gl_crossPanel);
		
		JPanel crossPercentagePanel = new JPanel();
		crossPercentagePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		crossPercentagePanel.setBackground(Color.LIGHT_GRAY);
		
		double minCross = 0.0;
        double valueCross = 60.0;
        double maxCross = 100.0;
        double stepSizeCross = 0.1;
		SpinnerNumberModel modelCross = new SpinnerNumberModel(valueCross, minCross, maxCross, stepSizeCross);
		crossSpinner = new JSpinner(modelCross);
		JSpinner.NumberEditor editorCross = (JSpinner.NumberEditor) crossSpinner.getEditor();
        DecimalFormat formatCross = editorCross.getFormat();
        formatCross.setMinimumFractionDigits(1);
		
		JLabel crossLabelPercentage = new JLabel("Cross (%)");
		crossLabelPercentage.setFont(new Font("Georgia", Font.PLAIN, 13));
		GroupLayout gl_crossPercentagePanel = new GroupLayout(crossPercentagePanel);
		gl_crossPercentagePanel.setHorizontalGroup(
			gl_crossPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_crossPercentagePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(crossLabelPercentage, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
					.addComponent(crossSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_crossPercentagePanel.setVerticalGroup(
			gl_crossPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_crossPercentagePanel.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addGroup(gl_crossPercentagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(crossSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(crossLabelPercentage, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		crossPercentagePanel.setLayout(gl_crossPercentagePanel);
		
		JPanel mutationPercentagePanel = new JPanel();
		mutationPercentagePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		mutationPercentagePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel mutationLabelPercentage = new JLabel("Mutation (%)");
		mutationLabelPercentage.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		double minMut = 3.0;
        double valueMut = 5.0;
        double maxMut = 50.0;
        double stepSizeMut = 0.1;
		SpinnerNumberModel modelMut = new SpinnerNumberModel(valueMut, minMut, maxMut, stepSizeMut);
		mutationSpinner = new JSpinner(modelMut);
		JSpinner.NumberEditor editorMut = (JSpinner.NumberEditor) mutationSpinner.getEditor();
        DecimalFormat formatMut = editorMut.getFormat();
        formatMut.setMinimumFractionDigits(1);
		
		GroupLayout gl_mutationPercentagePanel = new GroupLayout(mutationPercentagePanel);
		gl_mutationPercentagePanel.setHorizontalGroup(
			gl_mutationPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_mutationPercentagePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(mutationLabelPercentage, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
					.addComponent(mutationSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_mutationPercentagePanel.setVerticalGroup(
			gl_mutationPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_mutationPercentagePanel.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addGroup(gl_mutationPercentagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(mutationSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(mutationLabelPercentage, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		mutationPercentagePanel.setLayout(gl_mutationPercentagePanel);
		
		JPanel precisionPercentagePanel = new JPanel();
		precisionPercentagePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		precisionPercentagePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel precisionLabelPercentage = new JLabel("Precision (%)");
		precisionLabelPercentage.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		double minPreci = 0.001;
        double valuePreci = 0.001;
        double maxPreci = 0.100;
        double stepSizePreci = 0.001;
		SpinnerNumberModel modelPreci = new SpinnerNumberModel(valuePreci, minPreci, maxPreci, stepSizePreci);
		precisionSpinner = new JSpinner(modelPreci);
		JSpinner.NumberEditor editorPreci = (JSpinner.NumberEditor) elitismSpinner.getEditor();
        DecimalFormat formatPreci = editorPreci.getFormat();
        formatPreci.setMinimumFractionDigits(3);
		
		GroupLayout gl_precisionPercentagePanel = new GroupLayout(precisionPercentagePanel);
		gl_precisionPercentagePanel.setHorizontalGroup(
			gl_precisionPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_precisionPercentagePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(precisionLabelPercentage, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
					.addComponent(precisionSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_precisionPercentagePanel.setVerticalGroup(
			gl_precisionPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_precisionPercentagePanel.createSequentialGroup()
					.addContainerGap(16, Short.MAX_VALUE)
					.addGroup(gl_precisionPercentagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(precisionSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(precisionLabelPercentage, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		precisionPercentagePanel.setLayout(gl_precisionPercentagePanel);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(selectionPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
								.addComponent(MutationPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
								.addComponent(crossPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
								.addComponent(functionPanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 312, Short.MAX_VALUE)
								.addComponent(elitismPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(parametersLabel)
									.addGap(93))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(startButton)
									.addGap(115))
								.addComponent(generationsPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
								.addComponent(sizePanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
								.addComponent(crossPercentagePanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
								.addComponent(mutationPercentagePanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
								.addComponent(precisionPercentagePanel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
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
							.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
							.addComponent(sizePanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(generationsPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crossPercentagePanel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(mutationPercentagePanel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(precisionPercentagePanel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(functionPanel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crossPanel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(MutationPanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(elitismPanel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
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
		sizePop = (int)this.spinnerSizePopulation.getValue();
		numGenerations = (int)this.spinnerNumGenerations.getValue();
		crossProb = ((double) this.crossSpinner.getValue()) / 100.0;
		mutProb = ((double) this.mutationSpinner.getValue()) / 100.0;
		precision = (double) this.precisionSpinner.getValue();
		
		elitism = this.elitismCheckBox.isSelected(); 
		eliPercentage =	((double)this.elitismSpinner.getValue())/100.0;
		
		getFunctionType();
		getSelectionType();
		getCrossType();
		getMutationType();
		gA = new GeneticAlgorithm();
		
		gA.Evolute(sizePop, numGenerations,crossProb, mutProb, precision ,
				   f_type, s_type,c_type,m_type, elitism, eliPercentage);
	}
	
	private void getFunctionType() {
		int index = functionDropdown.getSelectedIndex();
		f_type = FunctionType.values()[index];
	}
	
	private void getSelectionType() {
		int index = selectionDropdown.getSelectedIndex();
		s_type = SelectionType.values()[index];
	}
	
	private void getCrossType() {
		int index = selectionDropdown.getSelectedIndex();
		c_type = CrossType.values()[index];
	}
	
	private void getMutationType() {
		int index = selectionDropdown.getSelectedIndex();
		m_type = MutationType.values()[index];
	}
}
