package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossBLX extends Cross {
	
	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2) {		
				List<Double> fenotipo1 = padre1.getFenotype();
				List<Double> fenotipo2 = padre2.getFenotype();

				Chromosome h1 = new Chromosome(padre1);
				Chromosome h2 = new Chromosome(padre2);

				List<Gen> hijo1 = h1.getGens();
				List<Gen> hijo2 = h2.getGens();
				
				
				for(int i = 0; i < hijo1.size(); i++){
					//Calculamos el fenotipo mas grade y pequeño de ambos
					double cmax = Math.max(fenotipo1.get(i), fenotipo2.get(i));
					double cmin = Math.min(fenotipo1.get(i), fenotipo2.get(i));

					//En caso de que sea el mismo numero
					if(cmax == cmin){
						//Pues se pone sin más
						hijo1.get(i).setGenotype(cmin);
						hijo2.get(i).setGenotype(cmin);
					}	
					else
					{
						double alfa,Ialfa,minI,maxI;

						do {
							//Cogemos un numero de 0 a 1
							alfa = ThreadLocalRandom.current().nextDouble(0, 1);
							//Y multiplicamos la diferencia por el numero
							Ialfa = (cmax - cmin) * alfa;

							//Sumamos y restamos la diferencia a los numeros
							minI = cmin - Ialfa;
							maxI = cmax + Ialfa;

							//Si el numero con la diferencia es mas pequeño/mayor que el original se sustituye el num original
							if(minI < hijo1.get(i).getMin()) minI = hijo1.get(i).getMin();
							if(maxI > hijo1.get(i).getMax() || maxI < minI) maxI = hijo1.get(i).getMax();
						}while(minI == maxI);
						
						//Y le ponemos o el mas grande o el mas pequeño
						hijo1.get(i).randomize(minI, maxI);
						hijo2.get(i).randomize(minI, maxI);
					}
				}
				
				this.hijos = new ArrayList<>();
//				this.hijos.add(new Chromosome(hijo1));
//				this.hijos.add(new Chromosome(hijo2));
	}

}
