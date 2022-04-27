package geneticAlgorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import crosses.Cross;
import crosses.CrossTree;
import functions.FunctMultiplexor;
import functions.Function;
import genetics.Gen;
import individual.Chromosome;
import individual.Generation;
import individual.Population;
import mutations.Contraction;
import mutations.Expansion;
import mutations.Functional;
import mutations.Hoist;
import mutations.Mutation;
import mutations.Permutation;
import mutations.Terminal;
import mutations.TreeSubtree;
import selection.Selection;
import selection.SelectionProbTournament;
import selection.SelectionRemains;
import selection.SelectionRoulette;
import selection.SelectionStochastic;
import selection.SelectionTournament;
import selection.SelectionTruncation;
import ui.Interface;
import utils.CrossType;
import utils.FunctionType;
import utils.MutationType;
import utils.Params;
import utils.SelectionType;
import utilsMultiplex.CreationType;
import utilsMultiplex.MultiplexType;

public class GeneticAlgorithm {
	
	Interface interface_;
	
	private FunctionType FunctType_;
	private SelectionType SelecType_;
	private CrossType CrossType_;
	private MutationType MutType_;
	List<Generation> generations;
	boolean minorTel_type_;
	
	Population poblation;
	Function funct;
	Selection select;
	Cross cross;
	Mutation mut;
	
	MultiplexType multType_;
	CreationType creatType_;
	
	Params param;
	private boolean elitism_;
	private int sizePop_;
	private int eliteSize;
	private double totalFitness;
	int minDepth, maxDepth;
	
	public GeneticAlgorithm(Interface inter) {
		interface_ = inter;
	}
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, 
						double eliPercentage, int truncProbability,MultiplexType multType, CreationType creatType, int min_depth, int max_depth, double betaValue) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		elitism_ = elitism;	
		minDepth = min_depth;
		maxDepth = max_depth;
		multType_ = multType;
		creatType_ = creatType;
		
		selectTypes();		
		
		//Contenedor de Parametros
		param = new Params();
		param.numGenerations = numGenerations;
		param.crossProb = crossProb;
		param.mutProb = mutProb;
		param.precision = precision;
		param.interval = funct.getInterval();
		param.f_type = f_Type;
		param.truncProb = truncProbability;
		param.beta = betaValue;
		param.numMutations = 0;
		param.numCrosses = 0;
		
		eliteSize = (int)(sizePopulation * eliPercentage);
		
		//INIT POPULATION
		poblation = InitPopulation(sizePopulation, precision, minDepth, maxDepth);
		//INIT ELITE
		Population elite = new Population();
		
		poblation.maximizePopulation = funct.maximize;
		//EVALUATE POPULATION
		totalFitness = Evaluate();
		//Para almacenar el resolutado de cada generacion
		generations = new ArrayList<>();
		int valueProgress = 0;
		//EVOLUTION
