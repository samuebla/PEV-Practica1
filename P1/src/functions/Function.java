package functions;

import java.util.List;

public abstract class Function {

	protected List<Double> interval;
	
	public abstract double ejecutar(List<Double> x);
	
	public List<Double> getInterval() { return this.interval; }
}
