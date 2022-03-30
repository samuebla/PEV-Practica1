package mutations;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class MutationInsertion extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params) {
		
		//Cogemos la probabilidad seleccionada
		double prob = params.get(0);
		
		//Y el rango
        int min = (int) poblation.get(0).getGens().get(0).getMin();
        int max = (int) poblation.get(0).getGens().get(0).getMax();

        for (Chromosome ind : poblation){
            double random = ThreadLocalRandom.current().nextDouble(0, 1);
            //Si se llega a mutar...
            if(random < prob){
            	//Calculamos un alelo aleatorio que coger y otro para colocar
                int posOri = ThreadLocalRandom.current().nextInt(min+1, max-1);
                int posDest = ThreadLocalRandom.current().nextInt(min+1, max-2);

                //Eliminamos el primero
                Gen swap = ind.getGens().remove(posOri);
                //Y con este metodo al añadirlo todos se arrastran correctamente, por lo que no hay que hacer nada mas
                ind.getGens().add(posDest,swap);
            }
        }

        this.mPoblation = poblation;
	}

}
