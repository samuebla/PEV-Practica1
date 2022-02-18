package algoritmoGenetico.individuos;
import java.util.Random;

public abstract class Individuo<T> {
	T[] cromosoma;
	int[] tamGenes;
	
	int precision;
	double valorError;
	Random rand;
	
	public Individuo() {
		// TODO Auto-generated constructor stub
	}
	
	// Sitio Provisional
	public int tamGen(double valorError, double min, double max) {
		return (int) (Math.log10(((max - min) / precision) + 1) / Math.log10(2));
	}

}
