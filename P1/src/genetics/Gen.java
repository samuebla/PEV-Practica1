package genetics;

import java.util.List;

public abstract class Gen {
	//Puede ser de cualquier tipo
	List<Object> alleles;
	//Error range
	float prec;
	boolean isNegative_;
	int tam_cod;
	double min_range;
	double max_range;
	
	public Gen() { }

	public abstract void randomize(double min, double max);
	
	//GETTERS
	
	public abstract double getGenFenotype();
	
	public double getMin() {
		return min_range;
	}
	
	public double getMax() {
		return max_range;
	}
	
	public int getTam() {
		return alleles.size();
	}
	
	boolean isNegative(){return this.isNegative_;}

	float getPrec() {return this.prec;}

	public List<Object> getAlleles(){return alleles;}

	public boolean equals(Object o){
		if(!(o instanceof  Gen)) return false;

		Gen g = (Gen)o;
		return g.getGenFenotype() == this.getGenFenotype();
	}
	
	public void setAlleles(List<Object> alelos) {
		this.alleles = alelos;
		//No se muy bien que hace esto
		if(this.getGenFenotype() < this.min_range || this.getGenFenotype() > this.max_range) this.isNegative_ = !this.isNegative_;
	}
	
	
	public void setGenotype(double ay) {
		
	}
	
	public void setMin(double min) {
		this.min_range = min;
	}

	public void setMax(double max) {
		this.max_range = max;
	}
	
}
