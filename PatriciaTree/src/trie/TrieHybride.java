package trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrieHybride implements RTrie{
	
	private int ordreInsert;
	private int lastInsert;
	
	private char key;
	private boolean isFinDeMot;
	
	private TrieHybride inf; //Fils gauche
	private TrieHybride eq; //Fils milieu
	private TrieHybride sup; //Fils droit
	
	public TrieHybride() {
		this.key = Character.MAX_VALUE;
		this.isFinDeMot = false;
		this.inf = null;
		this.eq = null;
		this.sup = null;
		this.ordreInsert=0;
	}
	
	/*public TrieHybride(char key) {
		this.key = key;
		this.isFinDeMot = false;
		this.inf = null;
		this.eq = null;
		this.sup = null;
	}*/
	
	public static TrieHybride rotationDroite(TrieHybride x){
		TrieHybride y = new TrieHybride(x.sup);
		x.sup = y.inf;
		x.inf = x;
		return y;
	}
	
	/**
	 * 
	 */
	
	public static TrieHybride rotationGauche(TrieHybride x){
		TrieHybride y = new TrieHybride(x.inf);
		x.inf = y.sup;
		y.sup = x;
		return y;
	}

	public TrieHybride(TrieHybride trie) {
		this.key = trie.key;
		this.isFinDeMot = trie.isFinDeMot;
		if(trie.inf == null)
			this.inf = trie.inf;
		else
			trie.inf = new TrieHybride(trie.inf);
		if(trie.eq == null)
			this.eq = trie.eq;
		else
			this.eq = new TrieHybride(trie.eq);
		if(trie.sup == null)
			this.sup = trie.sup;
		else {
			this.sup = new TrieHybride(trie.sup);
		}
		this.ordreInsert=trie.ordreInsert;
	}
	public boolean estVide() {
		if(this.key==Character.MAX_VALUE && this.inf==null && this.eq==null && this.sup==null)
			return true;
		
		return false;
	}

	public String val() {
		return ""+this.key;
	}

	public TrieHybride sousArbre(int i) {
		//i C {1;2;3} + faire -1
		//retourner soit inf/eq/sup
		
		if(i<=0 ||i>3)
			return null;
		else
		{
			return new TrieHybride();
		}
	}

	public TrieHybride filsSauf(int i) {
		
		//i C {1;2;3} + faire -1
		//retourner l'arbe sans inf,eq ou sup sans péter l'ancien arbre
		
		if(i<=0 ||i>3)
			return null;
		else
		{
			return new TrieHybride();
		}
	}
	
	private TrieHybride ajouterCaractere(char cara) {
		
		if (this.estVide()) {
			this.key = cara;
			return this;
		}
		
		if(cara == this.key)
		{
			return this;
		}
		
		if(cara > this.key)
		{
			if (this.sup == null)
				this.sup = new TrieHybride();
			
			return this.sup.ajouterCaractere(cara);
		}
		
		if(cara < this.key)
		{
			if (this.inf == null)
				this.inf = new TrieHybride();
			
			return this.inf.ajouterCaractere(cara);
		}
		
		return this;
	}
	
	public void insererMot(String mot) {
		
		if (mot != null && !mot.isEmpty()) {
			
			TrieHybride nvBranche = this.ajouterCaractere(mot.charAt(0));

			TrieHybride lastNode = nvBranche;
			
			for (int i = 1; i < mot.length(); i++) {
			
				if (lastNode.eq == null)
					lastNode.eq = new TrieHybride();
				
				lastNode = lastNode.eq.ajouterCaractere(mot.charAt(i));
			}

			this.lastInsert++;
			lastNode.ordreInsert+=this.lastInsert;
			lastNode.isFinDeMot = true; 
		}
	}
	
	public boolean recherche(String mot) {
		
		if (mot == null || mot.isEmpty())
			return false;

		
		char firstLetter = mot.charAt(0);
		
		//Si le mot a le même préfixe que la clé
		if (firstLetter == this.key) {
			if (mot.length() == 1 && this.isFinDeMot)
				return true;
			else if (this.eq == null)
				return false;
			else
				return this.eq.recherche(mot.substring(1));
		}
		
		//Si le mot a un préfixe plus petit que la clé
		if (firstLetter < this.key)
		{
			if(this.inf == null)
				return false;
			else
				return this.inf.recherche(mot);
		}

		//Si le mot a un préfixe plus grand que la clé
		if (firstLetter > this.key)
		{
			if(this.sup == null)
				return false;
			else
				return this.sup.recherche(mot);
		}
		
		return false;
	}	
	
	public boolean rechercheNotRecursive(String mot) {
		
		if (mot == null || mot.isEmpty())
			return false;
		
		TrieHybride currentTrie = this;

		char letter = mot.charAt(0);
		int i=0;
		
		while(i<mot.length())
		{		
			//Si la lettre est la même que la clé du noeud
			if (letter == currentTrie.key) {
				
				if ((i+1) == mot.length() && currentTrie.isFinDeMot)
					return true;
				else if (currentTrie.eq == null)
					return false;
				else
				{
					i++;
					letter = mot.charAt(i);
					currentTrie = currentTrie.eq;
				}
			}
			
			//Si la lettre est plus petite que la clé du noeud
			else if (letter < currentTrie.key)
			{				
				if(currentTrie.inf == null)
					return false;
				else
					currentTrie = currentTrie.inf;
			}
	
			//Si la lettre est plus grande que la clé du noeud
			else if (letter > currentTrie.key)
			{				
				if(currentTrie.sup == null)
					return false;
				else
					currentTrie = currentTrie.sup;
			}

		}
		
		return false;
	}	
	
	public int comptageMots()
	{		
		int nbTotal = 0;
		
		int nbMotBrancheInf = 0;
		int nbMotBrancheEq = 0;
		int nbMotBrancheSup = 0;
		
		if(this.isFinDeMot)
			nbTotal=1;
		
		if(this.inf!=null)
			nbMotBrancheInf += this.inf.comptageMots();
		
		if(this.eq!=null)
			nbMotBrancheEq += this.eq.comptageMots();
		
		if(this.sup!=null)
			nbMotBrancheSup += this.sup.comptageMots();
		
		nbTotal += nbMotBrancheInf + nbMotBrancheEq + nbMotBrancheSup;
		
		return nbTotal;
		
	}
	
	private List<String> listeMots(String prefixe,ArrayList<String> liste)
	{
		if(this.isFinDeMot)
			liste.add(prefixe  + this.key);
			
		if(this.inf != null)
			this.inf.listeMots(prefixe,liste);
				
		if(this.eq!=null)
			this.eq.listeMots(prefixe + this.key,liste);
				
		if(this.sup != null)
			this.sup.listeMots(prefixe,liste);
		
		return liste;
	}
	
	public List<String> listeMots()
	{
		ArrayList<String> word = new ArrayList<String>();
		return this.listeMots("", word);
	}
	
	public int comptageNil()
	{
		int nbTotal = 0;
		
		int nbNilBrancheInf = 0;
		int nbNilBrancheEq = 0;
		int nbNilBrancheSup = 0;
		
		if(this.inf!=null)
			nbNilBrancheInf += this.inf.comptageNil();
		else
			nbNilBrancheInf++;
		
		if(this.eq!=null)
			nbNilBrancheEq += this.eq.comptageNil();
		else
			nbNilBrancheEq++;
		
		if(this.sup!=null)
			nbNilBrancheSup += this.sup.comptageNil();
		else
			nbNilBrancheSup++;
		
		nbTotal = nbNilBrancheInf + nbNilBrancheEq + nbNilBrancheSup;
		
		return nbTotal;
	}
	
	private int maxOfNumbers(int... n)
	{
	    int max = n[0];

	    for(int i:n)
	    	max=Math.max(max,i);
	    
	    return max;
	}
	
	public int hauteur()
	{
		if(this.estVide())
			return 0;
		
		if(this.inf==null && this.eq==null && this.sup==null) 
			return 1;
			
		int hauteurInf = 0;
		int hauteurEq = 0;
		int hauteurSup = 0;
		
		if(this.inf!=null)
			hauteurInf+=this.inf.hauteur();
		if(this.eq!=null)
			hauteurEq+=this.eq.hauteur();
		if(this.sup!=null)
			hauteurSup+=this.sup.hauteur();
		
		return 1+maxOfNumbers(hauteurInf,hauteurEq,hauteurSup);
	}
	
	public double profondeurMoyenne()
	{
		return 0;
	}
	
	private int getNbSousNoeud(TrieHybride trie)
	{		
		if(trie==null)
			return 0;
		
		int nb = 0;
		
		if(trie.isFinDeMot)
			nb++;
		
		return nb+getNbSousNoeud(trie.eq)+getNbSousNoeud(trie.inf)+getNbSousNoeud(trie.sup);
	}
	
	public int prefixe(String mot)
	{
		TrieHybride startNode = this;
		String prefixe = "";
		
		int i = 0;
		
		while(!prefixe.equals(mot) && startNode!=null)
		{
			char lettre = mot.charAt(i);
			
			if (lettre == startNode.key) {
				prefixe+=startNode.key;
				if(!prefixe.equals(mot))
					startNode=startNode.eq;	
				i++;
			}
			
			else if (lettre < startNode.key)
			{
				startNode = startNode.inf;
			}
			
			else if (lettre > startNode.key)
			{
				startNode = startNode.sup;
			}		
		}
		
		int nbMot = 0;
		
		if(startNode.isFinDeMot)
			nbMot++;
		
		if(prefixe.equals(mot))
			return nbMot + getNbSousNoeud(startNode.eq);
		
		return 0;
	}
	
	public void suppression(String mot)
	{
		LinkedList<TrieHybride> l = new LinkedList<TrieHybride>();
		boolean existe = delete(mot, 0, this, l, "");
		if(existe)
			suppressionNode(l, mot, mot.length()-1);
	}
	public void suppressionNode(LinkedList<TrieHybride> list, String mot, int i){
		TrieHybride last = list.removeLast();
		if(last.eq == null && last.inf == null && last.sup == null){
				TrieHybride father = list.removeLast();
				if(father.eq == last)
					father.eq = null;
				else if(father.sup == last)
					father.sup = null;
				else if(father.inf == last)
					father.inf = null;
				if(!father.isFinDeMot){
					if(father.inf == null && father.sup != null && father.eq == null ){
						if(list.isEmpty())
							switchNode(father.sup);
						else
							list.getLast().eq = father.sup;
					}else if(father.inf != null && father.sup == null && father.eq ==null){
						if(list.isEmpty()){
							father.switchNode(father.inf);
						}else{
							list.getLast().eq = father.inf;
						}
					}
					list.add(father);
					suppressionNode(list, mot, i -1);
				}
		}
	}
	
	public void switchNode(TrieHybride copy){
		this.key = copy.key;
		this.isFinDeMot = copy.isFinDeMot;
		this.inf = copy.inf;
		this.sup = copy.sup;
		this.eq = copy.eq;
	}
	public static boolean delete(String mot, int i, TrieHybride t, LinkedList<TrieHybride> list, String mot_copy){
		if(mot.equals(mot_copy)){
			list.getLast().isFinDeMot = false;
			return true;
		}
		if(t != null){
				list.add(t);
				char character = mot.charAt(i);
				if(character < t.key ){
					return delete(mot, i , t.inf, list, mot_copy);
				}else if(character > t.key)
					return delete(mot, i, t.sup, list, mot_copy);
				else{
					return delete(mot, i+1, t.eq, list, mot_copy+= t.key);
				}
		}
		return false;
	}
	
	@Override
	public String toString() {
		
		String s =  "[key=" + key+ "]";
		if(this.inf!=null)
			s+= "\n[ Inf of " + key + " : \t" + this.inf.toString() + "]\n";
		if(this.eq!=null)
			s+= "\n[ Eq of " + key + ":\t" +this.eq.toString() + "\n";
		if(this.sup!=null)
			s+= "\n Sup of  " + key + ":\t" +this.sup.toString() + "\n";
		return s;
	}
	
	
	/*public void printHTML(String name,boolean showNullPointer){
		File f = new File (name+".html");
		try
		{			
		    PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
		    pw.println("<!doctype html>"
		    +"<html lang='fr'> "
		    +"<head> "
		      +"<meta charset='utf-8'>"
		      +"<title>"+name+"</title>"
		      +"<link rel='stylesheet' href='style_hybride.css'>"
		    +"</head>"
		    +"<body>"+
		    "<div class='tree'><ul>");
		    pw.println(parcourirArbreHTML(showNullPointer));
		    pw.println("</ul></div></body></html>");
		    pw.close();
		    System.out.println("End generation " + name);
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}*/
	
	public void printHTML(String name,boolean showNullPointer){
		File f = new File (name+".html");
		try
		{			
		    PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
		    pw.println("<!doctype html>"
		    +"<html lang='fr'> "
		    +"<meta charset='utf-8'>"
		    +"<head>"+"<title>"+name+"</title>"+
		    "<link rel='stylesheet' href='jquery.orgchart.css'/>"
		    +"<link rel='stylesheet' href='style_hybride.css'/>"
		    +"<script src='jquery.min.js'></script>"
		    +"<script src='jquery.orgchart.js'></script>"
		    +"<script src='script.js'></script>"+
		    "<script>"+
		    "$(function() {"+
		        "$('#tree').orgChart({container: $('#main'), interactive: true, fade: true, speed: 'fast'});"+
		    "});"+
		    "</script>"+
		"</head>"
		    +"<body>"+
		    "\n\n<ul id='tree'><ul>");
		    pw.println(parcourirArbreHTML(showNullPointer,"rootNode"));
		    pw.println("</ul>\n</ul>\n<div id='main'></div>\n</body>\n</html>");
		    pw.println("<script>"+
		    "$(function() {"+
		        "$('#tree').remove()"+
		    "});"+
		    "</script>");
		    pw.close();
		    System.out.println("End generation " + name);
		}
		catch (IOException exception)
		{
		    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		}
	}
	

	
	public String parcourirArbreHTML(boolean showNullPointer,String clazz){
		
		String str = "";
		
		if(this.isFinDeMot)
			str = "\n<li class='wordEnd "+clazz+"'>" + this.key +", "+this.ordreInsert+"\n";
		else
			str = "\n<li class='"+clazz+"'>" + this.key +"\n";
		
		
		str+="\n\t<ul>";
		
		if(this.inf != null){
			str += "" + this.inf.parcourirArbreHTML(showNullPointer,"infNode")+ "";
		}
		else if(showNullPointer)
			str += "\n<li class='nullPointer'>∅</li>";
		
		if(this.eq != null){
			str += "" + this.eq.parcourirArbreHTML(showNullPointer,"eqNode")+ "";
		}
		else if(showNullPointer)
			str += "\n<li class='nullPointer'>∅</li>";
		
		if(this.sup != null){
			str += "" + this.sup.parcourirArbreHTML(showNullPointer,"supNode")+ "";
		}
		else if(showNullPointer)
			str += "\n<li class='nullPointer'>∅</li>";
		
		str+="\n</ul>";
		return str + "\n</li>";
	}

	public static TrieHybride lectureFichier(String filename) {
		TrieHybride t = new TrieHybride();
	        try {
	            FileReader c = new FileReader(filename+".txt");
	            BufferedReader r = new BufferedReader(c);
	            String line = r.readLine();
	            while (line != null) {
	                String[] decompose = line.split(" ");
	                for(String s: decompose){
	                	t.insererMot(s);
	                }
	           line = r.readLine();
	           }
	 
	            r.close();
	 
	        } catch (Exception e) {
	        	e.printStackTrace();
	            throw new Error(e);
	        }
			return t;
	}

	@Override
	public void insertionListeMot(ArrayList<String> str) {
		for(String s : str){
			this.insererMot(s);
		}
		
	}

}
