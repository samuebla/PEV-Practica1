package functions;

import java.util.ArrayList;
import java.util.List;

public class Func2_Schubert extends Function {

	public Func2_Schubert(){
		interval = new ArrayList();
		interval.add(-10.0);
		interval.add(10.0);
		maximize = true;
	}
	
	@Override
	public double ejecutar(List<Double> x) {
		double total = 1;
		for(double y : x)
			total = total * sumatorio(y);
		
		return total;
	}
	
	private double sumatorio(double x) {
		double sumador = 0;
		for(int i = 1; i <= 5; ++i)
			sumador += i * Math.cos((i + 1) * x + i);
		return sumador;
	}
	
}
