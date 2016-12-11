package trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bench extends Thread implements Runnable{
	
	private PatriciaTrie trie;
	private  int nombre_file;
	private ArrayList<String> liste_mot;
	
	public Bench(int type_trie, ArrayList<String> liste) {
		this.trie = new PatriciaTrie();
		this.liste_mot = liste;
	}
	
	public Bench(RTrie trie, ArrayList<String> liste) {
		this.trie = new PatriciaTrie();
		this.liste_mot = liste;
	}
	
	
	@Override
	public void run() {
		this.trie.insertionListeMot(this.liste_mot);
	}

	public PatriciaTrie getTrie() {
		return trie;
	}


}
