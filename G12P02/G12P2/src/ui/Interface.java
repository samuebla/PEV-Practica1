package ui;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.math.plot.Plot2DPanel;

import geneticAlgorithm.GeneticAlgorithm;
import utils.CrossType;
import utils.FunctionType;
import utils.MutationType;
import utils.SelectionType;
import utils.TVuelo;

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
	JComboBox solutionList;
	JSpinner nIndividualsFunct4Param;
	JComboBox truncDropdown;
	JLabel truncLabel;
	JComboBox typeGenDropdown;
	
	private int sizePop; 
	private int numGenerations; 
	private double crossProb; 
	private double mutProb; 
	private double precision; 
	private boolean elitism;
	private double eliPercentage;
	private JTextField maxAbsSol;
	
	JTable flightTable;
	JTable telsTable;
	JTable weightsTable;
	
	List<TVuelo> TTEL_vuelo;
	List<List<Double>> separations;
	private JTable solTable;
	
	
	/**
	 * Create the frame.
	 */
	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();

		String title = "Practica 1 - Programacion Evolutiva           |            Jose Daniel Rave Robayo - Samuel Bl�zquez";
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
		precisionSpinner = new JSpinner(modelPreci);
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
		
		weightsTable = new JTable(){
			
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
		weightsTable.setRowHeight(30);
		weightsTable.setFont(new Font("Georgia", Font.PLAIN, 18));
		weightsTable.setModel(new DefaultTableModel(
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
		
		JLabel lblWeights = new JLabel("Weights");
		lblWeights.setFont(new Font("Georgia", Font.PLAIN, 18));
		GroupLayout gl_sizePanel_1_1_1 = new GroupLayout(sizePanel_1_1_1);
		gl_sizePanel_1_1_1.setHorizontalGroup(
			gl_sizePanel_1_1_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_sizePanel_1_1_1.createSequentialGroup()
					.addGap(124)
					.addComponent(lblWeights, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(132, Short.MAX_VALUE))
		);
		gl_sizePanel_1_1_1.setVerticalGroup(
			gl_sizePanel_1_1_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_sizePanel_1_1_1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblWeights)
					.addContainerGap())
		);
		sizePanel_1_1_1.setLayout(gl_sizePanel_1_1_1);
		GroupLayout gl_entryTab = new GroupLayout(entryTab);
		gl_entryTab.setHorizontalGroup(
			gl_entryTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGroup(gl_entryTab.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_entryTab.createSequentialGroup()
							.addContainerGap()
							.addComponent(telsTable, GroupLayout.DEFAULT_SIZE, 1039, Short.MAX_VALUE))
						.addComponent(flightTable, GroupLayout.DEFAULT_SIZE, 1049, Short.MAX_VALUE))
					.addGap(1376))
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(350)
					.addComponent(sizePanel_1, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1747, Short.MAX_VALUE))
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
					.addComponent(weightsTable, GroupLayout.PREFERRED_SIZE, 581, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1616, Short.MAX_VALUE))
		);
		gl_entryTab.setVerticalGroup(
			gl_entryTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_entryTab.createSequentialGroup()
					.addGap(20)
					.addComponent(sizePanel_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(flightTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(87)
					.addComponent(sizePanel_1_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(telsTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(62)
					.addComponent(sizePanel_1_1_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(weightsTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
		
		JPanel precisionPercentagePanel = new JPanel();
		precisionPercentagePanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		precisionPercentagePanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel precisionLabelPercentage = new JLabel("Precision (%)");
		precisionLabelPercentage.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		GroupLayout gl_precisionPercentagePanel = new GroupLayout(precisionPercentagePanel);
		gl_precisionPercentagePanel.setHorizontalGroup(
			gl_precisionPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGap(0, 320, Short.MAX_VALUE)
				.addGroup(gl_precisionPercentagePanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(precisionLabelPercentage, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
					.addComponent(precisionSpinner, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_precisionPercentagePanel.setVerticalGroup(
			gl_precisionPercentagePanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 121, Short.MAX_VALUE)
				.addGap(0, 117, Short.MAX_VALUE)
				.addGap(0, 113, Short.MAX_VALUE)
				.addGroup(gl_precisionPercentagePanel.createSequentialGroup()
					.addContainerGap(78, Short.MAX_VALUE)
					.addGroup(gl_precisionPercentagePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(precisionSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(precisionLabelPercentage, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		precisionPercentagePanel.setLayout(gl_precisionPercentagePanel);
		
		JPanel functionPanel = new JPanel();
		functionPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		functionPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel genTypeFunct4_1 = new JLabel("Gen Type");
		genTypeFunct4_1.setFont(new Font("Georgia", Font.PLAIN, 13));
		genTypeFunct4_1.setEnabled(false);
		
		typeGenDropdown = new JComboBox();
		typeGenDropdown.setModel(new DefaultComboBoxModel(new String[] {"Real", "Binary"}));
		typeGenDropdown.setToolTipText("");
		typeGenDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		typeGenDropdown.setEnabled(false);
		
		JLabel nIndividualsFunct4 = new JLabel("N\u00BA Individuals");
		nIndividualsFunct4.setFont(new Font("Georgia", Font.PLAIN, 13));
		nIndividualsFunct4.setEnabled(false);
		
		nIndividualsFunct4Param = new JSpinner(new SpinnerNumberModel(2, 2, 100, 1));
		nIndividualsFunct4Param.setEnabled(false);
		
		JLabel functionLabel = new JLabel("Function Type");
		functionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		functionDropdown = new JComboBox();
		functionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		functionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Function 1", "Function 2 Schubert", "Function 3 EggHolder", "Function 4 Michalewicz"}));
		
		functionDropdown.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        int funct = functionDropdown.getSelectedIndex();
		        
		        //Cuarta funcion Michalewicz
		        if(funct == 3) {
		        	nIndividualsFunct4.setEnabled(true);
		        	genTypeFunct4_1.setEnabled(true);
		        	typeGenDropdown.setEnabled(true);
		        	nIndividualsFunct4Param.setEnabled(true);
		        	crossDropdown.setModel(new DefaultComboBoxModel(new String[] {"Monopoint","Uniform", "BLX", "Arithmetic"}));
		        }else {
		        	
		        	nIndividualsFunct4.setEnabled(false);
		        	genTypeFunct4_1.setEnabled(false);
		        	typeGenDropdown.setEnabled(false);
		        	nIndividualsFunct4Param.setEnabled(false);
		        	crossDropdown.setModel(new DefaultComboBoxModel(new String[] {"Monopoint","Uniform"}));
		        }
		    }
		});
		
		GroupLayout gl_functionPanel = new GroupLayout(functionPanel);
		gl_functionPanel.setHorizontalGroup(
			gl_functionPanel.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGroup(gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_functionPanel.createSequentialGroup()
							.addComponent(genTypeFunct4_1, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
							.addComponent(typeGenDropdown, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_functionPanel.createSequentialGroup()
							.addComponent(nIndividualsFunct4, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
							.addGap(72)
							.addComponent(nIndividualsFunct4Param, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_functionPanel.createSequentialGroup()
							.addComponent(functionLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
							.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_functionPanel.setVerticalGroup(
			gl_functionPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 121, Short.MAX_VALUE)
				.addGap(0, 117, Short.MAX_VALUE)
				.addGroup(gl_functionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(functionDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(functionLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(nIndividualsFunct4, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(nIndividualsFunct4Param, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_functionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(typeGenDropdown, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(genTypeFunct4_1, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		functionPanel.setLayout(gl_functionPanel);
		
		JPanel selectionPanel = new JPanel();
		selectionPanel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		selectionPanel.setBackground(Color.LIGHT_GRAY);
		
		JLabel selectionLabel = new JLabel("Selection Type");
		selectionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		
		selectionDropdown = new JComboBox();
		selectionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		selectionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Ruleta", "Torneo Aleatorio", "Torneo Determinista", "Restos", "Truncamiento", "Estoc�stico Universal"}));
		
		selectionDropdown.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        int funct = selectionDropdown.getSelectedIndex();
		        
		        //Cuarta funcion Michalewicz
		        if(funct == 4) {
		        	truncDropdown.setEnabled(true);
		        	truncLabel.setEnabled(true);
		        	
		        }else {
		        	truncDropdown.setEnabled(false);
		        	truncLabel.setEnabled(false);
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
		GroupLayout gl_selectionPanel = new GroupLayout(selectionPanel);
		gl_selectionPanel.setHorizontalGroup(
			gl_selectionPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 328, Short.MAX_VALUE)
				.addGap(0, 324, Short.MAX_VALUE)
				.addGroup(gl_selectionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_selectionPanel.createSequentialGroup()
							.addComponent(selectionLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
							.addComponent(selectionDropdown, GroupLayout.PREFERRED_SIZE, 178, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_selectionPanel.createSequentialGroup()
							.addComponent(truncLabel, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(truncDropdown, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_selectionPanel.setVerticalGroup(
			gl_selectionPanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 82, Short.MAX_VALUE)
				.addGap(0, 78, Short.MAX_VALUE)
				.addGroup(gl_selectionPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectionLabel)
						.addComponent(selectionDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_selectionPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(truncDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(truncLabel))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		selectionPanel.setLayout(gl_selectionPanel);
		
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
		mutationDropdown.setModel(new DefaultComboBoxModel(new String[] {"Basic", "Basic_Double"}));
		
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
				processData();		
			}
		});
		
		JLabel solutionLabel = new JLabel("Solution:");
		solutionLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		solutionList = new JComboBox();
		
		panelMathPlot = new Plot2DPanel();
		
		maxAbsSol = new JTextField();
		maxAbsSol.setEditable(false);
		maxAbsSol.setColumns(10);
		
		JLabel graphTitle = new JLabel("Graph Evolution");
		graphTitle.setFont(new Font("Georgia", Font.PLAIN, 18));
		GroupLayout gl_evolTab = new GroupLayout(evolTab);
		gl_evolTab.setHorizontalGroup(
			gl_evolTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_evolTab.createSequentialGroup()
					.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_evolTab.createSequentialGroup()
							.addGap(127)
							.addComponent(parametersLabel)
							.addGap(149)
							.addComponent(solutionLabel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(maxAbsSol, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_evolTab.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
								.addComponent(elitismPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(MutationPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(crossPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(mutationPercentagePanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(generationsPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(crossPercentagePanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(sizePanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(precisionPercentagePanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
								.addComponent(functionPanel, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_evolTab.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_evolTab.createSequentialGroup()
									.addGap(310)
									.addComponent(startButton, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_evolTab.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_evolTab.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_evolTab.createSequentialGroup()
											.addComponent(solutionList, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
											.addGap(58)
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
							.addComponent(maxAbsSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(graphTitle, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addComponent(solutionList, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(3)
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
							.addComponent(precisionPercentagePanel, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(functionPanel, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(selectionPanel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(crossPanel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(MutationPanel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(elitismPanel, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)))
					.addGap(577))
		);
		evolTab.setLayout(gl_evolTab);
		
		JPanel solTab = new JPanel();
		tabbedPane.addTab("Solution", null, solTab, null);
		
		solTable = new JTable();
		solTable.setModel(new DefaultTableModel(
			new Object[][] {
				{"N\u00FAmero Pista", "1", null},
				{"Vuelo", "Nombre", "TLA"},
				{"", "", ""},
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
		solTable.setRowHeight(30);
		solTable.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		GroupLayout gl_solTab = new GroupLayout(solTab);
		gl_solTab.setHorizontalGroup(
			gl_solTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_solTab.createSequentialGroup()
					.addGap(28)
					.addComponent(solTable, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(2043, Short.MAX_VALUE))
		);
		gl_solTab.setVerticalGroup(
			gl_solTab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_solTab.createSequentialGroup()
					.addGap(33)
					.addComponent(solTable, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(1134, Short.MAX_VALUE))
		);
		solTab.setLayout(gl_solTab);
		
		contentPane.setLayout(gl_contentPane);		
	}
	
	private void processData() {
		sizePop = (int)this.spinnerSizePopulation.getValue();
		numGenerations = (int)this.spinnerNumGenerations.getValue();
		crossProb = ((double) this.crossSpinner.getValue()) / 100.0;
		mutProb = ((double) this.mutationSpinner.getValue()) / 100.0;
		precision = (double) this.precisionSpinner.getValue();
		
		boolean isBinFuct4 = false;
		if(typeGenDropdown.isEnabled()) {
			isBinFuct4 = typeGenDropdown.getSelectedIndex() == 1 ? true : false;
		}
		
		int truncProb = 0;
		if(truncDropdown.isEnabled()) {
			String val = truncDropdown.getSelectedItem().toString();
			truncProb = Integer.valueOf(val);
		}
				
		int paramFunc4 = 0;
		if(nIndividualsFunct4Param.isEnabled()) {
			paramFunc4 = Integer.valueOf (nIndividualsFunct4Param.getValue().toString());
		}
		elitism = this.elitismCheckBox.isSelected(); 
		eliPercentage =	((double)this.elitismSpinner.getValue())/100.0;
		
		getFunctionType();
		getSelectionType();
		getCrossType();
		getMutationType();
		gA = new GeneticAlgorithm(this);
		
		gA.Evolute(sizePop, numGenerations,crossProb, mutProb, precision ,
				   f_type, s_type,c_type,m_type, elitism, eliPercentage, paramFunc4, truncProb, isBinFuct4);
	}
	
	public void showGraph(double[] bestAbs, double[]  best, double[] avarage, double solution, List<Double> sol) {

		panelMathPlot.removeAllPlots();
		double [] x = new double[bestAbs.length];
		for(int i = 0; i < x.length; i++) x[i] = i+1;

		maxAbsSol.setEditable(true);
		maxAbsSol.setText(String.valueOf(solution));
		solutionList.removeAllItems();
		maxAbsSol.setEditable(false);
		
		int i = 0;
		for(Double d : sol) {
			i++;
			d = Math.floor(d / 0.0001) * 0.0001;

			String text = "X" + i + ": " + d;
			solutionList.addItem(text);
		}

		panelMathPlot.addLegend("SOUTH");
		panelMathPlot.addLinePlot("Best So Far", Color.BLUE, x, bestAbs);
		panelMathPlot.addLinePlot("Best Individual", Color.RED, x, best);
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