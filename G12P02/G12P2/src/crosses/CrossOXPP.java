package crosses;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import genetics.Gen;
import individual.Chromosome;

public class CrossOXPP extends Cross {

	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2) {
		//número de genes que se van a intercambiar entre padre e hijo
		//ESTO DEBERIA DE SER MUCHO MENOS AAAAAAA REVISAR
        final int NINTERCAMBIOS = 6;
        List<Integer> pcorte = new ArrayList<>();
        int min = (int) padre1.getGens().get(0).getMin();
        int max = (int) padre1.getGens().get(0).getMax();

        Chromosome h1 = new Chromosome(padre1);
        Chromosome h2 = new Chromosome(padre2);

        Chromosome p1 = new Chromosome(padre1);
        Chromosome p2 = new Chromosome(padre2);

        List<Gen> genes1 = p1.getGens();
        List<Gen> genes2 = p2.getGens();

        List<Gen> hijo1 = h1.getGens();
        List<Gen> hijo2 = h2.getGens();


        for (int i = 1; i < genes1.size() - 1; i++) {
            hijo1.get(i).setGenotype(-1);
            hijo2.get(i).setGenotype(max);
        }


        for (int i = 0; i < NINTERCAMBIOS; i++) {
            int c = ThreadLocalRandom.current().nextInt(min + 1, max - 1);

            while (pcorte.contains(c))
                c = ThreadLocalRandom.current().nextInt(min + 1, max - 1);
            pcorte.add(c);
        }

        Collections.sort(pcorte);

        for (int c : pcorte) {
            hijo1.set(c, genes2.get(c));
            hijo2.set(c, genes1.get(c));
        }

        int i = pcorte.get(NINTERCAMBIOS - 1) + 1, acum = i;

        fill(genes1, hijo1, i, acum);

        i = pcorte.get(NINTERCAMBIOS - 1) + 1;
        acum = i;

        fill(genes2, hijo2, i, acum);

        this.sons = new ArrayList<>();

        this.sons.add(new Chromosome(hijo1));
        this.sons.add(new Chromosome(hijo2));
    }

    private void fill(List<Gen> genes, List<Gen> hijo, int i, int acum) {
        while (i < hijo.size() - 1) {
            if (!hijo.contains(genes.get(acum))) {
                hijo.set(i, genes.get(acum));
                i++;
            }
            acum++;
            if (acum == genes.size() - 1) acum = 1;
        }

        i = 1;

        while (i < hijo.size() - 1) {
            if (hijo.get(i).getGenFenotype() == -1) {
                if (!hijo.contains(genes.get(acum))) {
                    hijo.set(i, genes.get(acum));
                    i++;
                }
                acum++;
                if (acum == genes.size() - 1) acum = 1;
            }
            else i++;
        }
	}

}
