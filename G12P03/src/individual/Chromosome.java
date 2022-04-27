package individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genetics.Gen;
import utilsMultiplex.TArbol;

//CROMOSOMA
public class Chromosome {

	public static String terminales[];
	public static final String terminales6[] = { "A0", "A1", "D0", "D1", "D2", "D3" };
	public static final String funciones[] = { "AND", "OR", "NOT", "IF" };
	private TArbol tree;
	private double fitness;
	private double puntuation;
	private double puntuation_acc;
	private String fenotipo;
	private double fitnessDisplaced;

	// Para la tabla multiplexor
	private static int soluciones[][];
	private static int numSoluciones;

	public Chromosome(int profundidad, int tipoCreacion, boolean useIf, int tipoMultiplexor) {

		tree = new TArbol(profundidad, useIf);
		switch (tipoCreacion) {
		case 0:
			tree.inicializacionCreciente(0);
			break;
		case 1:
			tree.inicializacionCompleta(0, 0);
			break;
		case 2:
			int ini = new Random().nextInt(2);
			if (ini == 0)
				tree.inicializacionCreciente(0);
			else
				tree.inicializacionCompleta(0, 0);
			break;
		}

		// TODO CREO QUE FALTAN COSAS
	}

	// Constructora por defecto
	public Chromosome() {
		this.tree = new TArbol();
	}

	// Constructora por copia
	public Chromosome(Chromosome copyCrom) {

		tree = copyCrom.tree;

		puntuation_acc = copyCrom.getPuntuationAcc();
		fitness = copyCrom.getFitness();
	}

	// Equivalente a constuctora por copia
	public Chromosome copia() {
		Chromosome c = new Chromosome();

		c.setArbol(this.tree.copia());
		c.setFitness(this.fitness);

		// AAAAAAAAAAAAAAAAAA TODO QUE HABRIA AQUI
//		c.setFitness_bruto(this.fitness_bruto);

		c.setPuntuation(this.puntuation);
		c.setPuntuationAcc(this.puntuation_acc);

		return c;
	}

	public double evalua() {
		ArrayList<String> func = tree.toArray();
		int fallos = numSoluciones;
		for (int i = 0; i < numSoluciones; i++) {
			ArrayList<String> f = convFuncion(func, i);
			int res = evaluar(f, 0);
			if (numSoluciones == 64) {
				if (res == soluciones[i][6])
					fallos--;
			} else if (numSoluciones == 2048) {
				if (res == soluciones[i][11])
					fallos--;
			}
		}
		//AAAAA TODO
//		fitness_bruto = fallos;
		fitness = fallos;
		return fallos;
//		return 0;
	}

	// Definimos que se hace dependiendo del valor de la funcion (Definimos IF AND y OR)
	private int evaluar(ArrayList<String> func, int index) {
		String function = func.get(index);
		int resul;
		// Si tiene 3 hijos
		if (function.equals("IF")) {
			// Los guardamos en una variable
			int hijo1 = evaluar(func, index + 1);
			int hijo2 = evaluar(func, index + 2);
			int hijo3 = evaluar(func, index + 3);
			// Si el primer hijo es 1 se hace lo del hijo siguiente
			// Si es un 0 (else) se hace lo del hijo 3
			if (hijo1 == 1)
				resul = hijo2;
			else
				resul = hijo3;
		}
		// Solo 1 y 1 = 1, si no es 0
		else if (function.equals("AND")) {
			int hijo1 = evaluar(func, index + 1);
			int hijo2 = evaluar(func, index + 2);
			if (hijo1 == 1 && hijo2 == 1)
				resul = 1;
			else
				resul = 0;
		}
		// Solo 0 0 = 0, si no es 1
		else if (function.equals("OR")) {
			int hijo1 = evaluar(func, index + 1);
			int hijo2 = evaluar(func, index + 2);
			if (hijo1 == 1 || hijo2 == 1)
				resul = 1;
			else
				resul = 0;
		}
		// 1 = 0 y 0 = 1
		else if (function.equals("NOT")) {
			int hijo1 = evaluar(func, index + 1);
			if (hijo1 == 1)
				resul = 0;
			else
				resul = 1;
		}
		// Si es un terminal
		else
			// Parseamos el resultado a INT y fuera
			resul = Integer.parseInt(function);
		return resul;
	}

