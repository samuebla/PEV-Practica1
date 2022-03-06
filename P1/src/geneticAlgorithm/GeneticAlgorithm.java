package geneticAlgorithm;
import functions.*;

import utils.*;

public class GeneticAlgorithm {
	
	private FunctionType FunctType_;
	private SelectionType SelecType_;
	private CrossType CrossType_;
	private MutationType MutType_;
	private boolean elitism_;
	
	private int sizePop_;
	Function funct;
	
	public GeneticAlgorithm() {
		
		
	}
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, double eliPercentage) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		
		elitism_ = elitism;		
		InitPopulation();
		
		selectTypes();
		
		Evaluate();
		for(int i = 0; i < numGenerations; i++) {
			if(elitism_)
				extractElite();
			Select();
			System.out.println("evolute");
			Reproduce();
			Mutate();
			if(elitism_)
				insertElite();
			Evaluate();
		}
	}
	
	private Function selectTypes() {
		Function f = new Function1(); //Compilation Purposes

		switch (FunctType_) {
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
