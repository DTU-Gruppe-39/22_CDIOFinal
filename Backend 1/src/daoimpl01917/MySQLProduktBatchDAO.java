package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import JDBC.Connector;
import daointerfaces01917.DALException;
import DTO.OperatoerDTO;
import DTO.Produktbatch;
import DTO.ReceptKompDTO;
import DTO.ProduktBatchKompDTO;
import daointerfaces01917.ProduktBatchDAO;

public class MySQLProduktBatchDAO implements ProduktBatchDAO {

	@Override
	public void createProduktBatch(Produktbatch pb) throws DALException, SQLException {		
		Connection conn = Connector.getConn();
		PreparedStatement createProBatch = null;
		PreparedStatement createProBaKomponent = null;

		String createProBa = "INSERT INTO produktbatch(pb_id, status, recept_id) VALUES " + 
				"(?, ?, ?)";

		try {
			createProBatch = conn.prepareStatement(createProBa);

			createProBatch.setInt(1, pb.getPbId());
			createProBatch.setInt(2, pb.getStatus());
			createProBatch.setInt(3, pb.getReceptId());
			createProBatch.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e);
		} finally {
			if (createProBatch != null) {
				createProBatch.close();
			}
		}
		}

	@Override
	public List<Produktbatch> getProduktBatchList() throws DALException, SQLException {
		List<Produktbatch> list = new ArrayList<Produktbatch>();

		Connection conn = Connector.getConn();
		PreparedStatement getProdBatchList = null;
		ResultSet rs = null;

		String getProBaList = "SELECT * FROM produktbatch";

		try {
			getProdBatchList = conn.prepareStatement(getProBaList);
			rs = getProdBatchList.executeQuery();
			while (rs.next()) 
			{
				list.add(new Produktbatch(rs.getInt("pb_id"), rs.getInt("status"), rs.getInt("recept_id")));
			}
		} catch (SQLException e) { 
			System.out.println(e);
		} finally {
			if (getProdBatchList != null) {
				getProdBatchList.close();
			}
		}
		MySQLProduktBatchKompDAO t = new MySQLProduktBatchKompDAO();
		ArrayList<ProduktBatchKompDTO> tmpList = new ArrayList<>();
		for(int i = 0; i < list.size(); i++)
		{
			tmpList = (ArrayList<ProduktBatchKompDTO>) t.getProduktBatchKompList(list.get(i).getPbId());
			list.get(i).setProduktBatchKomponent(tmpList);
		}
		return list;
	}
}
