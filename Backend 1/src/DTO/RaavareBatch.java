package DTO;

public class RaavareBatch {
	/** raavare batch id i området 1-99999999. Vælges af brugerne */  
	int rbId;                     
	/** raavare id i området 1-99999999 vælges af brugerne */
	int raavareId;             
	/** mængde på lager */
	double maengde;
	
	public int getRbId() {
		return rbId;
	}
	public void setRbId(int rbId) {
		this.rbId = rbId;
	}
	public int getRaavareId() {
		return raavareId;
	}
	public void setRaavareId(int raavareId) {
		this.raavareId = raavareId;
	}
	public double getMaengde() {
		return maengde;
	}
	public void setMaengde(double maengde) {
		this.maengde = maengde;
	}
	public String toString()
	{
		return "raavareBatchId: " + rbId + ", raavareId: " + raavareId + ", Maengde: " + maengde;
	}
}

