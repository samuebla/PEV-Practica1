package genetics;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RealGen extends Gen {

	public RealGen(float prec){
		this.prec = prec;
	}
	
	//Constructora por copia
	public RealGen(RealGen nGen){
		this.min = nGen.getMax();
		this.max = nGen.getMax();
		this.prec = nGen.getPrec();
		this.neg = nGen.getNeg();
		this.alleles = new ArrayList<Object>();	
		for(Object o : nGen.getAlelos()){
			this.alleles.add(o);
		}
	}
	
	@Override
	public void randomize(double min, double max) {
		setGenotype((ThreadLocalRandom.current().nextDouble(min, max+this.prec)));
	}
	
	//El fenotipo y genotipo son iguales
	@Override
	public double getFenotype() {
		return (double) alleles.get(0);
	}

	//Se mete sin ninguna conversion
	@Override
	public void setGenotype(double valor) {
		this.alleles = new ArrayList<>();

		this.alleles.add(valor);
	}

}
