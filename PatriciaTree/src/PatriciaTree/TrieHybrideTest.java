package PatriciaTree;

import java.util.ArrayList;

public class TrieHybrideTest {

	public static void main(String[] args) {
		
		TrieHybride t = new TrieHybride();
		t.ajouterMot("m");
		t.ajouterMot("kek");
		t.ajouterMot("koala");
		t.ajouterMot("ma");
		
		//t = TrieHybride.lectureFichier("jUnitTestFile");
		
		System.out.println(t.listeMots().size());
		
		System.out.println(t.prefixe("m"));
		t.printHTML("trieTest",true);
	}
}
