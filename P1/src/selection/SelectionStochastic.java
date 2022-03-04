package selection;

import java.util.ArrayList;
import java.util.List;

import individual.Chromosome;

public class SelectionStochastic extends Selection {


	//Seleccion estocástica
	@Override
	public void selection(List<Chromosome> poblation, Object param) {

		poblacionSeleccionada = new ArrayList<>();
		
		//Se genera un unico numero aleatorio y a partir de este se calculan los restantes
		double p = Math.random() * (1.0/(int)param);
	
		//Se generan tantas marcas como individuos queremos seleccionar
		for(int i = 0; i < (int)param; i++){
			
			int posSuper = 0;
			while(posSuper < poblation.size() && p > poblation.get(posSuper).getAcumulado())
				posSuper++;
			
			//Y vamos añadiendo a los individuos
			poblacionSeleccionada.add(i, poblation.get(posSuper));
			
			//La distancia entre las marcas es de 1/N
			p = p + (1.0/(int)param);
		}
	}

}
