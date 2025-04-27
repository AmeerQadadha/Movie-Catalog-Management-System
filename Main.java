package application;

//Ameer Qadadha - 1221147
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main extends Application {
	private MovieCatalog catalog;
	private static final String menustyle = "-fx-font-family: 'Serif'; -fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: black;";
	private static final String titlestyle = "-fx-font-family: 'Serif'; -fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white;"
			+ " -fx-font-style: italic; -fx-underline: true;";
	private static final String textstyle = "-fx-font-family: 'Serif';-fx-font-size: 17px; -fx-font-weight: bold; -fx-text-fill: black;";
	private static final String notestyle = "-fx-font-family: 'Serif';-fx-font-size: 15px; -fx-font-weight: bold; -fx-text-fill: white;";
	public int currentIndex = 0;
	TableView<Movie> tableView = new TableView<>();

	public void start(@SuppressWarnings("exports") Stage primaryStage) {
		try {
			if (catalog == null) {
				catalog = new MovieCatalog();
			}
			BorderPane root = new BorderPane();
			Label title1 = new Label("Movie Catalog Management System");
			title1.setStyle(titlestyle);
			MenuBar menubar = new MenuBar();
			ImageView imageMenu = new ImageView(new Image(getClass().getResource("movie.png").toExternalForm()));
			imageMenu.setFitWidth(20);
			imageMenu.setFitHeight(20);
			Menu menu1 = new Menu("Movie Menu", imageMenu);

			ImageView imageView = new ImageView(new Image(getClass().getResource("plus.png").toExternalForm()));
			imageView.setFitWidth(20);
			imageView.setFitHeight(20);
			MenuItem add = new MenuItem("Add Movie", imageView);

			ImageView imageView2 = new ImageView(new Image(getClass().getResource("update.png").toExternalForm()));
			imageView2.setFitWidth(20);
			imageView2.setFitHeight(20);
			MenuItem update = new MenuItem("Update Movie", imageView2);

			ImageView imageView3 = new ImageView(new Image(getClass().getResource("delete.png").toExternalForm()));
			imageView3.setFitWidth(20);
			imageView3.setFitHeight(20);
			MenuItem delete = new MenuItem("Delete Movie", imageView3);

			ImageView imageView4 = new ImageView(new Image(getClass().getResource("search.png").toExternalForm()));
			imageView4.setFitWidth(20);
			imageView4.setFitHeight(20);
			MenuItem search = new MenuItem("Search Movie", imageView4);

			ImageView imageView5 = new ImageView(new Image(getClass().getResource("desc.png").toExternalForm()));
			imageView5.setFitWidth(20);
			imageView5.setFitHeight(20);
			MenuItem print = new MenuItem("Print sorted", imageView5);

			ImageView imageView6 = new ImageView(new Image(getClass().getResource("asc.png").toExternalForm()));
			imageView6.setFitWidth(20);
			imageView6.setFitHeight(20);
			MenuItem printTop = new MenuItem("Print Top and Least Ranked Movies", imageView6);

			menu1.getItems().addAll(add, update, delete, search, print, printTop);

			ImageView imageFile = new ImageView(new Image(getClass().getResource("file.png").toExternalForm()));
			imageFile.setFitWidth(20);
			imageFile.setFitHeight(20);
			Menu menu2 = new Menu("File Menu", imageFile);

			ImageView imageView7 = new ImageView(new Image(getClass().getResource("open.png").toExternalForm()));
			imageView7.setFitWidth(20);
			imageView7.setFitHeight(20);
			MenuItem open = new MenuItem("Open File", imageView7);

			ImageView imageView8 = new ImageView(new Image(getClass().getResource("save.png").toExternalForm()));
			imageView8.setFitWidth(20);
			imageView8.setFitHeight(20);
			MenuItem save = new MenuItem("Save File", imageView8);

			ImageView imageView10 = new ImageView(new Image(getClass().getResource("clear.png").toExternalForm()));
			imageView10.setFitWidth(20);
			imageView10.setFitHeight(20);
			MenuItem clear = new MenuItem("Deallocate", imageView10);

			ImageView imageView9 = new ImageView(new Image(getClass().getResource("exit.png").toExternalForm()));
			imageView9.setFitWidth(20);
			imageView9.setFitHeight(20);
			MenuItem exit = new MenuItem("Exit System", imageView9);

			menu2.getItems().addAll(open, save, clear, exit);
			menubar.getMenus().addAll(menu1, menu2);
			menubar.setStyle(menustyle);

			TableColumn<Movie, String> titlecolumn = new TableColumn<>("Movie Title");
			titlecolumn.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
			titlecolumn.setPrefWidth(180);

			TableColumn<Movie, String> desccolumn = new TableColumn<>("Description");
			desccolumn.setCellValueFactory(new PropertyValueFactory<>("description"));
			desccolumn.setPrefWidth(300);

			TableColumn<Movie, Integer> yearcolumn = new TableColumn<>("Release Year");
			yearcolumn.setCellValueFactory(new PropertyValueFactory<>("releaseYear"));
			yearcolumn.setPrefWidth(150);

			TableColumn<Movie, Double> ratingcolumn = new TableColumn<>("Rating");
			ratingcolumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
			ratingcolumn.setPrefWidth(100);

			tableView.getColumns().addAll(List.of(titlecolumn, desccolumn, yearcolumn, ratingcolumn));
			tableView.setMaxWidth(800);
			tableView.setMaxHeight(500);

			open.setOnAction(e -> {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Movies File");
				File file = fileChooser.showOpenDialog(primaryStage); // Select the movies.txt file
				if (file != null) {
					try (Scanner scanner = new Scanner(file)) {
						while (scanner.hasNextLine()) {
							String title = scanner.nextLine().trim();
							if (title.startsWith("Title:")) {
								title = title.substring(6).trim();
								String description = scanner.nextLine().trim();
								if (description.startsWith("Description:")) {
									description = description.substring(12).trim();
								}
								String releaseYearLine = scanner.nextLine().trim();
								int releaseYear = 0;
								if (releaseYearLine.startsWith("Release Year:")) {
									releaseYear = Integer.parseInt(releaseYearLine.substring(13).trim());
								}
								String ratingLine = scanner.nextLine().trim();
								double rating = 0.0;
								if (ratingLine.startsWith("Rating:")) {
									rating = Double.parseDouble(ratingLine.substring(7).trim());
								}
								if (scanner.hasNextLine()) {
									scanner.nextLine();
								}
								Movie movie = new Movie(title, description, releaseYear, rating);
								catalog.put(movie); 
								catalog.refreshTableView(tableView);
							}
						}
						Alert readData = new Alert(Alert.AlertType.INFORMATION);
						readData.setHeaderText("Success!");
						readData.setContentText("Movies were read and added successfully.");
						readData.showAndWait();

					} catch (FileNotFoundException ex) {
						ex.printStackTrace();
					} catch (NumberFormatException ex) {
						Alert errorAlert = new Alert(Alert.AlertType.ERROR);
						errorAlert.setHeaderText("Error Reading File");
						errorAlert.setContentText("Invalid format in file. Please check the data.");
						errorAlert.showAndWait();
					}
				}
			});

			Image backgroundImage = new Image(getClass().getResource("bgg.jpg").toExternalForm()); // background image
			BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false,
					true, true);
			BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
					BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
			root.setBackground(new Background(bgImage));

			save.setOnAction(e -> {
				if (tableView.getItems().isEmpty()) {
					Alert readData = new Alert(Alert.AlertType.ERROR);
					readData.setHeaderText("Invalid save process!");
					readData.setContentText("There are no movies to save.");
					readData.showAndWait();
					return;
				}
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
						for (Movie movie : tableView.getItems()) {
							writer.write(movie.toString());
							writer.write("\n\n");
						}
						Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
						successAlert.setHeaderText("Success!");
						successAlert.setContentText("All movies have been saved successfully.");
						successAlert.showAndWait();
					} catch (IOException ex) {
						Alert alert = new Alert(Alert.AlertType.ERROR);
						alert.setTitle("Error");
						alert.setHeaderText("Save failed");
						alert.showAndWait();
					}
				} else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setTitle("Warning");
					alert.setHeaderText("Save Cancelled");
					alert.showAndWait();
				}
			});

			clear.setOnAction(e -> {
				if (tableView.getItems().isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setTitle("Invalid");
					alert.setHeaderText("No movies to clear");
					alert.showAndWait();
					return;
				}
				Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
				confirmationAlert.setTitle("Confirm Deletion");
				confirmationAlert.setHeaderText("Are you sure you want to clear the catalog?");
				confirmationAlert.showAndWait().ifPresent(response -> {
					if (response == ButtonType.OK) {
						catalog.deallocate();
						catalog.refreshTableView(tableView);
					}
				});
			});

			exit.setOnAction(e -> {
				primaryStage.close();
			});

			add.setOnAction(e -> {
				Dialog<ButtonType> dialog = new Dialog<>();
				dialog.setTitle("Add Movie");

				TextField titleField = new TextField();
				titleField.setPromptText("Movie Title");

				TextField descriptionField = new TextField();
				descriptionField.setPromptText("Description");

				TextField releaseYearField = new TextField();
				releaseYearField.setPromptText("Release Year");

				TextField ratingField = new TextField();
				ratingField.setPromptText("Rating (0.0 - 10.0)");

				VBox dialogContent = new VBox(10, titleField, descriptionField, releaseYearField, ratingField);
				dialogContent.setPadding(new Insets(10));

				dialog.getDialogPane().setContent(dialogContent);

				ButtonType okButtonType = new ButtonType("ADD", ButtonBar.ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
				dialog.getDialogPane().setStyle(textstyle);
				dialog.getDialogPane().setBackground(new Background(bgImage));
				dialog.getDialogPane().setPrefWidth(400);
				dialog.getDialogPane().setPrefHeight(300);
				dialog.showAndWait().ifPresent(buttonType -> {
					if (buttonType == okButtonType) {
						if (titleField.getText().isEmpty() || descriptionField.getText().isEmpty()
								|| releaseYearField.getText().isEmpty() || ratingField.getText().isEmpty()) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Invalid Input");
							alert.setHeaderText(null);
							alert.setContentText("All fields must be filled out.");
							alert.showAndWait();
						} else {
							try {
								String title = titleField.getText().trim();
								String description = descriptionField.getText().trim();
								if (!title.matches("[a-zA-Z\\\\s]+") || !description.matches("[a-zA-Z\\\\s]+")) {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setTitle("Invalid Input");
									alert.setContentText("Title or Description cannot contain numbers");
									alert.showAndWait();
									return;
								}
								String releaseYear = releaseYearField.getText().trim();
								String rating = ratingField.getText().trim();

								if (releaseYear.matches(".*[a-zA-Z]+.*") || rating.matches(".*[a-zA-Z]+.*")) {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setTitle("Invalid Input");
									alert.setHeaderText(null);
									alert.setContentText("Release Year and Rating must be numeric only.");
									alert.showAndWait();
									return;
								}
								int year = Integer.parseInt(releaseYear);
								double rate = Double.parseDouble(rating);
								if (rate < 0.0 || rate > 10.0) {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setTitle("Invalid Input");
									alert.setContentText("Rating should be bigger than 0.0 and smaller than 10.0");
									alert.showAndWait();
									return;
								}
								if (year < 1900 || year > 2024) {
									Alert alert = new Alert(Alert.AlertType.ERROR);
									alert.setTitle("Invalid Input");
									alert.setContentText("Rating year should be between 1900-2024");
									alert.showAndWait();
									return;
								}
								Movie movie = new Movie(title, description, year, rate);
								tableView.getItems().add(movie);
								catalog.put(movie);
								Alert alert = new Alert(Alert.AlertType.INFORMATION);
								alert.setTitle("Success");
								alert.setContentText("Movie was inserted successfully");
								alert.showAndWait();
								return;
							} catch (NumberFormatException ex) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid Input");
								alert.setContentText("Please enter valid numeric values for release year and rating.");
								alert.showAndWait();
							} catch (IllegalArgumentException ex) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid Rating");
								alert.setContentText(ex.getMessage());
								alert.showAndWait();
							}
						}
					}
				});
			});
			update.setOnAction(e -> {
				Dialog<ButtonType> dialog = new Dialog<>();
				dialog.setTitle("Update Movie");

				ComboBox<String> comboBox = new ComboBox<>();
				comboBox.setPromptText("--Select the movie title--");
				for (Movie movie : tableView.getItems()) {
					comboBox.getItems().add(movie.getMovieTitle());
				}

				TextField titleField = new TextField();
				titleField.setPromptText("New Movie Title");

				TextField descriptionField = new TextField();
				descriptionField.setPromptText("New Description");

				TextField releaseYearField = new TextField();
				releaseYearField.setPromptText("New Release Year");

				TextField ratingField = new TextField();
				ratingField.setPromptText("New Rating (0.0 - 10.0)");

				VBox dialogContent = new VBox(10, comboBox, titleField, descriptionField, releaseYearField,
						ratingField);
				dialogContent.setPadding(new Insets(10));

				dialog.getDialogPane().setContent(dialogContent);

				ButtonType okButtonType = new ButtonType("UPDATE", ButtonBar.ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);
				dialog.getDialogPane().setStyle(textstyle);
				dialog.getDialogPane().setBackground(new Background(bgImage));
				dialog.getDialogPane().setPrefWidth(400);
				dialog.getDialogPane().setPrefHeight(300);
				dialog.showAndWait().ifPresent(buttonType -> {
					if (buttonType == okButtonType) {
						String selectedTitle = comboBox.getValue();
						if (selectedTitle == null || selectedTitle.isEmpty()) {
							Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a movie to update.");
							alert.showAndWait();
							return;
						}
						Movie movieUpdated = null;
						for (Movie movie : tableView.getItems()) {
							if (movie.getMovieTitle().equalsIgnoreCase(selectedTitle)) {
								movieUpdated = movie;
								break;
							}
						}
						String newTitle = titleField.getText().trim();
						String newDescription = descriptionField.getText().trim();
						String newReleaseYear = releaseYearField.getText().trim();
						String newRating = ratingField.getText().trim();

						if (descriptionField.getText().isEmpty()) {
							newDescription = movieUpdated.getDescription();
						}
						if (titleField.getText().isEmpty()) {
							newTitle = movieUpdated.getMovieTitle();
						}
						try {
							if (!newReleaseYear.isEmpty() && !newReleaseYear.matches("\\d+")) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid Input");
								alert.setContentText("Release Year must be an integer.");
								alert.showAndWait();
								return;
							}
							if (!newRating.isEmpty() && !newRating.matches("\\d+(\\.\\d+)?")) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid Input");
								alert.setContentText("Rating must be numeric.");
								alert.showAndWait();
								return;
							}
							int year;
							if (!releaseYearField.getText().isEmpty()) {
								year = Integer.parseInt(releaseYearField.getText().trim());
							} else {
								year = movieUpdated.getReleaseYear();
							}
							double rate;
							if (!ratingField.getText().isEmpty()) {
								rate = Double.parseDouble(ratingField.getText().trim());
							} else {
								rate = movieUpdated.getRating();
							}
							if (newTitle.matches(".*\\d+.*") || newDescription.matches(".*\\d+.*")) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid Input");
								alert.setContentText("Title or Description cannot contain numbers.");
								alert.showAndWait();
								return;
							}
							if (!validateRate(rate)) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid input");
								alert.setContentText("Rate should be between 0.0 and 10.0");
								alert.showAndWait();
								return;
							}
							if (!validateYear(year)) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setTitle("Invalid input");
								alert.setContentText("Year should be between 1900 and 2024");
								alert.showAndWait();
								return;
							}
							movieUpdated.setMovieTitle(newTitle);
							movieUpdated.setDescription(newDescription);
							movieUpdated.setReleaseYear(year);
							movieUpdated.setRating(rate);
							catalog.refreshTableView(tableView);
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setTitle("Success");
							alert.setContentText("Movie was updated successfully");
							alert.showAndWait();
						} catch (NumberFormatException ex) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Invalid Input");
							alert.setContentText("Please ensure numerical fields contain valid numbers.");
							alert.showAndWait();
						}
					}
				});
			});
			delete.setOnAction(e -> showDeleteDialog(null));

			search.setOnAction(e -> {
				Dialog<ButtonType> searchDialog = new Dialog<>();
				searchDialog.setTitle("Search Movie");

				TextField txtfield = new TextField();
				txtfield.setPromptText("Movie Title");

				TextField txtfield2 = new TextField();
				txtfield2.setPromptText("Release Date");
				txtfield.clear();
				txtfield2.clear();
				VBox dialogContent = new VBox(10, txtfield, txtfield2);
				dialogContent.setPadding(new Insets(10));
				searchDialog.getDialogPane().setContent(dialogContent);
				searchDialog.getDialogPane().setStyle(textstyle);
				searchDialog.getDialogPane().setBackground(new Background(bgImage));
				searchDialog.getDialogPane().setPrefWidth(400);
				searchDialog.getDialogPane().setPrefHeight(300);
				ButtonType okButtonType = new ButtonType("SEARCH", ButtonBar.ButtonData.OK_DONE);
				searchDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

				searchDialog.showAndWait().ifPresent(buttonType -> {
					if (buttonType == okButtonType) {
						if (!txtfield.getText().isEmpty() && !txtfield2.getText().isEmpty()) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Invalid operation");
							alert.setContentText("You can only fill one of the textfields");
							alert.showAndWait();
							return;
						}
						if (txtfield.getText().isEmpty() && txtfield2.getText().isEmpty()) {
							Alert alert = new Alert(Alert.AlertType.ERROR);
							alert.setTitle("Invalid input");
							alert.setContentText("Empty textfields");
							alert.showAndWait();
							return;
						}

						if (!txtfield.getText().isEmpty()) {
							String title = txtfield.getText().trim();
							AVLNode node = catalog.searchByTitle(title);
							if (node == null) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setHeaderText("Error");
								alert.setContentText(title + " was not found");
								alert.showAndWait();
								return;
							} else {
								Dialog<Void> resultDialog = new Dialog<>();
								resultDialog.setTitle("Search Result");
								resultDialog.getDialogPane().setStyle(textstyle);
								TextArea txtarea = new TextArea(node.toString());
								txtarea.setEditable(false);
								txtarea.setPrefSize(800, 400);
								resultDialog.getDialogPane().setContent(txtarea);
								ButtonType closeButtonType = new ButtonType("CLOSE", ButtonBar.ButtonData.CANCEL_CLOSE);
								resultDialog.getDialogPane().getButtonTypes().add(closeButtonType);
								resultDialog.show();
								return;
							}
						}
						if (!txtfield2.getText().isEmpty()) {
							String date = txtfield2.getText().trim();
							if (!date.matches("\\d+")) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setHeaderText("Error");
								alert.setContentText("Release year should only contain numbers");
								alert.showAndWait();
								return;
							}
							Integer Rdate = Integer.parseInt(date);
							String resultText = catalog.searchByYear(Rdate);
							if (resultText.isEmpty()) {
								Alert alert = new Alert(Alert.AlertType.ERROR);
								alert.setHeaderText("Error");
								alert.setContentText(Rdate + " was not found");
								alert.showAndWait();
							} else {
								Dialog<Void> resultDialog = new Dialog<>();
								resultDialog.setTitle("Search Result");
								resultDialog.getDialogPane().setStyle(textstyle);
								TextArea txtarea = new TextArea(resultText);
								txtarea.setEditable(false);
								txtarea.setPrefSize(800, 400);
								resultDialog.getDialogPane().setContent(txtarea);
								ButtonType closeButtonType = new ButtonType("CLOSE", ButtonBar.ButtonData.CANCEL_CLOSE);
								resultDialog.getDialogPane().getButtonTypes().add(closeButtonType);
								resultDialog.show();
							}
						}
					}
				});
			});

			print.setOnAction(e -> {
				Dialog<Void> dialog = new Dialog<>();
				dialog.setTitle("Print Sorted Movies");
				dialog.setHeaderText("Sorted Movies List");

				VBox dialogVBox = new VBox(10);
				dialogVBox.setAlignment(Pos.CENTER_LEFT);
				dialogVBox.setPadding(new Insets(20));

				TextArea txtarea = new TextArea();
				txtarea.setEditable(false);
				txtarea.setPrefHeight(250);
				txtarea.setPrefWidth(400);
				txtarea.setStyle(textstyle);

				Button next = new Button("Next movie");
				Button prev = new Button("Previous movie");
				Button refresh = new Button("Refresh");
				refresh.setStyle(textstyle);
				HBox hbox = new HBox(10);
				hbox.getChildren().addAll(prev, next);
				hbox.setAlignment(Pos.BASELINE_CENTER);

				ComboBox<String> comboBox = new ComboBox<>();
				comboBox.getItems().addAll("Ascending", "Descending");
				Label data = new Label();
				HBox hbox2 = new HBox(100);
				hbox2.getChildren().addAll(comboBox, refresh, data);
				data.setStyle(menustyle);
				comboBox.setStyle(textstyle);
				hbox.setStyle(textstyle);
				dialogVBox.getChildren().addAll(hbox2, txtarea, hbox);
				comboBox.setValue("Ascending");
				dialog.getDialogPane().setContent(dialogVBox);

				if (comboBox.getValue().equalsIgnoreCase("ascending")) {
					currentIndex = 0;
				} else {
					currentIndex = catalog.getTable().length - 1;
				}

				next.setOnAction(e2 -> {
					if (comboBox.getValue().equalsIgnoreCase("ascending")) {
						if (currentIndex < catalog.getTable().length - 1) {
							currentIndex++;
							txtarea.setText(catalog.print(currentIndex));
						}
					} else {
						if (currentIndex > 0) {
							currentIndex--;
							txtarea.setText(catalog.print(currentIndex));
						}
					}
				});

				prev.setOnAction(e2 -> {
					if (comboBox.getValue().equalsIgnoreCase("ascending")) {
						if (currentIndex > 0) {
							currentIndex--;
							txtarea.setText(catalog.print(currentIndex));
						}
					} else {
						if (currentIndex < catalog.getTable().length - 1) {
							currentIndex++;
							txtarea.setText(catalog.print(currentIndex));
						}
					}
				});

				refresh.setOnAction(e4 -> {
					txtarea.clear();
				});

				txtarea.setText(catalog.print(currentIndex));

				ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
				dialog.getDialogPane().getButtonTypes().add(closeButton);
				dialog.showAndWait();
			});

			printTop.setOnAction(e -> {
				Dialog<Void> dialog = new Dialog<>();
				dialog.setTitle("Top and Lowest Ranked Movies");
				dialog.setHeaderText("Top and Lowest Ranked Movies in Each AVL Tree");

				VBox dialogVBox = new VBox(10);
				dialogVBox.setAlignment(Pos.CENTER_LEFT);
				dialogVBox.setPadding(new Insets(20));

				TextArea txtarea = new TextArea();
				txtarea.setEditable(false);
				txtarea.setPrefHeight(250);
				txtarea.setPrefWidth(400);

				dialogVBox.getChildren().add(txtarea);
				dialog.getDialogPane().setContent(dialogVBox);

				StringBuilder sb = new StringBuilder();

				for (int i = 0; i < catalog.getTable().length; i++) {
					if (catalog.getTable()[i] != null && catalog.getTable()[i].getRoot() != null) {
						Movie topMovie = catalog.toprating(catalog.getTable()[i]);
						Movie lowestMovie = catalog.lowrating(catalog.getTable()[i]);

						sb.append("Index " + i + ":\n");
						if (topMovie != null) {
							sb.append("Top Rated: " + topMovie.getMovieTitle() + " (" + topMovie.getRating()
									+ " stars)\n");
						} else {
							sb.append("No movies available in this index.\n");
						}

						if (lowestMovie != null) {
							sb.append("Lowest Rated: " + lowestMovie.getMovieTitle() + " (" + lowestMovie.getRating()
									+ " stars)\n");
						} else {
							sb.append("No movies available in this index.\n");
						}

						sb.append("\n");
					}
				}
				txtarea.setStyle(textstyle);
				txtarea.setText(sb.toString());

				ButtonType closeButton = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);
				dialog.getDialogPane().getButtonTypes().add(closeButton);
				dialog.showAndWait();
			});

			tableView.setRowFactory(tv -> {
				TableRow<Movie> row = new TableRow<>();
				row.setOnMouseClicked(event -> {
					if (event.getClickCount() == 1 && event.getButton() == MouseButton.PRIMARY) {
						if (!row.isEmpty()) {
							Movie clickedRow = row.getItem();
							tableView.getSelectionModel().select(row.getIndex());
							Alert optionsDialog = new Alert(Alert.AlertType.CONFIRMATION);
							optionsDialog.setTitle("Select Action");
							optionsDialog.setHeaderText("Choose an action for the selected movie:");
							ButtonType addbutton = new ButtonType("Insert");
							ButtonType deletebutton = new ButtonType("Delete");
							ButtonType cancelOption = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
							optionsDialog.getButtonTypes().setAll(addbutton, deletebutton, cancelOption);
							Optional<ButtonType> result = optionsDialog.showAndWait();
							if (result.isPresent()) {
								if (result.get() == deletebutton) {
									showDeleteDialog(clickedRow.getMovieTitle());
								} else if (result.get() == addbutton) {
									add.fire();
								}
							}
						} else if (row.isEmpty()) {
							add.fire();
						}
					}
				});
				return row;
			});

			VBox vbox = new VBox(50);
			vbox.getChildren().addAll(menubar, title1);
			vbox.setAlignment(Pos.TOP_CENTER);
			root.setTop(vbox);
			VBox vbox2 = new VBox(10);
			vbox2.setAlignment(Pos.CENTER);

			Label note = new Label("Note: Clicking on a row will ask for deletion or insertion confirmation");
			note.setStyle(notestyle);
			vbox2.getChildren().addAll(tableView, note);
			root.setCenter(vbox2);

			tableView.setStyle(textstyle);
			Scene scene = new Scene(root);
			primaryStage.setMaximized(true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	private void showDeleteDialog(String initialTitle) {
		Dialog<ButtonType> dialog = new Dialog<>();
		dialog.setTitle("Delete Movie");

		ComboBox<String> comboBox = new ComboBox<>();
		comboBox.setPromptText("--Select the movie title--");
		for (Movie movie : tableView.getItems()) {
			comboBox.getItems().add(movie.getMovieTitle());
		}

		if (initialTitle != null && !initialTitle.isEmpty()) {
			comboBox.setValue(initialTitle);
		}

		VBox dialogContent = new VBox(10, comboBox);
		dialogContent.setPadding(new Insets(10));
		dialog.getDialogPane().setContent(dialogContent);
		dialog.getDialogPane().setStyle(textstyle);
		Image backgroundImage = new Image(getClass().getResource("bgg.jpg").toExternalForm()); // background image
		BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true,
				true);
		BackgroundImage bgImage = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
				BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
		dialog.getDialogPane().setBackground(new Background(bgImage));
		dialog.getDialogPane().setPrefWidth(400);
		dialog.getDialogPane().setPrefHeight(300);
		ButtonType okButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

		dialog.showAndWait().ifPresent(buttonType -> {
			if (buttonType == okButtonType) {
				String selectedTitle = comboBox.getValue();
				if (selectedTitle == null || selectedTitle.isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a movie to delete.");
					alert.showAndWait();
					return;
				}
				catalog.erase(selectedTitle);
				tableView.getItems().removeIf(movie -> movie.getMovieTitle().equals(selectedTitle));
				Alert alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setTitle("Success");
				alert.setContentText("Movie was deleted successfully.");
				alert.showAndWait();
			}
		});

	}

	private boolean validateRate(double rate) {
		if (rate < 0.0 || rate > 10.0) {
			return false;
		}
		return true;
	}

	private boolean validateYear(int year) {
		if (year < 1900 || year > 2024) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
