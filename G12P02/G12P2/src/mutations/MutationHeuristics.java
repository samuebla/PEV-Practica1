package mutations;

import java.util.ArrayList;
import java.util.List;

import genetics.Gen;
import individual.Chromosome;

public class MutationHeuristics extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params) {
		//Cogemos la probabilidad seleccionada
		 double probabilidad = params.get(0);

	        for(Chromosome ind : poblation){
	            double random = Math.random();
	            //Si muta...
	            if(random < probabilidad) {
	            	//Cogemos 3 posiciones aleatorias
	                int pos1, pos2, pos3;
	                int size = ind.getGens().size();

	                do {
	                    pos1 = (int) (Math.random() * size);
	                } while (pos1 == 0 || pos1 == size - 1);

	                do {
	                    pos2 = (int) (Math.random() * size);
	                } while (pos2 == 0 || pos2 == size - 1 || pos2 == pos1);

	                do {
	                    pos3 = (int) (Math.random() * size);
	                } while (pos3 == 0 || pos3 == size - 1 || pos3 == pos1 || pos3 == pos2);

	                //Queremos saber cual es el mejor cromosoma
	                this.mejor = new Chromosome(new ArrayList<>(ind.getGens()));
	                List<Integer> posiciones = new ArrayList<>();
	                posiciones.add(pos1);
	                posiciones.add(pos2);
	                posiciones.add(pos3);

	                //REVISAR ESTO TODAVIA NO ESTA ACABADO AAAAAAAAAAAAAAAA
	                List<Gen> conjunto = new ArrayList<>();
	                conjunto.add(ind.getGens().get(pos1));
	                conjunto.add(ind.getGens().get(pos2));
	                conjunto.add(ind.getGens().get(pos3));

	                List<Gen> resultado = new ArrayList<>();

	                Chromosome indCopy = new Chromosome(new ArrayList<>(ind.getGens()));

	                permutaciones(indCopy, conjunto, resultado, posiciones);
	                ind.setGenes(this.mejor.getGenes());
	            }
	        }
	        this.mPoblation = poblation;

	    }

	    private void permutaciones(Chromosome individuo, List<Gen> conjunto, List<Gen> resultado, List<Integer> posiciones) {

	    	//aaAAAAAAAAAA
	        //Funcion f = new FuncionViajante();

	        if (conjunto.size()==0)
	        {
	            individuo.getGens().set(posiciones.get(0), resultado.get(0));
	            individuo.getGens().set(posiciones.get(1), resultado.get(1));
	            individuo.getGens().set(posiciones.get(2), resultado.get(2));
	            if(f.ejecutar(individuo.getFenotipo()) < f.ejecutar(this.mejor.getFenotipo()))
	            	//Nos guardamos el mejor cromosoma
	                this.mejor = new Chromosome(new ArrayList<>(individuo.getGens()));
	        }
	        for (int i=0;i<conjunto.size();i++)
	        {
	            Gen b = conjunto.remove(i);
	            resultado.add(b);
	            permutaciones (individuo, conjunto, resultado, posiciones);
	            resultado.remove(b);
	            conjunto.add(i,b);
	        }
	    }

}
