package genetics;

import java.util.List;

//FALTAN MAZO METODOS Y COSAS ASDVTHTGRGAAAAAAAAA
public abstract class Gen {

	//CAMBIAR NOMBRE VARIABLES
	List<Object> alelos;
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
	public abstract double getFenotipo();
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
	
	public int getTam() {
		return alelos.size();
	}
	
	boolean getNeg(){return this.neg;}

	float getPrec() {return this.prec;}

	public List<Object> getAlelos(){return alelos;}

	
	
	
	//Para colocar los alelos
	public void setAlelos(List<Object> alelos) {
		this.alelos = alelos;
		//No se muy bien que hace esto
		if(this.getFenotipo() < this.min || this.getFenotipo() > this.max) this.neg = !this.neg;
	}
	
	
	public void setGenotipo(double ay) {
		
	}
	
	public void setMin(double min) {
		this.min = min;
	}

	public void setMax(double max) {
		this.max = max;
	}
	
}
