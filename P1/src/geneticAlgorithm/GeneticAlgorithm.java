package geneticAlgorithm;
import functions.*;

import utils.FunctionType;

public class GeneticAlgorithm {
	
	private FunctionType type_;
	private boolean elitism_;
	Function funct;
	
	public GeneticAlgorithm() {
		
		
	}
	
	public void Evolute(FunctionType type, int numGenerations, boolean elitism, double eliPercentage) {
		type_ = type;
		elitism_ = elitism;
		
		InitPopulation();
		
		Evaluate();
		for(int i = 0; i < numGenerations; i++) {
			if(elitism_)
				extractElite();
			Select();
			Reproduce();
			Mutate();
			if(elitism_)
				insertElite();
			Evaluate();
		}
	}
	
	private Function selectFunction() {
		Function f = new Function1(); //Compilation Purposes

		switch (type_) {
			case f1 :
				f = new Function1();
				break;
			case f2_Schubert:
				f = new Func2_Schubert();
				break;
			case f3_EggHolder:
				f = new Func3_EggHolder();
				break;
			case f4_Michalewicz:
				f = new Func4_Michalewicz();
				break;
		}
		return f;
	}
	
	private void InitPopulation() {
		
	}
	
	private void Select() {
		
	}
	
	private void Reproduce() {
		
	}
	
	private void Mutate() {
		
	}
	
	private void extractElite() {
		
	}
	
	private void insertElite() {
		
	}
	
	private void Evaluate() {
		
	}
}
