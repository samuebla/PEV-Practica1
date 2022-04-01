package functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import genetics.FlightGen;
import genetics.Gen;
import utilsFlight.FlightType;
import utilsFlight.TAsignacion;
import utilsFlight.TVuelo;

public class FunctionP2 extends Function {

	int numPistas_;
	List<ArrayList<Double>> separations_;
	List<TVuelo> info_vuelos_;
	
	boolean menor_tel_type;
	
	public FunctionP2(int numPistas, List<TVuelo> info_vuelos, List<ArrayList<Double>> separations, boolean menor_tel) {
		maximize = false;
		info_vuelos_ = info_vuelos;
		separations_ = separations;
		menor_tel_type = menor_tel;
	}
	

	//Suma del cuadrado de todos los retardos
	@Override
	public double ejecutar(List<Gen> individuo) {
		double fitness = 0;
		for(int i = 0; i < individuo.size(); i++) {
			int pista_asignada = 0; 
			//Almacena el TLA de todas las pistas, en funcion de llegada de los aviones asignados
			List<ArrayList<Double>> TLA_Pistas = new ArrayList<ArrayList<Double>>();
			FlightGen vuelo = (FlightGen) individuo.get(i);
			 
			//Tipo del avion que ya está en la pista (anterior)
			FlightType typeBefore_ = FlightType.G; //Compilation Purposes
			//Para cada pista del vuelo i-ésimo. Se calcula el menor TLA 
			List<Double> tel_pistas_cpy = info_vuelos_.get(vuelo.pos_vuelo).TTEL_vuelo;
			
			//Menor tla de las 3 pistas
			double menor_tla = Double.POSITIVE_INFINITY;
			//TEL definitivo a la pista asignada
			double TEL_vuelo = Double.POSITIVE_INFINITY; 
			for(int j = 0; j < tel_pistas_cpy.size(); j++) {
				//Tipo del avion que viene
				FlightType typeCurrent_ = info_vuelos_.get(vuelo.pos_vuelo).type_;
				//Separacion del vuelo actual con el que ya esta en pista (actual con anterior)
				double sep = separations_.get(typeBefore_.ordinal()).get(typeCurrent_.ordinal());
				//TEL de la pista j-ésima
				double TEL_pista = tel_pistas_cpy.get(j);
				//Calculo del menor TEL de las 3 pistas
				if(menor_tel_type && TEL_pista < TEL_vuelo)  TEL_vuelo = TEL_pista;
				
				//Var aux
				double TLA = 0; 
				if(TLA_Pistas.get(j).size() == 0) { // es el primer vuelo, no hay vuelos en esa pista
								//tla 0-> no hay tla anterior porque no hay ningun avion en la pista
								//sep 0-> al no haber avion, no hay separacion
					TLA = Math.max(0 + 0, TEL_pista);
				}else{					
									   		//Pista j-ésima | ultimo valor/vuelo añadido a esa pista
					double beforeTLA = TLA_Pistas.get(j).get(TLA_Pistas.get(j).size() - 1);
					//maximo del TLA anterior mas la separacion | Tel de esa pista
					TLA = Math.max( beforeTLA + sep, TEL_pista);
				}
				
				//Nos quedamos con el menor TLA (minimización)
				if(TLA < menor_tla) {
					menor_tla = TLA;
					pista_asignada = j;
				}
			}
			
			//Añadimos el vuelo j-esimo a la pista asiganda (con menor TLA) 
			TLA_Pistas.get(pista_asignada).add(menor_tla);
			vuelo.pistaAsignada = pista_asignada;
			vuelo.TLA = menor_tla;
			//Calculo del TEL que es basicamente su TEL de la pista asignada
			if(!menor_tel_type) TEL_vuelo = tel_pistas_cpy.get(pista_asignada);
			
			//Suma de fitness
			fitness += Math.pow(vuelo.TLA - TEL_vuelo, 2); 
		}
		
		return fitness;
	}
	
	private double alternative(List<Gen> individuo) {
		List<ArrayList<TAsignacion>> pistas = new ArrayList<ArrayList<TAsignacion>>();
		double adapt, hourBefore = 0, TEL, TLA, menor_TEL = Double.MAX_VALUE;
		
		//PREPARACION DEL VECTOR
		//Se encapsula en 3 listas. Cada lista es una pista con los vuelos asignados a ella.
		for(int i = 0; i< numPistas_; i++) {
			ArrayList<TAsignacion> pista = new ArrayList<TAsignacion>();
			for(Gen ind : individuo) {
				FlightGen vuelo = (FlightGen) ind;
				if(vuelo.pistaAsignada == i) {
					TAsignacion asig = new TAsignacion();
					asig.pos_gen = vuelo.pos_vuelo;
					asig.tel = info_vuelos_.get(vuelo.pos_vuelo).TTEL_vuelo.get(i);
					pista.add(asig);
				}
			}
			//Se ordena la lista de menor a mayor
			sort(pista);
			//Se añade a la lista
			pistas.add(pista);
		}
		
		//CALCULO DEL FITNESS
		adapt = 0;
		FlightType typeBefore_ = FlightType.G;
		int pista = 0; //Pista 1
		
//		List<Double> telsVuelos 
		
		
		for(List<TAsignacion> pista_ : pistas) {
			int i = 0; //Numero de vuelo de cada pista
			for(TAsignacion vuelo : pista_) {
				if(i == 0) { // se trata del primer vuelo (el de menor TEL) que llega
					hourBefore = info_vuelos_.get(vuelo.pos_gen).TTEL_vuelo.get(pista);
					typeBefore_ = info_vuelos_.get(vuelo.pos_gen).type_;
					i++;
				}else {
					TLA = hourBefore;
//					FILA = el que ya ha pasado | COLUMNA = el que viene
					FlightType typeCurrent_ = info_vuelos_.get(vuelo.pos_gen).type_;
					TLA = TLA + separations_.get(typeBefore_.ordinal()).get(typeCurrent_.ordinal());
					
					if(TLA >= info_vuelos_.get(vuelo.pos_gen).TTEL_vuelo.get(pista)) {
						hourBefore = TLA;
					}else {
						hourBefore = info_vuelos_.get(vuelo.pos_gen).TTEL_vuelo.get(pista);
					}
				}
			}
			pista++;
		}
		
		//Mirar el menor TEL
		
		
		for(int j = 0; j < individuo.size(); j++) {
			
			adapt += Math.pow(hourBefore - menor_TEL, 2);
		}
		
		
		return adapt;
	}
	
	private void sort(ArrayList<TAsignacion> pista) {
		Collections.sort(pista, new Comparator<TAsignacion>() {
			//Para quitar los warning
			@SuppressWarnings("removal")
			@Override
		    public int compare(TAsignacion c1, TAsignacion c2) {
		        return new Double(c1.tel).compareTo(new Double(c2.tel));
		    }
		});
	}

}
