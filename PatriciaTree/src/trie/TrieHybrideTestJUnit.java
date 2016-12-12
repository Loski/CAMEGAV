package trie;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TrieHybrideTestJUnit {
	TrieHybride t1 = new TrieHybride();
	TrieHybride t2 = new TrieHybride();
	public final static String FILE = "jUnitTestFile";

	@Test
	public final void testPrefixeString() {
		t1.insererMot("ARBRE");
		t1.insererMot("VOITURE");
		t1.insererMot("VOITURAGE");
		t1.insererMot("VOIE");
		t1.insererMot("VOIX");
		assertEquals(2, t1.prefixe("VOIT"));
		assertEquals(1, t1.prefixe("VOIE"));
		assertEquals(4, t1.prefixe("VOI"));
		assertEquals(0, t1.prefixe("VOILO"));
	}

	@Test
	public final void testSuppression() {
		t1.insererMot("AREMOVE");
		t1.suppression("AREMOVE");
		assertEquals(t1, t2);
	}


	@Test
	public final void testInsererMot() {
		t1.insererMot("ARBRE");
		t1.insererMot("VOITURE");
		t1.insererMot("VOITURAGE");
		t1.insererMot("VOIE");
		t1.insererMot("VOIX");
		
		t2.insererMot("ARBRE");
		t2.insererMot("VOITURE");
		t2.insererMot("VOITURAGE");
		t2.insererMot("VOIE");
		t2.insererMot("VOIX");
		
		assertEquals(t1, t2);
	}

	@Test
	public final void testRechercherMot() {
		t1 = t1.lectureFichier(FILE);
		assertTrue(t1.recherche("professeur"));
		assertFalse(t1.recherche("professeur!"));
		assertFalse(t1.recherche("proffesseur!"));
		assertTrue(t1.recherche("dactylographie"));
	}

	@Test
	public final void testComptageMots() {
		assertEquals(TrieHybride.lectureFichier(FILE).comptageMots(), 36);
	}

	@Test
	public final void testComptageNil() {
		assertEquals(TrieHybride.lectureFichier(FILE).comptageNil(), 305);
	}

	@Test
	public final void testHauteur() {
		assertEquals(TrieHybride.lectureFichier(FILE).hauteur(), 18);
	}

	@Test
	public final void testConversionPatricia() {
		PatriciaTrie p = new PatriciaTrie();
		p.insertionListeMot(Interface.lectureFichier(FILE));		
		assertEquals(p.listeMots().size(), p.conversion().listeMots().size());
	}
	
	@Test
	public final void testConversionHybrid() {
		TrieHybride t = new TrieHybride();
		t.insertionListeMot(Interface.lectureFichier(FILE));		
		assertEquals(t.listeMots().size(), t.conversion().listeMots().size());
	}

	@Test
	public final void testIsEmpty() {
		assertTrue(new TrieHybride().estVide());
	}
}
