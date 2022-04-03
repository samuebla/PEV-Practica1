package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossOX extends Cross {

	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2) {

		Chromosome h1 = new Chromosome(padre1);
		Chromosome h2 = new Chromosome(padre2);

		Chromosome p1 = new Chromosome(padre1);
		Chromosome p2 = new Chromosome(padre2);

		//Copiamos los genes y los hijos
		List<Gen> genes1 = p1.getGens();
		List<Gen> genes2 = p2.getGens();

		List<Gen> hijo1 = h1.getGens();
		List<Gen> hijo2 = h2.getGens();

		
		for (int i = 1; i < genes1.size() - 1; i++) {
			hijo1.get(i).setGenotype(-1);
			hijo2.get(i).setGenotype(-1);
		}

		//Preparamos el corte doble
		int c1 = ThreadLocalRandom.current().nextInt(1, genes1.size() - 1);
		int c2 = c1;

		//Hasta que no sean distintos no se deja de hacer
		while (c1 == c2)
			c2 = ThreadLocalRandom.current().nextInt(1, genes1.size() - 1);

		//Intercambiamos los genes de dentro del corte
		for (int i = Math.min(c1, c2); i < Math.max(c1, c2); i++) {
			hijo1.set(i, genes2.get(i));
			hijo2.set(i, genes1.get(i));
		}

		//Comenzamos desde el corte de la derecha SIEMPRE
		int i = Math.max(c1, c2);
		int acum = i;

		fill(genes1, hijo1, c1, c2, i, acum, genes1.size());

		
		//Y vuelve a hacer lo mismo con el otro hijo
		i = Math.max(c1, c2);
		acum = i;

		fill(genes2, hijo2, c1, c2, i, acum, genes1.size());
		
		//Te crea dos hijos
		this.sons = new ArrayList<>();		
		this.sons.add(new Chromosome(hijo1));
		this.sons.add(new Chromosome(hijo2));
	}
	
	private void fill(List<Gen> genes, List<Gen> hijo, int c1, int c2, int i, int acum, int size) {
		//Desde el corte de la derecha hasta el final
		while (i < hijo.size() - 1) {
			if (!hijo.contains(genes.get(acum))) {
				hijo.set(i, genes.get(acum));
				i++;
			}

			acum++;
			//Al final del todo vuelve a comenzar
			if (acum == size - 1)
				acum = 0;
		}

		//Volvemos a comenzar por la izquierda
		i = 0;

		//Y desde 0 hasta el primer corte
		while (i < Math.min(c1, c2)) {
			if (!hijo.contains(genes.get(acum))) {
				hijo.set(i, genes.get(acum));
				i++;
			}
			acum++;
		}
	}

}
