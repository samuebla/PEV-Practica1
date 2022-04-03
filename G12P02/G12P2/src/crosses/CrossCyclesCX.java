package crosses;

import java.util.ArrayList;
import java.util.List;

import genetics.Gen;
import individual.Chromosome;

public class CrossCyclesCX extends Cross {

	@Override
	public void cruzar(Chromosome father1, Chromosome father2) {

        Chromosome h1 = new Chromosome(father1);
        Chromosome h2 = new Chromosome(father2);

        Chromosome p1 = new Chromosome(father1);
        Chromosome p2 = new Chromosome(father2);

        //Y preparamos la lista de genes
        List<Gen> gens1 = p1.getGens();
        List<Gen> gens2 = p2.getGens();

        List<Gen> son1 = h1.getGens();
        List<Gen> son2 = h2.getGens();


        //-1 Si no se ha modificado el gen todavia
        for(int i = 0; i < gens1.size(); i++){
            son1.get(i).setGenotype(-1);
            son2.get(i).setGenotype(-1);
        }

        //Realizamos el ciclo de un gen
        cycle(gens1, gens2, son1);

        //Y del otro
        cycle(gens2, gens1, son2);

        //Y apañao, los 2 para dentro
        this.sons = new ArrayList<>();

        this.sons.add(new Chromosome(son1));
        this.sons.add(new Chromosome(son2));
    }

    private void cycle(List<Gen> gens1, List<Gen> gens2, List<Gen> son1) {
    	
    	
        Gen genAux = gens1.get(0);
        int aux = 0;

        //Ciclo hasta encontrar uno que ya tiene
        while(!son1.contains(genAux)){
        	//Le establece en la posicion Aux el valor GenAux
            son1.set(aux,genAux);
            //Le damos el valor de la posición de su número
            genAux = gens2.get(aux);
            //Y nos vamos al indice de donde se encuentra ese valor
            aux = gens1.indexOf(genAux);
        }

        //Luego colocamos los restantes tal cual
        for(int i = 0; i < son1.size(); i++){
        	//Si no ha conseguido entrar en el ciclo...
            if(son1.get(i).getGenFenotype() == -1){
            	//Lo colocamos tal cual
                son1.set(i, gens2.get(i));
            }
        }
    }
}
