package histoire;

import personnages.Chef;
import villagegaulois.Etal;
import villagegaulois.Village;
import villagegaulois.Village.VillageSansChefException;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		try {
			try {
				System.out.println(etal.acheterProduit(1, null));
			} catch (IllegalArgumentException e) {
				System.out.println("Le nombre doit être supérieur ou égal à 1.\n");
			}
		} catch (IllegalStateException e) {
			System.out.println("L'étal est inoccupé.\n");
		}
		Village village = new Village("le village test", 50, 5);
		Chef abraracourcix = new Chef("Abraracourcix", 10, village);
		village.setChef(abraracourcix);
		try {
			village.afficherVillageois();
		} catch (VillageSansChefException e) {
			System.err.println(e);
		}
		
		System.out.println("Fin du test");
	}

}