	// Convertimos la funcion en la solucion
	private ArrayList<String> convFuncion(ArrayList<String> func, int sol) {
		ArrayList<String> funcConvertida = new ArrayList<String>();
		for (int i = 0; i < func.size(); i++) {
			String n = func.get(i);
			// Si estamos en los terminales (valores)
			if (!n.equals("IF") && !n.equals("NOT") && !n.equals("OR") && !n.equals("AND")) {
				// El multiplexor tiene 64 soluciones
				if (numSoluciones == 64) {
					switch (n) {
					case "A0": {
						funcConvertida.add(Integer.toString(soluciones[sol][0]));
						break;
					}
					case "A1": {
						funcConvertida.add(Integer.toString(soluciones[sol][1]));
						break;
					}
					case "D0": {
						funcConvertida.add(Integer.toString(soluciones[sol][2]));
						break;
					}
					case "D1": {
						funcConvertida.add(Integer.toString(soluciones[sol][3]));
						break;
					}
					case "D2": {
						funcConvertida.add(Integer.toString(soluciones[sol][4]));
						break;
					}
					case "D3": {
						funcConvertida.add(Integer.toString(soluciones[sol][5]));
						break;
					}
					}
					//Para el apartado donde te piden más variables
				} else if (numSoluciones == 2048) {
					switch (n) {
					case "A0": {
						funcConvertida.add(Integer.toString(soluciones[sol][0]));
						break;
					}
					case "A1": {
						funcConvertida.add(Integer.toString(soluciones[sol][1]));
						break;
					}
					case "A2": {
						funcConvertida.add(Integer.toString(soluciones[sol][2]));
						break;
					}
					case "D0": {
						funcConvertida.add(Integer.toString(soluciones[sol][3]));
						break;
					}
					case "D1": {
						funcConvertida.add(Integer.toString(soluciones[sol][4]));
						break;
					}
					case "D2": {
						funcConvertida.add(Integer.toString(soluciones[sol][5]));
						break;
					}
					case "D3": {
						funcConvertida.add(Integer.toString(soluciones[sol][6]));
						break;
					}
					case "D4": {
						funcConvertida.add(Integer.toString(soluciones[sol][7]));
						break;
					}
					case "D5": {
						funcConvertida.add(Integer.toString(soluciones[sol][8]));
						break;
					}
					case "D6": {
						funcConvertida.add(Integer.toString(soluciones[sol][9]));
						break;
					}
					case "D7": {
						funcConvertida.add(Integer.toString(soluciones[sol][10]));
						break;
					}
					}
				}
			} else {
				funcConvertida.add(func.get(i));
			}
		}
		return funcConvertida;
	}

	public void setArbol(TArbol treeAux) {
		this.tree = treeAux;
	}

	public TArbol getArbol() {
		return this.tree;
	}

	public String getPhenotype() {

		return fenotipo;
	}

	// Tamaño total de ambos genes
	public int getTam() {

		// TODO AAA NO SE SI ES NUMNODOS O NUNHIJOS
		return tree.getNumNodos();
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double valor) {
		fitness = valor;
	}

	public double getPuntuationAcc() {
		return puntuation_acc;
	}

	public void setPuntuationAcc(double valor) {
		puntuation_acc = valor;
	}

	public double getPuntuation() {
		return puntuation;
	}

	public void setPuntuation(double puntuation) {
		this.puntuation = puntuation;
	}

	public double getFitnessDisplaced() {
		return fitnessDisplaced;
	}

	public void setFitnessDisplaced(double fitnessDisplaced) {
		this.fitnessDisplaced = fitnessDisplaced;
	}
}
