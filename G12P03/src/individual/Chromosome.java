package individual;

import java.util.ArrayList;
import java.util.List;

import genetics.Gen;
import utilsMultiplex.TArbol;

//CROMOSOMA
public class Chromosome {

	//TODO METER UN PUTO ARBOL Y QUITAR LOS PUTOS GENES
	
	// Contiene una lista de genes. En total suelen ser 2.
	private List<Gen> gens;
	
	private TArbol tree;
	
	private double fitness;
	private double puntuation;
	private double puntuation_acc;

	private double fitnessDisplaced;

	// Constructora por copia
	public Chromosome(Chromosome copyCrom) {
		gens = new ArrayList<>();

		tree = copyCrom.tree;

		puntuation_acc = copyCrom.getPuntuationAcc();
		fitness = copyCrom.getFitness();
	}

	public Chromosome(List<Gen> genes) {
		this.gens = genes;
	}

	public List<Double> getPhenotype() {
		List<Double> fenotype_ = new ArrayList<>();

		for (Gen g : gens)
			fenotype_.add(g.getGenGenotype());
		return fenotype_;
	}

	// Devuelve una lista con todos los alelos de ambos genes
	public List<Object> getAlleles() {
		List<Object> alleles = new ArrayList<>();

		for (Gen g : this.gens)
			alleles.addAll(g.getAlleles());

		return alleles;
	}

	// Tamaño total de ambos genes
	public int getTam() {
		int ret = 0;
		if (!gens.isEmpty()) {
			for (Gen g : this.gens) {
				ret += g.getTam();
			}
		}

		return ret;
	}

	public List<Gen> getGens() {
		return gens;
	}

	public void setGens(List<Gen> aux) {
		this.gens = aux;
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
