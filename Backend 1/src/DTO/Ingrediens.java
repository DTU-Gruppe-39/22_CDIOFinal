package DTO;

public class Ingrediens {
	
  private int raavareId;
	private double nomNetto;
	
	/** Convert to %  */
	private double tolerance;

	public int getRaavareId() {
		return raavareId;
	}

	public void setRaavareId(int raavareId) {
		this.raavareId = raavareId;
	}

	public double getNomNetto() {
		return nomNetto;
	}

	public void setNomNetto(double nomNetto) {
		this.nomNetto = nomNetto;
	}

	public double getTolerance() {
		return tolerance;
	}

	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}
}
