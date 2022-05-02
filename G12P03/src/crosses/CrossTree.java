package crosses;

import java.util.ArrayList;

import individual.Chromosome;
import utils.Params;
import utilsMultiplex.TArbol;

public class CrossTree extends Cross {
	
	final static double probability = 0.9; 
		
	@Override
	public void cruzar(Chromosome father1, Chromosome father2, Object params) {
		Chromosome son1 = father1.copia();
		Chromosome son2 = father2.copia();
		
		//Coleccion de nodos que almacena o bien terminales o bien funciones
		ArrayList<TArbol> collectionNodes_1 = getSubTree(father1.getTree());
		ArrayList<TArbol> collectionNodes_2 = getSubTree(father2.getTree());
		
		//Elegimos cualquier nodo de esas colecciones
		int cutPoint_1 = (int) (Math.random()* collectionNodes_1.size());
		int cutPoint_2 = (int) (Math.random()* collectionNodes_2.size());
		
		//Obtenemos los arboles que empiezan con esos nodos elegidos y realizamos el cruce
		TArbol subTree1 = collectionNodes_1.get(cutPoint_1).copia();
		insertSubTreeAt(son2, subTree1, cutPoint_2);
		
		TArbol subTree2 = collectionNodes_2.get(cutPoint_2).copia();
		insertSubTreeAt(son1, subTree2, cutPoint_1);
		
		//Asignamos los nuevos numeros de nodos
		son1.getTree().setNumNodos(son1.getTree().getNodos(son1.getTree(), 0));
		son2.getTree().setNumNodos(son2.getTree().getNodos(son2.getTree(), 0));
		
		Params params_ = (Params) params;
		params_.numCrosses++;
		
		//Evaluamos los hijos ya que se necesita actualizar su propio fitness de nuevo
		this.sons = new ArrayList<>();		
		this.sons.add(new Chromosome(son1));
		this.sons.add(new Chromosome(son2));
	}

	public ArrayList<TArbol> getSubTree(TArbol tree){
		ArrayList<TArbol> nodes = new ArrayList<TArbol>();
		tree.getFunciones(tree.getSons(), nodes);
		
		double prob = Math.random();
		if(prob >= probability || nodes.size() == 0)
			tree.getTerminals(tree.getSons(), nodes);
		
		return nodes;
	}
	
	public void insertSubTreeAt(Chromosome c, TArbol tree, int cutPoint) {
		ArrayList<TArbol> sons = c.getTree().getSons();
		int initialPos = 0;
		if(tree.isRoot())
			c.getTree().insertTerminal(sons, tree, cutPoint, initialPos);
		else c.getTree().insertFunction(sons, tree, cutPoint, initialPos);
	}
}
