package it.polito.tdp.babs.model;

public class SimulationResult {

	private int numberOfPickMiss = 0;
	private int numberOfDropMiss = 0;
	
	
	/**
	 * @param numberOfPickMiss the numberOfPickMiss to set
	 */
	public void increaseNumberOfPickMiss() {
		this.numberOfPickMiss++;
	}
	
	/**
	 * @param numberOfDropMiss the numberOfDropMiss to set
	 */
	public void increaseNumberOfDropMiss() {
		this.numberOfDropMiss++;
	}

	/**
	 * @return the numberOfPickMiss
	 */
	public int getNumberOfPickMiss() {
		return numberOfPickMiss;
	}

	/**
	 * @return the numberOfDropMiss
	 */
	public int getNumberOfDropMiss() {
		return numberOfDropMiss;
	}
	
}
