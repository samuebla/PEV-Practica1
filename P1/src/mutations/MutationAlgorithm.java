package mutations;

import java.util.List;

import individual.Individual;

public abstract class MutationAlgorithm {

	protected List<Individual> mPoblation;
	
	public abstract void mutar(List<Individual> poblation, List<Double> params);
	
	public List<Individual> getPobMutada() { return mPoblation; }	
}
