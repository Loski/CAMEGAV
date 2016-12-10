package trie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PatriciaTest {
	public final static  String FILE = "jUnitTestFile";

	public static void main(String[] args) {
		PatriciaTrie p = new PatriciaTrie();
		p.insertionListeMot(Interface.lectureFichier("test"));
		p.printHTML("Switch", true);
		p.conversion().printHTML("michel", true
				);
	}

}
