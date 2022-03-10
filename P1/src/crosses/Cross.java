package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import individual.Chromosome;

public abstract class Cross {

	protected List<Chromosome> hijos;
	
	public List<Chromosome> getHijos() {
		return this.hijos;
	}
	
	public List<Chromosome> reproduce(List<Chromosome> population, double probCross) {

		Random r = new Random();
		ArrayList<Integer> elegidos = new ArrayList<>(); 
		this.hijos = new ArrayList<Chromosome>();
		//Se realiza el cruce sobre una lista nueva de cromosomas y se devuelve
        for (int i = 0; i < population.size(); i++){
            if (r.nextDouble() < probCross){
                elegidos.add(i);
            }
        }
        
        if(elegidos.size()%2 != 0){ //Deben ser pares para que haya dos padres
            elegidos.remove(elegidos.size() - 1);
        }
        
        for (int i = 0; i < elegidos.size(); i += 2){ 
        	cruzar(population.get(elegidos.get(i)), population.get(elegidos.get(i+1)));
        }
        
        if(probCross < 1) {
        	int pop = population.size();
        	int hij = hijos.size();
        	int rest =  pop - hij;
        	
        	hijos.addAll(population.subList(rest - 1, population.size() - 1));
        }
        	
        return hijos; //esto no esta bien, deberia ser nueva
	}
	
	//Metodo para cruzar 2 cromosomas
	public abstract void cruzar(Chromosome padre1, Chromosome padre2);
}
