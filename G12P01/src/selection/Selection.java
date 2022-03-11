package selection;

import java.util.List;
import individual.Chromosome;

//DONE
public abstract class Selection {

	//Lista de individuos que seleccionamos
	List<Chromosome> selectedPopulation;
	

	public List<Chromosome> getPopSelected() {
		return selectedPopulation;
	}

	public abstract void selection(List<Chromosome> poblation, Object param);

}
