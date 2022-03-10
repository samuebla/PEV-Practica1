package crosses;

import java.util.ArrayList;
import java.util.List;

import genetics.Gen;
import individual.Chromosome;

public class CrossArithmetic extends Cross {

	//Cruce aritmetico
	@Override
	public void cruzar(Chromosome father1, Chromosome father2) {
		
		//Cogemos el fenotipo de los dos cromosomas
		List<Double> phenotype1 = father1.getPhenotype();
		List<Double> phenotype2 = father2.getPhenotype();

		//Creamos el padre auxiliar y cogemos sus genes
		Chromosome copy_Father = new Chromosome(father1);
		List<Gen> hijo = copy_Father.getGens();
		
		for(int i = 0; i < phenotype1.size(); i++){
			//Hacemos la media de los dos cromosomas
			double media = (phenotype1.get(i) + phenotype2.get(i)) / 2;
			
			hijo.get(i).setGenotype(media);
			
			if(hijo.get(i).getGenFenotype() < hijo.get(i).getMin())
				System.out.println("stop");
		}
		
		this.hijos = new ArrayList<>();
		this.hijos.add(new Chromosome(hijo));
	}

}