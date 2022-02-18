import javax.swing.JFrame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import org.math.plot.*;

public class main {

	public static void main(String[] args) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setLayout(new GridLayout(1, false));

		graphGUI graph = new graphGUI(shell,SWT.NONE);

		shell.pack();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		display.dispose();
		
		
//		createInterface();
	}
	
	
	
	public static void createInterface() {
		// define your data
		double[] generaciones = { 1, 2, 3, 4, 5, 6, 7, 8, 9 ,10 };
		double[] fitness = { 12, 25, 32, 45, 65, 67 , 70, 72, 73, 76};
		
		System.out.println("Hoa");
		// create your PlotPanel (you can use it as a JPanel)
//		Plot2DPanel plot = new Plot2DPanel();
//		// define the legend position
//		plot.addLegend("SOUTH");
//		// add a line plot to the PlotPanel
//		plot.addLinePlot("EVOLUCIÓN", generaciones, fitness);
//		// put the PlotPanel in a JFrame like a JPanel
//		JFrame frame = new JFrame("a plot panel");
//		frame.setSize(600, 600);
//		frame.setContentPane(plot);
//		frame.setVisible(true);
	}

}