//		for(int i = 0; i < numGenerations; i++) {
//			generations.add(new Generation(poblation.getPopulation(), totalFitness));
//			if(elitism_) elite = extractElite();
//			Population selected = Selection();
//			poblation = Cross(selected);
//			poblation = Mutate();
//			poblation.sort();
//			if(elitism_) insertElite(elite);
//			totalFitness = Evaluate();
//			updateProgressBar(i * (100 / numGenerations));
//		}
		
		updateProgressBar(100);
		showSolution();
	}
	
	private void updateProgressBar(int value) {
		interface_.progressBar.setValue(value);
		
	}
	
	private Population extractElite() {
		
		//As the elite is ordered from major  to minor fitness, the best indivoduals are at the end of the list
		Population elite = new Population(poblation.getPopulation().subList(poblation.getPopulation().size() - eliteSize, poblation.getPopulation().size()), poblation);	
		return elite;
	}
	
	private void insertElite(Population elite) {
		//Se debe de llamar despues del primer Evaluate
		//Los vamos a insertar al principio de la poblacion, reemplazando a los peores
		for(int i = 0; i < elite.getPopulation().size() ; i++) {
			poblation.getPopulation().add(i, elite.getPopulation().get(i));
		}
		
		int n = 10;
	}
	
	private void showSolution() {
		int numGen = generations.size();
		//Quedaria la solucion de aquel que sea el mejor/peor segun la funcion
		List<Gen> sol = new ArrayList<Gen>();
		
		double maxAbs;
		double minAbs;
		if(funct.maximize) {
			maxAbs = Double.NEGATIVE_INFINITY;
			minAbs = Double.POSITIVE_INFINITY;
		}
		else {
			maxAbs =  Double.POSITIVE_INFINITY;
			minAbs = Double.NEGATIVE_INFINITY;
		}
		
		double[] best = new double[numGen];
		double[] worst = new double[numGen];
		double[] bestPob = new double[numGen];
		double[] worstPob = new double[numGen];
        double[] avarage = new double[numGen];
        
        int i = 0;
        while(i < numGen) {
        	if(funct.maximize) {
        		if(maxAbs < generations.get(i).best) {
        			maxAbs = generations.get(i).best;
        			sol = generations.get(i).sol;
        		}
        	}else {
        		if(maxAbs > generations.get(i).best) {
        			maxAbs = generations.get(i).best;
        			sol = generations.get(i).sol;
        		}
        		if(minAbs < generations.get(i).worst) {
        			minAbs = generations.get(i).worst;
        		}
        		
        	}
        	
        	Generation gen = generations.get(i);
        	bestPob[i] = maxAbs;
        	worstPob[i] = minAbs;
        	avarage[i] = gen.avarage;
			best[i] = gen.best;
			worst[i] = gen.worst;
			
        	i++;
        }
        
        //Muestra la grafica
        double solutionFitness = maxAbs;
        interface_.showGraph(bestPob, best, worstPob, worst, avarage, maxAbs, minAbs, sol, param.numCrosses, param.numMutations);
	};
	
	private double Evaluate() {
		double puntuationAcc = 0;
		double fitnessTotal = 0;
		double fitnessDisplacedTotal = 0;
		int best_pos = 0;
		//Para cada gen, evaluamos su valor con la funcion F.
		for(Chromosome c : poblation.getPopulation()) {
//			c.setFitness(funct.ejecutar(c.getGens()));
		}
		
		poblation.displaceFitness();
		
		//obtenemos el mejor y el total
		for(Chromosome c : poblation.getPopulation()) {
			fitnessTotal += c.getFitness();
			fitnessDisplacedTotal += c.getFitnessDisplaced();
		}
		
		poblation.sort();
		
		//Asiganmos la puntuacion y la acumulada a cada cromosoma
		for(Chromosome c : poblation.getPopulation()) {
			c.setPuntuation(c.getFitnessDisplaced()/fitnessDisplacedTotal);
			puntuationAcc += c.getPuntuation();
			c.setPuntuationAcc(puntuationAcc);
		}
		
		return fitnessTotal;
	}
	
	private void selectTypes() {
		funct = new FunctMultiplexor(); //Compilation Purposes
		
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
		
		cross = new CrossTree(); //Compilation Purposes

		
		mut = new Terminal(); //Compilation Purposes
		switch (MutType_) {
			case Terminal :
				mut = new Terminal();
			case Functional :
				mut = new Functional();
			case TreeSubtree :
				mut = new TreeSubtree();
			case Permutation :
				mut = new Permutation();
			case Hoist :
				mut = new Hoist();
			case Expansion :
				mut = new Expansion();
			case Contraction :
				mut = new Contraction();
				break;
		}
	}
	
	private Population InitPopulation(int pobSize, double precision, int minDepth, int maxDepth) {
		Population population = new Population();
		
		OperatorType = Random.
		
		for(int i = 0; i < pobSize; i++) {
			
			
		}
		return population;
	}
	
	private Population Selection() {
		select.selection(poblation.getPopulation(), param);
		return new Population(select.getPopSelected(), poblation);
	}
	
	private Population Cross(Population pop) {
		return new Population(cross.reproduce(pop.getPopulation(), param.crossProb,param), pop);
	}
	
	private Population Mutate() {
		mut.mutate(poblation.getPopulation(), param);
		return new Population(mut.getMutatedPop(), poblation);
	}
}
