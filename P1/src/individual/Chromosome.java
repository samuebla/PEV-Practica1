package individual;

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
	
		
	public Chromosome() {
		
	}
	
	public List<Gen> getGenes() {return genes;}

	public double getFitnessAdaptado() {
		return fitnessAdaptado;
	}
	
	public double getAcumulado() {
		return acumulado;
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
}
