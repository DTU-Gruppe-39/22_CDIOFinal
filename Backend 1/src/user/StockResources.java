package user;

import java.util.ArrayList; 

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import DTO.Raavare;
import DTO.RaavareBatch;

@Path("/stock")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StockResources {
	private static ArrayList <Raavare> ravareList = new ArrayList<>();



	//*** Ravare ***//
	@POST
	//	@Path("{ravareNr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String submit(Raavare rav) {
		ravareList.add(rav);

		System.out.println("Created user: " + rav.toString());
		System.out.println("Current list " + ravareList.toString());

		String result = "created ravare";
		return result;
	}

	@GET
	//	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Raavare> getRavare()
	{
		return ravareList;
	}

	@PUT
	//	@Path("{ravareNr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String update(Raavare rav) {

		for (Raavare ravare : ravareList) {
			if(ravare.getRavareId() == rav.getRavareId())
			{
				ravare.setRavareId(rav.getRavareId());
				ravare.setName(rav.getName());
				ravare.setSupplier(rav.getSupplier());
			}
		}			
		return "Updated råvare";
	}

	@DELETE
	//	@Path("{ravreNr}")
	@Consumes(MediaType.APPLICATION_JSON)
	public String delete(int ravareNr)
	{
		ravareList.removeIf(e-> e.getRavareId() == ravareNr);
		return "deleted ravare";
	}
}