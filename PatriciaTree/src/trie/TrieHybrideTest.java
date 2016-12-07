package trie;

import java.util.ArrayList;

public class TrieHybrideTest {

	public static void main(String[] args) {
		
		TrieHybride t = TrieHybride.lectureFichier("jUnitTestFile");
		System.out.println("Trie H" +  t.listeMots().size());
		System.out.println(PatriciaTrie.lectureFichier("jUnitTestFile").comptageMots());
		System.out.println(t.prefixe("m"));
		t.printHTML("trieTest",true);
	}
}
