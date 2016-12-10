package trie;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Ignore;
import org.junit.Test;

public class PatriciaTrieTest {
	PatriciaTrie patricia1 = new PatriciaTrie();
	public PatriciaTrie patricia2 = new PatriciaTrie();
	public final static String FILE = "jUnitTestFile";
	public final static String SH1 = "shakespeare\\1henryiv";
	public final static String SH2 = "shakespeare\\othello";
	public final static String SHF = "shakespeare\\fusion";
	
	
	@Test
	public final void testFusion(){
		assertEquals(PatriciaTrie.fusion(PatriciaTrie.lectureFichier(SH1), PatriciaTrie.lectureFichier(SH2)), PatriciaTrie.lectureFichier(SHF));
		assertFalse((PatriciaTrie.fusion(PatriciaTrie.lectureFichier(FILE), PatriciaTrie.lectureFichier(SH2))).equals(PatriciaTrie.lectureFichier(SHF)));
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
		patricia1 = patricia1.lectureFichier(FILE);
		assertTrue(patricia1.recherche("professeur"));
		assertFalse(patricia1.recherche("professeur!"));
		assertFalse(patricia1.recherche("proffesseur!"));
		assertTrue(patricia1.recherche("dactylographie"));
	}

	@Test
	public final void testComptageMots() {
		assertEquals(PatriciaTrie.lectureFichier(FILE).comptageMots(), 36);
	}

	@Test
	public final void testComptageNil() {
		assertEquals(PatriciaTrie.lectureFichier(FILE).comptageNil(), 36);
	}

	@Test
	public final void testHauteur() {
		assertEquals(PatriciaTrie.lectureFichier(FILE).hauteur(), 4);
	}

	@Test
	public final void testProfondeurMoyenne() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testConversionPatricia() {
		RTrie p = new PatriciaTrie();
		p.insertionListeMot(Interface.lectureFichier(FILE));		
		assertEquals(p.listeMots(), p.conversion().listeMots());
	}
	
	@Ignore
	public final void testConversionHybrid() {
		RTrie p = new TrieHybride();
		p.insertionListeMot(Interface.lectureFichier(FILE));		
		assertEquals(p.listeMots(), p.conversion().listeMots());
	}

	@Test
	public final void testIsEmpty() {
		assertTrue(new PatriciaTrie().estVide());
	}
}
