package PatriciaTree;

public interface RTrie {
	public RTrie TrieVide();
	public boolean estVide();
	public String val();
	public RTrie sousArbre(int i);
	public RTrie filsSauf(int i);
}
