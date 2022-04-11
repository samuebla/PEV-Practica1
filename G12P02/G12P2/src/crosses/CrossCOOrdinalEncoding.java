package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;
import utils.Params;

public class CrossCOOrdinalEncoding extends Cross {

	@Override
	public void cruzar(Chromosome father1, Chromosome father2, Object params_) {

		// Creamos los cromosomas del hijo
		Chromosome h1 = new Chromosome(father1);
		Chromosome h2 = new Chromosome(father2);

		Chromosome p1 = new Chromosome(father1);
		Chromosome p2 = new Chromosome(father2);

		List<Gen> genes1 = p1.getGens();
		List<Gen> genes2 = p2.getGens();

		List<Gen> son1 = h1.getGens();
		List<Gen> son2 = h2.getGens();

		// Listas para ayudar a codificar
		List<Integer> list1 = new ArrayList<>();
		List<Integer> list2 = new ArrayList<>();

		// Codificamos
		encoding(genes1, list1);
		encoding(genes2, list2);

		// Ahora preparamos el cruce monopunto COMO LA PRACTICA 1, es igual
		int random = ThreadLocalRandom.current().nextInt(1, genes1.size() - 1);

		// Cambiamos la mitad de uno
		List<Integer> monopoint1 = list1.subList(0, random);
		// Por la mitad del otro
		monopoint1.addAll(list2.subList(random, genes1.size()));

		// Y viceversa
		List<Integer> monopoint2 = list2.subList(0, random);
		monopoint2.addAll(list1.subList(random, genes1.size()));

		// Y descodificamos
		decoding(son1, monopoint1);
		decoding(son2, monopoint2);

		
		Params params = (Params) params_;
		params.numCrosses++;
		
		// Y añadimos los dos as always
		this.sons = new ArrayList<>();

		this.sons.add(new Chromosome(son1));
		this.sons.add(new Chromosome(son2));
	}

	private void decoding(List<Gen> son1, List<Integer> monopoint) {

		List<Integer> pos = new ArrayList<>();

		// Igual que codificando
		for (int i = 0; i < son1.size(); i++)
			// Creamos nuestro array de valores ordenados
			pos.add(i);

		//Decodificamos
		for (int i = 0; i < son1.size(); i++) {
			//Cogemos la lista de monopunto
			int index = monopoint.get(i);
			double feno = pos.get(index);
		    son1.get(i).setGenotype(feno);
			//Y lo borramos de la lista
			pos.remove(index);
		}

	}

	private void encoding(List<Gen> genes, List<Integer> aux) {
		List<Integer> listIndex = new ArrayList<>();

		// Creamos un array con numeros de 0 al tamaño del hijo
		for (int i = 0; i < genes.size(); i++)
			listIndex.add(i);

		// Orden
		for (int i = 0; i < genes.size(); i++) {
			Gen g = genes.get(i);
			// Buscamos el valor del gen en la lista ordenada y se añade en esa misma
			// posicion
			double feno = g.getGenGenotype();
			int index = listIndex.indexOf((int) feno); 
			aux.add(index);

			// Y borramos el valor y la posicion de ese fenotipo, disminuyendo el tamaño del
			// array
			listIndex.remove(index);
		}
	}

}
