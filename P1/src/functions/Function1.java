package functions;

import java.util.ArrayList;
import java.util.List;


//Con esta linea nos elimina los warnings de todo el .java
//@SuppressWarnings("unchecked")

public class Function1 extends Function {

	public Function1() {
		interval = new ArrayList();
		//Creo que esto está descolocado
		interval.add(-3.0);
		interval.add(12.1);
		interval.add(4.1);
		interval.add(5.8);	
		maximize = true;
	}
		
	@Override
	public double ejecutar(List<Double> x) {
		Double x1 = x.get(0);
		Double x2 = x.get(1);
		//No se si se llegan a borrar los que usa anteriormente
		return 21.5 + (x1 * Math.sin(4 * Math.PI * x1)) +
                (x2 * Math.sin(20 * Math.PI * x2));
	}

}
