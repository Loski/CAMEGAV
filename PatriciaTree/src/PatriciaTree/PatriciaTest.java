package PatriciaTree;

public class PatriciaTest {
	public final static  String FILE = "jUnitTestFile";

	public static void main(String[] args) {
		PatriciaTree p = new PatriciaTree("Vampire"+PatriciaTree.END_CHAR);
		p.insererMot("Vampirer");
		p.printHTML("michel");
		p.suppression("Vampirer");
		p.printHTML("Arbre");
		PatriciaTree.fusion(PatriciaTree.lectureFichier("FUSIONA"), PatriciaTree.lectureFichier("FUSIONB")).printHTML("FUSION");
		/*PatriciaTree f = PatriciaTree.lectureFichier("jUnitTestFile"); 
		f.printHTML("arbre");
		System.out.println(f.listeMots(""));
		System.out.println(f.comptageNil());
		System.out.println(p.comptageMots());
		p.printHTML("hello");
		System.out.println(f.hauteur());
		System.out.println((int)'\0');*/
	}

}
