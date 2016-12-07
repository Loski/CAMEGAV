package trie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public interface RTrie {
	/**
	 * Ins�re le nouveau mot dans l'arbre, 
	 * avec un Arbre vide (cle = '\0') � la fin
	 * @return 
	 * 
	 */
	public void insererMot(String mot);
	
	/**
	 * Ins�re la nouvelle phrase (phrase = suite de mots) dans l'arbre, 
	 * avec un Arbre vide (cle = '\0') � la fin
	 * 
	 */
	
	/**
	 * Retourne vrai si le mot est contenu dans l'arbre
	 */
	public boolean recherche(String mot);
	
	public int comptageMots();
	
	public int comptageNil();
	
	public List<String> listeMots();
	
	public int hauteur();
	
	public double profondeurMoyenne();
	
	public int prefixe(String mot);
	
	public void suppression(String mot);
		
	public boolean estVide();
	
	public void insertionListeMot(ArrayList<String> str);
	
	
	public RTrie conversion();
	
	public void printHTML(String name);

}
