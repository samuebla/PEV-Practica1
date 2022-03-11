package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossBLX extends Cross {
	
	@Override
	public void cruzar(Chromosome father1, Chromosome father2) {		
		List<Double> phenotype1 = father1.getPhenotype();
		List<Double> phenotype2 = father2.getPhenotype();

		//Creamos 2 hijos
		Chromosome s1 = new Chromosome(father1);
		Chromosome s2 = new Chromosome(father2);

		//Cogemos sus genes
		List<Gen> son1 = s1.getGens();
		List<Gen> son2 = s2.getGens();
		
		
		for(int i = 0; i < son1.size(); i++){
			
			//Calculamos el fenotipo mas grade y pequeño de ambos
			double max = Math.max(phenotype1.get(i), phenotype2.get(i));
			double min = Math.min(phenotype1.get(i), phenotype2.get(i));

				
			double alpha = 0,alphaI = 0,minI = 0,maxI = 0;
			check(alpha, alphaI, minI, maxI, max, min);
			
			while(minI == maxI) {
				check(alpha, alphaI, minI, maxI, max, min);
			} 
			
			//Y le ponemos o el mas grande o el mas pequeño
			son1.get(i).randomize(minI, maxI);
			son2.get(i).randomize(minI, maxI);
		}
		
		this.sons.add(new Chromosome(son1));
		this.sons.add(new Chromosome(son2));
	}
	
	void check(double alpha, double alphaI, double minI, double maxI, double max, double min) {
		//Cogemos un numero de 0 a 1
		alpha = ThreadLocalRandom.current().nextDouble(0, 1);
		
		//Y multiplicamos la diferencia por el numero
		alphaI = (max - min) * alpha;

		//Sumamos y restamos la diferencia a los numeros
		minI = min - alphaI;
		maxI = max + alphaI;

		//Si el numero con la diferencia es mas pequeño/mayor que el original se sustituye el num original
		if(minI < son1.get(i).getMin()) minI = son1.get(i).getMin();
		if(maxI > son1.get(i).getMax() || maxI < minI) maxI = son1.get(i).getMax();
	}

}
