package mutations;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;
import utils.FunctionType;
import utils.Params;

public class MutationBasic extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {
		//Coges la probabilidad de mutar (5% aprox)
		Params params = (Params) params_;
		double probabilidad = params.mutProb;		
		
		double min;
		double max;
		
		//Lo mismo pero con toda la poblacion
		for(Chromosome cromosoma : poblation){
			int i = 0;
			for(Gen gen : cromosoma.getGens()){
				double random = ThreadLocalRandom.current().nextDouble(0, 1);
				min = params.interval.get(i);
				max = params.interval.get(i + 1);
				if(random <= probabilidad)
					gen.randomize(min, max);
				if(params.f_type == FunctionType.f1 && i + 2 <= params.interval.size()) 
					i += 2; //se trata de dos intervalos para cada gen
			}
			
		}
		this.mPoblation = poblation;
	}
}
