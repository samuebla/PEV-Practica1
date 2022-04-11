package genetics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//Tipo de gen que vamos a usar para la practica1
public class FlightGen extends Gen {

	public int pos_vuelo;
	public int pistaAsignada;
	public double TLA;
	
	public FlightGen(float prec){
		//Crea la lista de los alelos
		this.alleles = new ArrayList<>();
		
		this.precision = prec;
	}
	
	//Crea un Gen a base de otro gen
	public FlightGen(FlightGen copyGen){
		this.min_range = copyGen.getMin();
		this.max_range = copyGen.getMax();
		this.isNegative_ = copyGen.isNegative();
		this.precision = copyGen.getPrec();
		this.alleles = new ArrayList<>();
		this.alleles.addAll(copyGen.getAlleles());
		this.pos_vuelo = copyGen.pos_vuelo;
		this.pistaAsignada = copyGen.pistaAsignada;
		this.TLA = copyGen.TLA;
	}
	

	@Override
	public double getGenGenotype() {
		
		 return pos_vuelo;
	}
	
	
	@Override
	public void setGenotype(double valor) {
		pos_vuelo = (int) valor;
	}

	@Override
	public void randomize(double min, double max) {
		// TODO Auto-generated method stub
		
	}

}
