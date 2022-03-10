package individual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {

	private List<Chromosome> population_;
	private double accumulatedFitness;
	private double fitness;
	private double selectionProbability;
	private double adaptedFitness;
	
	public Population() {
		// TODO Auto-generated constructor stub
		population_ = new ArrayList<Chromosome>();
	}
	
	public Population(List<Chromosome> pob){
		population_ = new ArrayList();
		for(Chromosome ind : pob)
			population_.add(new Chromosome(ind));
	}
	
	public void sort() {
		Collections.sort(population_, new Comparator<Chromosome>() {
		    @SuppressWarnings("removal")
			@Override
		    public int compare(Chromosome c1, Chromosome c2) {
		        return new Double(c1.getAdaptedFitness()).compareTo(new Double(c2.getAdaptedFitness()));
		    }
		});
	}
	
	public List<Chromosome> getPopulation() {
		return population_;
	}

	
	public String toString(){
		String cadena = "";
		for (Chromosome in : population_)
			cadena += in.getFenotype() + "\n";

		return cadena;
	}

	public double getAccumulatedFitness() {
		return accumulatedFitness;
	}

	public void setAccumulatedFitness(double accumulatedFitness) {
		this.accumulatedFitness = accumulatedFitness;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public double getSelectionProbability() {
		return selectionProbability;
	}

	public void setSelectionProbability(double selectionProbability) {
		this.selectionProbability = selectionProbability;
	}

	public double getAdaptedFitness() {
		return adaptedFitness;
	}

	public void setAdaptedFitness(double adaptedFitness) {
		this.adaptedFitness = adaptedFitness;
	}
}
