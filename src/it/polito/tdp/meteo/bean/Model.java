package it.polito.tdp.meteo.bean;

import java.time.Month;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {
	
	private double minCosto ;
	
	public List<UmiditaCitta> getUmiditaMedia(Month m) {
		MeteoDAO dao = new MeteoDAO() ;
		return dao.getUmiditaMedia(m) ;
	}
	
	private void ricerca(Map<Integer,String> viaggio, int step, int ldom) {
		
		// terminazione
		if( step==ldom+1 ) {
			double costo=calcolaCosto(viaggio, ldom) ;
			if(costo<minCosto) {
				minCosto = costo ;
				salva la soluzione trovata ;
			return ;
		}
		
		// caso normale
		if("Torino" non ha ancora 12 visite) {
			viaggio.put(step, "Torino") ;
			ricerca(viaggio, step+1, ldom) ;
			viaggio.remove(step) ;
		}
		
		"Milano"
		
		"Genova"
		
		
	}
	

}
