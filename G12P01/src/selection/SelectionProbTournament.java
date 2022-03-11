package selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import individual.Chromosome;

public class SelectionProbTournament extends Selection {

	@Override
	public void selection(List<Chromosome> poblation, Object param) {

		selectedPopulation = new ArrayList<>();
		//Numero de veces que se repite el proceso para escoger el mejor/peor
		int k = poblation.size();
		
		Random rand = new Random();
		// Generalmente p toma valores en el rango [0.5 , 1]
		double p = rand.nextDouble(0.5, 1.0);
		
		
		//Cogemos 2 o 3 muestras en funcion del tamaño de la poblacion
		int tamTorneo = 2;
		
		if(poblation.size() > 10) tamTorneo = 3;
		
		for(int i = 0; i < k; i++){
			
			int select = (int) (Math.random() * (poblation.size()));
			int selectMax = select;
			int selectMin = select;
			
			for(int j = 1; j < tamTorneo; j++){
				
				//Seleccionamos a una persona aleatoria
				select = (int) (Math.random() * (poblation.size()));
				
				//Seleccionamos el mejor
				if(poblation.get(select).getAdaptedFitness() > poblation.get(selectMax).getAdaptedFitness())
					selectMax = select;
				//Y el peor
				if(poblation.get(select).getAdaptedFitness() < poblation.get(selectMax).getAdaptedFitness())
					selectMin = select;
			}
			
			//Cogemos un numero entre 0/1; Si es menor a p
			if(p < Math.random() * 1)
				//Se coge al mejor
				selectedPopulation.add(poblation.get(selectMax));
			//Si es mayor a p
			else
				//Cogemos el peor
				selectedPopulation.add(poblation.get(selectMin));
		}
	}

}
