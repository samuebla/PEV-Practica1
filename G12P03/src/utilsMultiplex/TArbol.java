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

	// El equivalente a la constructora por copia
	public TArbol copia() {
		TArbol copia = new TArbol(this.max_prof, this.useIF);

		// Copiamos todas las variables
		copia.setEsHoja(this.esHoja);
		copia.setEsRaiz(this.esRaiz);
		copia.setNumHijos(this.numHijos);
		copia.setNumNodos(this.numNodos);
		copia.setProfundidad(this.profundidad);
		copia.setValor(this.valor);

		ArrayList<TArbol> aux = new ArrayList<TArbol>();

		aux = copiaHijos();
		copia.setHijos(aux);

		return copia;
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
					s = at(a.getSons().get(i), pos + i + 1, index);
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

		// Caso base
		if (nodo.esHoja)
			return n;

		// Recursion
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

	public void getTerminales(ArrayList<TArbol> hijos, ArrayList<TArbol> nodos) {

		for (int i = 0; i < hijos.size(); i++) {
			// Si es una hoja...
			if (hijos.get(i).isLeaf()) {
				// Lo añadimos
				nodos.add(hijos.get(i).copia());
			}
			// Si no he llegado al final
			else {
				// Seguimos indagando
				getTerminales(hijos.get(i).getSons(), nodos);
			}
		}
	}

	public int insertTerminal(ArrayList<TArbol> list_hijos, TArbol terminal, int index, int pos) {
		// Para la recursion
		int p = pos;

		// Recorremos los hijos
		for (int i = 0; i < list_hijos.size() && p != -1; i++) {

			// Si he llegado a un terminal y estamos en el index indicado...
			if (list_hijos.get(i).isLeaf() && (p == index)) {

				// Le cambiamos el terminal
				list_hijos.set(i, terminal.copia());

				// Y con esto detenemos la recursion
				p = -1;
			}
			// Si estas en una hoja pero no en la indicada...
			else if (list_hijos.get(i).esHoja && (p != index)) {
				// Seguimos avanzando
				p++;
			}
			// Si ni si quiera he llegado a una hoja
			else {
				// Seguimos profundizando con sus hijos...
				p = insertTerminal(list_hijos.get(i).hijos, terminal, index, p);
			}
		}

		return p;
	}

	// Devuelve los nodos internos del árbol
	public void getFunciones(ArrayList<TArbol> hijos, ArrayList<TArbol> nodos) {

		for (int i = 0; i < hijos.size(); i++) {
			// Si los hijos no son una terminal
			if (hijos.get(i).isRoot()) {
				// Guardamos las funciones
				nodos.add(hijos.get(i).copia());
				// Y llamamos a los hijos de los hijos
				getFunciones(hijos.get(i).hijos, nodos);
			}
		}
	}

	// Parecido al inserTerminal pero sin necesitar llegar al final del arbol
	public int insertFuncion(ArrayList<TArbol> list_hijos, TArbol mutated, int index, int pos) {
		int p = pos;
		for (int i = 0; i < list_hijos.size() && p != -1; i++) {
			// Si no es hoja y he llegado al lugar donde quiero cambiar el arbol
			if (list_hijos.get(i).esRaiz && (p == index)) {
				// Cogemos la posicion y le pasamos el arbol mutado
				list_hijos.set(i, mutated.copia());
				// Y con esto finalizamos el bucle
				p = -1;
			}
			// Mientras no sea hoja pero no sea la posicion que yo quiero
			else if (list_hijos.get(i).esRaiz && (p != index)) {
				// Seguimos avanzando por los hijos
				p++;
				p = insertFuncion(list_hijos.get(i).hijos, mutated, index, p);
			}
		}
		return p;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String aux) {
		valor = aux;
	}

	public int getNumHijos() {
		return numHijos;
	}

	public void setNumHijos(int numHijos) {
		this.numHijos = numHijos;
	}

	public int getNumNodes() {
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

	public ArrayList<TArbol> getSons() {
		return hijos;
	}

	public void setHijos(ArrayList<TArbol> hijos) {
		this.hijos = hijos;
	}

	// Copiamos los hijos
	private ArrayList<TArbol> copiaHijos() {
		ArrayList<TArbol> sonsCopy = new ArrayList<TArbol>();

		// Recorremos todos los hijos
		for (int i = 0; i < this.hijos.size(); i++) {
			// Y los añadimos al array
			sonsCopy.add(this.hijos.get(i).copia());
		}

		return sonsCopy;
	}

	public boolean isRoot() {
		return esRaiz;
	}

	public void setEsRaiz(boolean esRaiz) {
		this.esRaiz = esRaiz;
	}

	public boolean isLeaf() {
		return esHoja;
	}

	public void setEsHoja(boolean esHoja) {
		this.esHoja = esHoja;
	}
}
