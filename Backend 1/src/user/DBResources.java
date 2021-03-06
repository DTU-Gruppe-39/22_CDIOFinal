package user;

import java.sql.SQLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.FoundException;
import DTO.Personer;
import JDBC.Connector;
import daoimpl01917.MySQLPersonerDAO;
import daointerfaces01917.DALException;


@Path("/db")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DBResources {
	
	// establishes connection to database in connector class found under JDBC package.
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createConn() throws FoundException, DALException, SQLException {
		try { new Connector(); 
		System.out.println("DB connection setup done");} 
		catch (InstantiationException e) { e.printStackTrace(); }
		catch (IllegalAccessException e) { e.printStackTrace(); }
		catch (ClassNotFoundException e) { e.printStackTrace(); }
		catch (SQLException e) { e.printStackTrace(); }	
	}	
}
