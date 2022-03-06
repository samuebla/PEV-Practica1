package crosses;

import java.util.ArrayList;
import java.util.List;

import genetics.Gen;
import individual.Chromosome;

public class CrossArithmetic extends Cross {

	//Cruce aritmetico
	
	//CREO QUE LO TENGO MAL AAAAAAA
	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2, int param) {
		
		//Cogemos el fenotipo de los dos cromosomas
		List<Double> fenotipo1 = padre1.getFenotipo();
		List<Double> fenotipo2 = padre2.getFenotipo();

		//Creamos el padre y cogemos sus genes
		Chromosome copia_padre = new Chromosome(padre1);
		List<Gen> hijo = copia_padre.getGenes();
		
		for(int i = 0; i < fenotipo1.size(); i++){
			//Hacemos la media de los dos cromosomas
			double media = (fenotipo1.get(i) + fenotipo2.get(i)) / 2;
			
			hijo.get(i).setGenotipo(media);
			
			if(hijo.get(i).getFenotipo() < hijo.get(i).getMin())
				System.out.println("stop");
		}
		
		this.hijos = new ArrayList<>();
//		this.hijos.add(new Chromosome(hijo));
	}

}
