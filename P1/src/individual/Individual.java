package individual;

import java.util.List;

import genetics.Gen;

//CROMOSOMA
public class Individual {
	
	//UN CROMOSOMA TIENE UNA LISTA DE GENES
	private List<Gen> genes;
		
	private double acumulado;
		
	//Optimo
	private double fitness;
	private double fitnessAdaptado;
		
	private double probSeleccion;
	
		
	public Individual() {
		
	}
	
	public List<Gen> getGenes() {return genes;}

	
	public double getFitnessAdaptado() {
		return fitnessAdaptado;
	}

}
