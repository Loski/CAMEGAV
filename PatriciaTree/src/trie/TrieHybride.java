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
		//this.lastInsert = 0;
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
		if(this.key==Character.MAX_VALUE && this.inf==null && this.eq==null && this.sup==null && !this.isFinDeMot)
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
	public void insertLetter(String mot){
		if(this.key == Character.MAX_VALUE)
			this.key = mot.charAt(0);
		insertOnlyLetter(mot, 0, this);
	}
	public void insererMot(String mot) {
		
		/*if (mot != null && !mot.isEmpty()) {
			
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
		}*/
		
		//Pour Equilibrage
		
		if(this.key == Character.MAX_VALUE)
			this.key = mot.charAt(0);
		insert(mot, 0, this);
		TrieHybride.equilibrage(this);
	}
	static void equilibrage(TrieHybride t){
		int motsInf = (t.inf == null) ? 0 : t.inf.comptageMots();
		int motsSup = (t.sup == null) ? 0 : t.sup.comptageMots();
		
		int motsTotal = motsInf+motsSup;
		
		if(motsInf<motsTotal/2){
			t.rotationGauche();
		}
		else if(motsSup<motsTotal/2){
			t.rotationDroite();
		}
		//return t;
		
	}
	private void rotationDroite(){
		  TrieHybride t = new TrieHybride(this.inf);
		  this.inf = (t.sup==null) ? null : new TrieHybride(t.sup);
		  t.sup =  new TrieHybride(this);
		  switchNode(t);
	}
	
	private void rotationGauche(){
		  TrieHybride t = new TrieHybride(this.sup);
		  this.sup = (t.inf==null) ? null : new TrieHybride(t.inf);
		  t.inf = new TrieHybride(this);
		  switchNode(t);
	}
	
	public void switchNode(TrieHybride copy){
		if(copy==null) return;
		this.key = copy.key;
		this.isFinDeMot = copy.isFinDeMot;
		this.inf = copy.inf;
		this.sup = copy.sup;
		this.eq = copy.eq;
		this.ordreInsert=copy.ordreInsert;
	}
	public void switchNodeBool(TrieHybride copy){
		if(copy==null) return;
		this.key = copy.key;
		this.isFinDeMot = copy.isFinDeMot || this.isFinDeMot;
		this.inf = copy.inf;
		this.sup = copy.sup;
		this.eq = copy.eq;
		this.ordreInsert=copy.ordreInsert;
	}
	
	private static TrieHybride insert(String mot, int i, TrieHybride t){
		//System.out.println(i);
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
				//System.out.println("ORDRE :"+lastInsert);
			}else
				t.eq = tmp;
		}
		/*if(t!=null)
			equilibrage(t);*/
		return t;
	}
	private static TrieHybride insertOnlyLetter(String mot, int i, TrieHybride t){

		if(i >= mot.length() )
			return null;
		if(t == null){
			t = new TrieHybride(mot.charAt(i));
		}
		if(mot.charAt(i) < t.key){
			t.inf = insertOnlyLetter(mot, i, t.inf);
		}
		else if(mot.charAt(i) > t.key){
			t.sup = insertOnlyLetter(mot, i, t.sup);

		}
		else{
			TrieHybride tmp =  insertOnlyLetter(mot, i +1, t.eq);
			if(tmp == null){
				//System.out.println("ORDRE :"+lastInsert);
			}else
				t.eq = tmp;
		}
		/*if(t!=null)
			equilibrage(t);*/
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
		ArrayList<String> liste = new ArrayList<String>();
		this.listeMots("", liste);
		liste.sort(String.CASE_INSENSITIVE_ORDER);
		return liste;
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
	
	private void profondeurCalcul(int[] calcul,int profondeur)
	{		
		if(this.inf==null &&this.sup==null && this.eq==null)
		{
			calcul[0]+=profondeur;
			calcul[1]++;
		}
		
		if(this.inf!=null)
			this.inf.profondeurCalcul(calcul,profondeur+1);
		
		if(this.eq!=null)
			this.eq.profondeurCalcul(calcul,profondeur+1);
		
		if(this.sup!=null)
			this.sup.profondeurCalcul(calcul,profondeur+1);
	}
	
	public int profondeurMoyenne()
	{
		int[] calculProfondeur = new int[2];
		this.profondeurCalcul(calculProfondeur,1);
		int profondeurTotale = calculProfondeur[0];
		//return (int) Math.ceil(profondeurTotale/(double)this.comptageMotSansSousChaine());
		return (int) Math.ceil(profondeurTotale/(double)calculProfondeur[1]);
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
	
	@Override
	public void suppression(String mot)
	{
		if(mot==null || mot.isEmpty())
			return;
		
		ArrayList<TrieHybride> nodeOfWord = new ArrayList<>();
		TrieHybride currentNode = this;
		
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
		
		//Le mot n'existe pas dans l'arbre
		if(i!=mot.length())
			return;
		
		//Si le mot à supprimer est une sous-chaîne
		TrieHybride lastNode = nodeOfWord.get(nodeOfWord.size()-1);
		
		if(lastNode.eq!=null)
		{
			lastNode.isFinDeMot=false;
			return;
		}
		
		//Si le mot a supprimer est la racine et la racine n'est pas utilisée dans les autres mots
		TrieHybride root = nodeOfWord.get(0);
		if(nodeOfWord.size()==1)
		{
			if(root.inf==null && root.sup==null)
			{
				this.switchNode(new TrieHybride());
				return;
			}
			
			if(root.inf==null)
			{
				this.switchNode(root.sup);
				return;
			}
			
			if(root.sup==null)
			{
				this.switchNode(root.inf);
				return;
			}
			
			//Si il y a des mots dans les branches inf et sup
			TrieHybride t = new TrieHybride();
			t.key=root.inf.key;
			t.isFinDeMot=root.inf.isFinDeMot;
			t.eq=root.inf.eq;
			t.inf=root.inf.inf;
			TrieHybride ite = root.inf;
			while(ite.sup!=null)
				ite=ite.sup;
			ite.sup=root.sup;
			t.sup=ite;
			this.switchNode(t);
			return;
		}
		
		for(i=nodeOfWord.size()-1;i>=1;i--)
		{
			currentNode = nodeOfWord.get(i);		
			TrieHybride nodeParent = nodeOfWord.get(i-1);
			
			if(currentNode.inf==null && currentNode.sup==null)
			{
				if(nodeParent.inf==currentNode)
				{
					nodeParent.inf=null;
					return;
				}
				
				if(nodeParent.sup==currentNode)
				{
					nodeParent.sup=null;
					return;
				}
				
				nodeParent.eq=null;
				if(nodeParent.isFinDeMot)
					return;
			}
			else
			{
				if(currentNode.inf==null)
				{
					nodeParent.eq = currentNode.sup;
					return;
				}
				if(currentNode.sup==null)
				{
					nodeParent.eq = currentNode.inf;
					return;
				}
				
				//Si il y a des mots dans les branches inf et sup
				TrieHybride t = new TrieHybride();
				t.key=currentNode.inf.key;
				t.isFinDeMot=currentNode.inf.isFinDeMot;
				t.eq=currentNode.inf.eq;
				t.inf=currentNode.inf.inf;
				TrieHybride ite = currentNode.inf;
				while(ite.sup!=null)
					ite=ite.sup;
				ite.sup=currentNode.sup;
				t.sup=ite;
				nodeParent.eq=t;
				return;
			}
		}
		
		if(root.eq!=null)
			return;
		
		if(root.inf==null)
		{
			this.switchNode(this.sup);
			return;
		}
		
		if(root.sup==null)
		{
			this.switchNode(this.inf);
			return;
		}
				
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
	
	private void insertNode(String prefixe,PatriciaTrie trie)
	{		
		if(this.inf==null && this.sup==null)
		{
			prefixe+=this.key;
			if(this.eq==null)
				trie.setNode(prefixe);
			else if(!this.isFinDeMot)
				this.eq.insertNode(prefixe,trie);
			else
			{
				int index = PatriciaTrie.getIndexKey(prefixe);
				
				if(trie.getFrère()[index]==null)
					trie.getFrère()[index]=new Node(null);
				
				if(trie.getFrère()[index].getLink()==null)
					trie.getFrère()[index].setLink(new PatriciaTrie());
				
				trie.setNode(prefixe);
				this.eq.insertNode("",trie.getFrère()[index].getLink());
			}
		}
		else
		{
			int index = PatriciaTrie.getIndexKey(prefixe);
			
			if(trie.getFrère()[index]==null)
				trie.getFrère()[index]=new Node(null);
			
			if(trie.getFrère()[index].getLink()==null)
				trie.getFrère()[index].setLink(new PatriciaTrie());
			
			if(this.inf!=null)
				this.inf.insertNode("",trie.getFrère()[index].getLink());
			if(this.eq!=null)
			{
				trie.setNode(prefixe);
				this.eq.insertNode(this.key+"",trie.getFrère()[index].getLink());
			}
			if(this.sup!=null)
				this.sup.insertNode("",trie.getFrère()[index].getLink());
		}
		
	}
	
	public PatriciaTrie conversion(){
		
		PatriciaTrie trie = new PatriciaTrie();
		
		if(this.estVide())
			return trie;
		
		trie.setNode(this.key+"");
		
		if(this.inf!=null)
			this.inf.insertNode("",trie);
		if(this.eq!=null)
			this.eq.insertNode(this.key+"",trie);
		if(this.sup!=null)
			this.sup.insertNode("",trie);
		
		return trie;
	}

	public int getOrdreInsert() {
		return ordreInsert;
	}

	public void setOrdreInsert(int ordreInsert) {
		this.ordreInsert = ordreInsert;
	}

	public char getKey() {
		return key;
	}

	public void setKey(char key) {
		this.key = key;
	}

	public boolean isFinDeMot() {
		return isFinDeMot;
	}

	public void setFinDeMot(boolean isFinDeMot) {
		this.isFinDeMot = isFinDeMot;
	}

	public TrieHybride getInf() {
		return inf;
	}

	public void setInf(TrieHybride inf) {
		this.inf = inf;
	}

	public TrieHybride getEq() {
		return eq;
	}

	public void setEq(TrieHybride eq) {
		this.eq = eq;
	}

	public TrieHybride getSup() {
		return sup;
	}

	public void setSup(TrieHybride sup) {
		this.sup = sup;
	}
	public TrieHybride getLastEq(){
		TrieHybride eq = this;
		while(eq.eq != null)
			eq = eq.eq;
		return eq;
	}
}
