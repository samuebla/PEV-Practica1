package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import individual.Chromosome;

//CLASE ABSTRACTA PARA LOS TIPOS DE CRUCE
public abstract class Cross {

	protected int cut = -1;
	protected List<Chromosome> sons;
	
	public List<Chromosome> getHijos() {
		return this.sons;
	}
	
	public List<Chromosome> reproduce(List<Chromosome> population, double probCross) {

		Random r = new Random();
		ArrayList<Integer> elegidos = new ArrayList<>(); 
		this.sons = new ArrayList<Chromosome>();
		//Se realiza el cruce sobre una lista nueva de cromosomas y se devuelve
        for (int i = 0; i < population.size(); i++){
            if (r.nextDouble() < probCross){
                elegidos.add(i);
            }
        }
        
        if(elegidos.size()%2 != 0){ //Deben ser pares para que haya dos padres
            elegidos.remove(elegidos.size() - 1);
        }
        
        cut = ThreadLocalRandom.current().nextInt(0, population.get(0).getTam());
        
        for (int i = 0; i < elegidos.size(); i += 2){ 
        	cruzar(population.get(elegidos.get(i)), population.get(elegidos.get(i+1)));
        	//Reemplazamos a los padres por sus hijos
        	population.set(elegidos.get(i), this.sons.get(0));
        	if(this.sons.size() > 1)
        		population.set(elegidos.get(i + 1), this.sons.get(1));  
        	this.sons.clear();
        }
//        
//        if(probCross < 1) {
//        	int pop = population.size();
//        	int hij = sons.size();
//        	int rest =  pop - hij;
//        	
//        	sons.addAll(population.subList(rest - 1, population.size() - 1));
//        }
        	
        return population; //esto no esta bien, deberia ser nueva
	}
	
	//Metodo para cruzar 2 cromosomas
	public abstract void cruzar(Chromosome padre1, Chromosome padre2);
}
