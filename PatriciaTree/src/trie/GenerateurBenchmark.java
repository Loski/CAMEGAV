package trie;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenerateurBenchmark {
	private int trie;
	private List<ArrayList<String>> inputString;
	private int nbFichier;
	
	public GenerateurBenchmark(int type_trie, ArrayList<String> nomFichier) {
		this.inputString = new ArrayList<ArrayList<String>>();
		for(String file : nomFichier){
			inputString.add(Interface.lectureFichier(file));
		}
		this.nbFichier = nomFichier.size();
		this.trie = type_trie;
	}
	
	public void ajoutByThread(){
		Bench tab[] = new Bench[this.nbFichier];
		for(int i = 0; i < this.nbFichier; i++){
			tab[i] = new Bench(this.trie, inputString.get(i));
			tab[i].run();
		}
	}
	
	public PatriciaTrie ajoutByFusion(){
		PatriciaTrie p = new PatriciaTrie() , e;
		for(int i = 0; i < this.nbFichier; i++){
			e = new PatriciaTrie();
			e.insertionListeMot(inputString.get(i));
			PatriciaTrie.fusion(p,e);
		}
		return p;
	}
	
	public RTrie ajoutBySeq(){
		 RTrie t = (this.trie == Interface.PATRICIA_TRIE)? new PatriciaTrie() : new TrieHybride();
		for(int i = 0; i < this.nbFichier; i++){
			System.out.println("Im here");
			t.insertionListeMot(inputString.get(i));
		}
		return t;
	}
	
	public static void main(String[] args)
	{	 
		 File f = new File ("Bench.csv"), directory = new File("." +File.separator + "shakespeare" +File.separator);
		 long startTime,endTime,duration;
		 PrintWriter pw=null;
		 
	      FileFilter fileTextFilter = new FileFilter() {
		         public boolean accept(File file) {
		            return file.getName().endsWith(".txt");
		         }
		      };
	      
		File[] files = directory.listFiles(fileTextFilter);
		ArrayList<String> nom = new ArrayList<String>();
		for(File file : files){
			nom.add(file.getPath());
		}
				
		try {
			pw = new PrintWriter (new BufferedWriter (new FileWriter (f)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		 
		if(pw!=null)
		{
		 try
		 {			 
		     pw.println ("TempsCréationByFusion;TempsParSéquence");
		     GenerateurBenchmark gen = new GenerateurBenchmark(Interface.TRIE_HYBRIDE, nom);
		     startTime = System.nanoTime();
		  //   System.out.println(gen.ajoutByFusion().comptageMots());
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 
			 startTime = System.nanoTime();
		     System.out.println(gen.ajoutBySeq().comptageMots());
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 
		     pw.close();
		 }
		 catch (Exception exception)
		 {
		     System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		     pw.close();
		 }
		}
		System.out.println("FINISHED");
	}
	
}
