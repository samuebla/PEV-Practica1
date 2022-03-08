package selection;

import java.util.ArrayList;
import java.util.List;

import individual.Chromosome;

public class SelectionProbTournament extends Selection {

	//AAAAAAAAAAAAA HAY ERRORES
	@Override
	public void selection(List<Chromosome> poblation, Object param) {

		selectedPopulation = new ArrayList<>();
		//Numero de veces que se repite el proceso para escoger el mejor/peor
//		int k = ((Pair<Integer, Double>) param).getLeft();
		int k = 0; //PROVISIONAL
		
		// Generalmente p toma valores en el rango [0.5 , 1]
//		double p = (double) ((Pair<Object, Object>) param).getRight();
		int p = 0; //PROVISIONAL
		
		
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
				if(poblation.get(select).getFitnessAdaptado() > poblation.get(selectMax).getFitnessAdaptado())
					selectMax = select;
				//Y el peor
				if(poblation.get(select).getFitnessAdaptado() < poblation.get(selectMax).getFitnessAdaptado())
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
