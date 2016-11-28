package PatriciaTree;

import java.util.List;

public interface RTrie {
	/**
	 * Ins�re le nouveau mot dans l'arbre, 
	 * avec un Arbre vide (cle = '\0') � la fin
	 * @return 
	 * 
	 */
	public RTrie insererMot(String mot);
	
	/**
	 * Ins�re la nouvelle phrase (phrase = suite de mots) dans l'arbre, 
	 * avec un Arbre vide (cle = '\0') � la fin
	 * 
	 */
	public void insererPhrase(String phrase);

	public void insererListeMots(List<String> mots);
	
	/**
	 * Retourne vrai si le mot est contenu dans l'arbre
	 */
	public boolean rechercherMot(String mot);
	
	public int comptageMots();
	
	public int comptageNil();
	
	public List<String> listeMots(String prefixe);
	
	public int hauteur();
	
	public double profondeurMoyenne();
	
	public int prefixe(String mot);
	
	public RTrie suppression(String mot);
	
	public RTrie conversion();
	
	public boolean isEmpty();

}
