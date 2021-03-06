
package geneticAlgorithm;
import java.util.ArrayList;
import java.util.List;

import crosses.*;
import functions.*;
import individual.*;
import mutations.*;
import selection.*;
import ui.Interface;
import utils.*;
import utilsMultiplex.*;

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
	BloatingType bloatType_;
	
	Params param;
	private boolean elitism_;
	private int sizePop_;
	private int eliteSize;
	private double totalFitness;
	int maxDepth;
	public boolean useIF_;
	float tarpeianFactor_;
	
	public GeneticAlgorithm(Interface inter) {
		interface_ = inter;
	}
	
	//El mejor de la poblacion no coincide con el mejor hasta ahora con elitisimo
	//El mejor de la poblacion sube cuando deberia bajar
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, 
						double eliPercentage, int truncProbability,MultiplexType multType, CreationType creatType, BloatingType bloatType, float tarpeianFactor, int max_depth, boolean useIF, double betaValue) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		elitism_ = elitism;	
		maxDepth = max_depth;
		multType_ = multType;
		creatType_ = creatType;
		useIF_ = useIF;
		bloatType_ = bloatType;
		tarpeianFactor_ = tarpeianFactor; 
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
		double quantity = Math.ceil(sizePopulation * eliPercentage);
		eliteSize = (int)quantity;
		BloatingMethods.SetUpData(max_depth, creatType, multType, useIF);
		BloatingMethods.setFunction(funct);
		//INIT POPULATION
		poblation = InitPopulation(sizePopulation, precision);
		//INIT ELITE
		Population elite = new Population();
		poblation.maximizePopulation = funct.maximize;
		//EVALUATE POPULATION
		totalFitness = Evaluate();
		//Para almacenar el resolutado de cada generacion
		generations = new ArrayList<>();
		//EVOLUTION
		for(int i = 0; i < numGenerations; i++) {
			generations.add(new Generation(poblation.getPopulation(), totalFitness));
			if(elitism_) elite = extractElite();
			Population selected = Selection();
			poblation = Cross(selected);
			poblation = Mutate();
			poblation.sort();
			Bloat();
			if(elitism_) insertElite(elite);
			totalFitness = Evaluate();
			updateProgressBar(i * (100 / numGenerations) + 1);
			System.out.print(i + "\n");
		}
		
		updateProgressBar(0);
		showSolution();
	}
	
	private void updateProgressBar(int value) {
		if(interface_.evolTab.isShowing())
			return;
		
		interface_.progressBar.setValue(value);
		interface_.progressBar.update(interface_.progressBar.getGraphics());
	}
	
	private Population extractElite() {
		//As the elite is ordered from major  to minor fitness, the best indivoduals are at the end of the list
		Population elite = new Population(poblation.getPopulation().subList(poblation.getPopulation().size() - eliteSize, poblation.getPopulation().size()), poblation);	
		return elite;
	}
	
	private void insertElite(Population elite) {
		//Los vamos a insertar al principio de la poblacion, reemplazando a los peores
		for(int i = 0; i < elite.getPopulation().size() ; i++) {
			poblation.getPopulation().set(i, elite.getPopulation().get(i));
		}
		
		int n = 10;
	}
	
	private void showSolution() {
		int numGen = generations.size();
		//Quedaria la solucion de aquel que sea el mejor/peor segun la funcion
		Chromosome sol = null;
		
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
        		
        		if(minAbs > generations.get(i).worst) {
        			minAbs = generations.get(i).worst;
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
			
        	i++;
        }
        
        //Muestra la grafica
        double solutionFitness = maxAbs;
        interface_.showGraph(bestPob, best, worstPob, avarage, maxAbs, minAbs, sol, param.numCrosses, param.numMutations);
	};
	
	private void Bloat() {
		double avarage_fitness = 0;
		double avarage_size = 0;
		double size;
		size = poblation.getPopulation().size();
		for(Chromosome c : poblation.getPopulation()) {
			avarage_size += c.getTree().getNumNodes();
			avarage_fitness += c.getFitness();
		}
		avarage_size /= size;
		avarage_fitness /= size;
		
		if(bloatType_ != BloatingType.None) {
			if(bloatType_ == BloatingType.Tarpeian)
				BloatingMethods.Tarpeian(poblation, avarage_size, tarpeianFactor_);
			else BloatingMethods.Penalty(poblation, avarage_fitness, avarage_size);
		}
	}
	
	private double Evaluate() {
		double puntuationAcc = 0;
		double fitnessTotal = 0;
		double fitnessDisplacedTotal = 0;
		int best_pos = 0;
		//Para cada gen, evaluamos su valor con la funcion F.
		for(Chromosome c : poblation.getPopulation()) {
			c.setFitness(funct.ejecutar(c));
		}
		
		poblation.sort();
		
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
		funct = new FunctMultiplexor(multType_); //Compilation Purposes
		
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
				break;
			case Functional :
				mut = new Functional();
				break;
			case Permutation :
				mut = new Permutation();
				break;
		}
	}
	
	private Population InitPopulation(int pobSize, double precision) {
		Population population = new Population();
		if(creatType_ != CreationType.RampedNHalf)
			for(int i = 0; i < pobSize; i++)
				population.getPopulation().add(new Chromosome(maxDepth, creatType_, useIF_, multType_));
		else{
			int groups = maxDepth - 1;
			int sizePopulation = pobSize/ groups;
			int restPop = pobSize % groups;
			int depthGroup = 2; //Profundidad inicial
			for(int i = 0; i < groups; i++) {
				int half = sizePopulation / 2;
				for(int j = 0; j < sizePopulation; j++) {
					if( j <= half) { //Primera mitad
						population.getPopulation().add(new Chromosome(depthGroup, CreationType.Grow, useIF_,multType_));
					}else { //Segunda mitad
						population.getPopulation().add(new Chromosome(depthGroup, CreationType.Full, useIF_,multType_));
					}
				}
				depthGroup++;
				if(i == groups - 1) //Ultimo grupo se lleva al resto de la poblacion que no entro en la division decimal
					sizePopulation += restPop;
			}
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
