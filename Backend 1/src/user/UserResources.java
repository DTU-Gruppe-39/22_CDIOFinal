package user;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.NotFoundException;
import DTO.Personer;

@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResources {
	private static ArrayList <Personer> perList = new ArrayList<Personer>();

	//Inserts new user into system
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String createUser(Personer per) {
		System.out.println("helllloooooo");
		perList.add(per);
		//perList.add(new Personer(45, "peter", "pl", "12345678", "password", "admin"));
		//perList.add(new Personer(22, "Hans", "hs", "87654321", "kode", "admin"));
		System.out.println("Created user: " + per.toString());
		System.out.println("Current list " + perList.toString());

		String result = "It works, maybe";
		return result;
	}

	//Gets list of users
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Personer> getUsers() {
		System.out.println("Get list: " + perList.toString());
		return perList;
	}

	//removes user from list
	@DELETE
	//	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	// placed inside the parameter list of deleteUser -- @PathParam("id")
	public void deleteUser(int id) throws NotFoundException
	{
		boolean removeIf = perList.removeIf(e-> e.getUserId() == id);
		if(!removeIf)
		{
			throw new NotFoundException("Brugeren findes ikke");
		}
	}

	//Updates a user
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(Personer per)
	{
		for (Personer person : perList) {
			if(person.getUserId() == per.getUserId())
			{
				person.setUserId(per.getUserId());
				person.setUserName(per.getUserName());
				person.setCpr(per.getCpr());
				person.setIni(per.getIni());
				person.setPassword(per.getPassword());
				person.setRoles(per.getRoles());
			}
		}
	}
}
