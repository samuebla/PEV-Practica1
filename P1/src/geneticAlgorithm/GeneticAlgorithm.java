package geneticAlgorithm;
import functions.*;
import individual.Chromosome;

import java.util.List;

import crosses.*;
import selection.*;
import mutations.*;

import utils.*;


public class GeneticAlgorithm {
	
	private FunctionType FunctType_;
	private SelectionType SelecType_;
	private CrossType CrossType_;
	private MutationType MutType_;
	private boolean elitism_;
	List<Chromosome> poblation;
	
	private int sizePop_;
	Function funct;
	Selection select;
	Cross cross;
	MutationAlgorithm mut;
	
	public GeneticAlgorithm() {
		
		
	}
	
	
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, double eliPercentage) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		
		elitism_ = elitism;		
		InitPopulation(poblation);
		
		selectTypes();
		
		Evaluate();
		for(int i = 0; i < numGenerations; i++) {
			if(elitism_)
				extractElite();
			
			select.selection(poblation, 2);
			Select();
			System.out.println("evolute");
			Reproduce();
			Mutate();
			if(elitism_)
				insertElite();
			Evaluate();
		}
	}
	
	private void selectTypes() {
		funct = new Function1(); //Compilation Purposes

		switch (FunctType_) {
			case f1 :
				funct = new Function1();
				break;
			case f2_Schubert:
				funct = new Func2_Schubert();
				break;
			case f3_EggHolder:
				funct = new Func3_EggHolder();
				break;
			case f4_Michalewicz:
				funct = new Func4_Michalewicz();
				break;
		}
		
		select = new SelectionRoulette(); //Compilation Purposes
		switch (SelecType_) {
			case Roulette :
				select = new SelectionRoulette();
				break;
			case RandomTournament:
				select = new SelectionProbTournament();
				break;
			case DetTournament:
				select = new SelectionTournament();
				break;
			case Remains:
				select = new SelectionRemains();
				break;
			case Truncation:
				select = new SelectionTruncation();
				break;
			case Stochastic:
				select = new SelectionStochastic();
				break;
		}
		
		cross = new CrossMonopoint(); //Compilation Purposes
		switch (CrossType_) {
			case Monopoint :
				cross = new CrossMonopoint();
				break;
			case Uniform:
				cross = new CrossUniform();
				break;
		}
		
		mut = new BasicMutation(); //Compilation Purposes
		switch (MutType_) {
			case Basic :
				mut = new BasicMutation();
				break;
			case Basic_Double:
				mut = new BasicMutation();
				break;
		}
	}
	
	private void InitPopulation(List<Chromosome> poblation) {
		
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
