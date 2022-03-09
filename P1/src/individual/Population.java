package individual;
import java.util.List;
import java.util.ArrayList;

public class Population {

	private List<Chromosome> population_;
	
	public Population() {
		// TODO Auto-generated constructor stub
		population_ = new ArrayList<Chromosome>();
	}
	
	public Population(List<Chromosome> pob){
		population_ = new ArrayList();
		for(Chromosome ind : pob)
			population_.add(new Chromosome(ind));
	}
	
	public List<Chromosome> getPopulation() {
		return population_;
	}
	
	public String toString(){
		String cadena = "";
		for (Chromosome in : population_)
			cadena += in.getFenotipo() + "\n";

		return cadena;
	}
}
