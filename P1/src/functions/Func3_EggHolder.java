package functions;

import java.util.ArrayList;
import java.util.List;

public class Func3_EggHolder extends Function {
	
	public Func3_EggHolder(){
		interval = new ArrayList();
		interval.add(-512.0);
		interval.add(512.0);
		maximize = true;
	}
	
	@Override
	public double ejecutar(List<Double> x) {
		return -(x.get(1) + 47) * Math.sin(Math.sqrt(Math.abs(x.get(1) + (x.get(0)/2) + 47))) -
				x.get(0) * Math.sin(Math.sqrt(Math.abs(x.get(0) - (x.get(1) + 47))));
	}
}
