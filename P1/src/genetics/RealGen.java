package genetics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RealGen extends Gen {

	public RealGen(float prec){
		this.precision = prec;
	}
	
	//Constructora por copia
	public RealGen(RealGen copyGen){
		this.min_range = copyGen.getMax();
		this.max_range = copyGen.getMax();
		this.precision = copyGen.getPrec();
		this.isNegative_ = copyGen.isNegative();
		this.alleles = new ArrayList<Object>();	
		for(Object o : copyGen.getAlleles()){
			this.alleles.add(o);
		}
	}
	
	@Override
	public void randomize(double min, double max) {
		//Igual pero ahora en double porque es real
		setGenotype((ThreadLocalRandom.current().nextDouble(min, max+this.precision)));
	}
	
	//El fenotipo y genotipo son iguales
	@Override
	public double getGenFenotype() {
		return (double) alleles.get(0);
	}

	//Se mete sin ninguna conversion
	@Override
	public void setGenotype(double valor) {
		this.alleles = new ArrayList<>();

		this.alleles.add(valor);
	}

}
