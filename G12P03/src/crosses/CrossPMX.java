package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;
import utils.Params;

public class CrossPMX extends Cross {

	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2, Object params_) {

		Chromosome h1 = new Chromosome(padre1);
		Chromosome h2 = new Chromosome(padre2);
		List<Gen> hijo1 = h1.getGens();
		List<Gen> hijo2 = h2.getGens();


		Chromosome p1 = new Chromosome(padre1);
		Chromosome p2 = new Chromosome(padre2);
		List<Gen> genes1 = p1.getGens();
		List<Gen> genes2 = p2.getGens();


		// A un hijo le pone el rango más alto y al otro el "mas bajo"
		for (int i = 0; i < genes1.size(); i++) {
			// Ponemos -1 para comprobar si se ha llegado a cambiar al hacer el cruce más
			// adelante
			hijo1.get(i).setGenotype(-1);
			hijo2.get(i).setGenotype(-1);
		}

		// Preparamos el corte doble
		int c1 = ThreadLocalRandom.current().nextInt(1, genes1.size() - 1);
		int c2 = c1;

		// Sigue haciendo numeros aleatorios hasta que son distintos
		while (c1 == c2)
			c2 = ThreadLocalRandom.current().nextInt(1, genes1.size() - 1);

		exchangeMiddle(c1, c2, hijo1, hijo2, genes1, genes2);

		setNoDuplicates(hijo1, hijo2, genes1, genes2);
		
		for (int i = 0; i < hijo1.size(); i++) {
			
			boolean inserted = false;
			if (hijo1.get(i).getGenGenotype() == -1) {
				// Te devuelve el indice de donde se encuentra el valor genes1.get(i)
				Gen genToInstert = genes1.get(i);
				
				while(!inserted) {
					int indexHomologous = genes2.indexOf(genToInstert);
					Gen homologous = genes1.get(indexHomologous);
					
					if(!hijo1.contains(homologous)) {
						hijo1.set(i, homologous);
						inserted = true;
					}
					else {
						genToInstert = homologous;
					}
				}
			}
			
			inserted = false;
			
			if (hijo2.get(i).getGenGenotype() == -1) {
				// Te devuelve el indice de donde se encuentra el valor genes1.get(i)
				Gen genToInstert = genes2.get(i);
				
				while(!inserted) {
					int indexHomologous = genes1.indexOf(genToInstert);
					Gen homologous = genes2.get(indexHomologous);
					
					if(!hijo2.contains(homologous)) {
						hijo2.set(i, homologous);
						inserted = true;
					}
					else {
						genToInstert = homologous;
					}
				}
			}
		}
		
		Params params = (Params) params_;
		params.numCrosses++;
        
		
		// Por ultimo añadimos los dos hijos
		this.sons = new ArrayList<>();
		this.sons.add(new Chromosome(hijo1));
		this.sons.add(new Chromosome(hijo2));
	}
	
	
	private void exchangeMiddle(int c1, int c2, List<Gen> hijo1, List<Gen> hijo2, List<Gen> genes1, List<Gen> genes2) {
		for (int i = Math.min(c1, c2); i <= Math.max(c1, c2); i++) {
			// Y ponemos a un hijo los genes del otro y vicecersa
			hijo1.set(i, genes2.get(i));
			hijo2.set(i, genes1.get(i));
		}
	}
	
	private void setNoDuplicates(List<Gen> hijo1, List<Gen> hijo2, List<Gen> genes1, List<Gen> genes2) {
		for (int i = 0; i < hijo1.size(); i++) {
			// Si el fenotipo correspondiente no está repetido en el corte que hicimos
			// arriba
			if (hijo1.get(i).getGenGenotype() == -1 && !hijo1.contains(genes1.get(i)))
				// Lo cambio sin ningun problema
				hijo1.set(i, genes1.get(i));
			// Y lo mismo pero con el otro hijo
			if (hijo2.get(i).getGenGenotype() == -1 && !hijo2.contains(genes2.get(i)))
				hijo2.set(i, genes2.get(i));
		}
	}
}
