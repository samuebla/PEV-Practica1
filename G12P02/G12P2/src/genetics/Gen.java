package genetics;

import java.util.List;

public abstract class Gen {
	//Puede ser de cualquier tipo
	List<Object> alleles;
	boolean isNegative_;
	int maxCodificationSize;
	double min_range;
	double max_range;
	//Error range
	float precision;
	double genotype;
	
	public Gen() { }

	public abstract void randomize(double min, double max);
	
	//GETTERS
	
	public abstract double getGenFenotype();

	public abstract void setGenotype(double aux);
	
	
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

	float getPrec() {return this.precision;}

	public List<Object> getAlleles(){return alleles;}

	public boolean equals(Object o){
		if(!(o instanceof  Gen)) return false;

		Gen g = (Gen)o;
		return g.getGenFenotype() == this.getGenFenotype();
	}
	
	public void setAlleles(List<Object> alelos) {
		this.alleles = alelos;
		//Para ver si es valor negativo o no
		if(this.getGenFenotype() < this.min_range || this.getGenFenotype() > this.max_range) this.isNegative_ = !this.isNegative_;
	}	
	
	public void setMin(double min) {
		this.min_range = min;
	}

	public void setMax(double max) {
		this.max_range = max;
	}
	
}
