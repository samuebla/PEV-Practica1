package crosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossOXPP extends Cross {

	@Override
	public void cruzar(Chromosome father1, Chromosome father2) {
		//Aunque deba ser en funcion del tamaño Carlos dijo que con 3 servia
        final int numCuts = 3;
        
        List<Integer> cutPoints = new ArrayList<>();

        Chromosome h1 = new Chromosome(father1);
        Chromosome h2 = new Chromosome(father2);

        Chromosome p1 = new Chromosome(father1);
        Chromosome p2 = new Chromosome(father2);

        List<Gen> genes1 = p1.getGens();
        List<Gen> genes2 = p2.getGens();

        List<Gen> son1 = h1.getGens();
        List<Gen> son2 = h2.getGens();


        for (int i = 0; i < genes1.size() - 1; i++) {
            son1.get(i).setGenotype(-1);
            son2.get(i).setGenotype(-1);
        }


        //Calculamos el numero de cortes...
        for (int i = 0; i < numCuts; i++) {
            int c = ThreadLocalRandom.current().nextInt( 1, genes1.size() - 1);

            //Se repite para que no den cortes iguales
            while (cutPoints.contains(c))
                c = ThreadLocalRandom.current().nextInt(1, genes1.size() - 1);
            
            //Y lo añadimos
            cutPoints.add(c);
        }

        //Ordenamos los cortes para hacerlos de izquierda a derecha
        Collections.sort(cutPoints);

        //Invertimos los valores seleccionados
        for (int c : cutPoints) {
            son1.set(c, genes2.get(c));
            son2.set(c, genes1.get(c));
        }

        //Ahora comenzamos con el corte más a la derecha y vamos de derecha a izquierda
        int i = cutPoints.get(numCuts - 1)+1;
        int acum = i;

        //Ponemos el resto de valores en orden
        complete(genes1, son1, i, acum);

        //Volvemos a la misma posicion
        i = cutPoints.get(numCuts - 1) + 1;
        acum = i;

        //Y repetimos con el otro hijo
        complete(genes2, son2, i, acum);

        //Metemos los dos hijos
        this.sons = new ArrayList<>();
        this.sons.add(new Chromosome(son1));
        this.sons.add(new Chromosome(son2));
    }

    private void complete(List<Gen> gens, List<Gen> son1, int i, int acum) {
    	
    	//Hasta el final de la lista
        while (i < son1.size() - 1) {
            if (!son1.contains(gens.get(acum))) {
                son1.set(i, gens.get(acum));
                i++;
            }
            acum++;
            if (acum == gens.size()) 
            	acum = 0;
        }

        //Volvemos al inicio
        i = 0;

        while (i < son1.size() - 1) {
        	//Solo cambia el valor en los casos en los que no se haya cambiado ya (Para dejar los cortes intactos)
            if (son1.get(i).getGenFenotype() == -1) {
                if (!son1.contains(gens.get(acum))) {
                    son1.set(i, gens.get(acum));
                    i++;
                }
                acum++;
               
            }
            //Si ha topado con un corte avanzamos sin modificarlo
            else i++;
        }
	}

}
