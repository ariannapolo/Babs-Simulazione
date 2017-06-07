package it.polito.tdp.babs;

import java.net.URL;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.babs.model.Model;
import it.polito.tdp.babs.model.SimulationResult;
import it.polito.tdp.babs.model.Simulazione;
import it.polito.tdp.babs.model.Statistics;
import it.polito.tdp.babs.model.Trip;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;

public class BabsController {

	private Model model;

	public void setModel(Model model) {
		this.model = model;
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private DatePicker pickData;

	@FXML
	private Slider sliderK;

	@FXML
	private TextArea txtResult;

	@FXML
	void doContaTrip(ActionEvent event) {
		this.txtResult.clear();
		LocalDate ld = pickData.getValue();
		if(ld == null){
			txtResult.setText("Selezionare una data");
			return;
		}
		List<Statistics> stats = model.getStatistics(ld);
		Collections.sort(stats);
		
		for(Statistics stat : stats){
			if(stat.getPick()<=0)
				this.txtResult.appendText(String.format("Stazione %s con 0 pick", stat.getStazione().getName()));
			else
				this.txtResult.appendText(String.format("%s %d %d \n", stat.getStazione().getName(),stat.getPick(),stat.getDrop()));
		}
	}

	@FXML
	void doSimula(ActionEvent event) {
		this.txtResult.clear();
		LocalDate ld = pickData.getValue();
		if(ld == null){
			txtResult.setText("Selezionare una data");
			return;
		}
		
		List<Trip> tripsPick = model.getTripsForDayPick(ld);
		List<Trip> tripsDrop = model.getTripsForDayDrop(ld);
		
		Simulazione simulazione = new Simulazione(model);
		simulazione.loadPick(tripsPick);
		//Potrebbe falsare il risultato della simulazione
		//simulazione.loadDrop(tripsDrop);
		simulazione.run();
		SimulationResult sr = simulazione.collectResults();
		txtResult.appendText("PICK MISS: "+sr.getNumberOfPickMiss());
		txtResult.appendText("\nDROP MISS: "+sr.getNumberOfDropMiss());

	}

	@FXML
	void initialize() {
		assert pickData != null : "fx:id=\"pickData\" was not injected: check your FXML file 'Babs.fxml'.";
		assert sliderK != null : "fx:id=\"sliderK\" was not injected: check your FXML file 'Babs.fxml'.";
		assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Babs.fxml'.";

		pickData.setValue(LocalDate.of(2013, 9, 1));
	}
}
