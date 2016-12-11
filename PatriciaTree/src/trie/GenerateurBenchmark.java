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
	
	public GenerateurBenchmark(int type_trie, ArrayList<String> nomFichier, int number) {
		this.inputString = new ArrayList<ArrayList<String>>();
		for(String file : nomFichier){
			inputString.add(Interface.lectureFichier(file));
		}
		this.nbFichier = number;
		this.trie = type_trie;
	}
	
	public PatriciaTrie ajoutByThread(){
		Bench tab[] = new Bench[this.nbFichier];
		for(int i = 0; i < this.nbFichier; i++){
			tab[i] = new Bench(this.trie, inputString.get(i));
			tab[i].run();
		}
		for(int i = 1; i < this.nbFichier;i++){
			try {
				tab[i].join();
				PatriciaTrie.fusion(tab[0].getTrie(), tab[i].getTrie());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tab[0].getTrie();
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
	
	
	public PatriciaTrie ajoutByFusionDicho(int nb){
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
			t.insertionListeMot(inputString.get(i));
		}
	/*	if(t instanceof PatriciaTrie)
			System.err.println("My patric");
		else
			System.err.println("My Hybride");*/
		return t;
	}
	
	public static  void Write(PrintWriter pw, GenerateurBenchmark gen){

		long startTime, endTime, duration;
		for(int i = 5; i < gen.inputString.size(); i = Math.min(i+5, gen.inputString.size())){
			gen.nbFichier = i;
			gen.trie = Interface.PATRICIA_TRIE;
			//Trie Fusion
		     startTime = System.nanoTime();
		     gen.ajoutByFusion();
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 
			 //Trie Insertion
			 startTime = System.nanoTime();
		     gen.ajoutBySeq();
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 
			 pw.print(gen.ajoutBySeq().comptageMots()+";");

			 //By thread
			 startTime = System.nanoTime();
		     gen.ajoutByThread();
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 			 
			gen.trie = Interface.TRIE_HYBRIDE;
			
			 startTime = System.nanoTime();
			 gen.ajoutBySeq();
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 pw.print(gen.ajoutBySeq().comptageMots());
			 pw.print(";"+i+"\n");
		}
		gen.nbFichier = gen.inputString.size();
		gen.trie = Interface.PATRICIA_TRIE;
		//Trie Fusion
	     startTime = System.nanoTime();
	     gen.ajoutByFusion();
		 endTime = System.nanoTime();
		 duration = (endTime - startTime); 
		 pw.print((duration/1000)+";");
		 
		 //Trie Insertion
		 startTime = System.nanoTime();
	     gen.ajoutBySeq();
		 endTime = System.nanoTime();
		 duration = (endTime - startTime); 
		 pw.print((duration/1000)+";");
		 
		 pw.print(gen.ajoutBySeq().comptageMots()+";");

		 //By thread
		 startTime = System.nanoTime();
	     gen.ajoutByThread();
		 endTime = System.nanoTime();
		 duration = (endTime - startTime); 
		 pw.print((duration/1000)+";");
		 			 
		gen.trie = Interface.TRIE_HYBRIDE;
		
		 startTime = System.nanoTime();
		 gen.ajoutBySeq();
		 endTime = System.nanoTime();
		 duration = (endTime - startTime); 
		 pw.print((duration/1000)+";");
		 pw.print(gen.ajoutBySeq().comptageMots());
		 pw.print(";"+gen.nbFichier+"\n");
		
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
		     pw.println ("TempsCréationByFusionPatricia;TempsParSéquencePatricia;TempsBythread;TempsParSeqHybride");
		     

			 
		     GenerateurBenchmark gen = new GenerateurBenchmark(Interface.TRIE_HYBRIDE, nom,0);
		     Write(pw, gen);
		    
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
