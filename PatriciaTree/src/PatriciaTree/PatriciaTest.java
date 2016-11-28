package PatriciaTree;

public class PatriciaTest {

	public static void main(String[] args) {
		PatriciaTree p = new PatriciaTree("Vampire"+PatriciaTree.END_CHAR);
		
		PatriciaTree f = PatriciaTree.lectureFichier("jUnitTestFile");
		f.printHTML("arbre");
		System.out.println(f.listeMots(""));
		System.out.println(f.comptageNil());
		System.out.println(p.comptageMots());
		p.printHTML("hello");
		System.out.println(f.hauteur());
		System.out.println((int)'\0');
	}

}
