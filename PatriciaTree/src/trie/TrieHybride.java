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
	private static int lastInsert;
	
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
		this.lastInsert = 0;
	}
	
	public TrieHybride(char key) {
		this.key = key;
		this.isFinDeMot = false;
		this.inf = null;
		this.eq = null;
		this.sup = null;
	}
	


	public TrieHybride(TrieHybride trie) {
		this.key = trie.key;
		this.isFinDeMot = trie.isFinDeMot;
		this.inf = trie.inf;
		this.sup = trie.sup;
		this.eq= trie.eq;
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
			if(i==1)
				return this.inf;
			if(i==2)
				return this.eq;
			if(i==3)
				return this.sup;
		}

		return null;
	}

	public TrieHybride filsSauf(int i) {
		
		//i C {1;2;3} + faire -1
		//retourner l'arbe sans inf,eq ou sup sans péter l'ancien arbre
		
		if(i<=0 ||i>3)
			return null;
		else
		{
			TrieHybride t = new TrieHybride();
			t.key=this.key;
			t.inf=this.inf;
			t.eq=this.eq;
			t.sup=this.sup;

			if(i==1)
				t.inf=null;
			if(i==2)
				t.eq=null;
			if(i==3)
				t.eq=null;

			return t;
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
		if(this.key == Character.MAX_VALUE)
			this.key = mot.charAt(0);
		insert(mot, 0, this);
	}
	private static void equilibrage(TrieHybride t){
		int max_inf = (t.inf == null) ? 0 : t.inf.comptageMots();
		int max_sup = (t.sup == null) ? 0 : t.sup.comptageMots();
		if(max_inf *2 < max_sup){
			t.rotationGauche();
		}
		else if(max_inf > max_sup *2){
			t.rotationDroite();
		}
		//return t;
		
	}
	private void rotationDroite(){
		  TrieHybride aux = (this.inf==null) ? null : new TrieHybride(this.inf);
		  this.inf = (aux.sup==null) ? null : new TrieHybride(aux.sup);
		  aux.sup =  new TrieHybride(this);
		modifThis(aux);
	}
	
	private void rotationGauche(){
		  TrieHybride aux = (this.sup==null) ? null : new TrieHybride(this.sup);
		  this.sup = (aux.inf==null) ? null : new TrieHybride(aux.inf);
		  aux.inf = new TrieHybride(this);
			modifThis(aux);
	}
	private void modifThis(TrieHybride trie) {
		if(trie==null) return;
		this.key = trie.key;
		this.isFinDeMot = trie.isFinDeMot;
		this.inf = trie.inf;
		this.eq = trie.eq;
		this.sup = trie.sup;

}
	private static TrieHybride insert(String mot, int i, TrieHybride t){
		System.out.println(i);
		if(i >= mot.length() )
			return null;
		if(t == null){
			t = new TrieHybride(mot.charAt(i));
		}
		if(mot.charAt(i) < t.key){
			t.inf = insert(mot, i, t.inf);
		}
		else if(mot.charAt(i) > t.key){
			t.sup = insert(mot, i, t.sup);

		}
		else{
			TrieHybride tmp =  insert(mot, i +1, t.eq);
			if(tmp == null){
				t.isFinDeMot = true;
				t.ordreInsert = t.lastInsert;
				t.lastInsert++;
			}else
				t.eq = tmp;
		}
		if(t!=null)
			equilibrage(t);
		return t;
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

	private int comptageMotSansSousChaine()
	{		
		int nbTotal = 0;
		
		int nbMotBrancheInf = 0;
		int nbMotBrancheEq = 0;
		int nbMotBrancheSup = 0;
		
		if(this.isFinDeMot && this.eq==null)
		{
			nbTotal=1;
		}
		
		if(this.inf!=null)
			nbMotBrancheInf += this.inf.comptageMotSansSousChaine();
		
		if(this.eq!=null)
			nbMotBrancheEq += this.eq.comptageMotSansSousChaine();
		
		if(this.sup!=null)
			nbMotBrancheSup += this.sup.comptageMotSansSousChaine();
		
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
	
	private void profondeurListe(ArrayList<Integer> liste,int profondeur)
	{		
		if(this.isFinDeMot && this.eq==null)
		{
			liste.add(profondeur);
		}
		
		if(this.inf!=null)
			this.inf.profondeurListe(liste,profondeur+1);
		
		if(this.eq!=null)
			this.eq.profondeurListe(liste,profondeur+1);
		
		if(this.sup!=null)
			this.sup.profondeurListe(liste,profondeur+1);
	}
	
	public int profondeurMoyenne()
	{
		ArrayList<Integer> listeProfondeur = new ArrayList<>();
		this.profondeurListe(listeProfondeur,1);
		int profondeurTotale = 0;
		for(Integer i : listeProfondeur)
			profondeurTotale+=i.intValue();
		
		return (int) Math.ceil(profondeurTotale/(double)this.comptageMotSansSousChaine());
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
		
		if(startNode==null)
			return 0;
		
		int nbMot = 0;
		
		if(startNode.isFinDeMot)
			nbMot++;
		
		if(prefixe.equals(mot))
			return nbMot + getNbSousNoeud(startNode.eq);
		
		return 0;
	}
	
	public static TrieHybride suppression(TrieHybride trie,String mot)
	{
		if(mot==null || mot.isEmpty())
			return trie;
		
		ArrayList<TrieHybride> nodeOfWord = new ArrayList<>();
		TrieHybride currentNode = trie;
		
		int i = 0;
		
		while(currentNode!=null && i<mot.length())
		{
			char lettre = mot.charAt(i);
			
			nodeOfWord.add(currentNode);
			
			if (lettre == currentNode.key) {
				currentNode=currentNode.eq;	
				i++;
			}
			
			else if (lettre < currentNode.key)
			{
				currentNode = currentNode.inf;
			}
			
			else if (lettre > currentNode.key)
			{
				currentNode = currentNode.sup;
			}
		}
		
		//Si le mot à supprimé est une sous-chaîne
		if(nodeOfWord.get(nodeOfWord.size()-1).eq!=null)
		{
			nodeOfWord.get(nodeOfWord.size()-1).isFinDeMot=false;
			return trie;
		}
		
		//Si le mot a supprimé est la racine et la racine n'est pas utilisée dans les autres mots
		if(nodeOfWord.size()==1)
		{
			if(nodeOfWord.get(0).inf==null && nodeOfWord.get(0).sup==null)
				return new TrieHybride();
			
			if(nodeOfWord.get(0).inf==null)
				return nodeOfWord.get(0).sup;
			
			if(nodeOfWord.get(0).sup==null)
				return nodeOfWord.get(0).inf;
			
			//Si il y a des mots dans les branches inf et sup
			TrieHybride t = new TrieHybride();
			t.key=nodeOfWord.get(0).inf.key;
			t.isFinDeMot=nodeOfWord.get(0).inf.isFinDeMot;
			t.eq=nodeOfWord.get(0).inf.eq;
			t.inf=nodeOfWord.get(0).inf.inf;
			TrieHybride ite = nodeOfWord.get(0).inf;
			while(ite.sup!=null)
				ite=ite.sup;
			ite.sup=nodeOfWord.get(0).sup;
			t.sup=ite;
			return t;
		}
		
		for(i=nodeOfWord.size()-1;i>=1;i--)
		{
			currentNode = nodeOfWord.get(i);			
			
			if(currentNode.inf==null && currentNode.sup==null)
			{
				if(nodeOfWord.get(i-1).inf==currentNode)
				{
					nodeOfWord.get(i-1).inf=null;
					return trie;
				}
				
				if(nodeOfWord.get(i-1).sup==currentNode)
				{
					nodeOfWord.get(i-1).sup=null;
					return trie;
				}
				
				nodeOfWord.get(i-1).eq=null;
				if(nodeOfWord.get(i-1).isFinDeMot)
					return trie;
			}
			else
			{
				if(nodeOfWord.get(i).inf==null)
				{
					nodeOfWord.get(i-1).eq = nodeOfWord.get(i).sup;
					return trie;
				}
				if(nodeOfWord.get(i).sup==null)
				{
					nodeOfWord.get(i-1).eq = nodeOfWord.get(i).inf;
					return trie;
				}
				
				//Si il y a des mots dans les branches inf et sup
				TrieHybride t = new TrieHybride();
				t.key=nodeOfWord.get(i).inf.key;
				t.isFinDeMot=nodeOfWord.get(i).inf.isFinDeMot;
				t.eq=nodeOfWord.get(i).inf.eq;
				t.inf=nodeOfWord.get(i).inf.inf;
				TrieHybride ite = nodeOfWord.get(i).inf;
				while(ite.sup!=null)
					ite=ite.sup;
				ite.sup=nodeOfWord.get(i).sup;
				t.sup=ite;
				nodeOfWord.get(i-1).eq=t;
				return trie;
			}
		}
		
		if(nodeOfWord.get(0).inf==null)
			return trie.sup;
		
		if(nodeOfWord.get(0).sup==null)
			return trie.inf;
		
		return trie;		
	}
	
	public void switchNode(TrieHybride copy){
		this.key = copy.key;
		this.isFinDeMot = copy.isFinDeMot;
		this.inf = copy.inf;
		this.sup = copy.sup;
		this.eq = copy.eq;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((eq == null) ? 0 : eq.hashCode());
		result = prime * result + (isFinDeMot ? 1231 : 1237);
		result = prime * result + key;
		result = prime * result + ordreInsert;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrieHybride other = (TrieHybride) obj;
		if (eq == null) {
			if (other.eq != null)
				return false;
		} else if (!eq.equals(other.eq))
			return false;
		if (inf == null) {
			if (other.inf != null)
				return false;
		} else if (!inf.equals(other.inf))
			return false;
		if (isFinDeMot != other.isFinDeMot)
			return false;
		if (key != other.key)
			return false;
		if (ordreInsert != other.ordreInsert)
			return false;
		if (sup == null) {
			if (other.sup != null)
				return false;
		} else if (!sup.equals(other.sup))
			return false;
		return true;
	}

	@Override
	public void suppression(String mot) {
		//TrieHybride.suppression(this, mot);
	}


	@Override
	public RTrie conversion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void printHTML(String name) {
		// TODO Auto-generated method stub
		
	}
}
