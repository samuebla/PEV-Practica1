package utilsMultiplex;

public class TArbol {
	String data; //operator o operand
	
	TArbol Hi;
	TArbol Hc;	//Central
	TArbol Hd;
	
	int num_nodes;
	int depth;
	
	TArbol buildTree(TArbol tree, int depth_min, int depth_max) {
		
		//Sigue creando hijos si no ha llegado a la profundidad minima	
		if(depth_min > 0) {
			//Generamos un subarbol del operador
			//operador = operadorAleatorio;
			
			//Ponemos el simbolo del operador aleatorio
			//tree.data = operador;
			
			//Generamos los hijos
			Hi = buildTree(tree.Hi, depth_min-1, depth_max-1);
			
			//Le sumamos los nodos de sus hijos
			tree.num_nodes += tree.Hi.num_nodes;
			
			//Si el operador tiene 3 operandos (Un IF)
			//TODO FALTA POR HACER COÑO
			if(true) {
				//Tb creamos en la raiz
				Hc = buildTree(tree.Hc, depth_min-1, depth_max-1);
				tree.num_nodes += tree.Hc.num_nodes;
			}
			Hd = buildTree(tree.Hd, depth_min-1, depth_max-1);

			tree.num_nodes += tree.Hd.num_nodes;

		}
		else
			depth_min = 0;
		
		//Si has llegado al maximo
		if(depth_max == 0) {
			//Solo puede ser una hoja
			
			//Generamos un subarbol del operador
			//operador = operadorAleatorio;
			
			//Ponemos el simbolo del operador aleatorio
			//tree.data = operador;
			
			//Le sumamos el ultimo nodo
			tree.num_nodes += 1;
		}
		else {
			//Decidimos aleatoriamente su operando u operador
			//tipo = RANDOM DE 0 A 1;
			//if(tipo == 1){
			//Generamos un operador
			//Seguimos con la generacion del subarbol de operador
			//else
			//generas un operando
			//Seguimos con la generacion del subarbol de operando
		}
		
		
		
		//Provisional
		return tree;
		
	}
}
