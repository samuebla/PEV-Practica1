package functions;

import java.util.List;

import genetics.Gen;

public abstract class Function {

	protected List<Double> interval;
	public boolean maximize;
	
	public abstract double ejecutar(List<Gen> x);
	
	public List<Double> getInterval() { return this.interval; }
}
