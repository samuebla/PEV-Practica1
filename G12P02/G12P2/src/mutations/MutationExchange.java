package mutations;

import java.util.List;

import genetics.Gen;
import individual.Chromosome;
import utils.Params;

public class MutationExchange extends Mutation {

	// Mutacion por intercambio
	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {
		// Cogemos la probabilidad de mutacion establecida en la interfaz
		Params params = (Params) params_;
		double probabilidad = params.mutProb;

		// Lo hacemos por cada cromosoma que vayamos a mutar
		for (Chromosome i : poblation) {
			// Y hacemos un random de 0 a 1 para ver si lo cambiamos o no
			double random = Math.random();
			// Si hay mutacion...
			if (random < probabilidad) {
				int pos1, pos2;
				int size = i.getGens().size();

				do {
					// Cogemos una posicion dentro del rango para el primer swap
					pos1 = (int) (Math.random() * size);
				} while (pos1 == 0 || pos1 == size - 1);

				do {
					// Y lo mismo para el segundo
					pos2 = (int) (Math.random() * size);
				} while (pos2 == 0 || pos2 == size - 1 || pos2 == pos1);

				// Y luego intercambiamos la posicion de cada uno
				Gen aux = i.getGens().get(pos1);
				i.getGens().set(pos1, i.getGens().get(pos2));
				i.getGens().set(pos2, aux);
			}
		}
		this.mPoblation = poblation;

	}

}
