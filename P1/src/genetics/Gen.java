package genetics;

import java.util.List;

//FALTAN MAZO METODOS Y COSAS ASDVTHTGRGAAAAAAAAA
public abstract class Gen {

	//CAMBIAR NOMBRE VARIABLES
	List<Object> alelos;
	float prec;
	boolean neg;
	int tam_cod;
	double min;
	double max;
	
	public Gen() {

	}

	public abstract void randomize(double min, double max);
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
	
	
	//Para colocar los alelos
	public void setAlelos(List<Object> alelos) {
		this.alelos = alelos;
		//No se muy bien que hace esto
		if(this.getFenotipo() < this.min || this.getFenotipo() > this.max) this.neg = !this.neg;
	}
	
	
	public void setGenotipo(double ay) {
		
	}
}
