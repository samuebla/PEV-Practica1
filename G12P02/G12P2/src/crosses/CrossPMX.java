package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossPMX extends Cross {

	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2) {
		
		//Coge el gen más bajo y el más alto para ver el rango
		int min = (int) padre1.getGens().get(0).getMin();
		int max = (int) padre1.getGens().get(0).getMax();

		Chromosome h1 = new Chromosome(padre1);
		Chromosome h2 = new Chromosome(padre2);

		Chromosome p1 = new Chromosome(padre1);
		Chromosome p2 = new Chromosome(padre2);

		//Cogemos los genes de los padres y de los hijos
		List<Gen> genes1 = p1.getGens();
		List<Gen> genes2 = p2.getGens();

		List<Gen> hijo1 = h1.getGens();
		List<Gen> hijo2 = h2.getGens();
		
		//A un hijo le pone el rango más alto y al otro el "mas bajo"
		for (int i = 1; i < genes1.size() - 1; i++) {
			hijo1.get(i).setGenotype(max);
			
			//Ponemos -1 para comprobar si se ha llegado a cambiar al hacer el cruce más adelante
			hijo2.get(i).setGenotype(-1);
		}
		

		//Preparamos el corte doble
		int c1 = ThreadLocalRandom.current().nextInt(min + 1, max - 1);
		int c2 = c1;

		//Sigue haciendo numeros aleatorios hasta que son distintos
		while (c1 == c2)
			c2 = ThreadLocalRandom.current().nextInt(min + 1, max - 1);
		
		//Se recorre de izquierda a derecha
		for (int i = Math.min(c1, c2); i <= Math.max(c1, c2); i++) {
			//Y ponemos a un hijo los genes del otro y vicecersa
			hijo1.set(i, genes2.get(i));
			hijo2.set(i, genes1.get(i));
		}
		//Aqui los genes del corte ya están cruzados
		
		//Comenzamos de derecha a izquierda
		for (int i = 1; i < hijo1.size() - 1; i++) {
			//Si el fenotipo correspondiente no está repetido en el corte que hicimos arriba
			if (hijo1.get(i).getGenFenotype() == -1 && !hijo1.contains(genes1.get(i)))
				//Lo cambio sin ningun problema
				hijo1.set(i, genes1.get(i));
			//Y lo mismo pero con el otro hijo
			if (hijo2.get(i).getGenFenotype() == -1 && !hijo2.contains(genes2.get(i)))
				hijo2.set(i, genes2.get(i));
		}
		
		//Aqui todos los genes no repetidos se han colocado correctamente		
		while (tieneDuplicados(hijo1) || tieneDuplicados(hijo2)) {
			for (int i = 1; i < hijo1.size() - 1; i++) {
				//Si el gen sigue sin establecerse...
				if (hijo1.get(i).getGenFenotype() == -1){
					//Te devuelve el indice de donde se encuentra el valor genes1.get(i)
					//RECUERDA Que genes1 y genes2 son los del inicio sin cambiar nada
					hijo1.set(i, genes1.get(genes2.indexOf(genes1.get(i))));
				}
				//Este duplicado tampoco tiene ningun sentido AAAAAAAAAAAAAAAAAAAAAAAAAAA
				else if (duplicado(hijo1, i)) {
					hijo1.set(i, genes1.get(genes2.indexOf(hijo1.get(i))));
				}
				
				//Y lo mismo pero con el otro hijo
				if (hijo2.get(i).getGenFenotype() == -1) {
					hijo2.set(i, genes2.get(genes1.indexOf(genes2.get(i))));
				}
				else if (duplicado(hijo2, i)) {
					hijo2.set(i, genes2.get(genes1.indexOf(hijo2.get(i))));
				}
			}
		}

		//Por ultimo añadimos los dos hijos
		this.sons = new ArrayList<>();
		this.sons.add(new Chromosome(hijo1));
		this.sons.add(new Chromosome(hijo2));
	}

	//REVISAR ESTO PORQUE EL CHECK NO ME CUADRA UNA MIERDA AAAAAAAAAAAAAAAAAAAAAAAA
	private boolean tieneDuplicados(List<Gen> hijo1) {
		List<Gen> check = new ArrayList<>();
		for (int i = 1; i < hijo1.size() - 1; i++) {
			//Si hay algun gen sin poner...
			if(hijo1.get(i).getGenFenotype() != -1 && !check.contains(hijo1.get(i)))
				check.add(hijo1.get(i));
			else return true;
		}
		return false;
	}

	//Este metodo literalmente no tiene ningun sentido y no se que pinta aqui AAAAAAAAAAAA
	private boolean duplicado(List<Gen> hijo1, int index) {
		//El metodo mas ineficiente de toda mi puta existencia
		for (int i = 1; i < hijo1.size() - 1; i++) {
			if ((hijo1.get(i).equals(hijo1.get(index))) &&
					(i != index)) {
				return true;
			}
		}
		return false;
	}
}
