package geneticAlgorithm;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import crosses.*;
import functions.*;
import genetics.*;
import individual.*;
import mutations.*;
import selection.*;
import utils.*;
import utilsFlight.TVuelo;
import ui.Interface;

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
	
	List<TVuelo> TTEL_vuelo_;
	List<ArrayList<Double>> separations_;
	
	Params param;
	private boolean elitism_;
	private int sizePop_;
	private int eliteSize;
	private double totalFitness;
	int numPistas_;
	
	public GeneticAlgorithm(Interface inter) {
		interface_ = inter;
	}
	
	public void Evolute(int sizePopulation, int numGenerations, double crossProb, double mutProb, double precision, 
						FunctionType f_Type, SelectionType s_Type, CrossType c_Type, MutationType m_Type, boolean elitism, 
						double eliPercentage, int truncProbability,List<TVuelo> TTEL_vuelo, List<ArrayList<Double>> separations, int numPistas, boolean minorTel_type, double betaValue) {
		FunctType_ = f_Type;
		SelecType_ = s_Type;
		CrossType_ = c_Type;
		MutType_ = m_Type;
		elitism_ = elitism;	
		numPistas_ = numPistas;
		minorTel_type_ = minorTel_type;
		TTEL_vuelo_ = TTEL_vuelo;
		separations_ = separations;
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
		
		//AAAAAAAAAA Igual poner algun if de que si el tipo es heuristica se guarda y asi no 
		//Guardamos la función para la heurística
		param.funct2 = (FunctionP2)funct;
		
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
		int best_pos = 0;
		//Para cada gen, evaluamos su valor con la funcion F.
		for(Chromosome c : poblation.getPopulation()) {
			c.setFitness(funct.ejecutar(c.getGens()));
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
		funct = new FunctionP2(numPistas_, TTEL_vuelo_,separations_,minorTel_type_); //Compilation Purposes
		
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
		
		cross = new CrossPMX(); //Compilation Purposes
		switch (CrossType_) {
			case PMX :
				cross = new CrossPMX();
				break;
			case OX:
				cross = new CrossOX();
				break;
			case OX_PP:
				cross = new CrossOXPP();
				break;
			case Cycles_CX:
				cross = new CrossCyclesCX();
				break;
			case CO_Ordinal_Encoding:
				cross = new CrossCOOrdinalEncoding();
				break;
		}
		
		mut = new MutationInsertion(); //Compilation Purposes
		switch (MutType_) {
			case Insertion :
				mut = new MutationInsertion();
				break;
			case Exchange:
				mut = new MutationExchange();
				break;
			case Inversion:
				mut = new MutationInversion();
				break;
			case Heuristics:
				mut = new MutationHeuristics();
				break;
		}
	}
	
	private Population InitPopulation(int pobSize, double precision, FunctionType numFunct) {
		Population population = new Population();
		for(int i = 0; i < pobSize; i++) {
			List<Gen> genes = new ArrayList<>();
			
			List<Integer> vuelos = new ArrayList<>();
			int number = 0;
			while(vuelos.size() < TTEL_vuelo_.size()) vuelos.add(number++);
			
			for(int j = 0; j < TTEL_vuelo_.size(); j++) {
				int index = ThreadLocalRandom.current().nextInt(0, vuelos.size());
				
				FlightGen gen = new FlightGen((float) precision);
				gen.pos_vuelo = vuelos.get(index);
//				gen.pistaAsignada = ThreadLocalRandom.current().nextInt(0, 3);
				vuelos.remove(index);
				genes.add(gen);
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
