package application;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.io.File;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;  
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;

public class Doctor extends Application {
	//Test

	private Stage primaryStage;
    private Users users;
    @Override
    public void start(Stage primaryStage) {
    	this.primaryStage = primaryStage;
        this.users = new Users();
        primaryStage.setTitle("Doctor's Portal");
        BorderPane borderPane = new BorderPane();                      
        borderPane.setStyle("-fx-background-color: lightblue;");
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

        Label examDateLabel = new Label("Exam Date:");
        grid.add(examDateLabel, 0, 6);
        
        TextField examDate = new TextField();
        grid.add(examDate, 1, 6);

        Label findingsLabel = new Label("Visit Findings:");
        grid.add(findingsLabel, 0, 7);

        TextArea findingsTextArea = new TextArea();
        findingsTextArea.setPrefRowCount(10);
        findingsTextArea.setWrapText(true);
        grid.add(findingsTextArea, 0, 8, 2, 1);

        Button saveButton = new Button("Save");
        grid.add(saveButton, 0, 9);
             
        GridPane rightSide = new GridPane();
        //rightSide.setAlignment(Pos.);
        rightSide.setHgap(10);
        rightSide.setVgap(10);
        rightSide.setPadding(new Insets(95, 95, 95, 95));
        GridPane.setColumnSpan(titleLabel, 2);
        
        Label messageLabel = new Label("Message Patient");
        rightSide.add(messageLabel, 0, 0);
        messageLabel.setFont(Font.font("Arial", 20));
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setAlignment(Pos.CENTER);
		 
		TextField patient = new TextField(); 
		rightSide.add(patient, 0, 1);
		
		TextField patientbirth = new TextField();
		rightSide.add(patientbirth, 0, 2);
		 
        TextArea textMessage = new TextArea();
        textMessage.setPrefWidth(100);
        textMessage.setPrefHeight(300);
        textMessage.setPrefRowCount(10);
        textMessage.setWrapText(true);
        rightSide.add(textMessage, 0, 3, 1, 1);
        
        Button send = new Button("Send Message");
        rightSide.add(send, 0, 4);
        
        Button view = new Button("View Messages");
        rightSide.add(view, 0, 5);
        
        TextArea summary = new TextArea();
        summary.setPrefWidth(400);
        summary.setPrefHeight(300);
        summary.setPrefRowCount(10);
        summary.setWrapText(true);
        grid.add(summary, 2, 0, 1, 11);
        
        saveButton.setOnAction(e -> {
            String name = nameTextField.getText();
            String birthday = birthdayTextField.getText();
            String visitFindings = findingsLabel.getText();
            String healthHistory = healthHistoryTextField.getText();
            String medication = medicationTextField.getText();
            String immunizations = immunizationsTextField.getText();
            String date = examDate.getText();

            //users.displayPatientInformation(name, grid);
            display(name, birthday, summary);
            savePatientInformation(name, birthday, visitFindings, healthHistory, medication, immunizations, date);
        });
        
        send.setOnAction(e -> {
            String patientName = patient.getText();
            String message = textMessage.getText();
            String patientBirth = patientbirth.getText();

            saveMessageInformation(patientName, patientBirth, message);
        });
        
        view.setOnAction(e -> {
        	String patientName = patient.getText();
            //String message = textMessage.getText();
            
            view(patientName, textMessage);
        });

        borderPane.setLeft(grid);
        borderPane.setRight(rightSide);
        Scene scene = new Scene(borderPane, 1300, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void savePatientInformation(String name, String birthday, String visitFindings, String healthHistory, String medication, String immunizations, String date) {
    	if (name.isEmpty() || birthday.isEmpty() || visitFindings.isEmpty() || healthHistory.isEmpty() || medication.isEmpty() || immunizations.isEmpty() || date.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }
        String patientInfo = name + "," + birthday + ",PATIENT," + "," + healthHistory + "," + medication + "," + immunizations + "," + date + "\n";
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
    
    private void saveMessageInformation(String patientID, String patientBirth, String messages) {
    	if (patientID.isEmpty() || patientBirth.isEmpty() || messages.isEmpty()) {
            System.out.println("Please fill in all fields.");
            return;
        }
        String patientInfo = patientID + "," + patientBirth + ": " + messages;
        try (RandomAccessFile file = new RandomAccessFile(new File("message.txt"), "rw")) {
            long fileLength = file.length();
            if (fileLength > 0) {
                file.seek(fileLength - 1);
                char lastChar = (char) file.read();
                if (lastChar != '\n') {
                    file.writeBytes("\n");
                }
            }
            file.writeBytes(patientInfo);
            System.out.println("Message sent Successfully");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    public void view(String name, TextArea textMessage) {
    	File message = new File("./message.txt");
        try (Scanner scan = new Scanner(message);) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                String[] splitLine = line.split(":");
                if (splitLine[0].equals(name))
                // i know this looks dumb but its so we dont forget to talk about it.
                // && line.contains(user.getId())

                {
                    textMessage.appendText("\n" + line + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void display(String name, String birthday, TextArea summary) {
    	if (name.isEmpty() || birthday.isEmpty()) {
            return;
        }
    	File visits = new File("./past.txt");
        try(Scanner scan = new Scanner(visits);)
        {
        	
        	 while( scan.hasNextLine()) 
        	 {
        		 String line = scan.nextLine();
        		 String[] splitLine = line.split(",");
        		 String healthHistory = splitLine[3];
        		 String prevMeds = splitLine[4];
        		 String immunizations = splitLine[5];
        		 String date = splitLine[6];
        		 if (date.equals("NONE") && immunizations.equals("NONE") && prevMeds.equals("NONE") && healthHistory.equals("NONE")) {
        			    // All fields are "NONE", handle this case if needed
        			} else {
        			    if (line.contains(name) && line.contains(birthday)) {
        			        summary.appendText("\nHealth History: " + healthHistory + "\nPrevious Medications: " + prevMeds + "\nImmunizations: " + immunizations + "\nDate: " + date + "\n");
        			    }
        			}
        		 
             }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
