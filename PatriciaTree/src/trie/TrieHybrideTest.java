package trie;

import java.util.ArrayList;

public class TrieHybrideTest {

	public static void main(String[] args) {
		
		TrieHybride t = TrieHybride.lectureFichier("jUnitTestFile");
		System.out.println("Trie H" +  t.listeMots().size());
		//System.out.println(PatriciaTrie.lectureFichier("jUnitTestFile").printHTML("PatriciaTrie"));;
		PatriciaTrie.lectureFichier("jUnitTestFile").printHTML("PatriciaTrie");
		TrieHybride.lectureFichier("jUnitTestFile").printHTML("TrieHybride", true);
		TrieHybride tt = TrieHybride.lectureFichier("jUnitTestFile");
		tt.suppression("michel");
		tt.printHTML("tutut");
		System.out.println(t.prefixe("m"));
		t.printHTML("trieTest",true);
	}
}
