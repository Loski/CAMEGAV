package trie;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
public class Interface {
	
	private RTrie trie;
	private Scanner sc;
	public static final int CHARGEMENT = 0;
	public static final int INSERTION = 1;
	public static final int SUPPRESSION = 2;
	public static final int RECHERCHE = 3;
	public static final int LISTE_MOT = 4;
	public static final int COMPTAGE_MOT = 5;
	public static final int COMPTAGE_NIL = 6;
	public static final int PREFIXE = 7;
	public static final int HAUTEUR = 8;
	public static final int PROFONDEUR_MOYENNE = 9;
	public static final int CONVERSION = 10;
	public static final int FUSION = 11;
	
	public static final int PATRICIA_TRIE = 1;
	public static final int TRIE_HYBRIDE = 2;
	
	public Interface(){
		sc = new Scanner("System.in");
	}
	public static void main(String[] argc){
		RTrie arbre;
		Interface f = new Interface();
		arbre = f.choisirTypeTrie();
		
	}
	
	public RTrie choisirTypeTrie(){
		System.out.println("Choisissez le type d'arbre");
		System.out.println(PATRICIA_TRIE + ". PatriciaTrie");
		System.out.println(TRIE_HYBRIDE + ". Trie hybride");
		if(this.saisirString().equals(PATRICIA_TRIE)){
			return new PatriciaTrie();
		}else{
			return new TrieHybride();
		}
	}
	
	public void affichage(){
		System.out.println("What do u want !");
		System.out.println(CHARGEMENT + ". Chargez un fichier.");
		System.out.println(INSERTION + ". Insertion d'un mot..");
		System.out.println(SUPPRESSION+ ". Suppression d'un mot.");
		System.out.println(RECHERCHE+ ". Recherche d'un mot.");
		System.out.println(LISTE_MOT + ". ListeMot");
		System.out.println(COMPTAGE_MOT + ". Comptage des mots.");
		System.out.println(COMPTAGE_NIL + ". Comptage Nil.");
		System.out.println(PREFIXE + ". Nombre de mot préfixe.");
		System.out.println(HAUTEUR + ". Hauteur de l'arbre.");
		System.out.println(PROFONDEUR_MOYENNE + ". Profondeur moyenne de l'arbre.");
		String str = CONVERSION + ". Conversion en ";
		str += (this.trie instanceof PatriciaTrie) ? "Trie Hybride.\n" + FUSION +". Fusion de deux PatriciaTrie." : "PatriciaTrie.";
		System.out.println(str);
		
	}
	
	public void action(int commande){
		switch (commande) {
		case CHARGEMENT:
			System.out.println("Saisir votre fichier : ");
			this.trie.insertionListeMot(Interface.lectureFichier(saisirMot()));
			break;
		case INSERTION:
			trie.insererMot(saisirMot());
			break;
		case SUPPRESSION:
			trie.suppression(saisirMot());
			break;
		case RECHERCHE:
			if(trie.recherche(saisirMot())){
				System.out.println("Mot trouvé !");
			}
			else{
				System.out.println("Mot non trouvé !");
			}
			break;
		case LISTE_MOT:
			System.out.println(trie.listeMots());
			break;
		case COMPTAGE_MOT:
			System.out.println("L'arbre possede " + trie.comptageMots() + "mots.");
			break;
		case COMPTAGE_NIL:
			System.out.println("L'arbre possede " + trie.comptageNil() + "mots.");
			break;
		case PREFIXE:
			String mot = saisirMot();
			System.out.println("L'arbre possède " + trie.prefixe(mot) + "commençant par " + mot);
		case HAUTEUR:
			System.out.println("L'arbre a une hauteur de " + trie.hauteur()+"." );
			break;
		case PROFONDEUR_MOYENNE:
			System.out.println("L'arbre a une profondeur moyenne de " + trie.profondeurMoyenne() +" .");
			break;
		case CONVERSION:
			break;
		case FUSION:
			if (this.trie instanceof PatriciaTrie) {
					((PatriciaTrie) this.trie).fusion(t, copy);
			}
		default:
			System.out.println("Action incorrecte !");
			break;
		}
		System.out.println("End of the command");
	}
	
	public String saisirMot(){
		System.out.println("Veuillez saisir un mot :");
		return saisirString();
	}
	
	public String saisirString(){
		String str = sc.nextLine();
		if(isAllASCII(str))
			return str;
		else{
			System.out.println("Not a ASCII string.\n");
			return saisirString();
		}
	}
	private static boolean isAllASCII(String input) {
	    boolean isASCII = true;
	    for (int i = 0; i < input.length(); i++) {
	        int c = input.charAt(i);
	        if (c > 0x7F) {
	            isASCII = false;
	            break;
	        }
	    }
	    return isASCII;
	}
	
	public static ArrayList<String> lectureFichier(String name){
		 	ArrayList<String> str = new  ArrayList<String>(); 
	        try {
	            FileReader c = new FileReader(name+".txt");
	            BufferedReader r = new BufferedReader(c);
	            String line = r.readLine();
	            while (line != null) {
	                String[] decompose = line.split(" ");
	                for(String s : decompose){
	                	if(!isAllASCII(s)){
	                		System.out.println(s + " contains not ASCII caracters, not inserting...");
	                	}
	                	else{
	                		str.add(s);
	                	}
	                }
	                line = r.readLine();
	           }
	            
	            r.close();
	 
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Error(e);
	        }
			return str;	
	  }
}
