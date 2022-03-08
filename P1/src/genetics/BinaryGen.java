package genetics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//Tipo de gen que vamos a usar para la practica1
public class BinaryGen extends Gen {

	
	public BinaryGen(float prec){
		//Crea la lista de los alelos
		this.alelos = new ArrayList<>();
		
		this.prec = prec;
	}
	
	//Crea un Gen a base de otro gen
	public BinaryGen(BinaryGen nGen){
		this.min = nGen.getMin();
		this.max = nGen.getMax();
		this.prec = nGen.getPrec();
		this.neg = nGen.getNeg();
		this.alelos = new ArrayList<>();
		this.alelos.addAll(nGen.getAlelos());
	}
	
	@Override
	public void randomize(double min, double max) {
		this.max = max;
		this.min = min;

		//Coge un numero aleatorio entre el min y el max
		double aux = ThreadLocalRandom.current().nextDouble(min, max);
		
		int max_int = (int) (max/this.prec);
		int min_int = (int) (min/this.prec);

		//Te hace la conversion de binario a string 
		String arrmax = Integer.toBinaryString(max_int);
		String arrmin = Integer.toBinaryString(min_int);
		
		this.tam_cod = Math.max(arrmax.length(), arrmin.length());
		
		this.setGenotipo(aux);
	}

	//CONVERSIÓN DE BINARIO A DECIMAL
	@Override
	public double getFenotipo() {
		
		 double result = 0;
		 int p = 0;
         
         for(int i = this.alelos.size() - 1; i >= 0; i--){
        	 result += (int)alelos.get(i) * Math.pow(2,p);
             p++;
         }
         if(this.neg)
        	 return -(result * this.prec);
         else
         return result * this.prec;
	}

	
	//CONVERSIÓN DE DECIMAL A BINARIO
	@Override
	public void setGenotipo(double valor) {
		// TODO Auto-generated method stub
		if(valor < 0) {
			this.neg = true;
			valor = -valor;
		}else this.neg = false;

		int l = (int) (valor/this.prec);

		String arr = Integer.toBinaryString(l);
		this.alelos = new ArrayList<>(arr.length());
		
		//Añade los ceros necesarios al inicio
		for(int i = 0; i < this.tam_cod - arr.length(); i++)
			alelos.add(0);
		
		//Y aqui hace la conversion char por char
		for(int i = 0; i < arr.length(); i++)
			alelos.add(Character.getNumericValue(arr.charAt(i)));
	}

}
