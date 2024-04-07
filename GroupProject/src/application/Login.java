package application;


import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Login extends Application {

	private Stage primaryStage;
    private Users users;

    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Login");

        users = new Users();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: lightblue;");

        // Add the image to the grid pane in column 0, row 1
        ImageView imageView = new ImageView(new Image("file:patches_8506843.png"));
        // the png was made by lakonicon 
        imageView.setFitWidth(50); // Set the width of the image
        imageView.setFitHeight(50); // Set the height of the image
        grid.add(imageView, 0, 1);

        Label titleLabel = new Label("Pediatric Home Page Screen");
        titleLabel.setFont(Font.font("Arial", 20));
        titleLabel.setTextFill(Color.WHITE);
        titleLabel.setAlignment(Pos.CENTER);
        GridPane.setColumnSpan(titleLabel, 2);
        grid.add(titleLabel, 1, 1);

        Label nameLabel = new Label("Name:");
        grid.add(nameLabel, 0, 2);

        TextField nameTextField = new TextField();
        grid.add(nameTextField, 1, 2);

        Label idLabel = new Label("Birthday:");
        grid.add(idLabel, 0, 3);

        TextField idTextField = new TextField();
        grid.add(idTextField, 1, 3);

        Button loginButton = new Button("Login");
        grid.add(loginButton, 1, 4);
        
        Button createUserButton = new Button("Create User");
        grid.add(createUserButton, 2, 4);
        
        
        
        loginButton.setOnAction(e -> {
            String fullName = nameTextField.getText();
            String birthday = idTextField.getText();
            login(fullName, birthday);
        });
        

        createUserButton.setOnAction(e -> {
            String fullName = nameTextField.getText();
            String birthday = idTextField.getText();
            addUser(fullName, birthday);
            users = null;
        	users = new Users();
        });

        Scene scene = new Scene(grid, 600, 400); 
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private void login(String fullName, String id) {
        Users.User user = users.findUserByNameAndId(fullName, id);
        if (user != null) {
        	if (user.getUserType() == Users.UserType.PATIENT) {
        	    Users.Patient patient = (Users.Patient) user;
        	    users.getReadyPatients().add(patient);
        	}
            switch (user.getUserType()) {
                case PATIENT:
                    openPatientPortal(user);
                    break;
                case RECEPTIONIST:
                    openReceptionistPortal();
                    break;
                case DOCTOR:
                    openDoctorPortal();
                    break;
            }
        } else {
            System.out.println("User not found");
        }
    }
    
    private void addUser(String name, String birthday) {
    	String patientInfo = name + "," + birthday + ",PATIENT,NONE,NONE,NONE\n";
        try (RandomAccessFile file = new RandomAccessFile(new File("user.txt"), "rw")) {
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

    // Other methods for opening portals, GUI components, etc.
    private void openPatientPortal(Users.User user) {
    	Patient patientPortal = new Patient(user);
        patientPortal.start(new Stage());
        //primaryStage.close();
    }

    private void openReceptionistPortal() {
    	//String userName = user.getName(); // Get the user's name
        Receptionist receptionistPortal = new Receptionist();
        receptionistPortal.start(new Stage());
        primaryStage.close();
    	
    }

    private void openCTechPortal() {
        CTech cTechPortal = new CTech(users.getReadyPatients());
        cTechPortal.start(new Stage());
        primaryStage.close();
    }

    private void openDoctorPortal() {
    	Doctor doctorPortal = new Doctor();
        doctorPortal.start(new Stage());
        primaryStage.close();
    }

    

    public static void main(String[] args) {
        launch(args);
    }
}

