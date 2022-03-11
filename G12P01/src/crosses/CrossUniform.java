package crosses;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossUniform extends Cross {

	@Override
	public void cruzar(Chromosome father1, Chromosome father2) {

		//Damos por entendido que tienen el mismo tamaño de alelos
		List<Object> alelos1 = father1.getAlleles();
		List<Object> alelos2 = father2.getAlleles();
		
		List<Object> son1 = new ArrayList<>();
		List<Object> son2 = new ArrayList<>();
		
		//Vamos avanzando alelo por alelo
		for(int i = 0; i < alelos1.size(); i++){
			//50% de probabilidad
			int random = ThreadLocalRandom.current().nextInt(0, 10);
			
			if(random >= 5)
			{
				//Lo cambiamos
				son1.add(alelos2.get(i));
				son2.add(alelos1.get(i));
			} else
			{
				//Lo dejamos igual
				son1.add(alelos1.get(i));
				son2.add(alelos2.get(i));
			}	
		}
		
		int sum = 0;
		
		//Copiamos a los padres
		Chromosome h1 = new Chromosome(father1);
		Chromosome h2 = new Chromosome(father2);

		List<Gen> gens1 = h1.getGens();
		List<Gen> gens2 = h2.getGens();
		
		for(Gen g : gens1) {
			//Devuelve la lista de alelos desde SUM hasta SUM+G
			//Para realizar asi el corte
			g.setAlleles(son1.subList(sum, sum+g.getTam()));
			sum++;
		}
		
		sum = 0;
		
		for(Gen g : gens2) {
			g.setAlleles(son2.subList(sum, sum+g.getTam()));
			sum++;
		}
		
		this.sons.add(new Chromosome(gens1));
		this.sons.add(new Chromosome(gens2));
	}

}
