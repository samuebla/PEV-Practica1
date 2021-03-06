package mutations;

import java.util.List;
import java.util.Random;

import individual.Chromosome;
import utils.Params;
import utilsMultiplex.TArbol;

public class Permutation extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {
		Params params = (Params) params_;
		double probability = params.mutProb;

		Random rnd = new Random();
		for(int i = 0; i < poblation.size(); i++){
			double prob = rnd.nextDouble();
			if(prob < probability){
				//Aumentamos el contador de mutaciones
            	params.numMutations++;
            	
            	Chromosome c = poblation.get(i);
				TArbol a = c.getTree();
				//Para seleccionar en que arbol queremos hacer la permutacion
				int k = rnd.nextInt(a.getNumSons()+1);
				
				//Nos movemos a esa posicion
				TArbol toMutate = a.at(k);
				
				//Para evitar los NOT
				if(toMutate.getNumSons() > 1) {
					//Para los AND/OR
					if(toMutate.getNumSons() == 2) {
						//Cambiamos Izquierda por Derecha
						TArbol aux = toMutate.getSons().get(0);
						toMutate.getSons().set(1, aux);
						toMutate.getSons().set(0, toMutate.getSons().get(1));
					}
					//Para los IF
					else{
						//Cambiamos 1 random por otro random
						int pos1 = rnd.nextInt(3);
						int pos2 = pos1;
						while(pos2 == pos1) pos2 = rnd.nextInt(3);
						TArbol aux = toMutate.getSons().get(pos1);
						toMutate.getSons().set(pos2, aux);
						toMutate.getSons().set(pos1, toMutate.getSons().get(pos2));
					}
				}
			}
		}
		this.mPoblation = poblation;	
	}
}
