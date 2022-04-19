package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import org.math.plot.Plot2DPanel;

import geneticAlgorithm.GeneticAlgorithm;
import genetics.FlightGen;
import genetics.Gen;
import utils.CrossType;
import utils.FunctionType;
import utils.MutationType;
import utils.SelectionType;
import utilsFlight.FlightType;
import utilsFlight.TVuelo;

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
	JSpinner betaSpinner;
	JLabel betaLabel_;
	JComboBox solutionList;
	JComboBox truncDropdown;
	JLabel truncLabel;
	JComboBox fitnessTypeDropdown;
	JComboBox caseDropdown;
	JCheckBox readFileCheckbox;
	
	private int sizePop; 
	private int numGenerations; 
	private double crossProb; 
	private double mutProb; 
	private double precision; 
	private boolean elitism;
	private double eliPercentage;
	private JTextField mutField;
	
	JTable flightTable;
	JTable telsTable;
	JTable separationsTable;
	
	List<TVuelo> TTEL_vuelos;
	List<ArrayList<Double>> separations;
	final int numPistas = 3;
	private JTable solTablePista1;
	private JTable solTablePista2;
	private JTable solTablePista3;
	private JTextField crosField;
	
	
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
		
		flightTable = new JTable() {
			
			@Override
			  	public boolean isCellEditable(int row, int column) {
			       //Only the third column
			       return row != 0;
			   }
		};
		flightTable.setFont(new Font("Georgia", Font.PLAIN, 11));
		flightTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10", "V11", "V12"},
				{"UA138", "UA532", "UA599", "NW358", "UA2987", "AA128", "UA1482", "NW357", "AA129", "UA2408", "UA805", "AA309"},
				{"W", "G", "W", "W", "P", "W", "G", "W", "W", "P", "W", "G"},
			},
			new String[] {
				"V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10", "V11", "V12"
			}
		));
		
		telsTable = new JTable();
		telsTable.setRowHeight(20);
		telsTable.setFont(new Font("Georgia", Font.PLAIN, 11));
		telsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"11", "15", "6", "6", "9", "7", "15", "6", "6", "9", "7", "9"},
				{"10", "17", "7", "7", "12", "6", "17", "7", "7", "12", "6", "7"},
				{"9", "19", "8", "8", "15", "5", "19", "8", "8", "15", "5", "5"},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		
		separationsTable = new JTable(){
			
			@Override
			  	public boolean isCellEditable(int row, int column) {
			       //Only the third column
					if(column == 0)
						return false;
					
					if(row == 0)
						return false;
					
					return true;
			   }
		};
		separationsTable.setRowHeight(30);
		separationsTable.setFont(new Font("Georgia", Font.PLAIN, 18));
		separationsTable.setModel(new DefaultTableModel(
			new Object[][] {
				{null, "W", "G", "P"},
				{"W", "1", "1.5", "2"},
				{"G", "1", "1.5", "1.5"},
				{"P", "1", "1", "1"},
			},
			new String[] {
				"New column", "New column", "New column", "New column"
			}
		) {
			Class[] columnTypes = new Class[] {
				String.class, Object.class, String.class, Object.class
			};
			public Class getColumnClass(int columnIndex) {
				return columnTypes[columnIndex];
			}
		});
		
		JPanel sizePanel_1 = new JPanel();
		sizePanel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizePanel_1.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblFlights = new JLabel("Flights");
		lblFlights.setFont(new Font("Georgia", Font.PLAIN, 18));
		GroupLayout gl_sizePanel_1 = new GroupLayout(sizePanel_1);
		gl_sizePanel_1.setHorizontalGroup(
			gl_sizePanel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_sizePanel_1.createSequentialGroup()
					.addContainerGap(129, Short.MAX_VALUE)
					.addComponent(lblFlights, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addGap(127))
		);
		gl_sizePanel_1.setVerticalGroup(
			gl_sizePanel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_sizePanel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblFlights)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		sizePanel_1.setLayout(gl_sizePanel_1);
		
		JPanel sizePanel_1_1 = new JPanel();
		sizePanel_1_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizePanel_1_1.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblTels = new JLabel("TELS");
		lblTels.setFont(new Font("Georgia", Font.PLAIN, 18));
		GroupLayout gl_sizePanel_1_1 = new GroupLayout(sizePanel_1_1);
		gl_sizePanel_1_1.setHorizontalGroup(
			gl_sizePanel_1_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_sizePanel_1_1.createSequentialGroup()
					.addContainerGap(129, Short.MAX_VALUE)
					.addComponent(lblTels, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addGap(127))
		);
		gl_sizePanel_1_1.setVerticalGroup(
			gl_sizePanel_1_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_sizePanel_1_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblTels)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		sizePanel_1_1.setLayout(gl_sizePanel_1_1);
		
		JPanel sizePanel_1_1_1 = new JPanel();
		sizePanel_1_1_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		sizePanel_1_1_1.setBackground(Color.LIGHT_GRAY);
		
		JLabel lblWeights = new JLabel("Weights Separations");
		lblWeights.setFont(new Font("Georgia", Font.PLAIN, 18));
		GroupLayout gl_sizePanel_1_1_1 = new GroupLayout(sizePanel_1_1_1);
		gl_sizePanel_1_1_1.setHorizontalGroup(
			gl_sizePanel_1_1_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_sizePanel_1_1_1.createSequentialGroup()
					.addContainerGap(81, Short.MAX_VALUE)
					.addComponent(lblWeights, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)
					.addGap(63))
		);
		gl_sizePanel_1_1_1.setVerticalGroup(
			gl_sizePanel_1_1_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_sizePanel_1_1_1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblWeights)
					.addContainerGap())
		);
		sizePanel_1_1_1.setLayout(gl_sizePanel_1_1_1);
		
		readFileCheckbox = new JCheckBox("Read From File");
		readFileCheckbox.setFont(new Font("Georgia", Font.BOLD, 12));
		
		readFileCheckbox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        int funct = selectionDropdown.getSelectedIndex();
		        if(readFileCheckbox.isSelected()) {
		        	caseDropdown.setEnabled(true);
		        	flightTable.setEnabled(false);
		        	separationsTable.setEnabled(false);
		        	telsTable.setEnabled(false);
		        }else {
		        	caseDropdown.setEnabled(false);
		        	flightTable.setEnabled(true);
		        	separationsTable.setEnabled(true);
		        	telsTable.setEnabled(true);
		        }
		    }
		});
		
		caseDropdown = new JComboBox();
		caseDropdown.setEnabled(false);
		caseDropdown.setModel(new DefaultComboBoxModel(new String[] {"Case1", "Case2"}));
		caseDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		GroupLayout gl_entryTab = new GroupLayout(entryTab);
		gl_entryTab.setHorizontalGroup(
			gl_entryTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(361)
					.addComponent(sizePanel_1_1, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1736, Short.MAX_VALUE))
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(370)
					.addComponent(sizePanel_1_1_1, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1727, Short.MAX_VALUE))
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(228)
					.addComponent(separationsTable, GroupLayout.PREFERRED_SIZE, 581, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1616, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_entryTab.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_entryTab.createParallelGroup(Alignment.TRAILING)
						.addComponent(flightTable, GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE)
						.addComponent(telsTable, GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE))
					.addGap(1376))
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(359)
					.addComponent(sizePanel_1, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1738, Short.MAX_VALUE))
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(461)
					.addComponent(readFileCheckbox)
					.addContainerGap(1867, Short.MAX_VALUE))
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(442)
					.addComponent(caseDropdown, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1824, Short.MAX_VALUE))
		);
		gl_entryTab.setVerticalGroup(
			gl_entryTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_entryTab.createSequentialGroup()
					.addContainerGap()
					.addComponent(readFileCheckbox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(caseDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addGap(45)
					.addComponent(sizePanel_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(flightTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(sizePanel_1_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(telsTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(62)
					.addComponent(sizePanel_1_1_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(separationsTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(613, Short.MAX_VALUE))
		);
		entryTab.setLayout(gl_entryTab);
		
		JPanel evolTab = new JPanel();
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
		spinnerNumGenerations.setModel(new SpinnerNumberModel(100, 10, 100, 1));
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
		spinnerSizePopulation.setModel(new SpinnerNumberModel(100, 10, 100, 1));
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
		
		fitnessTypeDropdown = new JComboBox();
		fitnessTypeDropdown.setModel(new DefaultComboBoxModel(new String[] {"Minor TEL", "TEL Assigned"}));
		fitnessTypeDropdown.setToolTipText("");
		fitnessTypeDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		JLabel fitnessType = new JLabel("Fitness Type");
		fitnessType.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		JLabel functionLabel = new JLabel("Function Type");
		functionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		functionDropdown = new JComboBox();
		functionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		functionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Function Practice 2"}));
				
		GroupLayout gl_functionPanel = new GroupLayout(functionPanel);
		gl_functionPanel.setHorizontalGroup(
			gl_functionPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_functionPanel.createSequentialGroup()
							.addComponent(fitnessType, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(fitnessTypeDropdown, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_functionPanel.createSequentialGroup()
							.addComponent(functionLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
							.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_functionPanel.setVerticalGroup(
			gl_functionPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(functionLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(fitnessType, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(fitnessTypeDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(34, Short.MAX_VALUE))
		);
		functionPanel.setLayout(gl_functionPanel);
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		selectionPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel selectionLabel = new JLabel("Selection Type");
		selectionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		selectionDropdown = new JComboBox();
		selectionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		selectionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Ruleta", "Torneo Aleatorio", "Torneo Determinista", "Restos", "Truncamiento", "Estocástico Universal", "Ranking"}));
		
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
		crossDropdown.setModel(new DefaultComboBoxModel(new String[] {"Orden OX","PMX","Orden OX Priority Positions", "Cyles CX", "Ordinal Encoding"}));
		
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
		mutationDropdown.setModel(new DefaultComboBoxModel(new String[] {"Insertion", "Exchange", "Inversion", "Heuristics"}));
		
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
		
		JButton startButton = new JButton("Evolute");
		startButton.setBackground(new Color(175, 238, 238));
		
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
								.addComponent(elitismPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_evolTab.createSequentialGroup()
									.addGap(310)
									.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_evolTab.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_evolTab.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_evolTab.createSequentialGroup()
											.addGap(97)
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
										.addComponent(panelMathPlot, GroupLayout.PREFERRED_SIZE, 686, GroupLayout.PREFERRED_SIZE))))))
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
						.addGroup(gl_evolTab.createSequentialGroup()
							.addComponent(panelMathPlot, GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(startButton)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_evolTab.createSequentialGroup()
							.addComponent(sizePanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(generationsPanel, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(mutationPercentagePanel, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crossPercentagePanel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(functionPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crossPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(MutationPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(elitismPanel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(12)))
					.addGap(577))
		);
		evolTab.setLayout(gl_evolTab);
		
		JPanel solTab = new JPanel();
		tabbedPane.addTab("Solution", null, solTab, null);
		int tamCell = 20;
		solTablePista1 = new JTable();
		solTablePista1.setModel(new DefaultTableModel(
			new Object[][] {
				{"N\u00FAm Pista", "1", null},
				{"Vuelo", "Nombre", "TLA"},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		solTablePista1.setRowHeight(tamCell);
		solTablePista1.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		solTablePista2 = new JTable();
		solTablePista2.setModel(new DefaultTableModel(
			new Object[][] {
				{"N\u00FAm Pista", "2", null},
				{"Vuelo", "Nombre", "TLA"},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		solTablePista2.setRowHeight(tamCell);
		solTablePista2.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		solTablePista3 = new JTable();
		solTablePista3.setModel(new DefaultTableModel(
			new Object[][] {
				{"N\u00FAm Pista", "3", null},
				{"Vuelo", "Nombre", "TLA"},
			},
			new String[] {
				"New column", "New column", "New column"
			}
		) {
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		solTablePista3.setRowHeight(tamCell);
		solTablePista3.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JButton startButton_1 = new JButton("Evolute");
		startButton_1.setBackground(new Color(175, 238, 238));
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
		
		GroupLayout gl_solTab = new GroupLayout(solTab);
		gl_solTab.setHorizontalGroup(
			gl_solTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_solTab.createSequentialGroup()
					.addGroup(gl_solTab.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_solTab.createSequentialGroup()
							.addGap(22)
							.addComponent(solTablePista1, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(solTablePista2, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
							.addGap(30)
							.addComponent(solTablePista3, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_solTab.createSequentialGroup()
							.addGap(508)
							.addComponent(startButton_1, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(1405, Short.MAX_VALUE))
		);
		gl_solTab.setVerticalGroup(
			gl_solTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_solTab.createSequentialGroup()
					.addGap(4)
					.addComponent(startButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_solTab.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(solTablePista3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(solTablePista1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(solTablePista2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(1128, Short.MAX_VALUE))
		);
		solTab.setLayout(gl_solTab);
		
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
		
		boolean typeFitness_minorTel = fitnessTypeDropdown.getSelectedIndex() == 0 ? true : false;
		
		readEntry();
		
		getFunctionType();
		getSelectionType();
		getCrossType();
		getMutationType();
		gA = new GeneticAlgorithm(this);
		
		gA.Evolute(sizePop, numGenerations,crossProb, mutProb, precision ,
				   f_type, s_type,c_type,m_type, elitism, eliPercentage, truncProb, TTEL_vuelos, separations, numPistas, typeFitness_minorTel, betaValue);
	}
	
	private void readEntry() throws IOException {
		if(readFileCheckbox.isSelected()) {
			String file = caseDropdown.getSelectedItem().toString();
			BufferedReader br = new BufferedReader(new FileReader(file + ".txt"));
			try {
				int numFlights = Integer.parseInt(br.readLine());
				TTEL_vuelos = new ArrayList<TVuelo>();
				for(int i = 0; i < numFlights ; i++) {
					int j = 1;
					TVuelo vuelo = new TVuelo();
					String data = br.readLine();
					String[] tokens = data.split(" ");
					vuelo.name_ = tokens[0];
					vuelo.type_ = FlightType.valueOf(tokens[1]);
					vuelo.TTEL_vuelo = new ArrayList<Double>();
					int tok = 2;
					for(int k = 0; k < numPistas; k++) {
						double tel = Double.parseDouble(tokens[tok++]);
						vuelo.TTEL_vuelo.add(tel);
					}
					TTEL_vuelos.add(vuelo);
				}
				
			} finally {
			    br.close();
			}
		}else {
			readFlightsNTels();
		}
		readSeparations();
	}
	
	private void readFlightsNTels() {
		int numRows = flightTable.getRowCount();
		int numCols = flightTable.getColumnCount();
		
		TTEL_vuelos = new ArrayList<TVuelo>();
		for(int i = 0; i < numCols ; i++) {
			int j = 1;
			TVuelo vuelo = new TVuelo();
			vuelo.name_ = flightTable.getModel().getValueAt(j++ ,i).toString();
			vuelo.type_ = FlightType.valueOf(flightTable.getModel().getValueAt(j, i).toString());
			vuelo.TTEL_vuelo = new ArrayList<Double>();
			for(int k = 0; k < numPistas; k++) {
				double tel = Double.parseDouble(telsTable.getModel().getValueAt(k , i).toString());
				vuelo.TTEL_vuelo.add(tel);
			}
			TTEL_vuelos.add(vuelo);
		}
	}
	
	private void readSeparations() {
		int numRows = separationsTable.getRowCount();
		int numCols = separationsTable.getColumnCount();
		
		separations = new ArrayList<ArrayList<Double>>();
		
		for(int i = 1; i < numRows; i++) {
			ArrayList<Double>  sep = new ArrayList<Double>();
			for(int j = 1; j < numCols; j++) {
				sep.add(Double.parseDouble(separationsTable.getModel().getValueAt(i, j).toString()));
			}
			separations.add(sep);
		}
	}
	
	public void showGraph(double[] bestAbs, double[]  best,double[] worstAbs, double[]  worst, double[] avarage, double solutionBest, double solutionWorst, List<Gen> sol, int numCrosses, int numMutations) {

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
		
		int i = 0;
		String solu = "";
		for(Gen d : sol) {
			FlightGen vuelo = (FlightGen) d;
			solu += (vuelo.pos_vuelo + 1) + " ";	
		}
		System.out.println("==========================");
		System.out.println("");
		System.out.println("");
		System.out.println("Sol : {" + solu + "}");
		System.out.println("");
		System.out.println("");
		System.out.println("==========================");
		
		panelMathPlot.addLegend("SOUTH");
		panelMathPlot.addLinePlot("Best So Far", Color.BLUE, x, bestAbs);
		panelMathPlot.addLinePlot("Best Individual", Color.RED, x, best);
		panelMathPlot.addLinePlot("Worst Individual", Color.ORANGE, x, worst);
		panelMathPlot.addLinePlot("Worst So Far", Color.MAGENTA, x, worstAbs);
		panelMathPlot.addLinePlot("Avarage", Color.GREEN, x, avarage);
		
		
		List<FlightGen> vuelos = new ArrayList<>();
		for (Gen g : sol) vuelos.add(new FlightGen((FlightGen) g));
		MostrarTabla(vuelos);
	}
	
	private void MostrarTabla(List<FlightGen> vuelos) {
		//Los vuelos vienen ordenados de menor a mayor TLA
		DefaultTableModel model_ = (DefaultTableModel) solTablePista1.getModel();
		int rowCount = model_.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 2; i--) {
			model_.removeRow(i);
		}
		
		model_ = (DefaultTableModel) solTablePista2.getModel();
		rowCount = model_.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 2; i--) {
			model_.removeRow(i);
		}
		
		model_ = (DefaultTableModel) solTablePista3.getModel();
		rowCount = model_.getRowCount();
		//Remove rows one by one from the end of the table
		for (int i = rowCount - 1; i >= 2; i--) {
			model_.removeRow(i);
		}
		
		//Los vuelos se ordenan de menor a mayor TLA
		   Collections.sort(vuelos, new Comparator<FlightGen>() {
				//Para quitar los warning
				@SuppressWarnings("removal")
				@Override
			    public int compare(FlightGen c1, FlightGen c2) {
			        return new Double(c1.TLA).compareTo(new Double(c2.TLA));
			    }
			});
		   
		   for(FlightGen g : vuelos) {
			   
			   switch(g.pistaAsignada) {
			   		case 0:{
			   			DefaultTableModel model = (DefaultTableModel) solTablePista1.getModel();
			   			model.addRow(new Object[]{ (g.pos_vuelo + 1), TTEL_vuelos.get(g.pos_vuelo).name_, g.TLA});
			   		}
			   		break;
			   		case 1:{
			   			DefaultTableModel model = (DefaultTableModel) solTablePista2.getModel();
			   			model.addRow(new Object[]{ (g.pos_vuelo + 1) , TTEL_vuelos.get(g.pos_vuelo).name_, g.TLA});
			   		}
			   		break;
			   		case 2:{
			   			DefaultTableModel model = (DefaultTableModel) solTablePista3.getModel();
			   			model.addRow(new Object[]{ (g.pos_vuelo + 1) , TTEL_vuelos.get(g.pos_vuelo).name_, g.TLA});
			   		}
			   		break;
			   };
		   }
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
