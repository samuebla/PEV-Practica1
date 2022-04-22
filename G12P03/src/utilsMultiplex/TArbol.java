package utilsMultiplex;

import java.util.ArrayList;
import java.util.Random;

import individual.Chromosome;

public class TArbol {

	public static enum Tipo {
		AVANZA, GIRA_DERECHA, GIRA_IZQUIERDA, SIC, PROGN2, PROGN3
	}

	private String valor;
	private ArrayList<TArbol> hijos;
	private int numHijos;
	private int numNodos;
	private int max_prof;
	private int profundidad;
	private boolean useIF;
	private boolean esHoja;
	private boolean esRaiz;
	Tipo tipo;

	// Devuelve el arbol en forma de array
	public ArrayList<String> toArray() {
		ArrayList<String> array = new ArrayList<String>();
		toArrayAux(array, this);
		return array;
	}

	// Constructora por defecto
	public TArbol() {

		valor = "";
		hijos = new ArrayList<TArbol>();
		numHijos = 0;
		numNodos = 0;

	}

	public TArbol(String v) {
		valor = v;
		hijos = new ArrayList<TArbol>();
		numHijos = 0;
	}

	// Para los IF
	public TArbol(int depth, boolean useIF_) {
		valor = "";
		hijos = new ArrayList<TArbol>();
		numHijos = 0;
		max_prof = depth;
		setProfundidad(0);
		numNodos = 0;
		useIF = useIF_;
	}

	// Insertar un valor en el arbol (nodo simple)
	public TArbol insert(String v, int index) {
		TArbol a = new TArbol(v);
		if (index == -1) {
			hijos.add(a);
			setNumHijos(hijos.size());
		} else
			hijos.set(index, a);
		return a;
	}

	// Insertar un arbol en otro arbol.
	public void insert(TArbol a, int index) {
		if (index == -1) {
			hijos.add(a);
			setNumHijos(hijos.size());
		} else
			hijos.set(index, a);
	}

	public TArbol at(int index) {
		return at(this, 0, index);
	}

	private TArbol at(TArbol a, int pos, int index) {
		TArbol s = null;
		if (pos >= index)
			s = a;
		else if (a.getNumHijos() > 0) {
			for (int i = 0; i < a.getNumHijos(); i++)
				if (s == null)
					s = at(a.getHijos().get(i), pos + i + 1, index);
		}
		return s;
	}

	private void toArrayAux(ArrayList<String> array, TArbol a) {
		array.add(a.valor);
		for (int i = 0; i < a.hijos.size(); i++) {
			toArrayAux(array, a.hijos.get(i));
		}
	}

	// Creamos ramas hasta llegar siempre a la maxima profundidad
	public int inicializacionCompleta(int p, int nodos) {
		int n = nodos;
		int nHijos = 2;
		if (p < max_prof) {
			setProfundidad(p);
			Random rnd = new Random();
			int func = 0;
			if (useIF) {
				func = rnd.nextInt(Chromosome.funciones.length);
			} else {
				func = rnd.nextInt(Chromosome.funciones.length - 1);
			}
			this.valor = Chromosome.funciones[func];
			this.setEsRaiz(true);
		}
		// Si es una hoja
		else {
			setProfundidad(p);
			Random rnd = new Random();
			int terminal;
			this.setEsHoja(true);
			terminal = rnd.nextInt(Chromosome.terminales.length);
			valor = Chromosome.terminales[terminal];
			esHoja = true;
			numHijos = 0;

		}

		setNumNodos(n);

		return n;
	}

	// Creamos ramales hasta que se llegue por primera vez al máximo permitido
	public void inicializacionCreciente(int i) {

		int n = 0;

		// Mientras no hayas llegado a la longitud final...
		if (profundidad < max_prof) {
			// Sigo creando ramales
			setProfundidad(profundidad);
			Random rnd = new Random();
			int func = 0;

			if (useIF) {
				// Para poder seleccionar también el if
				func = rnd.nextInt(Chromosome.funciones.length);
			} else {
				// -1 Para eliminar el IF que tenemos puesto al final
				func = rnd.nextInt(Chromosome.funciones.length - 1);
			}

			// Le ponemos el valor seleccionado
			this.valor = Chromosome.funciones[func];
			esRaiz = true;
			n = creaHijos(profundidad, n);
		}
		// Si he llegado al final...
		else {
			setProfundidad(profundidad);
			Random rnd = new Random();
			int terminal;
			terminal = rnd.nextInt(Chromosome.terminales.length);
			valor = Chromosome.terminales[terminal];
			esHoja = true;
			// Y no creo más ramas ni nada
		}

		setNumNodos(n);
	}

