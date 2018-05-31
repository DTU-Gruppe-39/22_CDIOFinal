package DTO;

import java.util.ArrayList;

public class Recept {
	/** recept id i området 1-99999999 */
	int receptId;
	
	/** Receptnavn min. 2 max. 20 karakterer */
	String receptNavn;
	
	/** Ingredienser i recept */
	ArrayList<Ingrediens> ingrediens;
}
