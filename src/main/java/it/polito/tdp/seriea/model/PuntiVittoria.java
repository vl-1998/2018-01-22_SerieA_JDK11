package it.polito.tdp.seriea.model;

public class PuntiVittoria implements Comparable <PuntiVittoria>{
	private Integer season;
	private Integer puntiVittoria;
	/**
	 * @param season
	 * @param puntiVittoria
	 */
	public PuntiVittoria(Integer season, Integer puntiVittoria) {
		super();
		this.season = season;
		this.puntiVittoria = puntiVittoria;
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Integer getPuntiVittoria() {
		return puntiVittoria;
	}
	public void setPuntiVittoria(Integer puntiVittoria) {
		this.puntiVittoria = puntiVittoria;
	}
	@Override
	public int compareTo(PuntiVittoria o) {
		return this.season-o.getSeason();
	}
	@Override
	public String toString() {
		return String.format("Stagione: %d, punti Realizzati = %d", season, puntiVittoria);
	}
	
	
	
	
	
	

}
