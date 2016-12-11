package trie;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

public class PatriciaTrieTest {
	PatriciaTrie patricia1 = new PatriciaTrie();
	public PatriciaTrie patricia2 = new PatriciaTrie();
	public final static String FILE = "jUnitTestFile.txt";
	public final static String SH1 = "shakespeare\\1henryiv.txt";
	public final static String SH2 = "shakespeare\\othello.txt";
	public final static String SHF = "fusion.txt";
	
	
	@Test
	public final void testFusion(){
		patricia1.insertionListeMot(Interface.lectureFichier(SH1));
		patricia2.insertionListeMot(Interface.lectureFichier(SH2));
		PatriciaTrie patriciaF = new PatriciaTrie();
		patriciaF.insertionListeMot(Interface.lectureFichier(SHF));
		assertEquals(PatriciaTrie.fusion(patricia1, patricia2), patriciaF);
		patricia1 = new PatriciaTrie();
		patricia1.insertionListeMot(Interface.lectureFichier(FILE));
		assertFalse(PatriciaTrie.fusion(patricia1, patricia2).equals(patriciaF));
	}
	@Test
	public final void testIndexGetKey() {
		assertEquals(PatriciaTrie.getIndexKey("a"), 'a');
	}

	@Test
	public final void testPrefixeString() {
		patricia1.insererMot("ARBRE");
		patricia1.insererMot("VOITURE");
		patricia1.insererMot("VOITURAGE");
		patricia1.insererMot("VOIE");
		patricia1.insererMot("VOIX");
		assertEquals(2, patricia1.prefixe("VOIT"));
		assertEquals(1, patricia1.prefixe("VOIE"));
		assertEquals(4, patricia1.prefixe("VOI"));
		assertEquals(0, patricia1.prefixe("VOILO"));
	}

	@Test
	public final void testSuppression() {
		patricia1.insererMot("AREMOVE");
		patricia1.suppression("AREMOVE");
		assertEquals(patricia1, patricia2);
	}


	@Test
	public final void testInsererMot() {
		patricia1.insererMot("ARBRE");
		patricia1.insererMot("VOITURE");
		patricia1.insererMot("VOITURAGE");
		patricia1.insererMot("VOIE");
		patricia1.insererMot("VOIX");
		
		patricia2.insererMot("ARBRE");
		patricia2.insererMot("VOITURE");
		patricia2.insererMot("VOITURAGE");
		patricia2.insererMot("VOIE");
		patricia2.insererMot("VOIX");
		
		assertEquals(patricia1, patricia2);
	}

	@Test
	public final void testInsert() {
		patricia1.insererMot("Salut");
		assertEquals(new PatriciaTrie("Salut"+ PatriciaTrie.END_CHAR), patricia1);
	}


	@Test
	public final void testRechercherMot() {
		patricia1.insertionListeMot(Interface.lectureFichier(FILE));
		assertTrue(patricia1.recherche("professeur"));
		assertFalse(patricia1.recherche("professeur!"));
		assertFalse(patricia1.recherche("proffesseur!"));
		assertTrue(patricia1.recherche("dactylographie"));
	}

	@Test
	public final void testComptageMots() {
		patricia1.insertionListeMot(Interface.lectureFichier(FILE));
		assertEquals(patricia1.comptageMots(), 36);
	}

	@Test
	public final void testComptageNil() {
		patricia1.insertionListeMot(Interface.lectureFichier(FILE));
		assertEquals(patricia1.comptageNil(), 1906);
	}

	@Test
	public final void testHauteur() {
		patricia1.insertionListeMot(Interface.lectureFichier(FILE));
		assertEquals(patricia1.hauteur(), 4);
	}

	@Test
	public final void testProfondeurMoyenne() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testConversionPatricia() {
		patricia1.insertionListeMot(Interface.lectureFichier(SH1));	
		assertEquals(patricia1.listeMots(), patricia1.conversion().listeMots());
	}
	
	@Test
	public final void testConversionHybrid() {
		RTrie p = new TrieHybride();
		p.insertionListeMot(Interface.lectureFichier(FILE));		
		assertEquals(p.listeMots(), p.conversion().listeMots());
	}

}
