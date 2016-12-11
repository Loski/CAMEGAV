package trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bench implements Runnable{
	
	private RTrie trie;
	private  int nombre_file;
	private ArrayList<String> liste_mot;
	
	public Bench(int type_trie, ArrayList<String> liste) {
		this.trie = (type_trie == Interface.PATRICIA_TRIE)? new PatriciaTrie() : new TrieHybride();
		this.liste_mot = liste;
	}
	
	public Bench(RTrie trie, ArrayList<String> liste) {
		this.trie = trie;
		this.liste_mot = liste;
	}
	
	
	@Override
	public void run() {
		this.trie.insertionListeMot(this.liste_mot);
	}

}