	private int creaHijos(int p, int nodos) {
		int n = nodos;

		// Para establecer cuantos hijos hay que crear en funcion
		// Del valor establecido
		int nHijos = 2;

		if (valor.equals("IF"))
			nHijos = 3;
		if (valor.equals("NOT"))
			nHijos = 1;

		// Creamos un arbol por cada hijo
		for (int i = 0; i < nHijos; i++) {
			TArbol hijo = new TArbol(max_prof, useIF);
			// Un nodo mas
			n++;
			// Desarro
			n = hijo.inicializacionCrecienteAux(p + 1, n);
			// Le añadimos como hijo
			hijos.add(hijo);
			numHijos++;
		}
		return n;
	}

	// Para crear terminales aleatorios o seguir creciendo el arbol poco a poco
	private int inicializacionCrecienteAux(int p, int nodos) {

		int n = nodos;

		// Si sigo sin llegar al maximo...
		if (p < max_prof) {

			setProfundidad(p);
			Random rnd = new Random();
			int rango;

			// Si hay 3 hijos...
			if (useIF) {
				// Se coge todo el rango
				rango = Chromosome.funciones.length + Chromosome.terminales.length;

				// Se coge uno aleatorio
				int pos = rnd.nextInt(rango);

				// Cojo una terminal
				if (pos >= Chromosome.funciones.length) {

					pos -= Chromosome.funciones.length;
					valor = Chromosome.terminales[pos];
					// Y es una hoja y ale
					esHoja = true;

				}
				// O una funcion
				else {
					valor = Chromosome.funciones[pos];
					esRaiz = true;
					// Y sigo creando más hijos
					n = creaHijos(p, n);
				}
			}
			// No es un if
			else {
				// Lo mismo que arriba pero sin el if
				rango = Chromosome.funciones.length + Chromosome.terminales.length - 1;

				int pos = rnd.nextInt(rango);

				if (pos >= (Chromosome.funciones.length - 1)) {
					pos -= Chromosome.funciones.length - 1;
					valor = Chromosome.terminales[pos];
					esHoja = true;
				} else {
					valor = Chromosome.funciones[pos];
					esRaiz = true;
					n = creaHijos(p, n);
				}
			}
		}
		// Llegué al final
		else {
			setProfundidad(p);
			Random rnd = new Random();
			int terminal;
			// Seleccionamos un terminal aleatorio
			terminal = rnd.nextInt(Chromosome.terminales.length);
			valor = Chromosome.terminales[terminal];
			esHoja = true;
			// Y no creamos mas hijos
		}
		setNumNodos(n);
		return n;
	}

	public int getNodos(TArbol nodo, int n) {
		
		//Caso base
		if (nodo.esHoja)
			return n;

		//Recursion
		if (nodo.valor.equals("AND") || nodo.valor.equals("OR")) {
			n = getNodos(nodo.hijos.get(0), n + 1);
			n = getNodos(nodo.hijos.get(1), n + 1);
		} else if (nodo.valor.equals("IF")) {
			n = getNodos(nodo.hijos.get(0), n + 1);
			n = getNodos(nodo.hijos.get(1), n + 1);
			n = getNodos(nodo.hijos.get(2), n + 1);
		} else {
			n = getNodos(nodo.hijos.get(0), n + 1);
		}
		return n;
	}

	public int getNumHijos() {
		return numHijos;
	}

	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}

	public int getNumNodos() {
		return numNodos;
	}

	public void setNumNodos(int numNodos) {
		this.numNodos = numNodos;
	}

	public int getProfundidad() {
		return profundidad;
	}

	public void setProfundidad(int profundidad) {
		this.profundidad = profundidad;
	}

	public ArrayList<TArbol> getHijos() {
		return hijos;
	}

	public boolean isEsRaiz() {
		return esRaiz;
	}

	public void setEsRaiz(boolean esRaiz) {
		this.esRaiz = esRaiz;
	}

	public boolean isEsHoja() {
		return esHoja;
	}

	public void setEsHoja(boolean esHoja) {
		this.esHoja = esHoja;
	}
}
