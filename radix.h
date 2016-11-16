#define FILS_GAUCHE 0
#define FILS_DROITE 1

typedef struct Node
{
	char value;
	Node * fils_gauche;
	Node * fils_droite;
}Node;


void insertion(Node *t,char[] mot);
char[] suppression(Node * t, char[] mot);
bool search(Node *t, char []);
bool isLeaf(Node *t)