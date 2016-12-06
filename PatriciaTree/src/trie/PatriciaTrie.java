package trie;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PatriciaTrie implements RTrie, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int ASCII_NUMBER = 128;

	   private Node frère[];
	   public static final Character END_CHAR = new Character((char)38);

	   public PatriciaTrie(String key){
		   this.createNode();
		   this.frère[getIndexKey(key)] = new Node(key);
	   }
	   

	   
	   @Override
	public String toString() {
		String s = "";
		for(Node n : this.frère){
			if(n!=null){
				s+=n.toString();
			}
		}
		return s;
	}

	public PatriciaTrie(){
		   this.createNode();
	}
	   
	   public void createNode(){
		   this.frère = new Node[PatriciaTrie.ASCII_NUMBER]; 
	   }
	   public static int getIndexKey(String key){
		   try{
			   if(key == null || key.isEmpty())
				   throw new Exception("String vide !");
		   }catch(Exception e){
			   e.printStackTrace();
			   System.err.println(e);
		   }
		   return (int)key.charAt(0);
	   }
	   
		public static int prefixe(String  mot, String key){
			int motLght = mot.length();
			int keyLght = key.length();
			 for( int k=0; k<motLght; k++ )
				 if( k==keyLght || mot.charAt(k)!=key.charAt(k))
					 return k;
			 return motLght;
		}
		
		@Override
		public void suppression(String mot){
			PatriciaTrie.remove(this, mot + END_CHAR);
		}
		  private static PatriciaTrie remove(PatriciaTrie t, String mot){ // deleting x key from t tree 
			  if( t == null ) 
				  return null;
			  int n = mot.length();
			  int index = PatriciaTrie.getIndexKey(mot);
			  Node node = t.frère[index];
			  String key = node.getKey();
			  
			  int prefixe = prefixe(mot, key);
			  if( prefixe==n ) // Delete leaf
			  {   
				  t.frère[index] = null;
			  }else if(prefixe == 0){ //n'existe pas
				 
			  }else if(prefixe == key.length()){
				  PatriciaTrie.remove(node.getLink(),  mot.substring(prefixe));
				  if(node.getLink()!=null){
					  Node node2 = node.getLink().nodeRemontable();
					  if(node2 != null){
						  node.setKey(node.getKey() + node2.getKey());
						  node.setLink(node2.getLink());
					  }
				  }
			  }
			  return t;
		  }
		  
		  // S'il est forever alone, on peut le remonter
		  public Node nodeRemontable(){
			  int compteur = 0;
			  Node node = null;
			  for(Node n: this.frère){
					if(n != null){
						compteur++;
						if(compteur >=2)
							return null;
						node = n;
					}
			  }
			  return node;
		  }


		  public static PatriciaTrie lectureFichier(String name){
			  PatriciaTrie t = new PatriciaTrie();
		        try {
		            FileReader c = new FileReader(name+".txt");
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

			public void printHTML(String name){
				File f = new File (name+".html");
				try
				{
				    PrintWriter pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
				    pw.println("<!doctype html>"
						    +"<html lang='fr'> "
						    +"<head>"+"<title>"+this.getClass()+"</title>"
						    +"<meta charset='utf-8'>"
						    
						    +"<link rel='stylesheet' href='jquery.orgchart.css'/>"
						    +"<link rel='stylesheet' href='style.css'/>"
						    +"<script src='jquery.min.js'></script>"
						    +"<script src='jquery.orgchart.js'></script>"
						  +  "<script>"+
						    "$(function() {"+
						        "$('#tree').orgChart({container: $('#main'), interactive: true, fade: true, speed: 'fast'});"+
						    "});"+
						    "</script>"+
						"</head>"
						    +"<body>"+
						    "\n\n<ul id='tree'><li><ul>");
				    pw.println(parcourirArbre());
				    pw.println("</ul></li></ul><div id='main'></div>"
				    +"<script>$(function() {"
				    +"$('#organisation').orgChart({container: $('#main'), interactive: true, fade: true, speed: 'slow'});"
					+"});"
					+"$(function() {"
			        +"$('#tree').remove()"
			        +"});"
					+ "</script></body></html>");
				    pw.close();
				    System.out.println("End generation " + name);
				}
				catch (IOException exception)
				{
				    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
				}
			}
			

			public String parcourirArbre(){
				String str ="";
				for(Node n: this.frère){
					if(n == null)
						continue;
					str+="\n<li>" + n.getKey();
					if(n.getLink() != null){
						str += "\n\t<ul>" + n.getLink().parcourirArbre()+ "</ul>";
					}
					str+="</li>";
				}
				return str;
			}
		@Override
		public List<String> listeMots(){
			ArrayList<String> liste = new ArrayList<String>();
			return listeMots("", liste);
		}
		
		private List<String> listeMots(String prefixe, ArrayList<String> liste){
			for(Node n: this.frère){
				if(n == null)
					continue;
				if(n.getKey().endsWith(END_CHAR.toString()))  // Fin mot
					liste.add(prefixe  + n.getKey());
				else if(n.getLink() != null){
					n.getLink().listeMots(prefixe+n.getKey(), liste);
				}
			}
			return liste;
		}
		
		public PatriciaTrie fils(int index){
			return this.frère[index].getLink();
		}
		public void insererMot(String mot) {
			insert(this, mot+END_CHAR);
		}
		
		public void setNode(int index, Node node){
			this.frère[index] = node;
		}
		
		private boolean insert(PatriciaTrie t, String mot){
			if(t == null){
				t = new PatriciaTrie(mot);
			}
			int index = t.getIndexKey(mot);
			Node node  = t.frère[index];
			if(node == null || t.estVide()){
				t.setNode(index, new Node(mot));
			}else{
				String key = node.getKey();
				int prefixe = prefixe(mot, key);
				int taille_key = key.length();
				if(prefixe == taille_key){ 
					if(key.endsWith(END_CHAR.toString())) //Mot déjà présent !
						return false;
					else{
						 insert(node.getLink(), mot.substring(prefixe));
					}
				}else{
					PatriciaTrie tmp = node.getLink();
					String mot_prefixe = key.substring(0, prefixe);
					String after = key.substring(prefixe);
					node.setKey(mot_prefixe);
					node.setLink(new PatriciaTrie(after));
					node.getLink().insererDansFils(after, tmp);
					insert(node.getLink(), mot.substring(prefixe));
					}
				}
			return true;
		}

		public void insererDansFils(String key, PatriciaTrie link){
			this.frère[getIndexKey(key)].setLink(link);
		}

		@Override
		public boolean recherche(String mot) {
			return search(this, mot+PatriciaTrie.END_CHAR);
		}
		
		public boolean search(PatriciaTrie t, String mot){
			if(t == null || mot.isEmpty() || t.estVide())
				return false;
			int index = PatriciaTrie.getIndexKey(mot);
			Node node  = t.frère[index];
			if(node == null)
				return false;
			else{
				String key = node.getKey();
				int prefixe = prefixe(mot, key);
				int taille_key = key.length();
				if(prefixe == taille_key){ 
					if(key.endsWith(END_CHAR.toString())) //Mot déjà présent !
						return true;
					else{
						 return search(node.getLink(), mot.substring(prefixe));
					}
				}
				else{
					return false;
				}
			}
		}

		@Override
		public int comptageMots() {
			int cpt = 0;
			for(Node n: this.frère){
				if(n!=null) 
				{
					if(n.getKey().endsWith(END_CHAR.toString()))
						cpt++;
					else
						if(n.getLink() != null)
							cpt+= n.getLink().comptageMots();
				}
			}
			return cpt;
		}

		@Override
		public int comptageNil() {
				int cpt = 0;
				for(Node n: this.frère){
					if(n==null) 
						cpt++;
					else{
						if(n.getLink()!= null)
							cpt += n.getLink().comptageNil();
						else
							cpt++;
					}
				}
				return cpt;
		}

		@Override
		public int hauteur() {
			int hauteur = 0;
			for(Node n : this.frère){
				if(n != null){
					if(n.getLink() != null){
						hauteur = Math.max(hauteur, n.getLink().hauteur());
					}
				}	
			}
			return ++hauteur;
		}

		@Override
		public double profondeurMoyenne() {
			// TODO Auto-generated method stub
			return 0;
		}

	/*	public static PatriciaTree fusion(PatriciaTree t, PatriciaTree copy){
			return t.fusion(copy);
		}*/
		/*
		 * Ajoute un arbre entre 2 parties de mots
		 */
		public  static PatriciaTrie addPrefixEachWord(PatriciaTrie t, String prefixe){
			PatriciaTrie tmp;
			if(prefixe.isEmpty())
				return t;
			if(t == null || t.estVide())
				return new PatriciaTrie(prefixe);
			else{
				tmp = new PatriciaTrie();
				int index = PatriciaTrie.getIndexKey(prefixe);
				tmp.frère[index] = new Node(prefixe);
				tmp.frère[index].setLink(t);
			}
			return tmp;
		}
		public static PatriciaTrie fusion(PatriciaTrie t, PatriciaTrie copy) {
			if(t == null || t.estVide()){
				return copy;
			}else if(copy == null || copy.estVide()){
				return t;
			}
			for(int i = 0; i < PatriciaTrie.ASCII_NUMBER; i++){
				t.setNode(i, Node.fusion(t.frère[i], copy.frère[i]));
			}
			return t;
		}
	
		@Override
		public boolean estVide() {
			if(this.frère != null){
				for(Node n : this.frère){
					if(n != null && n.getKey() != null)
						return false;
				}
			}
			return true;
		}


		@Override
		public int prefixe(String mot) {
			int compteur = 0;
			int index = PatriciaTrie.getIndexKey(mot);
			Node node  = this.frère[index];
			if(node == null)
				return 0;
			String motCle = node.getKey();
			int taille_prefixe = prefixe(mot, motCle);
			if(taille_prefixe <= mot.length()){
				if(node.getLink() != null){
					String mot_buffer = mot.substring(taille_prefixe);
					if(mot_buffer.equals(""))
						return node.getLink().comptageMots();
					else
						return node.getLink().prefixe(mot_buffer);
				}
				else
					return 1;
			}
			return compteur;
		}



		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(frère);
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
			PatriciaTrie other = (PatriciaTrie) obj;
			if (!Arrays.equals(frère, other.frère))
				return false;
			return true;
		}



		public Node[] getFrère() {
			return frère;
		}



		public void setFrère(Node[] frère) {
			this.frère = frère;
		}



		@Override
		public void insertionListeMot(ArrayList<String> str) {
			for(String s : str){
				this.insererMot(s);
			}
		}

		   
}

