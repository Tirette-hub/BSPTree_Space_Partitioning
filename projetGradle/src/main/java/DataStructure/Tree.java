package DataStructure;

/**
 * Implémentation Java d'un arbre binaire récupérée de la partie "Implémentation" du cours de Structure de Données 2" sur Moodle (année 2021-2022)
 * @author Mme V. Bruyère
 *
 */

//Classe Tree decrivant un arbre binaire selon la definition recursive vue au cours
//L'arbre vide n'est pas represente par une reference vide, 
//mais par un "noeud vide", c'est-a-dire une feuille sans donnee

public class Tree<D> {
	private D data;
	protected Tree<D> left, right;
	
//constructeurs
	public Tree(D d, Tree<D> l, Tree<D> r) {
		data = d;
		left = l;
		right = r;
	}
	public Tree() {
		this(null,null,null);
	}

//get 
	public D getData() {
		return data;
	}
	public Tree<D> getLeft() {
		return left;
	}
	public Tree<D> getRight() {
		return right;
	}

//set
	public void setData(D d) {
		data = d;
	}
	public void setLeft(Tree<D> l) {
		left = l;
	}
	public void setRight(Tree<D> r) {
		right = r;
	}

//test de l'arbre vide, c'est-a-dire du "noeud vide"
	public boolean isEmpty() {
		if (data == null && left == null && right == null) 
			return true;
		else
			return false;
	}
	
//remplit un noeud vide avec la donnee d et 2 sous-arbres vides
	public void insertEmpty(D d) {
		data = d;
		left = new Tree();
		right = new Tree();
	}
	
//calcul de la hauteur
	public int height() {
		if (isEmpty()) 
			return 0;
		else
			return 1 + Math.max(left.height(),right.height());
	}

//affichage recursif
	public void print() {
		if (!isEmpty()) {
			left.print();
			System.out.println(data);
			right.print();
		}
	}	
}