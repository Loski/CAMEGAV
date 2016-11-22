package PatriciaTree;

public class PatriciaTest {

	public static void main(String[] args) {
		PatriciaTree p = PatriciaTree.lectureFichier("test");
		System.out.println(p);
		p.printHTML();
		System.out.println(p.ListeMot().toString() + "\n" +p.comptageMots());
		System.out.println(p.prefixe("dactylo"));
	}

}
