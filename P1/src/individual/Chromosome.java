package individual;

import java.util.ArrayList;
import java.util.List;

import genetics.Gen;

//CREO QUE TODO ESTO ESTA COMO EL OGT
//CROMOSOMA
public class Chromosome {
	
	//UN CROMOSOMA TIENE UNA LISTA DE GENES
	private List<Gen> genes;
		
	private double acumulado;
		
	//Optimo
	private double fitness;
	private double fitnessAdaptado;
		
	private double probSeleccion;
	
	public Chromosome(Chromosome nCrom){
		this.acumulado = nCrom.getAcumulado();
		this.fitness = nCrom.getFitness();
		this.fitnessAdaptado = nCrom.getFitnessAdaptado();
		this.probSeleccion = nCrom.getProbSeleccion();
		this.genes = new ArrayList<>();
		
//		for(Gen g : nCrom.getGenes()){
//			if(g instanceof GenBinario)
//				this.genes.add(new GenBinario((GenBinario)g));
//			else if(g instanceof GenReal)
//				this.genes.add(new GenReal((GenReal)g));
//			else if(g instanceof GenEntero)
//				this.genes.add(new GenEntero((GenEntero)g));
//		}
	}
		
	public Chromosome() {
		
	}
	
	public List<Gen> getGenes() {return genes;}

	public double getFitnessAdaptado() {
		return fitnessAdaptado;
	}
	
	public double getAcumulado() {
		return acumulado;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public double getProbSeleccion() {
		return probSeleccion;
	}
	
	//Revisar esto
	public int getTam(){
		int ret = 0;
		if(!genes.isEmpty()){
			for(Gen g : this.genes){
				ret += g.getTam();
			}
		}
		
		return ret;
	}

	//HAY QUE HACER ESTO AAAA
	public List<Object> getAlelos() {
		
	}
	
	public List<Double> getFenotipo(){
		
	}
	public List<Double> getGenotipo(){
		
	}
	
}
