package selection;

import java.util.List;
import individual.Individual;

//DONE
public abstract class Selection {

	//Lista de individuos que seleccionamos
	List<Individual> poblacionSeleccionada;
	

	public List<Individual> getPobSeleccionada() {
		return poblacionSeleccionada;
	}

	public abstract void selection(List<Individual> poblation, Object param);

}
