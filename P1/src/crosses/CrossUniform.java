package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossUniform extends Cross {

	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2, int param) {

		List<Object> alelos1 = padre1.getAlelos();
		List<Object> alelos2 = padre2.getAlelos();
		
		List<Object> hijo1 = new ArrayList<>();
		List<Object> hijo2 = new ArrayList<>();
		
		for(int i = 0; i < alelos1.size(); i++){
			int random = ThreadLocalRandom.current().nextInt(0, 10);
			
			if(random >= 5){
				hijo1.add(alelos2.get(i));
				hijo2.add(alelos1.get(i));
			}else{
				hijo1.add(alelos1.get(i));
				hijo2.add(alelos2.get(i));
			}	
		}
		
		int acum = 0;
		Chromosome h1 = new Chromosome(padre1);
		Chromosome h2 = new Chromosome(padre2);

		List<Gen> genes1 = h1.getGenes();
		List<Gen> genes2 = h2.getGenes();
		
		for(Gen g : genes1) {
			g.setAlelos(hijo1.subList(acum, acum+g.getTam()));
			acum++;
		}
		
		acum = 0;
		
		for(Gen g : genes2) {
			g.setAlelos(hijo2.subList(acum, acum+g.getTam()));
			acum++;
		}
		
		this.hijos = new ArrayList<>();
		
//		this.hijos.add(new Chromosome(genes1));
//		this.hijos.add(new Chromosome(genes2));
	}

}
