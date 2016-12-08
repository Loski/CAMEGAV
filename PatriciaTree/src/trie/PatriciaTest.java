package trie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatriciaTest {
	public final static  String FILE = "jUnitTestFile";

	public static void main(String[] args) {
		PatriciaTrie p = new PatriciaTrie("Vampire"+PatriciaTrie.END_CHAR);

		/*PatriciaTree f = PatriciaTree.lectureFichier("jUnitTestFile"); 
		f.printHTML("arbre");
		System.out.println(f.listeMots(""));
		System.out.println(f.comptageNil());
		System.out.println(p.comptageMots());
		p.printHTML("hello");
		System.out.println(f.hauteur());
		System.out.println((int)'\0');*/
		System.out.println(PatriciaTrie.lectureFichier("jUnitTestFile").profondeurMoyenne());
		ArrayList<String> l = Interface.lectureFichier("romeo_juliet");
	      long startTime = System.nanoTime();
	      PatriciaTrie p2 = new PatriciaTrie();
	      p2.insertionListeMot(l);
	     // p2.printHTML("kek");
	      long endTime = System.nanoTime();
	      System.out.println("log(42) is computed in : " + (endTime - startTime) + " ns");
	}

}
