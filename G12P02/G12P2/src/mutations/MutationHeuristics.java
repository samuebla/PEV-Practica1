package mutations;

import java.util.ArrayList;
import java.util.List;

import functions.FunctionP2;
import genetics.Gen;
import individual.Chromosome;
import utils.Params;

public class MutationHeuristics extends Mutation {

	private Chromosome best;

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {
		// Cogemos la probabilidad seleccionada
		Params params = (Params) params_;
		double probabilidad = params.mutProb;

		// Guardamos la funcion para la euristica
		FunctionP2 function = params.funct2;

		for (Chromosome ind : poblation) {
			double random = Math.random();
			// Si muta...
			if (random < probabilidad) {
            	params.numMutations++;
				// Cogemos 3 posiciones aleatorias (En el video pone entre 2/3)
				int pos1, pos2, pos3;
				int size = ind.getGens().size();

				do {
					pos1 = (int) (Math.random() * size);
				} while (pos1 == 0 || pos1 == size - 1);

				do {
					pos2 = (int) (Math.random() * size);
				} while (pos2 == 0 || pos2 == size - 1 || pos2 == pos1);

				do {
					pos3 = (int) (Math.random() * size);
				} while (pos3 == 0 || pos3 == size - 1 || pos3 == pos1 || pos3 == pos2);

				// Queremos saber cual es el mejor cromosoma
				this.best = new Chromosome(new ArrayList<>(ind.getGens()));
				List<Integer> posiciones = new ArrayList<>();
				posiciones.add(pos1);
				posiciones.add(pos2);
				posiciones.add(pos3);

				// Metemos los 3 valores para hacer la permutacion
				List<Gen> conjunto = new ArrayList<>();
				conjunto.add(ind.getGens().get(pos1));
				conjunto.add(ind.getGens().get(pos2));
				conjunto.add(ind.getGens().get(pos3));

				List<Gen> resultado = new ArrayList<>();

				Chromosome indCopy = new Chromosome(new ArrayList<>(ind.getGens()));

				permutaciones(indCopy, conjunto, resultado, posiciones, function);
				ind.setGens(this.best.getGens());
			}
		}
		this.mPoblation = poblation;

	}

	private void permutaciones(Chromosome individual, List<Gen> conjunto, List<Gen> result, List<Integer> positions,
			FunctionP2 func) {

		//Cuando hayas permutado las 3
		if (conjunto.size() == 0) {

			//Las añades 
			individual.getGens().set(positions.get(0), result.get(0));
			individual.getGens().set(positions.get(1), result.get(1));
			individual.getGens().set(positions.get(2), result.get(2));
			//Y Nos quedamos con el que tenga mejor fitness de la funcion
			if (func.ejecutar(individual.getGens()) < func.ejecutar(this.best.getGens()))
				// Nos guardamos el mejor cromosoma
				this.best = new Chromosome(new ArrayList<>(individual.getGens()));
		}

		for (int i = 0; i < conjunto.size(); i++) {
			//Eliminamos uno de la permutacion
			Gen b = conjunto.remove(i);
			result.add(b);
			//Y hacemos lo mismo
			permutaciones(individual, conjunto, result, positions, func);
			result.remove(b);
			conjunto.add(i, b);
		}
	}

}
