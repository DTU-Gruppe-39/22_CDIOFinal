package DTO;

import java.util.ArrayList;

public class Recept {
	/** recept id i området 1-99999999 */

	private int receptId;
	
	/** Receptnavn min. 2 max. 20 karakterer */
	private String receptNavn;
	
	/** Ingredienser i recept */
	private ArrayList<Ingrediens> ingrediens;

	
	
	
	
	public int getReceptId() {
		return receptId;
	}

	public void setReceptId(int receptId) {
		this.receptId = receptId;
	}

	public String getReceptNavn() {
		return receptNavn;
	}

	public void setReceptNavn(String receptNavn) {
		this.receptNavn = receptNavn;
	}

	public ArrayList<Ingrediens> getIngrediens() {
		return ingrediens;
	}

	public void setIngrediens(ArrayList<Ingrediens> ingrediens) {
		this.ingrediens = ingrediens;
	}
}
