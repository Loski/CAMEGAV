package PatriciaTree;

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

public class PatriciaTree implements RTrie, Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int ASCII_NUMBER = 128;

	   private Node frère[];
	   public static final Character END_CHAR = new Character((char)127);

	   public PatriciaTree(String key){
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

	public PatriciaTree(){
		   this.createNode();
	}
	   
	   public void createNode(){
		   this.frère = new Node[PatriciaTree.ASCII_NUMBER]; 
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
		public RTrie suppression(String mot){
			this.remove(this, mot + END_CHAR);
			return this;
		}
		  private PatriciaTree remove(PatriciaTree t, String mot){ // deleting x key from t tree 
			  if( t == null ) 
				  return null;
			  int n = mot.length();
			  int index = PatriciaTree.getIndexKey(mot);
			  Node node = t.frère[index];
			  String key = node.getKey();
			  
			  int prefixe = prefixe(mot, key);
			  if( prefixe==n ) // Delete leaf
			  {   
				  t.frère[index] = null;
			  }else if(prefixe == 0){ //n'existe pas
				 
			  }else if(prefixe == key.length()){
				  this.remove(node.getLink(),  mot.substring(prefixe));
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


		  public static PatriciaTree lectureFichier(String name){
			  PatriciaTree t = new PatriciaTree();
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
				    +"<head> "
				      +"<meta charset='utf-8'>"
				      +"<title>Titre de la page</title>"
				      +"<link rel='stylesheet' href='style.css'>"
				    +"</head>"
				    +"<body>"+
				    "<div class='tree'><ul>");
				    pw.println(parcourirArbre());
				    pw.println("</ul></div></body></html>");
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
					str+="<li><a href='#'>" + n.getKey() +"</a>";
					if(n.getLink() != null){
						str += "<ul>" + n.getLink().parcourirArbre()+ "</ul>";
					}
				}
				return str + "</li>";
			}
	
			@Override
		public ArrayList<String> listeMots(String prefixe){
			ArrayList<String> liste = new ArrayList<String>();
			for(Node n: this.frère){
				if(n == null)
					continue;
				if(n.getKey().endsWith(END_CHAR.toString()))  // Fin mot
					liste.add(prefixe  + n.getKey());
				else if(n.getLink() != null){
					liste.addAll(n.getLink().listeMots(prefixe+n.getKey()));
				}
			}
			return liste;
		}
		
		public PatriciaTree fils(int index){
			return this.frère[index].getLink();
		}
		public RTrie insererMot(String mot) {
			insert(this, mot+END_CHAR);
			return this;
		}
		
		public void setNode(int index, Node node){
			this.frère[index] = node;
		}
		
		private boolean insert(PatriciaTree t, String mot){
			if(t == null){
				t = new PatriciaTree(mot);
			}
			int index = t.getIndexKey(mot);
			Node node  = t.frère[index];
			if(node == null || t.isEmpty()){
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
					PatriciaTree tmp = node.getLink();
					String mot_prefixe = key.substring(0, prefixe);
					String after = key.substring(prefixe);
					node.setKey(mot_prefixe);
					node.setLink(new PatriciaTree(after));
					node.getLink().insererDansFils(after, tmp);
					insert(node.getLink(), mot.substring(prefixe));
					}
				}
			return true;
		}
		@Override
		public void insererPhrase(String phrase) {
			// TODO Auto-generated method stub
			
		}
		public void insererDansFils(String key, PatriciaTree link){
			this.frère[getIndexKey(key)].setLink(link);
		}

		@Override
		public void insererListeMots(List<String> mots) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public boolean rechercherMot(String mot) {
			return search(this, mot+PatriciaTree.END_CHAR);
		}
		
		public boolean search(PatriciaTree t, String mot){
			if(t == null || mot.isEmpty() || t.isEmpty())
				return false;
			int index = PatriciaTree.getIndexKey(mot);
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
		
		public  static PatriciaTree addPrefixEachWord(PatriciaTree t, String prefixe){
			if(t == null || t.isEmpty())
				return new PatriciaTree(prefixe);
			for(Node n:t.frère){
				if(n!=null)
					n.setKey(prefixe + n.getKey());
			}
			return t;
		}
		public static PatriciaTree fusion(PatriciaTree t, PatriciaTree copy) {
			if(t == null || t.isEmpty()){
				return copy;
			}else if(copy == null || copy.isEmpty()){
				return t;
			}
			for(int i = 0; i < PatriciaTree.ASCII_NUMBER; i++){
				t.setNode(i, Node.fusion(t.frère[i], copy.frère[i]));
			}
			return t;
		}
		



		@Override
		public RTrie conversion() {
			// TODO Auto-generated method stub
			return null;
		}



		@Override
		public boolean isEmpty() {
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
			// TODO Auto-generated method stub
			return 0;
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
			PatriciaTree other = (PatriciaTree) obj;
			if (!Arrays.equals(frère, other.frère))
				return false;
			return true;
		}

		   
}

