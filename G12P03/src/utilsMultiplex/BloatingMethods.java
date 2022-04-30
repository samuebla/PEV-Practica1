package utilsMultiplex;

import java.util.Random;

import functions.Function;
import individual.Chromosome;
import individual.Population;

public class BloatingMethods {
	
	static int depth;
	static CreationType creat;
	static MultiplexType multType;
	static boolean useIf;
	static Function funct;
	
	public static void SetUpData(int depth_, CreationType creat_, MultiplexType multType_, boolean useIf_) {
		depth = depth_;
		creat = creat_;
		multType = multType_;
		useIf = useIf_;
	}
	
	public static void setFunction(Function fuct) {
		funct = fuct;
	}
	public static void Tarpeian(Population poblation, double avarage_size, int factorProbability) {
		double prob = 1 / (double)factorProbability;
		Random rnd = new Random();
		int populationThreshold = 4;
		double sizeThreshold = 3;
		for(int i = 0; i < poblation.getPopulation().size(); i++){
			TArbol a = poblation.getPopulation().get(i).getTree();
			//Si su tamaño es mayor que el de la media
			double diff = a.getNumNodes() - avarage_size;
			if(a.getNumNodes() > avarage_size){		
				//Si se trata de los ultimos y su valor no es muy alto, no se borra
				if((i > (poblation.getPopulation().size() - populationThreshold) && diff < sizeThreshold)) 
					continue;
				double p = rnd.nextDouble();
				//Existe la probabilidad de que sea eliminado y reemplazado por otro
				if(p < prob) {
					Chromosome c = new Chromosome(depth, creat, useIf, multType);
					c.setFitness(funct.ejecutar(c));
					poblation.getPopulation().set(i,c);
				}
			}
		}
	}
	
	public static void Penalty(Population poblation, double avarage, double avarage_size) {
		double variance = 0;
		double covariance = 0;
		double k = 0;
		
		double sizePop = poblation.getPopulation().size();
		//Calculamos la varianza y la covarianza
		for(int i = 0; i < sizePop; i++){
			Chromosome c = poblation.getPopulation().get(i);
			covariance += (c.getFitness() - avarage) * (c.getTree().getNumNodes() - avarage_size);
			variance += (c.getTree().getNumNodes() - avarage_size) * (c.getTree().getNumNodes() - avarage_size);
		}
		variance /= sizePop;
		covariance /= sizePop;
		k = covariance / variance;
		for(int i = 0; i < sizePop; i++){
			Chromosome c = poblation.getPopulation().get(i);
			//Asignamos un fitness que penaliza en funcion de su tamaño
			//Basicamente f’(x) = f(x) + k * nodos-en(x)
			double fitness = c.getFitness() - k * c.getTree().getNumNodes();
			c.setFitness(fitness);
		}
	}
}
