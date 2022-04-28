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
		for(int i = 0; i < poblation.size(); i++)
		{
			double prob = rnd.nextDouble();
			if(prob < probability)
			{
				//Aumentamos el contador de mutaciones
            	params.numMutations++;				
			            	
				Chromosome c = poblation.get(i).copia();
				TArbol a = c.getTree().copia();
				
				//Se obtienen las funciones del árbol
				ArrayList<TArbol> funciones = new ArrayList<TArbol>();
				a.getFunciones(a.getSons(), funciones);
				
				//Con esto hacemos que el IF y el NOT no se puedan mutar, ya que no existiria otra opcion
				//Con este metodo el array Funciones elimina los if y not y solo se guarda los AND/OR
				if(tryChangeFunction(funciones)){
					
					//Cogemos una funcion cualquiera
					int selecc_funcion = rnd.nextInt(funciones.size());
					String val = "";
					
					//Cambia el OR por AND y viceversa
					if(funciones.get(selecc_funcion).getValor().equals("OR"))
						val = "AND";
					else
						val = "OR";
					
					//Le cambiamos el valor de la funcion por el seleccionado antes
					funciones.get(selecc_funcion).setValor(val);
					
					//Inserta la nueva función en el árbol
					a.insertFuncion(a.getSons(), funciones.get(selecc_funcion), selecc_funcion, 0);					
					
					//Y añadimos el arbol al cromosoma
					poblation.get(i).setArbol(a.copia());
					
					//Y no necesitamos evaluar aqui
				}
			}
		}
	}


	//Se encarga de encontrar funciones que se puedan mutar
	//dejando así una lista solo de esas funciones
	private boolean tryChangeFunction(ArrayList<TArbol> funciones) {
		boolean existe = false;
		ArrayList<TArbol> copia = new ArrayList<TArbol>();
		
		for(TArbol a : funciones){
			if(a.getValor().equals("OR") || a.getValor().equals("AND")){
				copia.add(a.copia());
				existe = true;
			}
		}
		
		funciones.clear();

		for(TArbol a : copia)
			funciones.add(a.copia());
		
		return existe;
	}

}
