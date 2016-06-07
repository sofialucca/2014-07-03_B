package it.polito.tdp.meteo.bean;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.db.MeteoDAO;

public class Model {

	private double minCosto;

	private List<String> citta;
	private final int MAX_VISITE = 12;
	//private final int MAX_VISITE = 4; // versione ridotta
	private final int YEAR = 2013 ;
	
	private final double C = 100.0 ; // euro
	private final double K = 1.0 ; // euro / punto%
	
	private Month m ;
	private Map<CittaData, Double> leUmidita ;
	
	Map<Integer,String> viaggioMigliore ;

	public List<UmiditaCitta> getUmiditaMedia(Month m) {
		MeteoDAO dao = new MeteoDAO();
		return dao.getUmiditaMedia(m);
	}
	
	public Map<Integer,String> ricercaViaggio(Month m) {
		this.m = m ; // salvo il mese perché mi serve nel calcolo costo
		
		Map<Integer,String> viaggio = new HashMap<>() ;
		
		LocalDate primoGiorno = LocalDate.of(YEAR, m, 1) ; 
		LocalDate ultimoGiorno = primoGiorno.with(TemporalAdjusters.lastDayOfMonth());

		MeteoDAO dao = new MeteoDAO() ;
		
		citta = dao.getElencoCitta() ;
		leUmidita = dao.getElencoUmidita() ;
		
		this.viaggioMigliore = null;
		this.minCosto = Double.MAX_VALUE ;
		
		ricerca(viaggio, 1, ultimoGiorno.getDayOfMonth()) ;
		//ricerca(viaggio, 1, 10) ;  // versione ridotta

		return this.viaggioMigliore ;
	}
		

	private void ricerca(Map<Integer,String> viaggio, int step, int ldom) {
		
		// terminazione
		if( step==ldom+1 ) {
			double costo=calcolaCosto(viaggio, ldom) ;
			if(costo<minCosto) {
				minCosto = costo ;
				
				System.out.println("Costo "+minCosto);
				
				//salva UNA COPIA DELLA la soluzione trovata
				
				viaggioMigliore = new HashMap<>(viaggio) ;
			}
			return ;
		}
		
		// caso normale
		for(String c : citta) {
			if(numVisite(c, viaggio)<MAX_VISITE) {
				viaggio.put(step, c) ;
				ricerca(viaggio, step+1, ldom) ;
				viaggio.remove(step) ;
			}
		}		
		
	}
		
	

	private double calcolaCosto(Map<Integer, String> viaggio, int ldom) {
		double costo = 0.0 ;
		
		String cittaIeri = null ;
		for(int giorno = 1; giorno<=ldom; giorno++) {
			String cittaOggi = viaggio.get(giorno) ;

			if( cittaIeri!=null && !cittaOggi.equals(cittaIeri)) {
				costo += C ;
			}
			cittaIeri = cittaOggi ;
			
			LocalDate oggi = LocalDate.of(YEAR, this.m, giorno) ;
			// trovare umitida nella 'cittaOggi' nel giorno 'oggi'
			Double um = leUmidita.get(new CittaData(cittaOggi, oggi)) ;
			if(um != null)
				costo += K * um ;
			else
				costo += K * 100.0 ;
				// se manca il dato, ipotizzo il caso peggiore
		}
		
		return costo ;
	}

	private int numVisite(String c, Map<Integer, String> viaggio) {
		int cnt = 0;
		for (String visitata : viaggio.values()) {
			if (visitata.equals(c))
				cnt++;
		}
		return cnt;
	}

}