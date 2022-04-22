package individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genetics.Gen;
import utilsMultiplex.TArbol;

//CROMOSOMA
public class Chromosome {

	public static String terminales[];
	public static final String terminales6[] = { "A0", "A1", "D0", "D1", "D2", "D3" };
	public static final String funciones[] = { "AND", "OR", "NOT", "IF" };
	private TArbol tree;
	private double fitness;
	private double puntuation;
	private double puntuation_acc;
	private String fenotipo;
	private double fitnessDisplaced;


	public Chromosome(int profundidad, int tipoCreacion, boolean useIf, int tipoMultiplexor) {

		tree = new TArbol(profundidad, useIf);
		switch (tipoCreacion) {
		case 0:
			tree.inicializacionCreciente(0);
			break;
		case 1:
			tree.inicializacionCompleta(0, 0);
			break;
		case 2:
			int ini = new Random().nextInt(2);
			if (ini == 0)
				tree.inicializacionCreciente(0);
			else
				tree.inicializacionCompleta(0, 0);
			break;
		}
	}

	// Constructora por copia
	public Chromosome(Chromosome copyCrom) {

		tree = copyCrom.tree;

		puntuation_acc = copyCrom.getPuntuationAcc();
		fitness = copyCrom.getFitness();
	}



	public String getPhenotype() {

		return fenotipo;
	}

	// Tamaño total de ambos genes
	public int getTam() {

		//TODO AAA NO SE SI ES NUMNODOS O NUNHIJOS
		return tree.getNumNodos();
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
