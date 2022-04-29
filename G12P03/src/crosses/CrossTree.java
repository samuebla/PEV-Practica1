package crosses;

import java.util.ArrayList;

import individual.Chromosome;
import utilsMultiplex.TArbol;

public class CrossTree extends Cross {
	
	final static double probability = 0.9; 
		
	@Override
	public void cruzar(Chromosome padre1, Chromosome padre2, Object params) {
		// TODO Auto-generated method stub
		Chromosome son1 = padre1.copia();
		Chromosome son2 = padre2.copia();
		
		//Coleccion de nodos que almacena o bien terminales o bien funciones
		ArrayList<TArbol> collectionNodes_1 = getSubTree(padre1.getTree());
		ArrayList<TArbol> collectionNodes_2 = getSubTree(padre2.getTree());
		
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
			tree.getTerminales(tree.getSons(), nodes);
		
		return nodes;
	}
	
	public void insertSubTreeAt(Chromosome c, TArbol tree, int cutPoint) {
		ArrayList<TArbol> sons = c.getTree().getSons();
		int initialPos = 0;
		if(tree.isRoot())
			c.getTree().insertTerminal(sons, tree, cutPoint, initialPos);
		else c.getTree().insertFuncion(sons, tree, cutPoint, initialPos);
	}
}
