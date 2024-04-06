package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Receptionist extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Nurse Page");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: lightblue;");
        
        // Add image to the top left corner
        ImageView imageView = new ImageView(new Image("file:patches_8506843.png"));
        imageView.setFitWidth(50); // Set the width of the image
        imageView.setFitHeight(50); // Set the height of the image
        grid.add(imageView, 0, 0);
        
        Label titleLabel = new Label("Patient Vitals");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(titleLabel, 2);
        grid.add(titleLabel, 1, 0);

        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 1);

        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 1);

        Label idLabel = new Label("Birthday");
        grid.add(idLabel, 0, 2);

        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 2);

        Label heightLabel = new Label("Height:");
        grid.add(heightLabel, 0, 3);

        TextField heightTextField = new TextField();
        grid.add(heightTextField, 1, 3);

        Label weightLabel = new Label("Weight:");
        grid.add(weightLabel, 0, 4);

        TextField weightTextField = new TextField();
        grid.add(weightTextField, 1, 4);

        Label ageLabel = new Label("Age:");
        grid.add(ageLabel, 0, 5);

        TextField ageTextField = new TextField();
        grid.add(ageTextField, 1, 5);

        Label emailLabel = new Label("Allergies:");
        grid.add(emailLabel, 0, 6);

        TextField emailTextField = new TextField();
        grid.add(emailTextField, 1, 6);
        
        Label bodyTempLabel = new Label("Body Temp:");
        grid.add(bodyTempLabel, 0, 7);
        
        TextField bodyTempTextField = new TextField();
        grid.add(bodyTempTextField, 1, 7);
        
        Label bloodLabel = new Label("Blood Pressure:");
        grid.add(bloodLabel, 0, 8);
        
        TextField bloodTextField = new TextField();
        grid.add(bloodTextField, 1, 8);
        
        Label dateLabel = new Label("Date:");
        grid.add(dateLabel, 0, 9);
        
        TextField dateTextField = new TextField();
        grid.add(dateTextField, 1, 9);

        Button saveButton = new Button("Save");
        grid.add(saveButton, 1, 10);

        
        saveButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String birthday = idTextField.getText();
            String height = heightTextField.getText();
            String weight = weightTextField.getText();
            int age = Integer.parseInt(ageTextField.getText());
            String allergies = emailTextField.getText();
            int temp = Integer.parseInt(bodyTempTextField.getText());
            String bloodP = bloodTextField.getText();
            String date = dateTextField.getText();
            //deletePatient(name, birthday, height, weight, age, allergies);
            savePatientInformation(name, birthday, height, weight, age, allergies, temp, bloodP, date);
            
        });

        Scene scene = new Scene(grid, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void deletePatient(String name, String birthday, String height, String weight, int age, String allergies) {
        String patientInfoToDelete = name + "," + birthday + ",PATIENT,0,0,0,N/A";
        File file = new File("user.txt");
        File newFile = new File("newUser.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // If the line contains the patient information to delete, skip it
                if (line.equals(patientInfoToDelete)) {
                    continue;
                }
                writer.write(line + System.getProperty("line.separator"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Delete the original file
        if (!file.delete()) {
            System.out.println("Failed to delete the original file.");
            return;
        }

        // Rename the new file to the original file name
        if (!newFile.renameTo(file)) {
            System.out.println("Failed to rename the new file.");
        }
    }

    private void savePatientInformation(String name, String birthday, String height, String weight, int age, String allergies, int temp, String bloodP, String date) {
    	if (age <= 12) {
            System.out.println("Patient needs parental assistance");
            return; // Exit the method if patient age is 12 or younger
        }
        String patientInfo = name + "," + birthday + ",PATIENT," + height + "," + weight + "," + age + "," + allergies + "," + temp + "," + bloodP + "," + date + "\n";
        try (RandomAccessFile file = new RandomAccessFile(new File("visits.txt"), "rw")) {
            long fileLength = file.length();
            if (fileLength > 0) {
                file.seek(fileLength - 1);
                char lastChar = (char) file.read();
                if (lastChar != '\n') {
                    file.writeBytes("\n");
                }
            }
            file.writeBytes(patientInfo);
            System.out.println("Patient information saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}


