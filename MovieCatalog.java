package application;

//Ameer Qadadha - 1221147
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;

public class MovieCatalog {
	private AVLTree[] table;
	private int storage;

	public MovieCatalog() {
		this.storage = findNextPrime(3);
		table = new AVLTree[this.storage];
		allocate(this.storage);
	}

	private int findNextPrime(int num) {
		while (!isPrime(num)) {
			num++;
		}
		return num;
	}

	private boolean isPrime(int num) {
		if (num <= 1)
			return false;
		if (num <= 3)
			return true;
		if (num % 2 == 0 || num % 3 == 0)
			return false;
		for (int i = 5; i * i <= num; i += 6) {
			if (num % i == 0 || num % (i + 2) == 0) {
				return false;
			}
		}
		return true;
	}

	private void allocate(int size) {
		table = new AVLTree[size];
		for (int i = 0; i < size; i++) {
			table[i] = new AVLTree();
		}
	}

	private int hashFunction(String title) {
		if (title == null || title.isEmpty()) {
			return 0;
		}
		title = title.toLowerCase();
		int hash = (int) title.charAt(0);
		return Math.abs(hash % storage);
	}

	public void put(Movie movie) {
		int index = hashFunction(movie.getMovieTitle());
		AVLTree tree = table[index];
		if (tree.contains(movie.getMovieTitle())) {
			tree.delete(movie.getMovieTitle());
		}
		table[index].insert(movie);
		double avgHeight = averageHeight();
		if (avgHeight > 3) {
			resizeTable();
		}
	}

	public Movie get(String title) {
		int index = hashFunction(title);
		if (table[index] == null) {
			return null;
		}
		return table[index].find(title);
	}

	public void erase(String title) {
		int index = hashFunction(title);
		if (table[index] != null) {
			table[index].delete(title);
		}
	}

