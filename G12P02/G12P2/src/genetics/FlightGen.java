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
	public void randomize(double min, double max) {
		this.max_range = max;
		this.min_range = min;

		//Coge un numero aleatorio entre el min y el max
		double aux = ThreadLocalRandom.current().nextDouble(min, max);
		

		//Obtenemos las cadenas de bits de los extremos del intervalo 
		String arrmax = Integer.toBinaryString((int) (max/this.precision));
		String arrmin = Integer.toBinaryString((int) (min/this.precision));
		//Determinamos el maximo tama�o posible
		this.maxCodificationSize = Math.max(arrmax.length(), arrmin.length());
		
		this.genotype = aux;
		this.setGenotype(aux);
	}

	@Override
	public double getGenFenotype() {
		
		 return pos_vuelo;
	}
	
	
	//CONVERSI�N DE DECIMAL A BINARIO
	@Override
	public void setGenotype(double valor) {
		// TODO Auto-generated method stub
		if(valor < 0) {
			this.isNegative_ = true;
			valor = -valor;
		}else this.isNegative_ = false;

		int l = (int) (valor/this.precision);

		String arr = Integer.toBinaryString(l);
		this.alleles = new ArrayList<>(arr.length());
		
		//En caso de que sea menor el numero. Se a�aden ceros al inicio
		int sizeAlleles = this.maxCodificationSize - arr.length();
		for(int i = 0; i < sizeAlleles; i++)
			alleles.add(0);
		
		//Y aqui hace la conversion char por char
		for(int i = 0; i < arr.length(); i++)
			alleles.add(Character.getNumericValue(arr.charAt(i)));
	}

}
