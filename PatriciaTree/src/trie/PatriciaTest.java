package trie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatriciaTest {
	public final static  String FILE = "jUnitTestFile";

	public static void main(String[] args) {
		RTrie p = new PatriciaTrie();
		p.insererMot("MAGICIEN");
		p.insererMot("MAGICIENNE");
		p.printHTML("dl", true);
	}
}
