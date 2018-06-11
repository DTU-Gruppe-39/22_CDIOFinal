package DTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import JDBC.Connector;

public class Recept {
	/** recept id i området 1-99999999 */
	private int receptId;

	/** Receptnavn min. 2 max. 20 karakterer */
	private String receptNavn;

	/** Ingredienser i recept */
	private ArrayList<ReceptKompDTO> receptKomponent;

	public Recept() {}
	
	public Recept(int receptId, String receptNavn) {
		// TODO Auto-generated constructor stub
		this.receptId = receptId;
		this.receptNavn = receptNavn;
	}


	public String toString() {
		return "receptId: " + receptId + ", receptNavn: " + receptNavn + 
				", recept komponents: " + Arrays.toString(receptKomponent.toArray());
	}

	public int getReceptId() {
		return receptId;
	}

	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}

	public String getReceptNavn() {
		return receptNavn;
	}

	public void setReceptNavn(String receptNavn) {
		this.receptNavn = receptNavn;
	}

	public ArrayList<ReceptKompDTO> getReceptKomponent() {
		return receptKomponent;
	}

	public void setReceptKomponent(ArrayList<ReceptKompDTO> receptKomponent) {
		this.receptKomponent = receptKomponent;
	}

	public String findReceptName (int id) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		String recept = null;
		PreparedStatement getReceptName = null;
		ResultSet rs = null;
	
		String getRecept = "Select recept_navn from produktbatch NATURAL JOIN recept where pb_id = ? group by recept_navn;";
	
		try {
			getReceptName = sqlCon.prepareStatement(getRecept);
	
			getReceptName.setInt(1, id);
			rs = getReceptName.executeQuery();
			if(rs.first()) {
				recept = rs.getString("recept_navn");	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(getReceptName != null) {
				getReceptName.close();
			}
		}
		return recept;
	}
}
