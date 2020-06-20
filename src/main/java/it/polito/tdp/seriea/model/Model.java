package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polito.tdp.seriea.db.SerieADAO;

public class Model {
	private SerieADAO dao;
	
	public Model() {
		this.dao = new SerieADAO();
	}
	
	public List<Team> getTeams (){
		List<Team> team = this.dao.listTeams();
		Collections.sort(team);
		return team;
	}
	
	public List<PuntiVittoria> puntiCampionato(Team team){
		List<PuntiVittoria> result = new ArrayList<>();
		List<PuntiVittoria> pareggioCasa = this.dao.pareggiCasa(team);
		List<PuntiVittoria> pareggioFuori = this.dao.pareggiFuori(team);
		List<PuntiVittoria> vincitaCasa = this.dao.vittorieInCasa(team);
		List<PuntiVittoria> vincitaFuori = this.dao.vittorieFuori(team);
		
		for (PuntiVittoria p : pareggioCasa) {
			for (PuntiVittoria vc : vincitaCasa) {
				for (PuntiVittoria vf : vincitaFuori) {
					for (PuntiVittoria pp : pareggioFuori) {
						if (p.getSeason().equals(vc.getSeason())&&vc.getSeason().equals(vf.getSeason()) && vf.getSeason().equals(pp.getSeason())) {
							Integer puntiTot = p.getPuntiVittoria()+vf.getPuntiVittoria()+vc.getPuntiVittoria()+pp.getPuntiVittoria();
							PuntiVittoria pTemp = new PuntiVittoria (p.getSeason(), puntiTot);
							result.add(pTemp);
						}
					}
					}
				}
			}
		
		Collections.sort(result);
		
		return result;
	}
}
