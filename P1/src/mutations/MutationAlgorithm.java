package mutations;

import java.util.List;

import individual.Chromosome;

public abstract class MutationAlgorithm {

	protected List<Chromosome> mPoblation;
	
	public abstract void mutar(List<Chromosome> poblation, List<Double> params);
	
	public List<Chromosome> getPobMutada() { return mPoblation; }	
}
