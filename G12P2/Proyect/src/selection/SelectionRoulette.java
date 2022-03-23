package selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import individual.Chromosome;
import utils.Params;

public class SelectionRoulette extends Selection {

	//Seleccion por ruleta
	@Override
	public void selection(List<Chromosome> poblation, Object param) {
		selectedPopulation = new ArrayList<Chromosome>();
		double rand;
		
		int pos_super;
		Params params = (Params) param;
		int nSelected = poblation.size();
		
		for(int i = 0; i < nSelected; i++){
			//Cogemos un numero aleatorio
			rand = Math.random();
			pos_super = 0;
			//Mientras sea menor al acumulado y siga siendo menor que la población
			while(rand > poblation.get(pos_super).getPuntuationAcc() && pos_super < poblation.size()) 
				pos_super++;
			//Y añadimos al array posicionandolo
			selectedPopulation.add(poblation.get(pos_super));
		}
	}

}
