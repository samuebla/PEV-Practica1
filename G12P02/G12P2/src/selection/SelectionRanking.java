package selection;

import java.util.List;

import individual.Chromosome;
import utils.Params;

public class SelectionRanking extends Selection {
	private double beta_;
	
	@Override
	public void selection(List<Chromosome> poblation, Object param) {
		// TODO Auto-generated method stub
		Params params = (Params) param;
		beta_ = params.beta;
		List<Chromosome> poblation_ = calcProbabilityRank(poblation);
		selectionRouletteCopy(poblation_);
	}
	
	private List<Chromosome> calcProbabilityRank(List<Chromosome> poblation) {
		double prob;
		double accPunc = 0.0;
		for (int i = poblation.size() - 1 ; i >= 0; i--) {
			prob = ((double)i - 1)/(poblation.size() - 1);
			prob *= 2 * (beta_ - 1);
			prob = beta_ - prob;
			prob = prob *  ((double)1/poblation.size());
			
			//Al estar ordenador de menor a mayor, hay que setear su probabilidad a la inversa
			poblation.get(poblation.size() - 1 - i).setPuntuationAcc(accPunc);			
			poblation.get(poblation.size() - 1 - i).setPuntuation(prob);			
			
			accPunc += prob;
		}
		
		return poblation;
	}
	
	private void selectionRouletteCopy(List<Chromosome> poblation){
		double rand;
		int pos_super;
		int nSelected = poblation.size();		
		for(int i = 0; i < nSelected; i++){
			//Cogemos un numero aleatorio
			rand = Math.random();
			pos_super = 0;
			//Mientras sea menor al acumulado y siga siendo menor que la población
			while(rand > poblation.get(pos_super).getPuntuationAcc() && pos_super < poblation.size()) 
				pos_super++;
			//Y añadimos al array posicionandolo
			selectedPopulation.add(poblation.get(pos_super));
		}
	}
}
