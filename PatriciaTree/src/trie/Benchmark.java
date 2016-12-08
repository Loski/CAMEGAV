package trie;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public abstract class Benchmark {
	
	public static void main(String[] args)
	{	 
		 File f = new File ("Bench.csv");
		 
		 long startTime,endTime,duration;
		 
		 PrintWriter pw=null;;
		 
		 
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
			 ArrayList<String> magic = Interface.lectureFichier("romeo_juliet");
			 
		     pw.println (" ;TempsCréation;NbPointeurNil;TempsRecherche (misshapen);");
		     
		     pw.print("TrieHybride;");
		     
		     TrieHybride t = new TrieHybride();
		     PatriciaTrie p = new PatriciaTrie();
		     
			 startTime = System.nanoTime();
			 
			 t.insertionListeMot(magic);
			 
			
			 
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 pw.print(t.comptageNil()+";");
			 
			 startTime = System.nanoTime();
			 
			 t.recherche("misshapen");
			 
			
			 
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 
			 pw.print("\nPatriciaTrie;");
			 
			 startTime = System.nanoTime();
			 
			 
			 p.insertionListeMot(magic);
			 
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 pw.print(p.comptageNil()+";");
			 
			 startTime = System.nanoTime();
			 
			 p.recherche("misshapen");
			 
			
			 
			 endTime = System.nanoTime();
			 duration = (endTime - startTime); 
			 pw.print((duration/1000)+";");
			 
			 
			 t.printHTML("test.html",true);

			 System.out.println(t.comptageMots());
			 System.out.println(p.comptageMots());
			 
			 System.out.println(p.listeMots());
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
