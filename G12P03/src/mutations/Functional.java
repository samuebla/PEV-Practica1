package mutations;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import individual.Chromosome;
import utils.Params;
import utilsMultiplex.TArbol;

public class Functional extends Mutation {

	@Override
	public void mutate(List<Chromosome> poblation, Object params_) {
		Params params = (Params) params_;
		double probability = params.mutProb;
		
		Random rnd = new Random();
		for(int i = 0; i < poblation.size(); i++) {
			double prob = rnd.nextDouble();
			if(prob < probability) {
				//Aumentamos el contador de mutaciones
            	params.numMutations++;				
			            	
				Chromosome c = poblation.get(i).copia();
				TArbol a = c.getTree().copia();
				
				//Se obtienen las funciones del ?rbol
				ArrayList<TArbol> funciones = new ArrayList<TArbol>();
				a.getFunciones(a.getSons(), funciones);
				
				//Con esto hacemos que el IF y el NOT no se puedan mutar, ya que no existiria otra opcion
				//Con este metodo el array Funciones elimina los if y not y solo se guarda los AND/OR
				if(tryChangeFunction(funciones)){
					//Cogemos una funcion cualquiera
					int selecc_funcion = rnd.nextInt(funciones.size());
					String val = "";
					
					//Cambia el OR por AND y viceversa
					if(funciones.get(selecc_funcion).getValue().equals("OR"))
						val = "AND";
					else
						val = "OR";
					
					//Le cambiamos el valor de la funcion por el seleccionado antes
					funciones.get(selecc_funcion).setValor(val);
					
					//Inserta la nueva funci?n en el ?rbol
					a.insertFunction(a.getSons(), funciones.get(selecc_funcion), selecc_funcion, 0);					
					
					//Y a?adimos el arbol al cromosoma
					poblation.get(i).setArbol(a.copia());
				}
			}
		}
		
		this.mPoblation = poblation;
	}


	//Se encarga de encontrar funciones que se puedan mutar
	//dejando as? una lista solo de esas funciones
	private boolean tryChangeFunction(ArrayList<TArbol> functions) {
		boolean canChange = false;
		ArrayList<TArbol> aux = new ArrayList<TArbol>();
		
		for(TArbol a : functions)
			//Para comprobar si se puede cambiar o no
			if(a.getValue().equals("OR") || a.getValue().equals("AND")){
				aux.add(a.copia());
				canChange = true;
			}
		
		//Limpiamos para quitar los Not y los IF
		functions.clear();
		//Y metemos solo las copias
		for(TArbol a : aux) functions.add(a.copia());
		
		return canChange;
	}

}
