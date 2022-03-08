package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossMonopoint extends Cross {

	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2, int param) {
		
		int min = 0;
		int max = padre1.getTam();

		//Cogemos un numero aleatorio para ver donde cortar
		int cut = ThreadLocalRandom.current().nextInt(min, max);
		
		//Creamos los 2 cromosomas hijos
		Chromosome h1 = new Chromosome(padre1);
		Chromosome h2 = new Chromosome(padre2);

		//Y accedemos a sus genes
		List<Gen> genes1 = h1.getGenes();
		List<Gen> genes2 = h2.getGenes();
		
		//Y a sus alelos
		List<Object> alelos1 = padre1.getAlelos();
		List<Object> alelos2 = padre2.getAlelos();
		
		//Añadimos mitad del primero mitad del segundo
		List<Object> hijo1 = alelos1.subList(0,cut);
		hijo1.addAll(alelos2.subList(cut,max));

		//Y viceversa
		List<Object> hijo2 = alelos2.subList(0,cut);
		hijo2.addAll(alelos1.subList(cut,max));

		int acum = 0;
		
		//Dividimos los genes para establecer sus alelos
		for(Gen g : genes1) {
			g.setAlelos(hijo1.subList(acum, acum+g.getTam()));
			acum += g.getTam();
		}
		
		acum = 0;
		
		//Y viceversa
		for(Gen g : genes2) {
			g.setAlelos(hijo2.subList(acum, acum+g.getTam()));
			acum += g.getTam();
		}
		
		this.hijos = new ArrayList<Chromosome>();

		this.hijos.add(new Chromosome(genes1));
		this.hijos.add(new Chromosome(genes2));
	}

}
