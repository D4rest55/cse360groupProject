package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class CTech extends Application {
    private List<Users.Patient> readyPatients;

    public CTech(List<Users.Patient> readyPatients) {
        this.readyPatients = readyPatients;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("CTech Portal");

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 600, 400);

        // Create a VBox to hold radio buttons for ready patients
        VBox radioButtons = new VBox(5);
        ToggleGroup toggleGroup = new ToggleGroup();
        for (Users.Patient patient : readyPatients) {
            RadioButton radioButton = new RadioButton(patient.getName());
            radioButton.setToggleGroup(toggleGroup);
            radioButtons.getChildren().add(radioButton);
        }
        root.setCenter(radioButtons);

        // Create a submit button
        Button submitButton = new Button("Submit");
        //submitButton.setOnAction(event -> handleSubmit(primaryStage));
        root.setBottom(submitButton);

        primaryStage.setScene(scene);
        primaryStage.show();
        

    }

}


