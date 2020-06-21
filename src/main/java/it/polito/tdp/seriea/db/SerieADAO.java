package it.polito.tdp.seriea.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.seriea.model.PuntiVittoria;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.Team;

public class SerieADAO {

	public List<Season> listAllSeasons() {
		String sql = "SELECT season, description FROM seasons";
		List<Season> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Season(res.getInt("season"), res.getString("description")));
			}

			conn.close();
			return result;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public List<Team> listTeams() {
		String sql = "SELECT team FROM teams";
		List<Team> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				result.add(new Team(res.getString("team")));
			}

			conn.close();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PuntiVittoria> vittorieInCasa(Team team) {
		String sql = "select s.season as stagione, count(FTR)*3 as vittorie_casa " + 
				"from matches m, seasons s " + 
				"where s.season = m.season " + 
				"and m.HomeTeam = ? " + 
				"and m.FTR = 'H' " + 
				"group by s.season";
		List<PuntiVittoria> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				PuntiVittoria p = new PuntiVittoria (res.getInt("stagione"), res.getInt("vittorie_casa"));
				result.add(p);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PuntiVittoria> vittorieFuori(Team team) {
		String sql = "select s.season as stagione, count(FTR)*3 as vittorie_fuori " + 
				"from matches m, seasons s " + 
				"where s.season = m.season " + 
				"and m.AwayTeam = ? " + 
				"and m.FTR = 'A' " + 
				"group by s.season";
		
		List<PuntiVittoria> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				PuntiVittoria p = new PuntiVittoria (res.getInt("stagione"), res.getInt("vittorie_fuori"));
				result.add(p);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PuntiVittoria> pareggiFuori(Team team) {
		String sql = "select s.season as stagione, count(FTR) as pareggi " + 
				"from matches m, seasons s " + 
				"where s.season = m.season " + 
				"and m.AwayTeam = ? " + 
				"and m.FTR = 'D' " + 
				"group by s.season";
		
		List<PuntiVittoria> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				PuntiVittoria p = new PuntiVittoria (res.getInt("stagione"), res.getInt("pareggi"));
				result.add(p);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public List<PuntiVittoria> pareggiCasa(Team team) {
		String sql = "select s.season as stagione, count(FTR) as pareggi " + 
				"from matches m, seasons s " + 
				"where s.season = m.season " + 
				"and m.HomeTeam = ? " + 
				"and m.FTR = 'D' " + 
				"group by s.season";
		
		List<PuntiVittoria> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();
		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, team.getTeam());
			
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				PuntiVittoria p = new PuntiVittoria (res.getInt("stagione"), res.getInt("pareggi"));
				result.add(p);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	

}

