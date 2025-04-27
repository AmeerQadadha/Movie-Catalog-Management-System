package application;

//Ameer Qadadha - 1221147
public class AVLNode {

	private Movie movie;
	private AVLNode left;
	private AVLNode right;
	private int height;

	public AVLNode(Movie movie) {
		this.movie = movie;
	}

	public AVLNode(Movie movie, AVLNode left, AVLNode right) {
		this.movie = movie;
		this.left = left;
		this.right = right;
		this.height = 0;
	}

	public AVLNode getLeft() {
		return left;
	}

	public void setLeft(AVLNode left) {
		this.left = left;
	}

	public AVLNode getRight() {
		return right;
	}

	public void setRight(AVLNode right) {
		this.right = right;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		return "AVLNode [movie=" + movie + "]";
	}

}
