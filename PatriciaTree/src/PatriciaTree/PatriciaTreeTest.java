package PatriciaTree;

import static org.junit.Assert.*;

import org.junit.Test;

public class PatriciaTreeTest {
	PatriciaTree patricia = new PatriciaTree();
	public final String FILE = "jUnitTestFile";
	
	@Test
	public final void testPatriciaTree() {
		assertEquals(patricia.insererMot("ARBRE"), new PatriciaTree("ARBRE"+ PatriciaTree.END_CHAR));
	}

	@Test
	public final void testIndexGetKey() {
		assertEquals(PatriciaTree.getIndexKey("a"), 'a'- PatriciaTree.MIN_ASCII);
	}

	@Test
	public final void testPrefixeStringString() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testSuppression() {
		assertEquals(patricia.insererMot("AREMOVE").suppression("AREMOVE"), new PatriciaTree());
	}

	@Test
	public final void testNodeRemontable() {
		fail("Not yet implemented"); // TODO
	}


	@Test
	public final void testListeMots() {
		fail("Not yet implemented"); // TODO
	}


	@Test
	public final void testInsererMot() {
		assertEquals(patricia.insererMot("ARBRE").insererMot("VOITURE").insererMot("VOITURAGE").insererMot("VOIE").insererMot("VOIX"),
				new PatriciaTree("ARBRE"+ PatriciaTree.END_CHAR).insererMot("VOIX").insererMot("VOITURAGE").insererMot("VOIE").insererMot("VOITURE"));
	}

	@Test
	public final void testInsert() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInsererPhrase() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testInsererListeMots() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testRechercherMot() {
		patricia = patricia.lectureFichier(FILE);
		assertTrue(patricia.rechercherMot("professeur"));
		assertFalse(patricia.rechercherMot("professeur!"));
		assertFalse(patricia.rechercherMot("proffesseur!"));
		assertTrue(patricia.rechercherMot("dactylographie"));
	}

	@Test
	public final void testComptageMots() {
		assertEquals(PatriciaTree.lectureFichier(FILE).comptageMots(), 36);
	}

	@Test
	public final void testComptageNil() {
		assertEquals(PatriciaTree.lectureFichier(FILE).comptageNil(), 36);
	}

	@Test
	public final void testHauteur() {
		assertEquals(PatriciaTree.lectureFichier(FILE).hauteur(), 4);
	}

	@Test
	public final void testProfondeurMoyenne() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testConversion() {
		fail("Not yet implemented"); // TODO
	}

	@Test
	public final void testIsEmpty() {
		assertTrue(new PatriciaTree().isEmpty());
	}

	@Test
	public final void testPrefixeString() {
		fail("Not yet implemented"); // TODO
	}

}
