package mutations;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Individual;

public class BasicMutation extends MutationAlgorithm {

	@Override
	public void mutar(List<Individual> poblation, List<Double> params) {

		//Coges la probabilidad de mutar (5% aprox)
		double probabilidad = params.get(0);
		
		double min = params.get(1);
		double max = params.get(2);
		
		//NO ENTIENDO MUY BIEN POR QUE HACEMOS ESTO CON SOLO TAM 5, IGUAL LITERALMENTE NO HACE FALTA 
		if(params.size() == 5) {
			for(Individual individuo : poblation){
				double min1 = params.get(3);
				double max1 = params.get(4);
				
				//Cogemos un numero aleatorio entre [0 y 1)
				double random = ThreadLocalRandom.current().nextDouble(0, 1);
				
				//Si es menor que la probabilidad establecida
				if(random <= probabilidad)
					//Muta
					individuo.getGenes().get(0).randomize(min, max);
				
				//Aqui igua pero con otro gen
				random = ThreadLocalRandom.current().nextDouble(0, 1);
				if(random <= probabilidad)
					individuo.getGenes().get(1).randomize(min1, max1);
			}
		}
		else {
			//Lo mismo pero con toda la poblacion
			for(Individual individuo : poblation){
				
				for(Gen gen : individuo.getGenes()){
					double random = ThreadLocalRandom.current().nextDouble(0, 1);
					
					if(random <= probabilidad)
						gen.randomize(min, max);
				}
			}
		}
		
		this.mPoblation = poblation;
	}

}
