package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

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

	public String afficherVillageois() {
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
		for (int i = 0; i < etalsAvecProduit.length; i++) {
			continue;
		}
		return sb.toString();
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

		private void afficherMarche() {
			int etalsVides = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					etals[i].afficherEtal();
				} else {
					etalsVides++;
				}
			}
			System.out.println("Il reste " + etalsVides + " étals non utilisés dans le marché.\n");
		}
	}

}