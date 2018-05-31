package user;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.Produktbatch;

@Path("/produktbatch")

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class ProduktResources {
	private static ArrayList <Produktbatch> produktList = new ArrayList<>();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Produktbatch batch) {
		produktList.add(batch);

		System.out.println("Created user: " + batch.toString());
		System.out.println("Current list " + produktList.toString());

		String result = "created productbatch";
		return result;
	}

	//GET
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Produktbatch> getProduktbatch()
	{
		return produktList;
	}

}
