package geneticAlgorithm;
import utils.*;

public class GeneticAlgorithm {
	
	private FunctionType type_;
	private boolean elitism_;
	
	
	public GeneticAlgorithm() {
		
		
	}
	
	public void Evolute(FunctionType type, int numGenerations, boolean elitism, double eliPercentage) {
		type_ = type;
		elitism_ = elitism;
		
		
		for(int i = 0; i < numGenerations; i++) {
			
		}
	}
	
	private void Select() {
		
	}
	
	private void Reproduce() {
		
	}
	
	private void Mutate() {
		
	}
	
	private void insertElite() {
		
	}
	
	private void Evaluate() {
		
	}
}
