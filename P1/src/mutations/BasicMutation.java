package mutations;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;
import utils.Params;

public class BasicMutation extends MutationAlgorithm {

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {

		//Coges la probabilidad de mutar (5% aprox)
		Params params = (Params) params_;
		
		double probabilidad = params.mutProb;
		
		double min = params.interval.get(0);
		double max = params.interval.get(1);
		
		//Lo mismo pero con toda la poblacion
		for(Chromosome individuo : poblation){
			
			for(Gen gen : individuo.getGenes()){
				double random = ThreadLocalRandom.current().nextDouble(0, 1);
				
				if(random <= probabilidad)
					gen.randomize(min, max);
			}
		}
		this.mPoblation = poblation;
	}

}
