package selection;

import java.util.ArrayList;
import java.util.List;

import individual.Chromosome;
import utils.Params;

public class SelectionTruncation extends Selection {

	//Seleccion por truncamiento:
	
	//Param es el porcentaje de truncamiento ( Probabilidad del 50% [0.5] o del 10% [0.1] )
	@Override
	public void selection(List<Chromosome> poblation, Object param) {

		//Revisar esta variable
		Params params = (Params) param;
		double n = params.truncProb;
		
		
		//Numero de veces que hay que seleccionar a cada individuo
		double prop = n / 100;
		double numRep = 1.0 / prop;
		selectedPopulation = new ArrayList<>();
		
		int limit = poblation.size() - (int)n;

		
		for(int i = poblation.size() - 1; i >= limit; i--)
			for(int j = 0; j < numRep; j++)
				//Añadimos a la mejor poblacion repetida
				selectedPopulation.add(poblation.get(i));		
	}

}
