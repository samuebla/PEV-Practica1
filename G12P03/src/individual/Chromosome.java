package individual;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import utilsMultiplex.CreationType;
import utilsMultiplex.MultiplexType;
import utilsMultiplex.TArbol;

//CROMOSOMA
public class Chromosome {

	public static final String terminales[] = { "A0", "A1", "D0", "D1", "D2", "D3" };
	public static final String funciones[] = { "AND", "OR", "NOT", "IF" };
	private TArbol tree;
	private double puntuation;
	private double puntuation_acc;
	private String fenotipo;
	private double fitness;
	private double fitnessDisplaced;

	// Para la tabla multiplexor
	private static int soluciones6[][];
	private static int soluciones11[][];
	private static int soluciones[][];
	private static int numSolutions;

	public Chromosome(int profundidad, CreationType type, boolean useIf, MultiplexType type_) {

		tree = new TArbol(profundidad, useIf);
		switch (type) {
		case Grow:
			tree.inicializacionCreciente(0);
			break;
		case Full:
			tree.inicializacionCompleta(0, 0);
			break;
		case RampedNHalf:
			int ini = new Random().nextInt(2);
			if (ini == 0)
				tree.inicializacionCreciente(0);
			else
				tree.inicializacionCompleta(0, 0);
			break;
		}
		
		if(type_ == MultiplexType.SixEntries) {
			numSolutions = (int) Math.pow(2, 6);
			soluciones = soluciones6;
		}
		else {
			numSolutions = (int) Math.pow(2, 11);
			soluciones = soluciones11;
		}
	}
	
	public static void setUpData() {
		numSolutions = (int) Math.pow(2, 6);
		fillTable();
	    numSolutions = (int) Math.pow(2, 11);
		fillTable();
	}
	
	private static void fillTable() {
		int rows = numSolutions;
		int columns = (int)(Math.log(numSolutions) / Math.log(2)) + 1;
		File archivo = null;
	    Scanner in = null;
	    try {
	    	int fileNumber = columns - 1;
	    	String file = "src/utilsMultiplex/multiplex" + String.valueOf(fileNumber) + ".txt";
	        archivo = new File (file); 
	        in = new Scanner(archivo);
	        if(fileNumber == 6) {
	        	soluciones6 = new int[rows][columns];
		        for(int i = 0; i < rows; i++)
		          	 for(int j = 0; j < columns; j++)
		          		soluciones6[i][j] = in.nextInt(); 
	        }else {
	        	soluciones11 = new int[rows][columns];
		        for(int i = 0; i < rows; i++)
		          	 for(int j = 0; j < columns; j++)
		          		soluciones11[i][j] = in.nextInt(); 
	        }
	        
	        if (in != null) in.close();
	      }
	      catch(Exception e){
	    	  System.out.println(e.getMessage());
	      }
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
		int lenght = soluciones[0].length;
		
		int fallos = numSolutions;
		for (int i = 0; i < numSolutions; i++) {
			ArrayList<String> f = convFuncion(func, i);
			int res = evaluar(f, 0);
			if (res == soluciones[i][lenght - 1])
				fallos--;
		}
		return fallos;
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
				if (numSolutions == 64) {
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
				} else if (numSolutions == 2048) {
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

	public TArbol getTree() {
		return this.tree;
	}

	//AAAAA TODO No se usa el fenotipo ni se cambia ni ostias xd
	public String getPhenotype() {
		fenotipo = parseToString();
		return fenotipo;
	}
	
	private String parseToString() {
		ArrayList<String> func = tree.toArray();
		String s = "(";
		int numParamOperator = 0;
		for(int i = 0; i < func.size(); i++){
			s += func.get(i);
			if(i < func.size()-1) s += " ";
		}
		s+= ")";
		return s;
	}

	// Tamaño total de ambos genes
	public int getTam() {

		// TODO AAA NO SE SI ES NUMNODOS O NUNHIJOS
		return tree.getNumNodes();
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
