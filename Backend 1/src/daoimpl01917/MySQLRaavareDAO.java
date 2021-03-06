package daoimpl01917;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import JDBC.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareDAO;
import DTO.FoundException;
import DTO.NotFoundException;
import DTO.Raavare;

public class MySQLRaavareDAO implements RaavareDAO{

	// Returns a list of Raavare containing all Raavare from the database.
	@Override
	public List<Raavare> getRaavareList() throws DALException, SQLException {
		List<Raavare> list = new ArrayList<Raavare>();

		Connection conn = Connector.getConn();
		PreparedStatement getRaavareList = null;
		ResultSet rs = null;
		
		String getRaaList = "SELECT * FROM raavare";
		
		try {
			getRaavareList = conn.prepareStatement(getRaaList);
			rs = getRaavareList.executeQuery();
			while (rs.next()) {
					list.add(new Raavare(rs.getInt("raavare_id"),rs.getString("raavare_navn"),rs.getString("leverandoer")));
				}
		} catch (SQLException e ) {
			System.out.println(e);
			e.printStackTrace();
		} finally {
			if (getRaavareList != null) {
				getRaavareList.close();
	        }
		}
		return list;
	}

	// Creates a Raavare in the database with the information from the Raavare parameter.
	@Override
	public String createRaavare(Raavare raavare) throws DALException, SQLException, FoundException {	
		Connection conn = Connector.getConn();
		PreparedStatement createRaavare = null;
		
		String createRaa = "INSERT INTO raavare(raavare_id, raavare_navn, leverandoer) VALUES ( ? , ? , ? )";

		try {
			createRaavare = conn.prepareStatement(createRaa);
			
			createRaavare.setInt(1, raavare.getRavareId());
			createRaavare.setString(2, raavare.getName());
			createRaavare.setString(3, raavare.getSupplier());
			createRaavare.executeUpdate();
		} catch (MySQLIntegrityConstraintViolationException e) {
			throw new FoundException("Raavaren findes allerede - error code Rax01");
		} catch (SQLException e ) {
			System.out.println(e);
			return "sql fejl ikke relateret til eksisterende id, tjek consol - Error code Rax02";
		} finally {
			if (createRaavare != null) {
				createRaavare.close();
	        }
		}
		return "Created raavare";
	}
		

	// Updates a Raavare in the database with the information from the Raavare parameter.
	// A raavare with the ravareId in the DTO needs to be in the database before the method is called.
	@Override
	public String updateRaavare(Raavare raavare) throws DALException, SQLException, NotFoundException{
		Connection conn = Connector.getConn();
		PreparedStatement updateRaavare = null;		
				
		String updateRaa = "UPDATE raavare SET raavare_navn = ? , leverandoer = ? WHERE raavare_id = ?";
		int NumberOfRows = -1;
		try {
			updateRaavare = conn.prepareStatement(updateRaa);
			updateRaavare.setString(1, raavare.getName());
			updateRaavare.setString(2, raavare.getSupplier());
			updateRaavare.setInt(3, raavare.getRavareId());
			NumberOfRows = updateRaavare.executeUpdate();
		} catch (SQLException e ) {
				System.out.println(e);
				return "sql fejl ikke relateret til mangel paa eksistens af raavare, se consol - error code Rax04";
		} finally {
			if (updateRaavare != null) {
				updateRaavare.close();
	        }
		}
		if(NumberOfRows == 0)
		{
			throw new NotFoundException("Raavaren eksisterer ikke - Error code Rax05");
		}
		return "Raavaren er opdateret";
	}
	
	public String findRaavareName (int pb_id) throws SQLException {
		Connection sqlCon = Connector.getConn();

		PreparedStatement getWeighed = null;
		PreparedStatement getToWeigh = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		int count = 0;
		String name = null;

		PreparedStatement getRaavareName = null;	
		ResultSet rs = null;

		//Checks what raavare name from a defined raavare_id
		String getName = "SELECT raavare_navn FROM raavare WHERE raavare_id = ?;";

		//gets id from the items that we weighed
		String getWeighedItems = "SELECT raavare_id FROM raavarebatch WHERE rb_id IN (SELECT rb_id FROM produktbatchkomponent WHERE pb_id = ?);";
		//gets id from the items that needs to be weighed
		String getToWeighItems = "SELECT raavare_id FROM receptkomponent WHERE recept_id = (SELECT recept_id FROM produktbatch WHERE pb_id = ?);";

		try {

			getWeighed = sqlCon.prepareStatement(getWeighedItems);
			getWeighed.setInt(1,pb_id);
			rs1 = getWeighed.executeQuery();

			// Go to the last row 
			rs1.last(); 
			int rowCount = rs1.getRow(); 

			// Reset row before iterating to get data 
			rs1.beforeFirst();

			int [] checkerArr1 = new int [rowCount];
			int arrayCount = 0;

			while(rs1.next()) {
				checkerArr1[arrayCount] = rs1.getInt(1);
				arrayCount++;
			}
			System.out.println("Arary 1: \n" + Arrays.toString(checkerArr1));

			//Get second array from database
			getToWeigh = sqlCon.prepareStatement(getToWeighItems);
			getToWeigh.setInt(1,pb_id);
			rs2 = getToWeigh.executeQuery();

			// Go to the last row 
			rs2.last(); 
			int rowCount2 = rs2.getRow(); 

			// Reset row before iterating to get data 
			rs2.beforeFirst();

			int [] checkerArr2 = new int [rowCount2];
			int arrayCount2 = 0;
			int compare = 0;

			while(rs2.next()) {
				checkerArr2[arrayCount2] = rs2.getInt(1);
				arrayCount2++;
			}
			System.out.println("Arary 2: \n" + Arrays.toString(checkerArr2));


			//compare arrays and gets the first id if they do not match
			for (int i = 0; i < checkerArr1.length; i++) {

				if(checkerArr1[i] != checkerArr2[i]) {
					compare = checkerArr2[i]; 
					count++;
					break;
				}
			}
			if(count == checkerArr2.length) {
				return "Opfyldt";
			}

			if(checkerArr1.length == 0) {
				getRaavareName = sqlCon.prepareStatement(getName);

				//Finds the raavare_navn from the id
				getRaavareName.setInt(1, checkerArr2[0]);
				rs = getRaavareName.executeQuery();
				if(rs.first()) {

					name = rs.getString("raavare_navn");

					System.out.println(name);
					return name;
				}
			}
			
			
			getRaavareName = sqlCon.prepareStatement(getName);

			//Finds the raavare_navn from the id
			getRaavareName.setInt(1, compare);
			rs = getRaavareName.executeQuery();
			if(rs.first()) {

				name = rs.getString("raavare_navn");

				System.out.println(name);
				return name;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(getRaavareName != null) {
				getRaavareName.close();
			}
		}
		return name;
	}
}