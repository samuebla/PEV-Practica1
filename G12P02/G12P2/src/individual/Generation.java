package individual;

import java.util.ArrayList;
import java.util.List;

import genetics.Gen;

public class Generation {
	
	public double best;
	public double worst;
	public double avarage;
	public List<Gen> sol;
	public Generation(List<Chromosome> population, double totalFitness) {
		// TODO Auto-generated constructor stub
		int size = population.size();
		avarage = totalFitness / size;
		best = population.get(size - 1).getFitness();
		worst = population.get(0).getFitness();
		
		sol = new ArrayList<Gen>();
		sol.addAll(population.get(size - 1).getGens());
	}
}
