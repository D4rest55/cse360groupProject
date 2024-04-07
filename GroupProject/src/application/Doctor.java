package application;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Doctor extends Application {

	private Stage primaryStage;
    private Users users;
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        this.users = new Users();
        primaryStage.setTitle("Doctor's Portal");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: lightblue;");

        Label titleLabel = new Label("Doctor's Portal");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(titleLabel, 2);
        grid.add(titleLabel, 0, 0);

        Label nameLabel = new Label("Patient's Name:");
        grid.add(nameLabel, 0, 1);

        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        Label birthdayLabel = new Label("Patient's Birthday:");
        grid.add(birthdayLabel, 0, 2);

        TextField birthdayTextField = new TextField();
        grid.add(birthdayTextField, 1, 2);

        Label healthHistoryLabel = new Label("Health History:");
        grid.add(healthHistoryLabel, 0, 3);

        TextField healthHistoryTextField = new TextField();
        grid.add(healthHistoryTextField, 1, 3);

        Label medicationLabel = new Label("Medication:");
        grid.add(medicationLabel, 0, 4);

        TextField medicationTextField = new TextField();
        grid.add(medicationTextField, 1, 4);

        Label immunizationsLabel = new Label("Immunizations:");
        grid.add(immunizationsLabel, 0, 5);

        TextField immunizationsTextField = new TextField();
        grid.add(immunizationsTextField, 1, 5);

        Label findingsLabel = new Label("Visit Findings:");
        grid.add(findingsLabel, 0, 6);

        TextArea findingsTextArea = new TextArea();
        findingsTextArea.setPrefRowCount(10);
        findingsTextArea.setWrapText(true);
        grid.add(findingsTextArea, 0, 7, 2, 1);

        Button saveButton = new Button("Save");
        grid.add(saveButton, 1, 8);
        
        saveButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String birthday = birthdayTextField.getText();
            String visitFindings = findingsLabel.getText();
            String healthHistory = healthHistoryTextField.getText();
            String medication = medicationTextField.getText();
            String immunizations = immunizationsTextField.getText();

            users.displayPatientInformation(name, grid);
            savePatientInformation(name, birthday, visitFindings, healthHistory, medication, immunizations);
        });

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void savePatientInformation(String name, String birthday, String visitFindings, String healthHistory, String medication, String immunizations) {
        String patientInfo = name + "," + birthday + ",PATIENT," + "," + healthHistory + "," + medication + "," + immunizations + "\n";
        try (RandomAccessFile file = new RandomAccessFile(new File("past.txt"), "rw")) {
            long fileLength = file.length();
            if (fileLength > 0) {
                file.seek(fileLength - 1);
                char lastChar = (char) file.read();
                if (lastChar != '\n') {
                    file.writeBytes("\n");
                }
            }
            file.writeBytes(patientInfo);
            System.out.println("Visit findings saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
