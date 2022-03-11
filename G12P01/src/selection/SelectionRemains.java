package selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import individual.Chromosome;

public class SelectionRemains extends Selection {

	//
	@Override
	public void selection(List<Chromosome> poblation, Object param) {
		int k = poblation.size();
		selectedPopulation = new ArrayList<Chromosome>();
		Random r = new Random();
		
		for(int i = 0; i < k; i++) {
			double pK = poblation.get(i).getAcc() * k;
			int selected = (int) Math.round(pK);
			for(int j = 0; j < selected  && selectedPopulation.size() < k; j++) {
				double diff = pK - j;
				if(diff >= 0.5) selectedPopulation.add(poblation.get(i));
			}
		}
		
		if(selectedPopulation.size() < poblation.size()) {
			int toSelect = poblation.size() - selectedPopulation.size();
			int i = 0;
			while(i < toSelect) {
				int posSelected = r.nextInt(0, poblation.size());
				selectedPopulation.add(poblation.get(posSelected));
				i++;
			}
		}
	}

}
