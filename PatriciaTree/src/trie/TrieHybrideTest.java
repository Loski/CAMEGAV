package trie;

import java.util.ArrayList;

public class TrieHybrideTest {
	public final static  String FILE = "jUnitTestFile.txt";

	public static void main(String[] args) {
		
		TrieHybride t = new TrieHybride();
		t.insertionListeMot(Interface.lectureFichier(FILE));
		t.printHTML("bi", true);
		System.out.println(t.comptageMots());
	}
}
