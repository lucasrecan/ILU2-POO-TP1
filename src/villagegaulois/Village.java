package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import villagegaulois.Village.VillageSansChefException;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException();
		}
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		int indiceEtal = marche.trouverEtalLibre();
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("Le vendeur %s cherche un endroit pour vendre %d %s.%n", vendeur.getNom(),
				nbProduit, produit));
		if (indiceEtal != -1) {
			marche.utiliserEtal(indiceEtal, vendeur, produit, nbProduit);
			stringBuilder.append("Le vendeur ");
			stringBuilder.append(vendeur.getNom());
			stringBuilder.append(" vend des ");
			stringBuilder.append(produit);
			stringBuilder.append(" à l'étal n°");
			stringBuilder.append(indiceEtal + 1);
			stringBuilder.append(".\n");
			return stringBuilder.toString();
		} else {
			return "Pas d'étal libre.\n";
		}
	}

	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsAvecProduit = marche.trouverEtals(produit);
		StringBuilder sb = new StringBuilder();
		if (etalsAvecProduit.length > 0) {
			sb.append(String.format("Les vendeurs qui proposent des %s sont :%n", produit));
			for (int i = 0; i < etalsAvecProduit.length; i++) {
				sb.append(String.format("- %s %n", etalsAvecProduit[i].getVendeur().getNom()));
			}
		} else {
			sb.append(String.format("Pas de vendeur qui propose des %s.%n", produit));
		}
		return sb.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

	public String partirVendeur(Gaulois vendeur) {
		return rechercherEtal(vendeur).libererEtal();
	}
	
	public String afficherMarche() {
		StringBuilder sb = new StringBuilder();
		if (marche.etals.length > 0) {
			sb.append(String.format("le marché du village \"%s\" possède plusieurs étals :%n", getNom()));
		} else {
			sb.append(String.format("le marché du village \"%s\" ne possède aucune étal.%n", getNom()));
		}
		return sb.toString() + marche.afficherMarche();
	}
	
	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtals) {
			etals = new Etal[nbEtals];
			for (int i = 0; i < nbEtals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}

		private Etal[] trouverEtals(String produit) {
			Etal[] etalsVente;
			int indiceEtalsVente = 0;
			int nbEtalsContientProduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nbEtalsContientProduit++;
				}
			}
			etalsVente = new Etal[nbEtalsContientProduit];
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsVente[indiceEtalsVente] = etals[i];
					indiceEtalsVente++;
				}
			}
			return etalsVente;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur() == gaulois) {
					return etals[i];
				}
			}
			return null;
		}

		private String afficherMarche() {
			StringBuilder sb = new StringBuilder();
			int etalsVides = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					sb.append(etals[i].afficherEtal());
				} else {
					etalsVides++;
				}
			}

			sb.append(String.format("Il reste %d étals non utilisés dans le marché.%n", etalsVides));
			return sb.toString();
		}
	}
	
	public class VillageSansChefException extends Exception {
		private static final long serialVersionUID = 1L;
		private static String errorMessage = "Le village est obligé de posséder un chef.\n";

		public VillageSansChefException() {
	        super(errorMessage);
	    }


	}

}