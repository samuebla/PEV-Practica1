package geneticAlgorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import crosses.Cross;
import functions.Func2_Schubert;
import functions.Func3_EggHolder;
import functions.Func4_Michalewicz;
import functions.Function;
import functions.Function1;
import genetics.BinaryGen;
import genetics.Gen;
import genetics.RealGen;
import individual.Chromosome;
import individual.Generation;
import individual.Population;
import mutations.Mutation;
import mutations.MutationBasic;
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
import utils.TVuelo;

public class GeneticAlgorithm {
	
	Interface interface_;
	
	private FunctionType FunctType_;
	private SelectionType SelecType_;
	private CrossType CrossType_;
	private MutationType MutType_;
	List<Generation> generations;
	
	Population poblation;
	Function funct;
	Selection select;
	Cross cross;
	Mutation mut;
	
	List<TVuelo> TTEL_vuelo_;
	List<ArrayList<Double>> separations_;
	
	Params param;
	private boolean elitism_;
	private int sizePop_;
	private int eliteSize;
	private double totalFitness;
	
	public GeneticAlgorithm(Interface inter) {
		interface_ = inter;
	}
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, 
						double eliPercentage, int truncProbability,List<TVuelo> TTEL_vuelo, List<ArrayList<Double>> separations) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		elitism_ = elitism;		
		selectTypes();		
		
		TTEL_vuelo_ = TTEL_vuelo;
		separations_ = separations;
		
		//Contenedor de Parametros
		param = new Params();
		param.numGenerations = numGenerations;
		param.crossProb = crossProb;
		param.mutProb = mutProb;
		param.precision = precision;
		param.interval = funct.getInterval();
		param.f_type = f_Type;
		param.truncProb = truncProbability;
		
		eliteSize = (int)(sizePopulation * eliPercentage);
		
		//INIT POPULATION
		poblation = InitPopulation(sizePopulation, precision,f_Type);
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
			if(elitism_) insertElite(elite);
			totalFitness = Evaluate();
			System.out.println(i);
		}
		
		showSolution();
	}
	
	private Population extractElite() {
		
		//As the elite is ordered from minor to major fitness, the best indivoduals are at the end of the list
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
		int numGen = param.numGenerations;
		//Quedaria la solucion de aquel que sea el mejor/peor segun la funcion
		List<Double> sol = new ArrayList<Double>();
		
		double maxAbs;
		if(funct.maximize) maxAbs = Double.NEGATIVE_INFINITY;
		else maxAbs =  Double.POSITIVE_INFINITY;
		
		double[] best = new double[numGen];
		double[] bestPob = new double[numGen];
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
        	}
        	
        	Generation gen = generations.get(i);
        	bestPob[i] = maxAbs;
        	avarage[i] = gen.avarage;
			best[i] = gen.best;
			
        	i++;
        }
        
        //Muestra la grafica
        interface_.showGraph(bestPob, best, avarage, maxAbs, sol);
	};
	
	private double Evaluate() {
		double puntuationAcc = 0;
		double fitnessTotal = 0;
		double fitnessDisplacedTotal = 0;
		double bestFitness = 0;
		int best_pos = 0;
		//Para cada gen, evaluamos su valor con la funcion F.
		for(Chromosome c : poblation.getPopulation()) {
			//Son genes ya convertidos a numero, ya no son una cadena de bits o x
			List<Double> fenotipo = c.getPhenotype();
			c.setFitness(funct.ejecutar(fenotipo));
		}
		
		poblation.displaceFitness();
		
		//obtenemos el mejor y el total
		for(Chromosome c : poblation.getPopulation()) {
			if(c.getFitness() > bestFitness) {
				bestFitness = c.getFitness();
			}
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
		
		mut = new MutationBasic(); //Compilation Purposes
		switch (MutType_) {
			case Basic :
				mut = new MutationBasic();
				break;
			case Basic_Double:
				mut = new MutationBasic();
				break;
		}
		
		if(FunctType_ == FunctionType.f4_Michalewicz) 
			mut = new MutationBasic();
		
	}
	
	private Population InitPopulation(int pobSize, double precision, FunctionType numFunct) {
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
					
//				case f4_Michalewicz:
//					for(int j = 0; j < function4params; j++) {
//						if(!func4IsBin)
//							genes.add(new RealGen((float) precision));
//						else genes.add(new BinaryGen((float) precision));
//						genes.get(j).randomize(0, Math.PI);
//					}
//					break;
			}
			population.getPopulation().add(new Chromosome(genes));
		}
		return population;
	}
	
	private Population Selection() {
		select.selection(poblation.getPopulation(), param);
		return new Population(select.getPopSelected(), poblation);
	}
	
	private Population Cross(Population pop) {
		return new Population(cross.reproduce(pop.getPopulation(), param.crossProb), pop);
	}
	
	private Population Mutate() {
		mut.mutate(poblation.getPopulation(), param);
		return new Population(mut.getMutatedPop(), poblation);
	}
}
