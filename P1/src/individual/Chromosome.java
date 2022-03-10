package individual;

import java.util.ArrayList;
import java.util.List;

import genetics.BinaryGen;
import genetics.Gen;
import genetics.RealGen;

//CROMOSOMA
public class Chromosome{
	
	//Contiene una lista de genes. En total suelen ser 2. 
	private List<Gen> gens;
	private double acc;
	private double fitness;
	private double adaptedFitness;
	private double selectionProbability;
	
	//Constructora por copia
	public Chromosome(Chromosome nCrom){
		gens = new ArrayList<>();
		
		for(Gen g : nCrom.getGens()){
			if(g instanceof BinaryGen)
				this.gens.add(new BinaryGen((BinaryGen)g));
			else if(g instanceof RealGen)
				this.gens.add(new RealGen((RealGen)g));
		}
		
		acc = nCrom.getAcc();
		fitness = nCrom.getFitness();
		adaptedFitness = nCrom.getAdaptedFitness();
		selectionProbability = nCrom.getProbSeleccion();
	}		
	
	public Chromosome(List<Gen> genes){ 
		this.gens = genes; 
	}
	
	public List<Double> getFenotype(){
		List<Double> fenotype_ = new ArrayList<>();
		
		for(Gen g : gens) 
			fenotype_.add(g.getGenFenotype());
		return fenotype_;
	}	
	
	//Devuelve una lista con todos los alelos de ambos genes
	public List<Object> getAlleles(){
		List<Object> alleles = new ArrayList<>();

		for(Gen g : this.gens) 
			alleles.addAll(g.getAlleles());
		
		return alleles;
	}
	
	//Tamaño total de ambos genes
	public int getTam(){
		int ret = 0;
		if(!gens.isEmpty()){
			for(Gen g : this.gens){
				ret += g.getTam();
			}
		}
		
		return ret;
	}
	
	public List<Gen> getGens() {return gens;}
	
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double valor) {
		fitness = valor;
	}
	
	public double getAcc() {
		return acc;
	}
	public void setAcc(double valor) {
		acc = valor;
	}
		
	public double getProbSeleccion() {
		return selectionProbability;
	}
	public void setProbSeleccion(double valor) {
		selectionProbability = valor;
	}
	
	public double getAdaptedFitness() {
		return adaptedFitness;
	}
	
	public void setAdaptedFitness(double valor) {
		this.adaptedFitness = valor;
	}
}
