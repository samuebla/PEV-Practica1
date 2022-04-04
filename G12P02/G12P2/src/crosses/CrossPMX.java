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

		Chromosome p1 = new Chromosome(padre1);
		Chromosome p2 = new Chromosome(padre2);

		// Cogemos los genes de los padres y de los hijos
		List<Gen> genes1 = p1.getGens();
		List<Gen> genes2 = p2.getGens();

		List<Gen> hijo1 = h1.getGens();
		List<Gen> hijo2 = h2.getGens();

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
		while (c1 == c2) {
			c2 = ThreadLocalRandom.current().nextInt(1, genes1.size() - 1);
		}

		// Se recorre de izquierda a derecha
		for (int i = Math.min(c1, c2); i < Math.max(c1, c2); i++) {
			// Y ponemos a un hijo los genes del otro y vicecersa
			hijo1.set(i, genes2.get(i));
			hijo2.set(i, genes1.get(i));
		}
		// Aqui los genes del corte ya están cruzados

		// Desde el segundo corte hasta el final
		for (int i = Math.max(c1, c2); i < hijo1.size(); i++) {
			if (hijo1.get(i).getGenFenotype() == -1 && !hijo1.contains(genes1.get(i))) {
				// Lo cambio sin ningun problema
				hijo1.set(1, genes1.get(i));
			}
			// Y lo mismo pero con el otro hijo
			if (hijo2.get(i).getGenFenotype() == -1 && !hijo2.contains(genes2.get(i))) {

				hijo2.set(i, genes2.get(i));
			}
		}
		

		//Y ahora desde el inicio hasta el primer corte
		for (int i = 0; i < Math.min(c1,c2); i++) {
			// Si el fenotipo correspondiente no está repetido en el corte que hicimos
			// arriba
			if (hijo1.get(i).getGenFenotype() == -1 && !hijo1.contains(genes1.get(i))) {
				// Lo cambio sin ningun problema
				hijo1.set(1, genes1.get(i));
			}
			// Y lo mismo pero con el otro hijo
			if (hijo2.get(i).getGenFenotype() == -1 && !hijo2.contains(genes2.get(i))) {

				hijo2.set(i, genes2.get(i));
			}
		}

		// Aqui todos los genes no repetidos se han colocado correctamente
		for (int i = 0; i < hijo1.size(); i++) {
			// Si el gen sigue sin establecerse...
			if (hijo1.get(i).getGenFenotype() == -1) {
				// Te devuelve el indice de donde se encuentra el valor genes1.get(i)
				// RECUERDA Que genes1 y genes2 son los del inicio sin cambiar nada
				hijo1.set(i, genes1.get(genes2.indexOf(genes1.get(i))));
			}

			// Y lo mismo pero con el otro hijo
			if (hijo2.get(i).getGenFenotype() == -1) {
				hijo2.set(i, genes2.get(genes1.indexOf(genes2.get(i))));
			}

		}

		Params params = (Params) params_;
		params.numCrosses++;

		// Por ultimo añadimos los dos hijos
		this.sons = new ArrayList<>();
		this.sons.add(new Chromosome(hijo1));
		this.sons.add(new Chromosome(hijo2));
	}

}
