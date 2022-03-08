package selection;

import java.util.ArrayList;
import java.util.List;

import individual.Chromosome;

public class SelectionTruncation extends Selection {

	//Seleccion por truncamiento:
	
	//Param es el porcentaje de truncamiento ( Probabilidad del 50% [0.5] o del 10% [0.1] )
	@Override
	public void selection(List<Chromosome> poblation, Object param) {

		//Revisar esta variable
		double aElegir = (double) param;
		
		//Numero de veces que hay que seleccionar a cada individuo
		double numRep = 1.0 / (double) param;
		selectedPopulation = new ArrayList<>();
		
		for(int i = 0; i < aElegir; i++)
			for(int j = 0; j < numRep; j++)
				//A�adimos a la mejor poblacion repetida
				selectedPopulation.add(poblation.get(i));		
	}

}
