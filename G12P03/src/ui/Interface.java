package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import org.math.plot.Plot2DPanel;

import functions.FunctMultiplexor;
import geneticAlgorithm.GeneticAlgorithm;
import individual.Chromosome;
import utils.CrossType;
import utils.FunctionType;
import utils.MutationType;
import utils.SelectionType;
import utilsMultiplex.BloatingType;
import utilsMultiplex.CreationType;
import utilsMultiplex.MultiplexType;

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
	JCheckBox IFCheckBox;
	JComboBox functionDropdown;
	JSpinner spinnerNumGenerations;
	JSpinner spinnerSizePopulation;
	JComboBox selectionDropdown; 
	JComboBox crossDropdown;
	JComboBox mutationDropdown;
	JSpinner elitismSpinner;
	JSpinner crossSpinner;
	JSpinner mutationSpinner;
	JSpinner betaSpinner;
	JLabel betaLabel_;
	JComboBox solutionList;
	JComboBox truncDropdown;
	JLabel truncLabel;
	JComboBox creationTypeDropdown;
	JComboBox entriesDropdown;
	JSpinner maxDepthSpinner;
	JComboBox bloatingDropdown;
	JSpinner tarpeianSpinner;
	
	private int sizePop; 
	private int numGenerations; 
	private double crossProb; 
	private double mutProb; 
	private double precision; 
	private boolean elitism;
	private double eliPercentage;
	private JTextField mutField;
	public JPanel evolTab;
	public JProgressBar progressBar;
	
	List<ArrayList<Double>> separations;
	final int numPistas = 3;
	private JTextField crosField;
	private JTextArea expressionField;
	
	
	/**
	 * Create the frame.
	 */
	public Interface() {
		FunctMultiplexor.setUpData();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();

		String title = "Practica 3 - Programacion Evolutiva           |            Jose Daniel Rave Robayo - Samuel Bl?zquez";
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		this.setTitle(title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		double[] x = { 1, 2, 3, 4, 5, 6 };
		double[] y = { 45, 89, 6, 32, 63, 12 };
		
		double min = 2.0;
        double value = 2.0;
        double max = 7.0;
        double stepSize = 0.1;
		SpinnerNumberModel model = new SpinnerNumberModel(value, min, max, stepSize);
		elitismSpinner = new JSpinner(model);
		JSpinner.NumberEditor editor = (JSpinner.NumberEditor) elitismSpinner.getEditor();
        DecimalFormat format = editor.getFormat();
        format.setMinimumFractionDigits(1);
		
		double minParam = 2;
        double valueParam = 2;
        double maxParam = 100;
        double stepSizeParam = 1;
		SpinnerNumberModel modelParam = new SpinnerNumberModel(valueParam, minParam, maxParam, stepSizeParam);
		
		double minCross = 0.0;
        double valueCross = 60.0;
        double maxCross = 100.0;
        double stepSizeCross = 0.1;
		SpinnerNumberModel modelCross = new SpinnerNumberModel(valueCross, minCross, maxCross, stepSizeCross);
		crossSpinner = new JSpinner(modelCross);
		JSpinner.NumberEditor editorCross = (JSpinner.NumberEditor) crossSpinner.getEditor();
        DecimalFormat formatCross = editorCross.getFormat();
        formatCross.setMinimumFractionDigits(1);
		
		double minMut = 3.0;
        double valueMut = 5.0;
        double maxMut = 50.0;
        double stepSizeMut = 0.1;
		SpinnerNumberModel modelMut = new SpinnerNumberModel(valueMut, minMut, maxMut, stepSizeMut);
		mutationSpinner = new JSpinner(modelMut);
		JSpinner.NumberEditor editorMut = (JSpinner.NumberEditor) mutationSpinner.getEditor();
        DecimalFormat formatMut = editorMut.getFormat();
        formatMut.setMinimumFractionDigits(1);
		
		double minPreci = 0.001;
        double valuePreci = 0.001;
        double maxPreci = 0.100;
        double stepSizePreci = 0.001;
		SpinnerNumberModel modelPreci = new SpinnerNumberModel(valuePreci, minPreci, maxPreci, stepSizePreci);
		JSpinner.NumberEditor editorPreci = (JSpinner.NumberEditor) elitismSpinner.getEditor();
        DecimalFormat formatPreci = editorPreci.getFormat();
        formatPreci.setMinimumFractionDigits(3);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1040, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel entryTab = new JPanel();
		tabbedPane.addTab("Entry", null, entryTab, null);
		
		entriesDropdown = new JComboBox();
		entriesDropdown.setModel(new DefaultComboBoxModel(new String[] {"6 entries", "11 entries"}));
		entriesDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		JButton startButton_1 = new JButton("Evolute");
		startButton_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				try {
					processData();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
		});
		startButton_1.setBackground(new Color(175, 238, 238));
		
		expressionField = new JTextArea();
		expressionField.setLineWrap(true);
		expressionField.setWrapStyleWord(true);
		expressionField.setForeground(Color.BLACK);
		expressionField.setBackground(Color.LIGHT_GRAY);
		expressionField.setFont(new Font("Consolas", Font.BOLD | Font.ITALIC, 18));
		expressionField.setAlignmentY(TOP_ALIGNMENT);
		expressionField.setEditable(false);
		expressionField.setColumns(10);
		
		JLabel lblExpressionResult = new JLabel("Expression Result:");
		lblExpressionResult.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		progressBar = new JProgressBar();
		
		JLabel lblMultiplexorSimulator = new JLabel("Multiplexor Simulator");
		lblMultiplexorSimulator.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JLabel lblNumEntries = new JLabel("Num Entries");
		lblNumEntries.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		maxDepthSpinner = new JSpinner();
		maxDepthSpinner.setModel(new SpinnerNumberModel(new Integer(2), null, null, new Integer(1)));
		
		JLabel lblNumEntries_1_1 = new JLabel("Max Depth");
		lblNumEntries_1_1.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		IFCheckBox = new JCheckBox("Use Operator IF");
		IFCheckBox.setSelected(true);
		IFCheckBox.setFont(new Font("Georgia", Font.PLAIN, 14));
		IFCheckBox.setBackground(Color.LIGHT_GRAY);
		
		JLabel bloatingLabl = new JLabel("Bloating Type");
		bloatingLabl.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JLabel tarpeianlabel = new JLabel("Tarpeian Factor (n)");
		tarpeianlabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		tarpeianlabel.setEnabled(true);
		
		bloatingDropdown = new JComboBox();
		bloatingDropdown.setModel(new DefaultComboBoxModel(new String[] {"None", "Tarpeian", "Well-Founded Penalty"}));
		bloatingDropdown.setSelectedIndex(1);
		bloatingDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		bloatingDropdown.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        int select = bloatingDropdown.getSelectedIndex();
	        	tarpeianSpinner.setEnabled(select == 1);
	        	tarpeianlabel.setEnabled(select == 1);
		    }
		});
		
		
		double minTar = 0.5;
		double valueTar = 2.0;
		double maxTar = 5.0;
		double stepSizeTar = 0.1;
		SpinnerNumberModel modelTar = new SpinnerNumberModel(valueTar, minTar, maxTar, stepSizeTar);
		tarpeianSpinner = new JSpinner(modelTar);
		tarpeianSpinner.setEnabled(true);
		JSpinner.NumberEditor editorTar = (JSpinner.NumberEditor) tarpeianSpinner.getEditor();
		DecimalFormat formatTar = editorTar.getFormat();
		formatTar.setMinimumFractionDigits(1);
		
		GroupLayout gl_entryTab = new GroupLayout(entryTab);
		gl_entryTab.setHorizontalGroup(
			gl_entryTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_entryTab.createSequentialGroup()
							.addGap(139)
							.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_entryTab.createSequentialGroup()
									.addGap(9)
									.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_entryTab.createSequentialGroup()
											.addGap(110)
											.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
												.addComponent(lblExpressionResult, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
												.addComponent(expressionField, 499, 499, 499)))
										.addGroup(gl_entryTab.createSequentialGroup()
											.addGap(314)
											.addComponent(startButton_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))))
								.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, 684, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_entryTab.createSequentialGroup()
									.addGap(279)
									.addComponent(IFCheckBox, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_entryTab.createSequentialGroup()
							.addGap(339)
							.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_entryTab.createSequentialGroup()
									.addGap(67)
									.addComponent(lblMultiplexorSimulator, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_entryTab.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_entryTab.createSequentialGroup()
										.addComponent(lblNumEntries_1_1, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(maxDepthSpinner, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_entryTab.createSequentialGroup()
										.addComponent(lblNumEntries, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(entriesDropdown, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_entryTab.createSequentialGroup()
										.addComponent(bloatingLabl, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(bloatingDropdown, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_entryTab.createSequentialGroup()
										.addComponent(tarpeianlabel, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
										.addGap(33)
										.addComponent(tarpeianSpinner, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE))))))
					.addContainerGap(1602, Short.MAX_VALUE))
		);
		gl_entryTab.setVerticalGroup(
			gl_entryTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_entryTab.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMultiplexorSimulator, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_entryTab.createParallelGroup(Alignment.BASELINE)
						.addComponent(entriesDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNumEntries, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_entryTab.createParallelGroup(Alignment.TRAILING)
						.addComponent(maxDepthSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNumEntries_1_1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
						.addComponent(bloatingLabl, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_entryTab.createSequentialGroup()
							.addGap(2)
							.addComponent(bloatingDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_entryTab.createSequentialGroup()
							.addGap(12)
							.addComponent(tarpeianSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_entryTab.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tarpeianlabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
					.addGap(32)
					.addComponent(IFCheckBox, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(progressBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(35)
					.addComponent(lblExpressionResult, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(expressionField, GroupLayout.PREFERRED_SIZE, 177, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(startButton_1)
					.addContainerGap(713, Short.MAX_VALUE))
		);
		entryTab.setLayout(gl_entryTab);
		
		evolTab = new JPanel();
		tabbedPane.addTab("Evolution", null, evolTab, null);
		
		JLabel parametersLabel = new JLabel("Parameters");
		parametersLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JPanel mutationPercentagePanel = new JPanel();
		mutationPercentagePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		mutationPercentagePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel mutationLabelPercentage = new JLabel("Mutation (%)");
		mutationLabelPercentage.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		GroupLayout gl_mutationPercentagePanel = new GroupLayout(mutationPercentagePanel);
		gl_mutationPercentagePanel.setHorizontalGroup(
			gl_mutationPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGap(0, 320, Short.MAX_VALUE)
				.addGroup(gl_mutationPercentagePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(mutationLabelPercentage, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
					.addComponent(mutationSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_mutationPercentagePanel.setVerticalGroup(
			gl_mutationPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 121, Short.MAX_VALUE)
				.addGap(0, 121, Short.MAX_VALUE)
				.addGap(0, 117, Short.MAX_VALUE)
				.addGap(0, 113, Short.MAX_VALUE)
				.addGroup(gl_mutationPercentagePanel.createSequentialGroup()
					.addContainerGap(78, Short.MAX_VALUE)
					.addGroup(gl_mutationPercentagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(mutationSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(mutationLabelPercentage, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		mutationPercentagePanel.setLayout(gl_mutationPercentagePanel);
		
		JPanel crossPercentagePanel = new JPanel();
		crossPercentagePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		crossPercentagePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel crossLabelPercentage = new JLabel("Cross (%)");
		crossLabelPercentage.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		GroupLayout gl_crossPercentagePanel = new GroupLayout(crossPercentagePanel);
		gl_crossPercentagePanel.setHorizontalGroup(
			gl_crossPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGap(0, 320, Short.MAX_VALUE)
				.addGroup(gl_crossPercentagePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(crossLabelPercentage, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
					.addComponent(crossSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_crossPercentagePanel.setVerticalGroup(
			gl_crossPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 121, Short.MAX_VALUE)
				.addGap(0, 117, Short.MAX_VALUE)
				.addGap(0, 113, Short.MAX_VALUE)
				.addGroup(gl_crossPercentagePanel.createSequentialGroup()
					.addContainerGap(78, Short.MAX_VALUE)
					.addGroup(gl_crossPercentagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(crossSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(crossLabelPercentage, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		crossPercentagePanel.setLayout(gl_crossPercentagePanel);
		
		JPanel generationsPanel = new JPanel();
		generationsPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		generationsPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel generationsLabel = new JLabel("# Generations");
		generationsLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		spinnerNumGenerations = new JSpinner();
		spinnerNumGenerations.setModel(new SpinnerNumberModel(200, 10, 600, 1));
		GroupLayout gl_generationsPanel = new GroupLayout(generationsPanel);
		gl_generationsPanel.setHorizontalGroup(
			gl_generationsPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGap(0, 320, Short.MAX_VALUE)
				.addGroup(gl_generationsPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(generationsLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
					.addComponent(spinnerNumGenerations, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_generationsPanel.setVerticalGroup(
			gl_generationsPanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 121, Short.MAX_VALUE)
				.addGap(0, 117, Short.MAX_VALUE)
				.addGap(0, 113, Short.MAX_VALUE)
				.addGroup(gl_generationsPanel.createSequentialGroup()
					.addContainerGap(78, Short.MAX_VALUE)
					.addGroup(gl_generationsPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(generationsLabel)
						.addComponent(spinnerNumGenerations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		generationsPanel.setLayout(gl_generationsPanel);
		
		JPanel sizePanel = new JPanel();
		sizePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel sizeLabel = new JLabel("Size Population");
		sizeLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		spinnerSizePopulation = new JSpinner();
		spinnerSizePopulation.setModel(new SpinnerNumberModel(200, 2, 600, 1));
		GroupLayout gl_sizePanel = new GroupLayout(sizePanel);
		gl_sizePanel.setHorizontalGroup(
			gl_sizePanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGap(0, 320, Short.MAX_VALUE)
				.addGroup(gl_sizePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(sizeLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addComponent(spinnerSizePopulation, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_sizePanel.setVerticalGroup(
			gl_sizePanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 121, Short.MAX_VALUE)
				.addGap(0, 117, Short.MAX_VALUE)
				.addGap(0, 113, Short.MAX_VALUE)
				.addGroup(gl_sizePanel.createSequentialGroup()
					.addContainerGap(78, Short.MAX_VALUE)
					.addGroup(gl_sizePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(sizeLabel)
						.addComponent(spinnerSizePopulation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		sizePanel.setLayout(gl_sizePanel);
		
		JPanel functionPanel = new JPanel();
		functionPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		functionPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel functionLabel = new JLabel("Function Type");
		functionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		functionDropdown = new JComboBox();
		functionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		functionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Function Multiplexor"}));
				
		GroupLayout gl_functionPanel = new GroupLayout(functionPanel);
		gl_functionPanel.setHorizontalGroup(
			gl_functionPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(functionLabel)
					.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
					.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_functionPanel.setVerticalGroup(
			gl_functionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(functionLabel))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		functionPanel.setLayout(gl_functionPanel);
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		selectionPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel selectionLabel = new JLabel("Selection Type");
		selectionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		selectionDropdown = new JComboBox();
		selectionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		selectionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Ruleta", "Torneo Aleatorio", "Torneo Determinista", "Restos", "Truncamiento", "Estoc?stico Universal", "Ranking"}));
		selectionDropdown.setSelectedIndex(SelectionType.Stochastic.ordinal());
		selectionDropdown.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        int select = selectionDropdown.getSelectedIndex();
		        
		        //Cuarta funcion Michalewicz
		        if(select == 4) {
		        	truncDropdown.setEnabled(true);
		        	truncLabel.setEnabled(true);
		        	
		        }else {
		        	truncDropdown.setEnabled(false);
		        	truncLabel.setEnabled(false);
		        }
		        
		        if(select == 6) {
		        	betaSpinner.setEnabled(true);
		        	betaLabel_.setEnabled(true);
		        	
		        }else {
		        	betaSpinner.setEnabled(false);
		        	betaLabel_.setEnabled(false);
		        }
		        
		    }
		});
		
		truncLabel = new JLabel("Trunc Probability(%)");
		truncLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		truncLabel.setEnabled(false);
		
		truncDropdown = new JComboBox();
		truncDropdown.setModel(new DefaultComboBoxModel(new String[] {"10", "50"}));
		truncDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		truncDropdown.setEnabled(false);
		
		
		mutationSpinner = new JSpinner(modelMut);

		
        
        double minBeta = 1.0;
        double valueBeta = 1.5;
        double maxBeta = 2.0;
        double stepSizeBeta = 0.1;
        SpinnerNumberModel modelBeta = new SpinnerNumberModel(valueBeta, minBeta, maxBeta, stepSizeBeta);
		betaSpinner = new JSpinner(modelBeta);
		JSpinner.NumberEditor editorBeta = (JSpinner.NumberEditor) betaSpinner.getEditor();
        DecimalFormat formatBeta = editorBeta.getFormat();
        formatBeta.setMinimumFractionDigits(1);
        betaSpinner.setEnabled(false);
		
		betaLabel_ = new JLabel("Beta/Sampling  Factor");
		betaLabel_.setFont(new Font("Georgia", Font.PLAIN, 13));
		betaLabel_.setEnabled(false);
		GroupLayout gl_selectionPanel = new GroupLayout(selectionPanel);
		gl_selectionPanel.setHorizontalGroup(
			gl_selectionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_selectionPanel.createSequentialGroup()
							.addComponent(selectionLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
							.addComponent(selectionDropdown, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_selectionPanel.createSequentialGroup()
							.addGroup(gl_selectionPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(truncLabel, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
								.addComponent(betaLabel_, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
							.addGroup(gl_selectionPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(truncDropdown, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
								.addComponent(betaSpinner, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_selectionPanel.setVerticalGroup(
			gl_selectionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_selectionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectionLabel)
						.addComponent(selectionDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(truncLabel)
						.addComponent(truncDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(betaSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(betaLabel_, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		selectionPanel.setLayout(gl_selectionPanel);
		
		JPanel crossPanel = new JPanel();
		crossPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		crossPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel crossLabel = new JLabel("Cross Type");
		crossLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		crossDropdown = new JComboBox();
		crossDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		crossDropdown.setModel(new DefaultComboBoxModel(new String[] {"Tree"}));
		
		GroupLayout gl_crossPanel = new GroupLayout(crossPanel);
		gl_crossPanel.setHorizontalGroup(
			gl_crossPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGroup(gl_crossPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(crossLabel)
					.addGap(26)
					.addComponent(crossDropdown, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_crossPanel.setVerticalGroup(
			gl_crossPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 47, Short.MAX_VALUE)
				.addGap(0, 43, Short.MAX_VALUE)
				.addGroup(gl_crossPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_crossPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(crossLabel)
						.addComponent(crossDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		crossPanel.setLayout(gl_crossPanel);
		
		JPanel MutationPanel = new JPanel();
		MutationPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		MutationPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel mutationLabel = new JLabel("Mutation Type");
		mutationLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		mutationDropdown = new JComboBox();
		mutationDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		mutationDropdown.setModel(new DefaultComboBoxModel(new String[] {"Terminal", "Functional", "Permutation"}));
		
		GroupLayout gl_MutationPanel = new GroupLayout(MutationPanel);
		gl_MutationPanel.setHorizontalGroup(
			gl_MutationPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGroup(gl_MutationPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(mutationLabel)
					.addGap(18)
					.addComponent(mutationDropdown, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(27, Short.MAX_VALUE))
		);
		gl_MutationPanel.setVerticalGroup(
			gl_MutationPanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 48, Short.MAX_VALUE)
				.addGap(0, 44, Short.MAX_VALUE)
				.addGroup(gl_MutationPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_MutationPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(mutationDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(mutationLabel))
					.addContainerGap())
		);
		MutationPanel.setLayout(gl_MutationPanel);
		
		JPanel elitismPanel = new JPanel();
		elitismPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		elitismPanel.setBackground(Color.LIGHT_GRAY);
		
		elitismCheckBox = new JCheckBox("Elitism (%)");
		elitismCheckBox.setSelected(true);
		elitismCheckBox.setFont(new Font("Georgia", Font.PLAIN, 14));
		elitismCheckBox.setBackground(Color.LIGHT_GRAY);
		
		GroupLayout gl_elitismPanel = new GroupLayout(elitismPanel);
		gl_elitismPanel.setHorizontalGroup(
			gl_elitismPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGroup(gl_elitismPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(elitismCheckBox)
					.addPreferredGap(ComponentPlacement.RELATED, 131, Short.MAX_VALUE)
					.addComponent(elitismSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_elitismPanel.setVerticalGroup(
			gl_elitismPanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 42, Short.MAX_VALUE)
				.addGap(0, 38, Short.MAX_VALUE)
				.addGroup(gl_elitismPanel.createSequentialGroup()
					.addContainerGap(11, Short.MAX_VALUE)
					.addGroup(gl_elitismPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(elitismCheckBox)
						.addComponent(elitismSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		elitismPanel.setLayout(gl_elitismPanel);
		
		JLabel solutionLabel = new JLabel("Solution:");
		solutionLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		solutionList = new JComboBox();
		
		panelMathPlot = new Plot2DPanel();
		
		mutField = new JTextField();
		mutField.setEditable(false);
		mutField.setColumns(10);
		
		JLabel graphTitle = new JLabel("Graph Evolution");
		graphTitle.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JLabel lblNmut = new JLabel("nMut:");
		lblNmut.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JLabel lblNcross = new JLabel("nCross:");
		lblNcross.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		crosField = new JTextField();
		crosField.setEditable(false);
		crosField.setColumns(10);
		
		JPanel functionPanel_1 = new JPanel();
		functionPanel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		functionPanel_1.setBackground(Color.LIGHT_GRAY);
		
		JLabel creationType = new JLabel("Creation Type");
		creationType.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		creationTypeDropdown = new JComboBox();
		creationTypeDropdown.setModel(new DefaultComboBoxModel(new String[] {"Grow", "Full", "Ramped & Half"}));
		creationTypeDropdown.setToolTipText("");
		creationTypeDropdown.setSelectedIndex(CreationType.Grow.ordinal());
		creationTypeDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		GroupLayout gl_functionPanel_1 = new GroupLayout(functionPanel_1);
		gl_functionPanel_1.setHorizontalGroup(
			gl_functionPanel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_functionPanel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(creationType, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(creationTypeDropdown, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_functionPanel_1.setVerticalGroup(
			gl_functionPanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_functionPanel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_functionPanel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(creationType, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(creationTypeDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		functionPanel_1.setLayout(gl_functionPanel_1);
		
		JButton startButton = new JButton("Evolute");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {			
				try {
					processData();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}		
			}
		});
		startButton.setBackground(new Color(175, 238, 238));
		GroupLayout gl_evolTab = new GroupLayout(evolTab);
		gl_evolTab.setHorizontalGroup(
			gl_evolTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_evolTab.createSequentialGroup()
					.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_evolTab.createSequentialGroup()
							.addGap(127)
							.addComponent(parametersLabel)
							.addGap(149)
							.addComponent(solutionLabel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_evolTab.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_evolTab.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
									.addComponent(mutationPercentagePanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
									.addComponent(generationsPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
									.addComponent(crossPercentagePanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
									.addComponent(sizePanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
								.addComponent(functionPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(crossPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(MutationPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(elitismPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(functionPanel_1, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_evolTab.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_evolTab.createSequentialGroup()
										.addGap(103)
										.addComponent(solutionList, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNcross, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
										.addGap(10)
										.addComponent(crosField, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(lblNmut)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(mutField, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(graphTitle, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_evolTab.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panelMathPlot, GroupLayout.PREFERRED_SIZE, 686, GroupLayout.PREFERRED_SIZE)))
								.addGroup(Alignment.TRAILING, gl_evolTab.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addGap(279)))))
					.addGap(1385))
		);
		gl_evolTab.setVerticalGroup(
			gl_evolTab.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_evolTab.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
						.addComponent(parametersLabel)
						.addGroup(gl_evolTab.createParallelGroup(Alignment.BASELINE)
							.addComponent(solutionLabel, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addComponent(graphTitle, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addComponent(solutionList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(mutField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNmut, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_evolTab.createSequentialGroup()
							.addGap(3)
							.addComponent(crosField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNcross, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_evolTab.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_evolTab.createSequentialGroup()
							.addComponent(panelMathPlot, GroupLayout.PREFERRED_SIZE, 558, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(startButton))
						.addGroup(gl_evolTab.createSequentialGroup()
							.addComponent(sizePanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(generationsPanel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(mutationPercentagePanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crossPercentagePanel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(functionPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(functionPanel_1, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crossPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(MutationPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(elitismPanel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(45)))
					.addGap(577))
		);
		evolTab.setLayout(gl_evolTab);
		int tamCell = 20;
		
		contentPane.setLayout(gl_contentPane);		
	}
	
	private void processData() throws IOException {
		sizePop = (int)this.spinnerSizePopulation.getValue();
		numGenerations = (int)this.spinnerNumGenerations.getValue();
		crossProb = ((double) this.crossSpinner.getValue()) / 100.0;
		mutProb = ((double) this.mutationSpinner.getValue()) / 100.0;
		
		
		int truncProb = 0;
		if(truncDropdown.isEnabled()) {
			String val = truncDropdown.getSelectedItem().toString();
			truncProb = Integer.valueOf(val);
		}
				
		double betaValue = 0;
		if(betaSpinner.isEnabled()) {
			betaValue = (double)this.betaSpinner.getValue();
		}
		
		int paramFunc4 = 0;
		
		elitism = this.elitismCheckBox.isSelected(); 
		eliPercentage =	((double)this.elitismSpinner.getValue())/100.0;
		
		boolean typeFitness_minorTel = creationTypeDropdown.getSelectedIndex() == 0 ? true : false;
		
//		readEntry();
		getFunctionType();
		getSelectionType();
		getCrossType();
		getMutationType();
		gA = new GeneticAlgorithm(this);
		
		MultiplexType multiplexType;
		int index = entriesDropdown.getSelectedIndex();
		multiplexType = MultiplexType.values()[index];
		
		CreationType creationType_;
		index = creationTypeDropdown.getSelectedIndex();
		creationType_ = CreationType.values()[index];
		
		int maxDepth = (int)maxDepthSpinner.getValue();
		
		boolean useIF = this.IFCheckBox.isSelected();
		
		BloatingType bloatType;
		index = bloatingDropdown.getSelectedIndex();
		bloatType = BloatingType.values()[index];
		
		float factorBloatingTarpeian = (float) ((double) this.tarpeianSpinner.getValue());
		
		gA.Evolute(sizePop, numGenerations,crossProb, mutProb, precision ,
				f_type, s_type,c_type,m_type, elitism, eliPercentage, truncProb, multiplexType, creationType_, bloatType,factorBloatingTarpeian, maxDepth , useIF , betaValue);
	}
	
	public void showGraph(double[] bestAbs, double[]  best,double[] worstAbs, double[] avarage, double solutionBest, double solutionWorst, Chromosome sol, int numCrosses, int numMutations) {

		panelMathPlot.removeAllPlots();
		double [] x = new double[bestAbs.length];
		for(int i = 0; i < x.length; i++) x[i] = i+1;

		mutField.setEditable(true);
		mutField.setText(String.valueOf(numMutations));
		mutField.setEditable(false);
		
		crosField.setEditable(true);
		crosField.setText(String.valueOf(numCrosses));
		crosField.setEditable(false);
		
		solutionList.removeAllItems();
		
		solutionList.addItem("Best: " +  solutionBest);
		solutionList.addItem("Worst: " +  solutionWorst);
		
		String expression = sol.getSolParsed() + "\nFitness: " + sol.getFitness() + "\n"; 
		expressionField.setText(expression);
		System.out.print("========================================");
		System.out.print("\n" + expression + "\n");
		System.out.print("========================================");
		
		panelMathPlot.addLegend("SOUTH");
		panelMathPlot.addLinePlot("Best So Far", Color.BLUE, x, bestAbs);
		panelMathPlot.addLinePlot("Best Individual", Color.RED, x, best);
		panelMathPlot.addLinePlot("Worst So Far", Color.MAGENTA, x, worstAbs);
		panelMathPlot.addLinePlot("Avarage", Color.GREEN, x, avarage);
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
		int index = crossDropdown.getSelectedIndex();
		c_type = CrossType.values()[index];
	}
	
	private void getMutationType() {
		int index = mutationDropdown.getSelectedIndex();
		m_type = MutationType.values()[index];
	}
}
