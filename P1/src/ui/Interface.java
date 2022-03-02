package ui;
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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.math.plot.Plot2DPanel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class Interface extends JFrame {

	private JPanel contentPane;
	private Plot2DPanel panelMathPlot;
	private JLabel parametersLabel;

	/**
	 * Create the frame.
	 */
	public Interface() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1080, 720);
		contentPane = new JPanel();

		String title = "Practica 1 - Programacion Evolutiva";
		contentPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		setContentPane(contentPane);
		this.setTitle(title);
		this.setResizable(false);
		double[] x = { 1, 2, 3, 4, 5, 6 };
		double[] y = { 45, 89, 6, 32, 63, 12 };
		panelMathPlot = new Plot2DPanel();
		panelMathPlot.addLegend("SOUTH");
		panelMathPlot.addLinePlot("Best", x, y);
		
		JButton startButton = new JButton("Evolute");
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JLabel graphTitle = new JLabel("Graph Evolution");
		graphTitle.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		parametersLabel = new JLabel("Parameters");
		parametersLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		
		JCheckBox elitismCheckBox = new JCheckBox("Elitism");
		
		JPanel Selection = new JPanel();
		Selection.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		Selection.setBackground(new Color(192, 192, 192));
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(22)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(80)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(startButton)
								.addComponent(elitismCheckBox)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(83)
							.addComponent(parametersLabel))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Selection, GroupLayout.PREFERRED_SIZE, 265, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addComponent(panelMathPlot, GroupLayout.PREFERRED_SIZE, 770, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(596, Short.MAX_VALUE)
					.addComponent(graphTitle)
					.addGap(325))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(graphTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(parametersLabel)
							.addGap(354)
							.addComponent(Selection, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
							.addComponent(elitismCheckBox)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(startButton))
						.addComponent(panelMathPlot, GroupLayout.DEFAULT_SIZE, 640, Short.MAX_VALUE))
					.addContainerGap())
		);
		JComboBox selectionDropdown = new JComboBox();
		selectionDropdown.setFont(new Font("Georgia", Font.PLAIN, 13));
		selectionDropdown.setModel(new DefaultComboBoxModel(new String[] {"Ruleta", "Torneo Aleatorio", "Torneo Determinista", "Ranking"}));
		
		JLabel selectionLabel = new JLabel("Selection Type");
		selectionLabel.setFont(new Font("Georgia", Font.PLAIN, 13));
		GroupLayout gl_Selection = new GroupLayout(Selection);
		gl_Selection.setHorizontalGroup(
			gl_Selection.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Selection.createSequentialGroup()
					.addContainerGap()
					.addComponent(selectionLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(selectionDropdown, 0, 140, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_Selection.setVerticalGroup(
			gl_Selection.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_Selection.createSequentialGroup()
					.addContainerGap(51, Short.MAX_VALUE)
					.addGroup(gl_Selection.createParallelGroup(Alignment.BASELINE)
						.addComponent(selectionDropdown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(selectionLabel))
					.addContainerGap())
		);
		Selection.setLayout(gl_Selection);
		
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
	
	private void addParameters() {
		
	}
	
	private void addGraph() {
		
	}
}
