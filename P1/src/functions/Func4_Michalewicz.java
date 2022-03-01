package functions;

import java.util.ArrayList;
import java.util.List;

public class Func4_Michalewicz extends Function {

	public Func4_Michalewicz(){
		intervalo = new ArrayList();
		intervalo.add(0.0);
		intervalo.add(Math.PI);
	}
	
	@Override
	public double ejecutar(List<Double> x) {
		double solucion = 0;
		
		for(int i = 0; i < x.size(); i++) 
			solucion += Math.sin(x.get(i)) * Math.sin(Math.pow(((i + 1) * Math.pow(x.get(i), 2))/Math.PI,20));
		
		return solucion;
	}
}
