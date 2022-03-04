package crosses;

import java.util.List;
import individual.Chromosome;

public abstract class Cross {

	protected List<Chromosome> hijos;
	
	public List<Chromosome> getHijos() {
		return this.hijos;
	}
	
	//Metodo para cruzar 2 cromosomas
	public abstract void cruzar(Chromosome padre1, Chromosome padre2, int param);
}
