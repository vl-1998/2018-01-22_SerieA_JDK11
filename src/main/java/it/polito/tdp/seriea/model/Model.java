package it.polito.tdp.seriea.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.seriea.db.SerieADAO;



public class Model {
	private SerieADAO dao;
	private List<PuntiVittoria> result;
	private Graph <Integer, DefaultWeightedEdge> grafo;
	private AnnataOro aoBest;
	
	public Model() {
		this.dao = new SerieADAO();
	}
	
	public List<Team> getTeams (){
		List<Team> team = this.dao.listTeams();
		Collections.sort(team);
		return team;
	}
	
	public List<PuntiVittoria> puntiCampionato(Team team){
		result = new ArrayList<>();
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
	
	public void creaGrafo() {
		this.grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		int peso = 0;
		for (PuntiVittoria pv: result) {
			this.grafo.addVertex(pv.getSeason());
		}
		
		for (PuntiVittoria i : result) {
			for (PuntiVittoria j : result) {
				if (i!=j) {
					if (i.getPuntiVittoria()>j.getPuntiVittoria()) {
						peso = i.getPuntiVittoria()-j.getPuntiVittoria();
						Graphs.addEdgeWithVertices(this.grafo, j.getSeason(), i.getSeason(), peso);
					} else {
						peso = j.getPuntiVittoria()-i.getPuntiVittoria();
						Graphs.addEdgeWithVertices(this.grafo, i.getSeason(), j.getSeason(), peso);
					}
				}
			}
		}	
		
	}
	
	public int vertexNumber() {
		return this.grafo.vertexSet().size();
	}
	public int edgeNumber() {
		return this.grafo.edgeSet().size();
	}
	
	public AnnataOro annataOro() {
		int entranti = 0;
		int uscenti = 0;
		int differenza = 0;
		
		//QUESTO NON FUNZIONA SCRITTO DA ME
		/*for (Integer i : this.grafo.vertexSet()) {
			List<Integer> successori = Graphs.successorListOf(this.grafo, i);
			List<Integer> predecessori = Graphs.predecessorListOf(this.grafo, i);
			for (Integer s : successori) {
				for (Integer p : predecessori) {
					entranti += this.grafo.getEdgeWeight(this.grafo.getEdge(p, i));
					uscenti += this.grafo.getEdgeWeight(this.grafo.getEdge(i, s));
					differenza = entranti-uscenti;
					if (aoBest == null) {
						this.aoBest = new AnnataOro (i,differenza);
					} else if (differenza> aoBest.getDifferenza()) {
						this.aoBest = new AnnataOro (i,differenza);
					}
				}
			}
			
		}*/
		
		for (Integer i : this.grafo.vertexSet()) {
			
			for (DefaultWeightedEdge e : this.grafo.incomingEdgesOf(i)) {
				entranti += this.grafo.getEdgeWeight(e);
			}
			
			for (DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(i)) {
				uscenti += this.grafo.getEdgeWeight(e);
			}
			differenza = entranti-uscenti;
			
			if (aoBest == null) {
				this.aoBest = new AnnataOro (i,differenza);
			} else if (differenza> aoBest.getDifferenza()) {
				this.aoBest = new AnnataOro (i,differenza);
			}
			
		}
		
		return aoBest;
	}

	
	
	
	
}
