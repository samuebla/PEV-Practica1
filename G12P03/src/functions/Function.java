package functions;

import java.util.List;

import individual.Chromosome;

public abstract class Function {

	protected List<Double> interval;
	public boolean maximize;
	
	public abstract double ejecutar(Chromosome x);
	
	public List<Double> getInterval() { return this.interval; }
}
