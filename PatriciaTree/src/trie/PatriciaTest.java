package trie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatriciaTest {
	public final static  String FILE = "jUnitTestFile";

	public static void main(String[] args) {
		RTrie p = new PatriciaTrie();
		p.insertionListeMot(Interface.lectureFichier(FILE));
		p.printHTML("Switch", true);
		
		System.out.println(p.listeMots());
		ArrayList<String> o = (ArrayList<String>) p.listeMots();
		System.out.println(p.conversion().listeMots());
	}
}
