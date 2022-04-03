package mutations;

import java.util.List;

import genetics.Gen;
import individual.Chromosome;
import utils.Params;

public class MutationInversion extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {
		
		//Cogemos la probabilidad de mutacion
		Params params = (Params) params_;
		double probabilidad = params.mutProb;

		//Por cada cromosoma
        for(Chromosome ind : poblation){
            double random = Math.random();
            if(random < probabilidad){
            	params.numMutations++;
                int pos1, pos2;
                int size = ind.getGens().size();

                //Calcula una posicion para los 2 cortes
                do {
                    pos1 = (int) (Math.random() * size);
                } while (pos1 == 0 || pos1 == size-1);

                do {
                    pos2 = (int) (Math.random() * size);
                } while (pos2 == 0 || pos2 == size-1 || pos2 == pos1);

                //Los ordena para ver cual es el menor
                int min = Math.min(pos1, pos2);
                int max = Math.max(pos1, pos2);

                //Y reescribe los valores pero al reves
                //CREO QUYE ESTO DA ERRORES SI SON IMPARES O MIERDAS RARA AAAA
                //En vdd creo que no pq si es impar el den medio no se moveria pero no se no se
                while(min < max) {
                    Gen aux = ind.getGens().get(min);
                    ind.getGens().set(min, ind.getGens().get(max));
                    ind.getGens().set(max, aux);
                    min++;
                    max--;
                }
            }
        }
        this.mPoblation = poblation;
	}

}
