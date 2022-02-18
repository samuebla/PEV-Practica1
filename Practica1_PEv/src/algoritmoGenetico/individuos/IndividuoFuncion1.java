package algoritmoGenetico.individuos;

import java.math.BigInteger;
import java.util.Vector;

public class IndividuoFuncion1 extends Individuo<Boolean> {
	double[] min;
	double[] max;
	
	public IndividuoFuncion1() {
		this.tamGenes = new int[2];
		this.min = new double[2];
		this.max = new double[2];
		this.min[0] = -3.000;
		this.min[1] = 4.100;
		this.max[0] = 12.100;
		this.max[1] = 5.800;
		this.tamGenes[0] = this.tamGen(this.valorError, min[0], max[0]);
		this.tamGenes[1] = this.tamGen(this.valorError, min[1], max[1]);
		int tamTotal = tamGenes[0] + tamGenes[1];
		this.cromosoma = new Boolean[tamTotal];
		for(int i = 0; i < tamTotal; i++) this.cromosoma[i] = this.rand.nextBoolean();
	
	}
	
	public double getFenotipo(String v) {
		return Double.longBitsToDouble(new BigInteger(v, 2).longValue());;
	}
	
	
	
	public double getValor() {
		double x1 = this.getFenotipo("0"), x2 = this.getFenotipo("1");
		return (21.5 + x1 * Math.sin(4 * Math.PI * x1) + x2 * Math.sin(20 * Math.PI * x2));
	}
	
	public double getFitness() {
		return this.getValor();
	}

}
