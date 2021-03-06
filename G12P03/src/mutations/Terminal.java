package mutations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import individual.Chromosome;
import utils.Params;
import utilsMultiplex.TArbol;

public class Terminal extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {

		Params params = (Params) params_;
		double probability = params.mutProb;
		
		Random rnd = new Random();
		for(int i = 0; i < poblation.size(); i++){
			//Se calcula un numero del 0 al 1
			double prob = rnd.nextDouble();
			if(prob < probability){
				//Aumentamos el contador de mutaciones
            	params.numMutations++;
            	
            	//Copiamos el arbol del cromosoma
				Chromosome c = poblation.get(i).copia();
				TArbol a = c.getTree().copia();
				
				//Selecciona los terminales del ?rbol
				ArrayList<TArbol> terminals = new ArrayList<TArbol>();
				a.getTerminals(a.getSons(), terminals);
				
				//Cogemos un terminal aleatorio de todos los que hay
				int select_terminal = rnd.nextInt(terminals.size());
				int newTry = rnd.nextInt(Chromosome.terminales.length);
				
				//Y le establecemos el valor				
				String val = Chromosome.terminales[newTry];
				
				//Repetimos el proceso hasta que haya un terminal distinto al original
				while(terminals.get(select_terminal).getValue().equals(val)){
					newTry = rnd.nextInt(Chromosome.terminales.length);
					val = Chromosome.terminales[newTry];
				}
				
				//Fija el nuevo valor del terminal
				terminals.get(select_terminal).setValor(val);
				
				//Inserta el nuevo terminal
				a.insertTerminal(a.getSons(), terminals.get(select_terminal), select_terminal, 0);
				
				poblation.get(i).setArbol(a.copia());
			}
		}
		
		this.mPoblation = poblation;
	}

}
