package functions;

import java.util.ArrayList;
import java.util.List;


//Con esta linea nos elimina los warnings de todo el .java
//@SuppressWarnings("unchecked")

public class Function1 extends Function {

	public Function1() {
		intervalo = new ArrayList();
		//Creo que esto está descolocado
		intervalo.add(-3.0);
		intervalo.add(12.1);
		intervalo.add(4.1);
		intervalo.add(5.8);		
	}
		
	@Override
	public double ejecutar(List<Double> x) {
		//No se si se llegan a borrar los que usa anteriormente
		
		
		// TODO Auto-generated method stub
		return 21.5 + (x.get(0) * Math.sin(4 * Math.PI * x.get(0))) +
                (x.get(1) * Math.sin(20 * Math.PI * x.get(1)));
	}

}
