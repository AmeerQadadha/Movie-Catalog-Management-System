package application;

//Ameer Qadadha - 1221147
public class Movie {
	private String movieTitle;
	private String description;
	private int releaseYear;
	private double rating;

	public Movie() {

	}

	public Movie(String movietitle, String description, int releaseYear, double rating) {
		this.movieTitle = movietitle;
		this.description = description;
		this.releaseYear = releaseYear;
		this.rating = rating;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getReleaseYear() {
		return releaseYear;
	}

	public void setReleaseYear(int releaseYear) {
		this.releaseYear = releaseYear;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		if (rating >= 0.0 && rating <= 10.0) {
			this.rating = rating;
		} else {

		}
	}

	@Override
	public String toString() {
		return "Movie Title=" + movieTitle + "\nDescription=" + description + "\nRelease Year=" + releaseYear
				+ "\nRating=" + rating + "";
	}

}
