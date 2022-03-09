package genetics;

import java.util.List;

public abstract class Gen {
	//Puede ser de cualquier tipo
	List<Object> alleles;
	//No se que es pero se multiplica para sacar el fenotipo
	float prec;
	//Para ver si es positivo o negativo
	boolean neg;
	int tam_cod;
	double min;
	double max;
	
	public Gen() {

	}

	public abstract void randomize(double min, double max);
	
	//GETTERS
	
	//Cada gen tiene un fenotipo distinto
	public abstract double getFenotype();
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
	
	public int getTam() {
		return alleles.size();
	}
	
	boolean getNeg(){return this.neg;}

	float getPrec() {return this.prec;}

	public List<Object> getAlelos(){return alleles;}

	
	//Para colocar los alelos
	public void setAlelos(List<Object> alelos) {
		this.alleles = alelos;
		//No se muy bien que hace esto
		if(this.getFenotype() < this.min || this.getFenotype() > this.max) this.neg = !this.neg;
	}
	
	
	public void setGenotype(double ay) {
		
	}
	
	public void setMin(double min) {
		this.min = min;
	}

	public void setMax(double max) {
		this.max = max;
	}
	
}
