package genetics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RealGen extends Gen {

	public RealGen(float prec){
		this.prec = prec;
	}
	
	//Constructora por copia
	public RealGen(RealGen nGen){
		this.min_range = nGen.getMax();
		this.max_range = nGen.getMax();
		this.prec = nGen.getPrec();
		this.isNegative_ = nGen.isNegative();
		this.alleles = new ArrayList<Object>();	
		for(Object o : nGen.getAlleles()){
			this.alleles.add(o);
		}
	}
	
	@Override
	public void randomize(double min, double max) {
		setGenotype((ThreadLocalRandom.current().nextDouble(min, max+this.prec)));
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
