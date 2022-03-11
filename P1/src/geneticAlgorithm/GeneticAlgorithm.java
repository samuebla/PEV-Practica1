package geneticAlgorithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	
	Params param;
	private boolean elitism_;
	private int sizePop_;
	private int eliteSize;
	private double fitness;
	
	private int function4params_;
	
	public GeneticAlgorithm(Interface inter) {
		interface_ = inter;
	}
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, double eliPercentage, int function4params, int truncProbability) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		elitism_ = elitism;		
		selectTypes();		
		
		//Contenedor de Parametros
		param = new Params();
		param.numGenerations = numGenerations;
		param.sizePopulation = sizePopulation;
		param.crossProb = crossProb;
		param.mutProb = mutProb;
		param.precision = precision;
		param.interval = funct.getInterval();
		param.f_type = f_Type;
		param.truncProb = truncProbability;
		
		function4params_ = function4params;//Todo esto va en la interfaz
		
		//INIT POPULATION
		poblation = InitPopulation(sizePopulation, precision,f_Type, function4params_);
		//INIT ELITE
		eliteSize = (int)(sizePopulation * eliPercentage);
		Population elite = new Population();
		
		poblation.maximizePopulation = funct.maximize;
		//EVALUATE POPULATION
		fitness = Evaluate();
		
		//Para almacenar el resolutado de cada generacion
		generations = new ArrayList<>();
		//EVOLUTION
		for(int i = 0; i < numGenerations; i++) {
			generations.add(new Generation(poblation.getPopulation(), fitness));
			//Extract Elite
			if(elitism_) elite = extractElite(poblation);
			//Select
			Population selected = Selection();
			//Cross
			poblation = Cross(selected);
			//Mutate
			poblation = Mutate();
			//Reinsert Elite
			if(elitism_) insertElite(elite, poblation);
			//Get fitness
			fitness = Evaluate();
			System.out.println(i);
		}
		
		showSolution();
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
		double adapatedFitnessTotal = 0;
		double fitnessTotal = 0;
		List<Chromosome> chromosomes = poblation.getPopulation(); 
		//Para cada gen, evaluamos su valor con la funcion F.
		for(Chromosome c : chromosomes) {
			//Son genes ya convertidos a numero, ya no son una cadena de bits o x
			List<Double> fenotipo = c.getPhenotype();
			c.setFitness(funct.ejecutar(fenotipo));
		}
		poblation.calculateAdaptedFitness();
		//Acumulamos los valores de adaptaci�n y el fitness adaptado al maximo/minimo de la poblacion
		for(Chromosome c : chromosomes) {
			fitnessTotal = c.getFitness();
			adapatedFitnessTotal += c.getAdaptedFitness();
		}
		//La poblacion se ordena de MENOR A MAYOR fitness
		poblation.sort();
		poblation.setSelectionProbability(adapatedFitnessTotal);
		
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
		}
		
		if(FunctType_ == FunctionType.f4_Michalewicz) 
			mut = new MutationBasic();
		
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
	
	private Population Selection() {
		select.selection(poblation.getPopulation(), param);
		return new Population(select.getPopSelected());
	}
	
	private Population Cross(Population pop) {
		return new Population(cross.reproduce(pop.getPopulation(), param.crossProb));
	}
	
	private Population Mutate() {
		mut.mutate(poblation.getPopulation(), param);
		return new Population(mut.getMutatedPop());
	}
}
