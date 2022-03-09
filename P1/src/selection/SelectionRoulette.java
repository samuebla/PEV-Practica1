package selection;

import java.util.ArrayList;
import java.util.List;

import individual.Chromosome;
import utils.Params;

public class SelectionRoulette extends Selection {

	//Seleccion por ruleta
	@Override
	public void selection(List<Chromosome> poblation, Object param) {
		//Creamos la lista de la poblacion
		selectedPopulation = new ArrayList<>();
		double rand;
		
		int posSelected;
		Params params = (Params) param;
		int nSelected = params.sizePopulation;
		
		for(int i = 0; i < nSelected; i++){
			//Cogemos un numero aleatorio
			rand = Math.random();
			posSelected = 0;
			//Mientras sea menor al acumulado y siga siendo menor que la poblaci�n
			while(rand > poblation.get(posSelected).getAcumulado() && posSelected < poblation.size()) posSelected++;
			//Y a�adimos al array posicionandolo
			selectedPopulation.add(i, poblation.get(posSelected));
		}
	}

}
