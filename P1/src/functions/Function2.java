package functions;

import java.util.ArrayList;
import java.util.List;

public class Function2 extends Function {

	public Function2(){
		intervalo = new ArrayList();
		intervalo.add(-10.0);
		intervalo.add(10.0);
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
