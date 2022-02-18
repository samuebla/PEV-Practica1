package algortimoGenetico;
import algoritmoGenetico.individuos.*;

public class AlgoritmoGenetico {

	private int tamPoblacion;
	private Individuo[] poblacion;
	private double[] fitness;
	private int maxGeneraciones;
	private double probCruce;
	private double probMutacion;
	private int tamTorneo;
	private Individuo elMejor;
	private int pos_mejor;
	
	private int generacionActual;
	public AlgoritmoGenetico() {
		// TODO Auto-generated constructor stub
	}
	
	public void run() {
		iniciarPoblacion();
		while(this.generacionActual < this.maxGeneraciones) {
			//Seleccion
			//Cruce
			//Mutacion
			evaluar();
			generaGrafica();
			//Siguiente generacion
			generacionActual++;
		}
	}
	
	public void iniciarPoblacion() {
		
	}
	
	public void evaluar() {
		
	}
	
	public void generaGrafica() {
		
	}
	
}
