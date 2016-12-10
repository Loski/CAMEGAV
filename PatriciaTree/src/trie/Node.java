package trie;

public class Node {
		private String key;
		private PatriciaTrie link;
		 public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public PatriciaTrie getLink() {
			return link;
		}
		public void setLink(PatriciaTrie link) {
			this.link = link;
		}
		public Node(String mot) {
			this.link = null;
			this.key = mot;
		}
		@Override
		public String toString() {
			String s = "";
			if(this.link != null)
				s+= this.link.toString();
			return "\t"+ key +  s;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((link == null) ? 0 : link.hashCode());
			return result;
		}
		
		public static Node fusion(Node original, Node copy){
			if(original == null)
				return copy;
			else if (copy == null){
				return original;
			}else{
				int prefixe = PatriciaTrie.prefixe(original.key, copy.key);
				if(prefixe == original.key.length() && prefixe == copy.key.length()){
					if(original.key.endsWith(PatriciaTrie.END_CHAR.toString())){  //Même mot final sur le noeud
						return original;
					}
					else{
						 //Même préfixe, on fusionne les fils.
					}
				}else{
						original.link =	PatriciaTrie.addPrefixEachWord(original.link, original.key.substring(prefixe));
						original.key = original.key.substring(0, prefixe);
						copy.link = PatriciaTrie.addPrefixEachWord(copy.link, copy.key.substring(prefixe));
						copy.key = copy.key.substring(0, prefixe);
				}
				original.link = PatriciaTrie.fusion(original.link, copy.link);
			}
			return original;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Node other = (Node) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (link == null) {
				if (other.link != null)
					return false;
			} else if (!link.equals(other.link))
				return false;
			return true;
		}
		public boolean isLeaf(){
			return key.endsWith(PatriciaTrie.END_CHAR.toString());
		}
		
		public RTrie conversion(){
			TrieHybride trie = new TrieHybride();
			if(this.isLeaf()){
				if(this.key.length() == 1)
					return null;
				trie.insererMot(this.key.substring(0, this.key.length()-1));
			}
			else if(this.getLink()!= null){
				trie.insererMot(this.key.substring(0, this.key.length()));
				TrieHybride tmp = trie;
				while(tmp.getEq()!= null){
					tmp = tmp.getEq();
				}
				trie.setEq((TrieHybride) this.getLink().conversion());
			}
			
			return trie;	
		}
}
