package it.polito.tdp.babs.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.babs.model.Trip;
import it.polito.tdp.babs.db.BabsDAO;

public class Model {
	private BabsDAO dao;
	private List<Station> stazioni;
	private Map<Integer,Station> mappaStazioni;
	
	public Model(){
		this.dao = new BabsDAO();
		this.mappaStazioni = new HashMap<>();
	}
	
	public List<Station> getStazioni(){
		if(stazioni==null){
			stazioni = dao.getAllStations();
			for(Station s : stazioni){
				mappaStazioni.put(s.getStationID(), s);
			}
		}
		return stazioni;
	}

	public List<Statistics> getStatistics(LocalDate ld) {
		//per ogni data voglio contare per ogni stazione il numero di volte in cui le bici sono state prese
		List<Statistics> stats = new ArrayList<>();
		for(Station s: getStazioni()){
			int picks = dao.getPickNumber(s, ld);
			int drops = dao.getDropNumber(s,ld);
			Statistics stat = new Statistics(s,picks,drops);
			stats.add(stat);
		}
		return stats;
	}

	public List<Trip> getTripsForDayPick(LocalDate ld) {
		
		return dao.getTripsForDayPick(ld);
	}
	public List<Trip> getTripsForDayDrop(LocalDate ld) {
			
			return dao.getTripsForDayDrop(ld);
	}
	public Station getStationById(int id){
		return mappaStazioni.get(id);
	}

}
