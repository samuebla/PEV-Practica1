package selection;

import java.util.ArrayList;
import java.util.List;

import individual.Chromosome;

public class SelectionRoulette extends Selection {

	//Seleccion por ruleta
	@Override
	public void selection(List<Chromosome> poblation, Object param) {

		//Creamos la lista de la poblacion
		selectedPopulation = new ArrayList<>();
		double rand;
		
		int posSuper;
		
		for(int i = 0; i < (int)param; i++){
			//Cogemos un numero aleatorio
			rand = Math.random();
			posSuper = 0;
			//Mientras sea menor al acumulado y siga siendo menor que la poblaci�n
			while(rand > poblation.get(posSuper).getAcumulado() && posSuper < poblation.size())
				//Seguimos sumando
				posSuper++;
			//Y a�adimos al array posicionandolo
			selectedPopulation.add(i, poblation.get(posSuper));
		
		}
	}

}
