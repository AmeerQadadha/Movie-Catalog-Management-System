package application;

//Ameer Qadadha - 1221147
public class AVLTree {

	private AVLNode root;

	public AVLTree() {
		root = null;
	}

	public int height(AVLNode e) {
		if (e == null)
			return -1;
		return e.getHeight();
	}

	public int getHeight() {
		return height(root);
	}

	public AVLNode rotateWithLeftChild(AVLNode k2) {
		AVLNode k1 = k2.getLeft();
		k2.setLeft(k1.getRight());
		k1.setRight(k2);
		k2.setHeight(Math.max(height(k2.getLeft()), height(k2.getRight())) + 1);
		k1.setHeight(Math.max(height(k1.getLeft()), k2.getHeight()) + 1);
		return k1;
	}

	public AVLNode rotateWithRightChild(AVLNode k1) {
		AVLNode k2 = k1.getRight();
		k1.setRight(k2.getLeft());
		k2.setLeft(k1);
		k1.setHeight(Math.max(height(k1.getLeft()), height(k1.getRight())) + 1);
		k2.setHeight(Math.max(height(k2.getRight()), k1.getHeight()) + 1);
		return k2;
	}

	public AVLNode DoubleWithLeftChild(AVLNode k3) {
		k3.setLeft(rotateWithRightChild(k3.getLeft()));
		return rotateWithLeftChild(k3);
	}

	public AVLNode DoubleWithRightChild(AVLNode k1) {
		k1.setRight(rotateWithLeftChild(k1.getRight()));
		return rotateWithRightChild(k1);
	}

	public void insert(Movie movie) {
		root = insert(movie, root);
	}

	private AVLNode insert(Movie movie, AVLNode current) {
		if (current == null) {
			return new AVLNode(movie);
		}

		int comparison = movie.getMovieTitle().compareTo(current.getMovie().getMovieTitle());
		if (comparison < 0) {
			current.setLeft(insert(movie, current.getLeft()));
		} else if (comparison > 0) {
			current.setRight(insert(movie, current.getRight()));
		} else {

			return current;
		}

		current.setHeight(Math.max(height(current.getLeft()), height(current.getRight())) + 1);
		return balance(current);
	}

	public void delete(String title) {
		root = delete(root, title);
	}

	private AVLNode delete(AVLNode node, String title) {
		if (node == null) {
			return null;
		}

		int compareResult = title.compareTo(node.getMovie().getMovieTitle());
		if (compareResult < 0) {
			node.setLeft(delete(node.getLeft(), title));
		} else if (compareResult > 0) {
			node.setRight(delete(node.getRight(), title));
		} else {
			// This is the node to be deleted
			if (node.getLeft() != null && node.getRight() != null) {
				// Replace with the smallest in the right subtree
				AVLNode min = findMin(node.getRight());
				node.setMovie(min.getMovie());
				node.setRight(delete(node.getRight(), min.getMovie().getMovieTitle()));
			} else {
				node = (node.getLeft() != null) ? node.getLeft() : node.getRight();
			}
		}
		return balance(node);
	}

	private AVLNode findMin(AVLNode node) {
		while (node.getLeft() != null) {
			node = node.getLeft();
		}
		return node;
	}

	private AVLNode balance(AVLNode node) {
		if (node == null) {
			return node;
		}
		int balanceFactor = height(node.getLeft()) - height(node.getRight());
		if (balanceFactor > 1) {
			if (height(node.getLeft().getLeft()) >= height(node.getLeft().getRight())) {
				node = rotateWithLeftChild(node);
			} else {
				node = DoubleWithLeftChild(node);
			}
		} else if (balanceFactor < -1) {
			if (height(node.getRight().getRight()) >= height(node.getRight().getLeft())) {
				node = rotateWithRightChild(node);
			} else {
				node = DoubleWithRightChild(node);
			}
		}
		node.setHeight(Math.max(height(node.getLeft()), height(node.getRight())) + 1);
		return node;
	}

	public Movie find(String title) {
		AVLNode resultNode = find(root, title);
		if (resultNode != null) {
			return resultNode.getMovie();
		}
		return null;
	}

	private AVLNode find(AVLNode node, String title) {
		if (node == null) {
			return null;
		}
		int compareResult = title.compareTo(node.getMovie().getMovieTitle());

		if (compareResult < 0) {
			return find(node.getLeft(), title);
		} else if (compareResult > 0) {
			return find(node.getRight(), title);
		} else {
			return node;
		}
	}

	public boolean contains(String title) {
		return contains(title, root);
	}

	private boolean contains(String title, AVLNode current) {
		if (current == null)
			return false;
		int comparison = title.compareTo(current.getMovie().getMovieTitle());
		if (comparison < 0)
			return contains(title, current.getLeft());
		else if (comparison > 0)
			return contains(title, current.getRight());
		return true;
	}

	public AVLNode getRoot() {
		return root;
	}

	public void setRoot(AVLNode root) {
		this.root = root;
	}

}
