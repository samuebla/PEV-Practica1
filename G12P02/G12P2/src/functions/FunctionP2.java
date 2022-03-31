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
	
	public FunctionP2(int numPistas, List<TVuelo> info_vuelos, List<ArrayList<Double>> separations) {
		maximize = false;
		info_vuelos_ = info_vuelos;
		separations_ = separations;
	}
	

	//Suma del cuadrado de todos los retardos
	@Override
	public double ejecutar(List<Gen> individuo) {
		List<ArrayList<TAsignacion>> pistas = new ArrayList<ArrayList<TAsignacion>>();
		double adapt, hour = 0, TEL, TLA, menor_TEL = Double.MAX_VALUE;
		
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
		int pistaCounter = 0; //Pista 1
		for(List<TAsignacion> pista_ : pistas) {
			int i = 0; //Numero de vuelo de cada pista
			for(TAsignacion vuelo : pista_) {
				if(i == 0) { // se trata del primer vuelo (el de menor TEL)
					hour = info_vuelos_.get(vuelo.pos_gen).TTEL_vuelo.get(pistaCounter);
					typeBefore_ = info_vuelos_.get(vuelo.pos_gen).type_;
					i++;
				}else {
					TLA = hour;
//					FILA = el que ya ha pasado | COLUMNA = el que viene
					FlightType typeCurrent_ = info_vuelos_.get(vuelo.pos_gen).type_;
					TLA = TLA + separations_.get(typeBefore_.ordinal()).get(typeCurrent_.ordinal());
					
					//MIRAR QUE HORA ES LA ANTERIOR Y LA ACTUAL
					if(TLA >= info_vuelos_.get(vuelo.pos_gen).TTEL_vuelo.get(pistaCounter)) {
						hour = TLA;
					}else {
						hour = info_vuelos_.get(vuelo.pos_gen).TTEL_vuelo.get(pistaCounter);
					}
				}
			}
			pistaCounter++;
		}
		
		//Mirar el menor TEL
		
		adapt = adapt + Math.pow(hour - menor_TEL, 2);
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
