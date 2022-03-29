package genetics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//Tipo de gen que vamos a usar para la practica1
public class FlightGen extends Gen {

	public int pos_vuelo;
	public int pista;
	public double hour;
	
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
		//Determinamos el maximo tamaño posible
		this.maxCodificationSize = Math.max(arrmax.length(), arrmin.length());
		
		this.genotype = aux;
		this.setGenotype(aux);
	}

	//CONVERSIÓN DE BINARIO A DECIMAL
	@Override
	public double getGenFenotype() {
		
		 double result = 0;
		 int p = 0;
         
         for(int i = this.alleles.size() - 1; i >= 0; i--){
        	 result += (int)alleles.get(i) * Math.pow(2,p);
             p++;
         }
         if(this.isNegative_)
        	 return -(result * this.precision);
         else
         return result * this.precision;
	}

	
	//CONVERSIÓN DE DECIMAL A BINARIO
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
		
		//En caso de que sea menor el numero. Se añaden ceros al inicio
		int sizeAlleles = this.maxCodificationSize - arr.length();
		for(int i = 0; i < sizeAlleles; i++)
			alleles.add(0);
		
		//Y aqui hace la conversion char por char
		for(int i = 0; i < arr.length(); i++)
			alleles.add(Character.getNumericValue(arr.charAt(i)));
	}

}
