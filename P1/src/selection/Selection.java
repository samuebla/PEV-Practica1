package selection;

import java.util.List;
import individual.Chromosome;

//DONE
public abstract class Selection {

	//Lista de individuos que seleccionamos
	List<Chromosome> poblacionSeleccionada;
	

	public List<Chromosome> getPobSeleccionada() {
		return poblacionSeleccionada;
	}

	public abstract void selection(List<Chromosome> poblation, Object param);

}
