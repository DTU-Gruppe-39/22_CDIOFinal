package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.ProduktBatchKompDAO;
import DTO.NotFoundException;
import DTO.produktBatchKompDTO;

public class MySQLProduktBatchKompDAO implements ProduktBatchKompDAO {

	// Returns the produktBatchKompDTO associated with the pbId and rbId parameters, from the database.
	@Override
	public produktBatchKompDTO getProduktBatchKomp(int pbId, int rbId) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement getProBatchKomp = null;
		ResultSet rs = null;
		produktBatchKompDTO PbkDTO = null;

		String getProBaKo = "SELECT * FROM produktbatchkomponent WHERE pb_id = ? AND rb_id = ?";

		try {
			getProBatchKomp = conn.prepareStatement(getProBaKo);
			getProBatchKomp.setInt(1, pbId);
			getProBatchKomp.setInt(2, rbId);
			rs = getProBatchKomp.executeQuery();
			if (!rs.first()) throw new DALException("Produktbatchkomponent ID: " + pbId + "eller Raavarebatch ID: " + rbId + " findes ikke");
			PbkDTO = new produktBatchKompDTO (rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id"));
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (getProBatchKomp != null) {
				getProBatchKomp.close();
			}
		}
		return PbkDTO;
	}

	// Returns a list of produktBatchKompDTO associated with the pbId parameter, from the database.
	@Override
	public List<produktBatchKompDTO> getProduktBatchKompList(int pbId) throws DALException, SQLException, NotFoundException {
		List<produktBatchKompDTO> list = new ArrayList<produktBatchKompDTO>();

		Connection conn = Connector.getConn();
		PreparedStatement getProdBatchKompList = null;
		ResultSet rs = null;

		String getProBaKoList = "SELECT * FROM produktbatchkomponent WHERE pb_id = ?";

		try {
			getProdBatchKompList = conn.prepareStatement(getProBaKoList);

			getProdBatchKompList.setInt(1, pbId);
			rs = getProdBatchKompList.executeQuery();
			while (rs.next())
			{
				list.add(new produktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("rolle_id")));
			}
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (getProdBatchKompList != null) {
				getProdBatchKompList.close();
			}
		}
		if(list.size() == 0)
		{
			throw new NotFoundException("Either ProduktBatch id doesn't exist or there are no components added to it yet - Error code Pbx04)");
		}
		return list;
	}

	// Returns a list of produktBatchKompDTO containing all the ProduktBatchKomp from the database. 
	@Override
	public List<produktBatchKompDTO> getProduktBatchKompList() throws DALException, SQLException {
		List<produktBatchKompDTO> list = new ArrayList<produktBatchKompDTO>();
		
		Connection conn = Connector.getConn();
		PreparedStatement getProdBatchListKomp = null;
		ResultSet rs = null;

		String getProBaListKo = "SELECT * FROM produktbatchkomponent";

		try {
			getProdBatchListKomp = conn.prepareStatement(getProBaListKo);
			rs = getProdBatchListKomp.executeQuery();
			while (rs.next()) 
			{
				list.add(new produktBatchKompDTO(rs.getInt("pb_id"), rs.getInt("rb_id"), rs.getDouble("tara"), rs.getDouble("netto"), rs.getInt("opr_id")));
			}
		} catch (SQLException e) { 
			//throw new DALException(e);
			//Do error handling
		} finally {
			if (getProdBatchListKomp != null) {
				getProdBatchListKomp.close();
			}
		}
		return list;
	}

	// Creates a produktBatchKomp in the database with the information from the produktBatchKompDTO parameter.
	@Override
	public void createProduktBatchKomp(produktBatchKompDTO produktbatchkomponent) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement createProBatchKomp = null;
		
		String createProBaKo = "CALL CreateProduktBatchKomp(?,?,?,?,?)";
		
		try {
			createProBatchKomp = conn.prepareStatement(createProBaKo);

			createProBatchKomp.setInt(1, produktbatchkomponent.getPbId());
			createProBatchKomp.setInt(2, produktbatchkomponent.getRbId());
			createProBatchKomp.setDouble(3, produktbatchkomponent.getTara());
			createProBatchKomp.setDouble(4, produktbatchkomponent.getNetto());
			createProBatchKomp.setInt(5, produktbatchkomponent.getRolle_id());
			createProBatchKomp.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (createProBatchKomp != null) {
				createProBatchKomp.close();
			}
		}
	}

	// Updates the produktbatchkomponent in the database with the information from the produktBatchKompDTO parameter.
	// The pbId and rbId from the DTO needs to be associated with a produktbatchkomponent in the database before this method is called.
	@Override
	public void updateProduktBatchKomp(produktBatchKompDTO produktbatchkomponent) throws DALException, SQLException {
		Connection conn = Connector.getConn();
		PreparedStatement updateProBatchKomp = null;

		String updateProBaKo = "UPDATE produktbatchkomponent SET  pb_id = ?, rb_id = ?, tara = ?, netto = ?, opr_id = ? WHERE pb_id = ? AND rb_id = ?";
		
		try {
			updateProBatchKomp = conn.prepareStatement(updateProBaKo);

			updateProBatchKomp.setInt(1, produktbatchkomponent.getPbId());
			updateProBatchKomp.setInt(2, produktbatchkomponent.getRbId());
			updateProBatchKomp.setDouble(3, produktbatchkomponent.getTara());
			updateProBatchKomp.setDouble(4, produktbatchkomponent.getNetto());
			updateProBatchKomp.setInt(5, produktbatchkomponent.getRolle_id());
			updateProBatchKomp.setInt(6, produktbatchkomponent.getPbId());
			updateProBatchKomp.setInt(7, produktbatchkomponent.getRbId());
			updateProBatchKomp.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (updateProBatchKomp != null) {
				updateProBatchKomp.close();
			}
		}
	}
	
	//Calls a SP in the SQL that will insert a row in produktbatchkomp 
	public void insertProBaKomRow(int pd_id, int rb_id, double tara, double netto, int oprId) throws SQLException {
		Connection sqlCon = Connector.getConn();
	
		PreparedStatement row = null;
		ResultSet rs = null;
	
		String insertRow = "CALL MakeProBaKompRow(?,?,?,?,?) ";
	
		try {
			row = sqlCon.prepareStatement(insertRow);
	
			row.setInt(1,pd_id);
			row.setInt(2,rb_id);
			row.setDouble(3,tara);
			row.setDouble(4,netto);
			row.setInt(5,oprId);
			row.execute();
			
		} catch (SQLException e) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if(row != null) {
				row.close();
			}
		}
	}
}