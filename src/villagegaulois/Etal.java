package villagegaulois;

import personnages.Gaulois;

public class Etal {
	private Gaulois vendeur;
	private String produit;
	private int quantiteDebutMarche;
	private int quantite;
	private boolean etalOccupe = false;

	public boolean isEtalOccupe() {
		return etalOccupe;
	}

	public Gaulois getVendeur() {
		return vendeur;
	}
	
	public int getQuantiteDebutMarche() {
		return quantiteDebutMarche;
	}
	
	public int getQuantite() {
		return quantite;
	}
	
	public String getProduit() {
		return produit;
	}
	
	public void occuperEtal(Gaulois vendeur, String produit, int quantite) {
		this.vendeur = vendeur;
		this.produit = produit;
		this.quantite = quantite;
		quantiteDebutMarche = quantite;
		etalOccupe = true;
	}

	public String libererEtal() {
		etalOccupe = false;
		StringBuilder sb = new StringBuilder();
		try {
			String nomVendeur = getVendeur().getNom();
			int nbDeBase = getQuantiteDebutMarche();
			int nbVendu = nbDeBase - getQuantite();
			sb.append(String.format("Le vendeur %s quitte son étal,", nomVendeur));
			if (nbVendu > 0) {
			sb.append(String.format(" il a vendu %d %s parmi les %d qu'il voulait vendre.%n", nbVendu, produit, nbDeBase));
			} else {
			sb.append("il n'a malheureusement rien vendu.\n");
			}
		} catch (NullPointerException e) {
			sb.append("Étal déjà libre.");
		}
		return sb.toString();
	}

	public String afficherEtal() {
		StringBuilder sb = new StringBuilder();
		if (etalOccupe) {
			sb.append(String.format("%s vend %d %s %n", vendeur.getNom(), quantite, produit));
		} else {
			sb.append("L'étal est libre");
		}
		return sb.toString();
	}

	public String acheterProduit(int quantiteAcheter, Gaulois acheteur) {
		
		StringBuilder chaine = new StringBuilder();
		if (quantiteAcheter < 1) {
			throw new java.lang.IllegalArgumentException();
		}
		if (!etalOccupe) {
			throw new java.lang.IllegalStateException();
		}
		try {
			chaine.append(acheteur.getNom() + " veut acheter " + quantiteAcheter
					+ " " + produit + " à " + vendeur.getNom());
			if (quantite == 0) {
				chaine.append(", malheureusement il n'y en a plus !\n");
				quantiteAcheter = 0;
			}
			if (quantiteAcheter > quantite) {
				chaine.append(", comme il n'y en a plus que " + quantite + ", "
						+ acheteur.getNom() + " vide l'étal de "
						+ vendeur.getNom() + ".\n");
				quantiteAcheter = quantite;
				quantite = 0;
			}
			if (quantite != 0) {
				quantite -= quantiteAcheter;
				chaine.append(". " + acheteur.getNom()
						+ ", est ravi de tout trouver sur l'étal de "
						+ vendeur.getNom() + "\n");
			}
		} catch (NullPointerException e) {
			System.err.println(e);
		}
		return chaine.toString();
	}

	public boolean contientProduit(String produit) {
		if (this.produit==null) {
			return false;
		}
		return this.produit.equals(produit);
	}

}
