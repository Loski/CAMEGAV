

fusion(PatriciaTrie t, PatriciaTrie t2)
	if(t == null OR t.estVide())
		return t2
	else if(t2 == null OR t2.estVide())
		return t
	for i allant de 0 à 128
		t.node[i] = Node.fusion(t.node[i], t2.node[i])
	return t

fusion(Node n, Node n2)
	if(n == null)
		return n2
	else if(n2 == null)
		return n
	prefixe_max = taillePrefixe(n.key, n2.key)
	if (prefixe_max == n.key.length AND prefixe_max == n2.key.length)
		if(t.key.isLeaf)
			return n;
	else
		n.sousArbre = addPrefix(n.sousArbre, prefixe)
		n.key = n.key[:prefixe_max]
		n2.key = n2.key[:prefixe_max]
		n2.sousArbre = addPrefix(n2.sousArbre, prefixe)
	n.sousArbre = PatriciaTrie.fusion(n.sousArbre,n2.sousArbre)
	return n

addPrefix(PatriciaTrie t, String prefixe)
	if(prefixe.isEmpty())
		return t;
	else if(t == null || t.isEmpty())
		return PatriciaTrie().insererMot(prefixe)
	else
		buffer = PatriciaTrie()
		index = ASCII(prefixe[0])
		buffer.node[index] = Node(prefixe)
		buffer.sousArbre = t
		return buffer


int profondeur = 0;
int profondeur_total;

Pour cq node n do{
	int recur = 0;
	while(n!=null){
		recur++;
		Pour cq node in n do{
			if node != null
				profondeur++
	}
		n = n.getLink()
	}
}