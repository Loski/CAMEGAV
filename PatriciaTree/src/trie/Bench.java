package trie;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Bench implements Runnable{
	
	private RTrie trie;
	private  int nombre_file;
	private List<ArrayList<String>> inputString;
	
	public Bench(RTrie trie, String[] nomFichier) {
		inputString = new ArrayList<ArrayList<String>>();
		for(String file : nomFichier){
			inputString.add(Interface.lectureFichier(file));
		}
		this.trie = trie;
	}
	
	public Bench(int type_trie, String[] nomFichier) {
		inputString = new ArrayList<ArrayList<String>>();
		for(String file : nomFichier){
			inputString.add(Interface.lectureFichier(file));
		}
		this.inputString = Collections.synchronizedList(this.inputString);
		this.trie = (type_trie == Interface.PATRICIA_TRIE)? new PatriciaTrie() : new TrieHybride();
	}
	@Override
	public void run() {
		this.trie.insertionListeMot(this.inputString.remove(0));
	}

}
