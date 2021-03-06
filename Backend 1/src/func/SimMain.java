package func;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

import DTO.Afvejning;
import DTO.Personer;
import DTO.RaavareBatch;
import JDBC.Connector;
import user.UserResources;

public class SimMain {

	public static void main(String[] args) throws UnknownHostException, IOException {

		try { new Connector(); 
		System.out.println("DB connection works, maybe!");} 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }	
		
		Afvejning afv = new Afvejning();
		//connection to weight simulator
		Weight_IO io = new Weight_IO(afv, "127.0.0.1");
		io.run();
	}
}