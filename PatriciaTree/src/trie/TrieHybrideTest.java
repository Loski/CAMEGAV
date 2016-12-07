package trie;

import java.util.ArrayList;

public class TrieHybrideTest {

	public static void main(String[] args) {
		
		TrieHybride t = new TrieHybride();
		t = TrieHybride.lectureFichier("jUnitTestFile");
		System.out.println(t.listeMots().size());
		t.suppression("ma");
		System.out.println(t.prefixe("m"));
		t.printHTML("trieTest",true);
	}
}
