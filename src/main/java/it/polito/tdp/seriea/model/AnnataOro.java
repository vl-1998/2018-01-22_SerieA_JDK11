package it.polito.tdp.seriea.model;

public class AnnataOro {
	private Integer season;
	private Integer differenza;
	/**
	 * @param season
	 * @param differenza
	 */
	public AnnataOro(Integer season, Integer differenza) {
		super();
		this.season = season;
		this.differenza = differenza;
	}
	public AnnataOro() {
	}
	public Integer getSeason() {
		return season;
	}
	public void setSeason(Integer season) {
		this.season = season;
	}
	public Integer getDifferenza() {
		return differenza;
	}
	public void setDifferenza(Integer differenza) {
		this.differenza = differenza;
	}
	@Override
	public String toString() {
		return "AnnataOro season=" + season + ", differenza=" + differenza;
	}
	
	
}
