package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Patient extends Application {


	private Stage primaryStage;
    private Users.User user;

    public Patient(Users.User user) {
        this.user = user;
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Patient Portal");

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightblue;");

        Label nameLabel = new Label("Welcome, " + user.getName());
        nameLabel.setFont(Font.font("Arial", 16));
        nameLabel.setTextFill(Color.WHITE);
        borderPane.setTop(nameLabel);

        // You can access other user information using the user object here

        Scene scene = new Scene(borderPane, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}