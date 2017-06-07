package it.polito.tdp.babs.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class Simulazione {
	SimulationResult simRes;
	private Map<Integer,Integer> mapStationOccupacy;
	private Model model;
	
	private enum EventType{
		PICK, DROP;
	}
	PriorityQueue<Event> queue;
	
	
	public Simulazione(Model model){
		queue = new PriorityQueue<>();
		simRes = new SimulationResult();
		this.mapStationOccupacy = new HashMap<>();
		this.model = model;
	}
	public void loadPick(List<Trip> trips){
		for(Trip trip: trips){
			queue.add(new Event(EventType.PICK, trip.getStartDate(), trip));
		}
	}
//	public void loadDrop(List<Trip> trips){
//		for(Trip trip: trips){
//			queue.add(new Event(EventType.DROP, trip.getStartDate(), trip));
//		}	
//	}

	public void loadStations(double k, List<Station> stazioni){
		for(Station s : stazioni){
			int occupacy = (int)(s.getDockCount()*k);
			this.mapStationOccupacy.put(s.getStationID(), occupacy);
		}
	}
	
	public void run() {
		while(queue.isEmpty()){
			Event event = queue.poll();
			switch(event.type){
			case PICK :
				//ci sono biciclette disponibili?
				int occupacy = this.mapStationOccupacy.get(event.trip.getStartStationID());
				if(occupacy >0){
					occupacy--;
					this.mapStationOccupacy.put(event.trip.getStartStationID(), occupacy);
					queue.add(new Event(EventType.DROP,event.trip.getEndDate(),event.trip));
				}else{
					simRes.increaseNumberOfPickMiss();
				}
				break;
			case DROP :
				occupacy =this.mapStationOccupacy.get(event.trip.getEndStationID());
				int availability = model.getStationById(event.trip.getEndStationID()).getDockCount(); //num posti totali
				if(occupacy >=availability){
					simRes.increaseNumberOfDropMiss();
				}else{
					occupacy++;
					this.mapStationOccupacy.put(event.trip.getStartStationID(), occupacy);
					//model.getStationById(event.trip.getEndStationID())
				}
				
				break;
			}
		}
		
	}
	public SimulationResult collectResults(){
		return simRes;
		
	}
	private class Event implements Comparable<Event>{
		EventType type;
		LocalDateTime ldt;
		Trip trip;
		
		
		public Event(EventType type, LocalDateTime ldt, Trip trip) {
			super();
			this.type = type;
			this.ldt = ldt;
			this.trip = trip;
		}


		@Override
		public int compareTo(Event o) {
			
			return this.ldt.compareTo(o.ldt);
		}
		
	}
	

}
