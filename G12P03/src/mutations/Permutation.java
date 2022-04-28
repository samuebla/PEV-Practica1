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
		for(int i = 0; i < poblation.size(); i++)
		{
			double prob = rnd.nextDouble();
			if(prob < probability)
			{
				//Aumentamos el contador de mutaciones
            	params.numMutations++;
            	
            	Chromosome c = poblation.get(i);
				TArbol a = c.getTree();
				//Para seleccionar en que arbol queremos hacer la permutacion
				int k = rnd.nextInt(a.getNumHijos()+1);
				
				//Nos movemos a esa posicion
				TArbol aMutar = a.at(k);
				
				//Para evitar los NOT
				if(aMutar.getNumHijos() > 1)
				{
					//Para los AND/OR
					if(aMutar.getNumHijos() == 2)
					{
						//Cambiamos Izquierda por Derecha
						TArbol aux = aMutar.getSons().get(0);
						aMutar.getSons().set(0, aMutar.getSons().get(1));
						aMutar.getSons().set(1, aux);
					}
					//Para los IF
					else
					{
						//Cambiamos 1 random por otro random
						int pos1 = rnd.nextInt(3);
						int pos2 = pos1;
						while(pos2 == pos1) pos2 = rnd.nextInt(3);
						TArbol aux = aMutar.getSons().get(pos1);
						aMutar.getSons().set(pos1, aMutar.getSons().get(pos2));
						aMutar.getSons().set(pos2, aux);
					}
				}
				//Aunque lo hayamos cambiado no necesitamos evaluar nada
			}
		}
	}
}
