package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossMonopoint extends Cross {

	@Override
	public void cruzar(Chromosome parent_1, Chromosome parent_2) {
		
		//Cogemos un numero aleatorio para ver donde cortar
		int sizeChromosome = parent_1.getTam();
		int cut = ThreadLocalRandom.current().nextInt(0, parent_1.getTam());
		
		//Creamos los 2 cromosomas hijos
		Chromosome copyParent_1 = new Chromosome(parent_1);
		Chromosome copyParent_2 = new Chromosome(parent_2);

		//Alelos de cada padre. Devuelve una lista con los dos genes en "un" gen 
		List<Object> alleles1 = parent_1.getAlleles();
		List<Object> alleles2 = parent_2.getAlleles();
		
		//Y accedemos a sus genes
		List<Gen> gen_1 = copyParent_1.getGens();
		List<Gen> gen_2 = copyParent_2.getGens();
		
		//Añadimos mitad del primero mitad del segundo
		List<Object> newAlleles_1 = alleles1.subList(0,cut);
		newAlleles_1.addAll(alleles2.subList(cut,sizeChromosome));

		//Y viceversa
		List<Object> newAlleles_2 = alleles2.subList(0,cut);
		newAlleles_2.addAll(alleles1.subList(cut,sizeChromosome));

		int sum = 0;
		
		//Ya que newAllels contiene los alelos de ambos genes. Ahora hay que repatirlos de nuevo
		for(Gen g : gen_1) {
			g.setAlleles(newAlleles_1.subList(sum, sum + g.getTam()));
			sum += g.getTam();
		}
		
		sum = 0;
		
		//Y viceversa
		for(Gen g : gen_2) {
			g.setAlleles(newAlleles_2.subList(sum, sum + g.getTam()));
			sum += g.getTam();
		}
		
		this.sons.add(new Chromosome(gen_1));
		this.sons.add(new Chromosome(gen_2));
	}

}
