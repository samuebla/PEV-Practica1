package functions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import individual.Chromosome;
import utilsMultiplex.MultiplexType;

public class FunctMultiplexor extends Function {

	public static int soluciones6[][];
	public static int soluciones11[][];
	private static int soluciones[][];
	private static int numSolutions;
	
	public FunctMultiplexor(MultiplexType type_) {
		maximize = true;
		
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
	
	@Override
	public double ejecutar(Chromosome c) {
		ArrayList<String> func = c.getTree().toArray();
		int lenght = soluciones[0].length;
		
		int fallos = 0;
		for (int i = 0; i < numSolutions; i++) {
			ArrayList<String> f = parseFunction(func, i);
			int res = recursiveEv(f, 0);
			if (res == soluciones[i][lenght - 1])
				fallos++;
		}
		return fallos;
	}
	
	public int recursiveEv(ArrayList<String> func, int index) {
		String function = func.get(index);
		int resul;
		// Si tiene 3 hijos
		if (function.equals("IF")) {
			// Los guardamos en una variable
			int son1 = recursiveEv(func, index + 1);
			int son2 = recursiveEv(func, index + 2);
			int hijo3 = recursiveEv(func, index + 3);
			// Si el primer hijo es 1 se hace lo del hijo siguiente
			// Si es un 0 (else) se hace lo del hijo 3
			if (son1 == 1) resul = son2;
			else resul = hijo3;
		}
		// Solo 1 y 1 = 1, si no es 0
		else if (function.equals("AND")) {
			int son1 = recursiveEv(func, index + 1);
			int son2 = recursiveEv(func, index + 2);
			if (son1 == 1 && son2 == 1)
				resul = 1;
			else
				resul = 0;
		}
		// Solo 0 0 = 0, si no es 1
		else if (function.equals("OR")) {
			int son1 = recursiveEv(func, index + 1);
			int son2 = recursiveEv(func, index + 2);
			if (son1 == 1 || son2 == 1)
				resul = 1;
			else
				resul = 0;
		}
		// 1 = 0 y 0 = 1
		else if (function.equals("NOT")) {
			int son1 = recursiveEv(func, index + 1);
			if (son1 == 1)
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
		public ArrayList<String> parseFunction(ArrayList<String> func, int sol) {
			ArrayList<String> parsedOperator = new ArrayList<String>();
			for (int i = 0; i < func.size(); i++) {
				String n = func.get(i);
				if (!n.equals("IF") && !n.equals("NOT") && !n.equals("OR") && !n.equals("AND")) {
					if (numSolutions == 64) {
						parsedOperator.add(Integer.toString(soluciones[sol][selectEntry6(n)]));
					} else if (numSolutions == 2048) {
						parsedOperator.add(Integer.toString(soluciones[sol][selectEntry11(n)]));
					}
				} else {
					parsedOperator.add(func.get(i));
				}
			}
			return parsedOperator;
		}
		
		private int selectEntry6(String entry) {
			switch (entry) {
			case "A0": 
				return 0;
			case "A1": 
				return 1;
			case "D0": 
				return 2;
			case "D1": 
				return 3;
			case "D2": 
				return 4;
			case "D3":
				return 5;
			}
			return 0;
		}
		
		private int selectEntry11(String entry) {
			switch (entry) {
			case "A0":
				return 0;
			case "A1": 
				return 1;
			case "A2": 
				return 2;
			case "D0": 
				return 3;
			case "D1": 
				return 4;
			case "D2":
				return 5;
			case "D3": 
				return 6;
			case "D4": 
				return 7;
			case "D5":
				return 8;
			case "D6":
				return 9;
			case "D7":
				return 10;
			}
			return 0;
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

}
