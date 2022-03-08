package individual;

import java.util.ArrayList;
import java.util.List;

import genetics.BinaryGen;
import genetics.Gen;
import genetics.RealGen;

//CROMOSOMA
public class Chromosome {
	
	//UN CROMOSOMA TIENE UNA LISTA DE GENES
	private List<Gen> genes;
		
	private double acumulado;
		
	//Optimo
	private double fitness;
	private double fitnessAdaptado;
		
	private double probSeleccion;
	
	//Constructora por copia
	public Chromosome(Chromosome nCrom){
		this.acumulado = nCrom.getAcumulado();
		this.fitness = nCrom.getFitness();
		this.fitnessAdaptado = nCrom.getFitnessAdaptado();
		this.probSeleccion = nCrom.getProbSeleccion();
		this.genes = new ArrayList<>();
		
		for(Gen g : nCrom.getGenes()){
			if(g instanceof BinaryGen)
				this.genes.add(new BinaryGen((BinaryGen)g));
			else if(g instanceof RealGen)
				this.genes.add(new RealGen((RealGen)g));
		}
	}		
	
	
	public Chromosome(List<Gen> genes){ this.genes = genes; }
	
	
	//GETTERS
	public List<Double> getFenotipo(){
		List<Double> fenot = new ArrayList<>();
		
		for(Gen g : genes) 
			fenot.add(g.getFenotipo());
		return fenot;
	}	
	
	public List<Object> getAlelos(){
		List<Object> alelos = new ArrayList<>();

		for(Gen g : this.genes) 
			alelos.addAll(g.getAlelos());
		
		return alelos;
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
	
	public List<Gen> getGenes() {return genes;}


	public double getFitness() {
		return fitness;
	}
	public void setFitness(double valor) {
		fitness = valor;
	}
	
	public double getAcumulado() {
		return acumulado;
	}
	public void setAcumulado(double valor) {
		acumulado = valor;
	}
		
	public double getProbSeleccion() {
		return probSeleccion;
	}
	public void getProbSeleccion(double valor) {
		probSeleccion = valor;
	}
	
	public double getFitnessAdaptado() {
		return fitnessAdaptado;
	}
	public void setFitnessAdaptado(double valor) {
		this.fitnessAdaptado = valor;
	}
	
	//Creo que falta definir operators de boleanos para ver si un Object es igual que un cromosoma
	


	
	

}
