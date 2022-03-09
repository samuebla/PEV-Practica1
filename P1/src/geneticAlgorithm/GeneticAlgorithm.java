package geneticAlgorithm;
import java.util.ArrayList;
import java.util.List;

import crosses.Cross;
import crosses.CrossMonopoint;
import crosses.CrossUniform;
import functions.Func2_Schubert;
import functions.Func3_EggHolder;
import functions.Func4_Michalewicz;
import functions.Function;
import functions.Function1;
import genetics.BinaryGen;
import genetics.Gen;
import genetics.RealGen;
import individual.Chromosome;
import individual.Population;
import mutations.BasicMutation;
import mutations.MutationAlgorithm;
import selection.Selection;
import selection.SelectionProbTournament;
import selection.SelectionRemains;
import selection.SelectionRoulette;
import selection.SelectionStochastic;
import selection.SelectionTournament;
import selection.SelectionTruncation;
import utils.CrossType;
import utils.FunctionType;
import utils.MutationType;
import utils.Params;
import utils.SelectionType;
import ui.*;

public class GeneticAlgorithm {
	
	private FunctionType FunctType_;
	private SelectionType SelecType_;
	private CrossType CrossType_;
	private MutationType MutType_;
	private boolean elitism_;
	Population poblation;
	private int sizePop_;
	private int eliteSize;
	Function funct;
	Selection select;
	Cross cross;
	MutationAlgorithm mut;
	Interface interface_;
	
	public GeneticAlgorithm(Interface inter) {
		interface_ = inter;
		
	}
	
	
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, double eliPercentage) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		elitism_ = elitism;		
		
		int function4params = 4;//esto hay que ponerlo en la interfaz
		
		poblation = InitPopulation(sizePopulation, precision,f_Type, function4params);
		selectTypes();		
		
		eliteSize = (int)(sizePopulation * eliPercentage);
		Population elite = new Population();
		
		Params param = new Params();
		param.numGenerations = numGenerations;
		param.sizePopulation = sizePopulation;
		param.crossProbM = crossProb;
		param.mutProb = mutProb;
		param.precision = precision;
		param.interval = funct.getInterval();
		param.f_type = f_Type;
		
		
		
		double[] best = new double[numGenerations];
		double[] bestPob = new double[numGenerations];
        double[] media = new double[numGenerations];
        double solution = 4.0;
        List<Double> sol = new ArrayList<Double>();
		Evaluate();
		for(int i = 0; i < numGenerations; i++) {
			//Extract Elite
			if(elitism_) elite = extractElite(poblation);
			//Select
			select.selection(poblation.getPopulation(), param);
			Population selected = new Population(select.getPopSelected());
			//Cross
			poblation = new Population(cross.reproduce(poblation.getPopulation(), crossProb));
			//Mutate
			mut.mutate(poblation.getPopulation(), param);
			poblation = new Population(mut.getMutatedPop());
			//Reinster Elite
			if(elitism_) insertElite(elite, poblation);
			Evaluate();
		}
		
		
		interface_.showGraph(media, best, media, bestPob, solution, sol); //temporals
	}
	
	private void showSolution() {
		
	};
	
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
	
	private Population InitPopulation(int pobSize, double precision, FunctionType numFunct, int function4params) {
		Population population = new Population();
		for(int i = 0; i < pobSize; i++) {
			List<Gen> genes = new ArrayList<>();
						
			switch(numFunct) {			
			case f1:
				
				//Creamos los genes binarios
				genes.add(new BinaryGen((float) precision));
				genes.add(new BinaryGen((float) precision));
				
				//Establecemos el rango
				genes.get(0).randomize(-3, 12.1);
				genes.get(1).randomize(4.1, 5.8);				
				break;
			case f2_Schubert:
				
				genes.add(new BinaryGen((float) precision));
				genes.add(new BinaryGen((float) precision));

				//Xi cuenta tanto para x1 como x2
				genes.get(0).randomize(-10, 10);
				genes.get(1).randomize(-10, 10);				
				break;
			case f3_EggHolder:
				
				genes.add(new BinaryGen((float) precision));
				genes.add(new BinaryGen((float) precision));
				
				genes.get(0).randomize(-512, 512);
				genes.get(1).randomize(-512, 512);
				
				
				break;
			case f4_Michalewicz:
				for(int j = 0; j < function4params; j++) {
					genes.add(new RealGen((float) precision));
					genes.get(j).randomize(0, Math.PI);
				}
				
				break;
			}
			population.getPopulation().add(new Chromosome(genes));
		}
		return population;
	}
	
	private Population extractElite(Population base) {
		return new Population (base.getPopulation().subList(0, eliteSize));
	}
	
	private void insertElite(Population elite, Population base) {
		for(int i = 0; i < elite.getPopulation().size() ; i++) {
			base.getPopulation().add(i, elite.getPopulation().get(i));
		}
	}
	
	private void Evaluate() {
		
	}
}
