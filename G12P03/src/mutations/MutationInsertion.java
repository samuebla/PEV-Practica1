package mutations;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;
import utils.Params;

public class MutationInsertion extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {
		
		//Cogemos la probabilidad seleccionada
		Params params = (Params) params_;
		double prob = params.mutProb;
		
        int size = poblation.get(0).getGens().size();


        for (Chromosome ind : poblation){
            double random = ThreadLocalRandom.current().nextDouble(0, 1);
            //Si se llega a mutar...
            if(random < prob){
            	params.numMutations++;
            	int pos1 = 0;
                int pos2 = 0;
                
            	while(pos1 == pos2 ) {
            	//Calculamos un alelo aleatorio que coger y otro para colocar
                pos1 = ThreadLocalRandom.current().nextInt(1, size-1);
                pos2 = ThreadLocalRandom.current().nextInt(1, size-1);
            	}
                //Eliminamos el primero
                Gen swap = ind.getGens().remove(Math.max(pos1, pos2));
                //Y con este metodo al añadirlo todos se arrastran correctamente, por lo que no hay que hacer nada mas
                ind.getGens().add(Math.min(pos1, pos2),swap);
            }
        }

        this.mPoblation = poblation;
	}

}
