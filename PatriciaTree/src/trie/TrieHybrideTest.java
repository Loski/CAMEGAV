package trie;

import java.util.ArrayList;

public class TrieHybrideTest {

	public static void main(String[] args) {
		
		TrieHybride t = new TrieHybride();
		t.insererMot("m");
		t.insererMot("kek");
		t.insererMot("koala");
		t.insererMot("ma");
		
		//t = TrieHybride.lectureFichier("jUnitTestFile");
		
		System.out.println(t.listeMots().size());
		
		System.out.println(t.prefixe("m"));
		t.printHTML("trieTest",true);
	}
}
