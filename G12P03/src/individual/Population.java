package individual;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Population {

	private List<Chromosome> population_;
	
	public boolean maximizePopulation;
	
	public Population() {
		// TODO Auto-generated constructor stub
		population_ = new ArrayList<Chromosome>();
	}
	
	public Population(List<Chromosome> pob, Population popu){
		population_ = new ArrayList();
		for(Chromosome ind : pob)
			population_.add(new Chromosome(ind));
		
		maximizePopulation = popu.maximizePopulation;
	}
	
	public void sort() {
		Collections.sort(population_, new Comparator<Chromosome>() {
			//Para quitar los warning
			@SuppressWarnings("removal")
			@Override
		    public int compare(Chromosome c1, Chromosome c2) {
		        return new Double(c2.getFitness()).compareTo(new Double(c1.getFitness()));
		    }
		});
	}
	
	public List<Chromosome> getPopulation() {
		return population_;
	}

	public void displaceFitness() {
		double max_substract = Double.NEGATIVE_INFINITY;
		double min_add = Double.POSITIVE_INFINITY;
		for(Chromosome c : population_){
			if(c.getFitness() > max_substract)
				max_substract = c.getFitness();
			if(c.getFitness() < min_add)
				min_add = c.getFitness();
		}
		max_substract *= 1.05;
		
		if(maximizePopulation)
			for(Chromosome c : population_)
				c.setFitnessDisplaced(Math.abs(min_add) + c.getFitness()); 
		else
			for(Chromosome c : population_)
				c.setFitnessDisplaced(max_substract - c.getFitness());
	}
	
	public String toString(){
		String cadena = "";
		for (Chromosome in : population_)
			cadena += in.getPhenotype() + "\n";

		return cadena;
	}
}
