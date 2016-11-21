package PatriciaTree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PatriciaTree implements RTrie{
	   private String key;
	   private PatriciaTree next;
	   private PatriciaTree link;
	   public static final char END_CHAR = '$';
	
	   public PatriciaTree(String key){
		   this.next = null;
		   this.link = null;
		   this.key = key;
	   }
	   
	   public PatriciaTree(){
		   this.next = null;
		   this.link = null;
		   this.key = "";
	   }
		@Override
		public RTrie TrieVide() {
			return new PatriciaTree();
		}

		@Override
		public boolean estVide() {
			if(this.key.equals("") && this.next == null && this.next ==null)
				return true;
			return false;
		}

		@Override
		public String val() {
			return this.key;
		}

		@Override
		public RTrie sousArbre(int i) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public RTrie filsSauf(int i) {
			// TODO Auto-generated method stub
			return null;
		}
		public static int prefix(String  mot, String key){
			int motLght = mot.length();
			int keyLght = key.length();
			 for( int k=0; k<motLght; k++ )
				 if( k==keyLght || mot.charAt(k)!=key.charAt(k))
					 return k;
			 return motLght;
		}
		
		public static PatriciaTree find(PatriciaTree t, String mot){
		    if(t == null)
		        return null;
		    int prefixe_max = prefix(mot, t.key);
		    if(prefixe_max == 0)
		        return find(t.next, mot);  // On cherche dans les frères !
		    else if(prefixe_max == mot.length()){
		        return t;  //SuccessFull Search
		    }
		    else if(prefixe_max== t.key.length()){   // Taille max du noeud, on a trouver le bon noeud, on regarde dans ses fils, tout en modifiant le string pour virer les trucs du mot déjà présent dans cette clé
		        return find(t.link, mot.substring(mot.length() - prefixe_max));
		    }
		    return null;
		}
		
		void split(int indexDivide) // dividing this node according to indexDivide key symbol
		{   
			PatriciaTree p = new PatriciaTree(this.key.substring(indexDivide)); 
			p.link = this.link;   
			this.link = p;
			this.key = this.key.substring(0, indexDivide);
		}
		
		public static PatriciaTree insertion(PatriciaTree t, String x) // inserting x key in t tree 
		{
		    if(t == null) 
		    	return new PatriciaTree(x);
		    int n = x.length();   
		    int k = prefix(x, t.key);
		    if( k==0 )
		    	t.next = insertion(t.next,x);  
		    else if( k<=n )    
		    {       
		    	if( k <t.key.length() ) // cut or not to cut?
			    		t.split(k);  
			    	t.link = insertion(t.link,x.substring(k));  
			}
			    return t;
		}
		
		
		public static PatriciaTree insert(PatriciaTree t, String x){
			return insertion(t, x+END_CHAR);
		}
		    
		  public void join() // t and t->link nodes merge
		  {
		    	PatriciaTree p = this.link;
		    	String mot_fusionne = new String(this.key + p.key );
		    	this.key = mot_fusionne;
		    	this.link = p.link;
		  }
		 /*   void join(node* t) // t and t->link nodes merge{
		    node* p = t->link;  
		    char* a = new char[t->len+p->len];
		    strncpy(a,t->key,t->len);   
		    strncpy(a+t->len,p->key,p->len); 
		    delete[] t->key;    t->key = a;    
		    t->len += p->len;  
		    t->link = p->link; 
		    delete p;}*/
		  public static PatriciaTree remove(PatriciaTree t, String mot) // deleting x key from t tree 
		  { 
			  if( t == null ) 
				  return null;
			  int n = mot.length();
			  int k = prefix(mot, t.key);
			  if( k==n ) // Delete leaf
			  {   
				  return t.next; 
			  } 
			  if( k==0)
				  t.next = remove(t.next, mot);
			  else if(k== t.key.length()) {
				  t.link = remove(t.link, mot.substring(k)); // A vérifier !!
			  
					if( t.link != null && t.link.next == null){ // does t node have just one sister node?         
						  t.join();
					}
			}
				  return t;
		  }
		  
			@Override
			public String toString() {
		String s =  "[key=" + key+ "]";
		if(this.link!=null)
			s+= "\n[  Children of " + key + "\t" + this.link.toString() + "]\n";
		if(this.next!=null)
			s+= "\\\t Brother of " + key + "\t" +this.next.toString() + " / ";
		return s;
	}
			

		  public static PatriciaTree lectureFichier(String name){
			  PatriciaTree t = new PatriciaTree();
		        try {
		            FileReader c = new FileReader(name+".txt");
		            BufferedReader r = new BufferedReader(c);
		 
		            String line = r.readLine();
		             
		 
		            while (line != null) {
		                String[] decompose = line.split(" ");
		                for(String s: decompose)
		                	PatriciaTree.insert(t, s);
		           line = r.readLine();
		           }
		 
		            r.close();
		 
		        } catch (Exception e) {
		            throw new Error(e);
		        }
				return t;
		 
		  }

			public void printHTML(){
				double[] data = {15.9, 21.2, 18.4, 25.4, 31.1};
				File f = new File ("index.html");
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
				    pw.println(parcourirArbreHTML());
				    pw.println("</ul></div></body></html>");
				    pw.close();
				}
				catch (IOException exception)
				{
				    System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
				}
			}
			
			public String parcourirArbreHTML(){
				String str = "<li><a href='#'>" + this.val() +"</a>";
				if(this.link != null){
					str += "<ul>" + this.link.parcourirArbreHTML()+ "</ul>";
				}
				if(this.next != null)
					str += this.next.parcourirArbreHTML();
				return str + "</li>";
			}
    
}