	public void deallocate() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				clearAll(table[i].getRoot());
				table[i] = null;
			}
		}
	}

	private void clearAll(AVLNode node) {
		if (node != null) {
			clearAll(node.getLeft());
			clearAll(node.getRight());
			node.setLeft(null);
			node.setRight(null);
			node.setMovie(null);
		}
	}

	private double averageHeight() {
		int height = 0;
		int nonEmptyTrees = 0;
		for (AVLTree tree : table) {
			if (tree != null && tree.getRoot() != null) {
				height += tree.getHeight();
				nonEmptyTrees++;
			}
		}
		if (nonEmptyTrees == 0) {
			return 0;
		} else {
			return (double) height / nonEmptyTrees;
		}
	}

	private void resizeTable() {
		int newStorage = findNextPrime(storage * 2);
		AVLTree[] newTable = new AVLTree[newStorage];
		for (int i = 0; i < newStorage; i++) {
			newTable[i] = new AVLTree();
		}
		for (AVLTree tree : table) {
			if (tree != null) {
				rehashTree(tree, newTable, newStorage);
			}
		}
		table = newTable;
		storage = newStorage;
	}

	private void rehashTree(AVLTree tree, AVLTree[] newTable, int newStorage) {
		rehashNode(tree.getRoot(), newTable, newStorage);
	}

	private void rehashNode(AVLNode node, AVLTree[] newTable, int newStorage) {
		if (node != null) {
			int index = Math.abs(node.getMovie().getMovieTitle().charAt(0)) % newStorage;
			newTable[index].insert(node.getMovie());
			rehashNode(node.getLeft(), newTable, newStorage);
			rehashNode(node.getRight(), newTable, newStorage);
		}
	}

	public void refreshTableView(TableView<Movie> tableView) {
		tableView.getItems().clear();
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				traverseAdd(table[i].getRoot(), tableView);
			}
		}
	}

	private void traverseAdd(AVLNode node, TableView<Movie> tableView) {
		if (node != null) {
			traverseAdd(node.getLeft(), tableView);
			tableView.getItems().add(node.getMovie());
			traverseAdd(node.getRight(), tableView);
		}
	}

	public AVLNode searchByTitle(String title) {
		int index = hashFunction(title.toLowerCase());
		if (table[index] != null) {
			return find(table[index].getRoot(), title);
		}
		return null;
	}

	private AVLNode find(AVLNode node, String title) {
		if (node == null) {
			return null;
		}
		if (title.equalsIgnoreCase(node.getMovie().getMovieTitle())) {
			return node;
		}
		AVLNode leftResult = find(node.getLeft(), title);
		if (leftResult != null) {
			return leftResult;
		}
		return find(node.getRight(), title);
	}

	public String searchByYear(int year) {
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null) {
				findYear(table[i].getRoot(), year, result);
			}
		}
		return result.toString();
	}

	private void findYear(AVLNode node, int year, StringBuilder result) {
		if (node == null) {
			return;
		}
		findYear(node.getLeft(), year, result);
		if (node.getMovie().getReleaseYear() == year) {
			result.append(node.getMovie().toString()).append("\n\n");
		}
		findYear(node.getRight(), year, result);
	}

	public void index() {
		for (int i = 0; i < table.length; i++) {
			if (table[i] != null && table[i].getRoot() != null) {
				printinindex(i);
			}
		}
	}

	private void printinindex(int index) {
		AVLTree currentTree = table[index];
		if (currentTree != null) {
			StringBuilder movies = new StringBuilder();
			traversee(currentTree.getRoot(), movies);
			sorted("Index " + index + ": \n" + movies.toString());
		}
	}

	private void traversee(AVLNode node, StringBuilder movies) {
		if (node != null) {
			traversee(node.getLeft(), movies);
			movies.append(node.getMovie().toString()).append("\n\n");
			traversee(node.getRight(), movies);
		}
	}

	private void sorted(String sortedMovies) {
		TextArea txtarea = new TextArea(sortedMovies);
		txtarea.setEditable(false);
		txtarea.setPrefHeight(250);
		txtarea.setPrefWidth(400);
	}

	public String print(int index) {
		if (table[index] != null) {
			StringBuilder movies = new StringBuilder();
			traversee(table[index].getRoot(), movies);
			return "Index " + index + ": \n" + movies.toString();
		}
		return "Index " + index + " is empty.";
	}

	public Movie toprating(AVLTree tree) {
		if (tree == null || tree.getRoot() == null) {
			return null;
		} else {
			AVLNode node = topRatedNode(tree.getRoot());
			if (node != null) {
				return node.getMovie();
			} else {
				return null;
			}
		}
	}

	private AVLNode topRatedNode(AVLNode node) {
		if (node == null) {
			return null;
		}
		AVLNode left = topRatedNode(node.getLeft());
		AVLNode right = topRatedNode(node.getRight());
		AVLNode root = node;
		if (left != null && left.getMovie().getRating() > root.getMovie().getRating()) {
			root = left;
		}
		if (right != null && right.getMovie().getRating() > root.getMovie().getRating()) {
			root = right;
		}
		return root;
	}

	public Movie lowrating(AVLTree tree) {
		if (tree == null || tree.getRoot() == null) {
			return null;
		} else {
			AVLNode node = lowRatedNode(tree.getRoot());
			if (node != null) {
				return node.getMovie();
			} else {
				return null;
			}
		}
	}

	private AVLNode lowRatedNode(AVLNode node) {
		if (node == null) {
			return null;
		}
		AVLNode left = lowRatedNode(node.getLeft());
		AVLNode right = lowRatedNode(node.getRight());
		AVLNode root = node;
		if (left != null && left.getMovie().getRating() < root.getMovie().getRating()) {
			root = left;
		}
		if (right != null && right.getMovie().getRating() < root.getMovie().getRating()) {
			root = right;
		}
		return root;
	}

	public AVLTree[] getTable() {
		return table;
	}

	public void setTable(AVLTree[] table) {
		this.table = table;
	}

	public int getStorage() {
		return storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

}
