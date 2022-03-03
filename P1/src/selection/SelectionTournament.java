package selection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import individual.Individual;

public class SelectionTournament extends Selection {	

	//Seleccion por torneo:
	//Seleccion determinística (Siempre se coge el mejor)
	
	@Override
	public void selection(List<Individual> poblation, Object param) {

		poblacionSeleccionada = new ArrayList<Individual>();
		int n = (int) param;
		
		//Se coge el mejor de los individuos de un conjunto de 2 elementos
		int tamTorneo = 2;
		
		//o de 3 en funcion del tamaño de la poblacion
		if(poblation.size() > 10) tamTorneo = 3;
		
		//El proeso se repite N veces
		for(int i = 0; i < n; i++){
			
			List<Individual> seleccionados = new ArrayList();
			
			//Añadimos los individuos a la lista
			for(int j = 1; j < tamTorneo; j++)
				seleccionados.add(poblation.get(ThreadLocalRandom.current().nextInt(0,poblation.size())));
			
			//Y nos quedamos con el mejor de la muestra
			Individual mejor = seleccionados.get(0);
			for(Individual ind : seleccionados)
				if(ind.getFitnessAdaptado() > mejor.getFitnessAdaptado())
					mejor = ind;
			
			//Nos quedamos en el mejor de cada vuelta
			poblacionSeleccionada.add(mejor);
		}
	}

}
