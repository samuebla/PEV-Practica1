package individual;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import functions.FunctMultiplexor;
import utilsMultiplex.CreationType;
import utilsMultiplex.MultiplexType;
import utilsMultiplex.TArbol;

//CROMOSOMA
public class Chromosome {

	public static final String terminales[] = { "A0", "A1", "D0", "D1", "D2", "D3" };
	public static final String terminales11[] = { "A0", "A1","A2", "D0", "D1", "D2", "D3","D4", "D5", "D6", "D7"};
	public static final String funciones[] = { "AND", "OR", "NOT", "IF" };
	private TArbol tree;
	private double puntuation;
	private double puntuation_acc;
	private String fenotipo;
	private double fitness;
	private double fitnessDisplaced;

	// Para la tabla multiplexor
	private static int soluciones[][];
	private static int numSolutions;

	public Chromosome(int profundidad, CreationType type, boolean useIf, MultiplexType type_) {

		tree = new TArbol(profundidad, useIf);
		switch (type) {
		case Grow:
			tree.initGrow(0,type_);
			break;
		case Full:
			tree.initComplete(0, 0,type_);
			break;
		case RampedNHalf:
			int ini = new Random().nextInt(2);
			if (ini == 0)
				tree.initGrow(0,type_);
			else
				tree.initComplete(0, 0,type_);
			break;
		}
		
		if(type_ == MultiplexType.SixEntries) {
			numSolutions = (int) Math.pow(2, 6);
			soluciones = FunctMultiplexor.soluciones6;
		}
		else {
			numSolutions = (int) Math.pow(2, 11);
			soluciones = FunctMultiplexor.soluciones11;
		}
	}
	
	public String getSolParsed() {
		return tree.getValue() + " " +  parserAux(tree.getSons());
	}
	
	private String parserAux(ArrayList<TArbol> h) {
		String s = "";
		
		for(int i  = 0; i < h.size(); i++)
			if(h.get(i).isRoot()){
				s += "(" + h.get(i).getValue() + " " + parserAux(h.get(i).getSons());
				s += ")";
			}
			else s += h.get(i).getValue() + " ";
		return s;
	}
		
	// Constructora por defecto
	public Chromosome() {
		this.tree = new TArbol();
	}

	// Constructora por copia
	public Chromosome(Chromosome copyCrom) {

		tree = copyCrom.tree;

		puntuation_acc = copyCrom.getPuntuationAcc();
		fitness = copyCrom.getFitness();
	}

	// Equivalente a constuctora por copia
	public Chromosome copia() {
		Chromosome c = new Chromosome();

		c.setArbol(this.tree.copia());
		c.setFitness(this.fitness);
		c.setFitnessDisplaced(this.fitnessDisplaced);
		c.setPuntuation(this.puntuation);
		c.setPuntuationAcc(this.puntuation_acc);

		return c;
	}	

	public void setArbol(TArbol treeAux) {
		this.tree = treeAux;
	}

	public TArbol getTree() {
		return this.tree;
	}

	//AAAAA TODO No se usa el fenotipo ni se cambia ni ostias xd
	public String getPhenotype() {
		fenotipo = parseToString();
		return fenotipo;
	}
	
	private String parseToString() {
		ArrayList<String> func = tree.toArray();
		String s = "(";
		int numParamOperator = 0;
		for(int i = 0; i < func.size(); i++){
			s += func.get(i);
			if(i < func.size()-1) s += " ";
		}
		s+= ")";
		return s;
	}

	// Tamaño total de ambos genes
	public int getTam() {

		// TODO AAA NO SE SI ES NUMNODOS O NUNHIJOS
		return tree.getNumNodes();
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double valor) {
		fitness = valor;
	}

	public double getPuntuationAcc() {
		return puntuation_acc;
	}

	public void setPuntuationAcc(double valor) {
		puntuation_acc = valor;
	}

	public double getPuntuation() {
		return puntuation;
	}

	public void setPuntuation(double puntuation) {
		this.puntuation = puntuation;
	}

	public double getFitnessDisplaced() {
		return fitnessDisplaced;
	}

	public void setFitnessDisplaced(double fitnessDisplaced) {
		this.fitnessDisplaced = fitnessDisplaced;
	}
}
