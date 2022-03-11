package mutations;

import java.util.List;

import individual.Chromosome;

public abstract class Mutation {

	protected List<Chromosome> mPoblation;
	
	public abstract void mutate(List<Chromosome> poblation, Object params);
	
	public List<Chromosome> getMutatedPop() { return mPoblation; }	
}
