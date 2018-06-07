package user;
import java.sql.SQLException;
import java.util.ArrayList;

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
import DTO.NotFoundException;
import DTO.Personer;
import daoimpl01917.MySQLPersonerDAO;
import daointerfaces01917.DALException;



@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResources {
	MySQLPersonerDAO perConn = new MySQLPersonerDAO();
	private static ArrayList <Personer> perList = new ArrayList<Personer>();
	
	//Inserts new user into system
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(Personer per) throws FoundException, DALException, SQLException {
		boolean found = false;
		for (Personer person : perConn.getPersonerList()) {
			if (per.getUserId() == person.getUserId()) {
				found = true;
			}
		}
		
		if (found) {
			throw new FoundException("Brugeren findes allerede");
		}
		
		perConn.createPersoner(per);		//from DB
		
//		perList.add(per);		//from arrayList
		System.out.println("Created user: " + per.toString());
		System.out.println("Current list " + perConn.getPersonerList().toString());

		String result = "It works, maybe";
		return result;
	}

	//Gets list of users
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Personer> getUsers() throws DALException, SQLException {
		System.out.println("Get list: " + perConn.getPersonerList().toString());
		return perList;
	}
	
//	@PUT
//	@Path("{id}/status")
//	@Consumes(MediaType.APPLICATION_JSON)
//	public void createStatus(@PathParam("id") int id, boolean status) {
//		//code that updates the status of a given id.
//	}
	
	
	
	//removes user from list
	@DELETE
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	//placed inside the parameter list of deleteUser -- @PathParam("id")
	public void deleteUser(@PathParam("id") int id) throws NotFoundException, DALException, SQLException
	{
		boolean found = false;
		for (Personer person : perConn.getPersonerList()) {
			if (id == person.getUserId()) {
				perConn.deletePersoner(id);
				found = true;
			}
		}
		if (!found) {
			throw new NotFoundException("Brugeren findes ikke");
		}
		
//		boolean removeIf = perList.removeIf(e-> e.getUserId() == id);
//		if(!removeIf)
//		{
//			throw new NotFoundException("Brugeren findes ikke");
//		}
	}

	public static ArrayList<Personer> getPerList() {
		return perList;
	}


	//Updates a user
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(Personer per) throws NotFoundException, DALException, SQLException
	{
		boolean found = false;
		for (Personer person : perList) {
			if(person.getUserId() == per.getUserId())
			{
				perConn.updatePersoner(per);
				found = true;
			}
		}
		if (!found) {
			throw new NotFoundException("Brugeren findes ikke");
		}
	}
}