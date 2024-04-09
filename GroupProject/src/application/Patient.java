package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Patient extends Application {


	private Stage primaryStage;
	private GridPane grid;
	private Users.User user;
    private Label messageLabel, contactLabel, summaryLabel, welcome;
    Button send, generate, changeInfo;

	
	 public Patient(Users.User user) { this.user = user; }
	
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Patient Portal");

        BorderPane borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightblue;");
        
        grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.setStyle("-fx-background-color: lightblue;");
        
        welcome = new Label("Welcome to your Patient Portal");
        welcome.setFont(Font.font("Arial", 30));
        welcome.setTextFill(Color.WHITE);
        welcome.setAlignment(Pos.CENTER);
        
        contactLabel = new Label("Contact Information");
        contactLabel.setFont(Font.font("Arial", 20));
        contactLabel.setTextFill(Color.WHITE);
        contactLabel.setAlignment(Pos.CENTER);
        
        messageLabel = new Label("Message Your Provider Here");
        messageLabel.setFont(Font.font("Arial", 20));
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setAlignment(Pos.CENTER);
        
        summaryLabel = new Label("Summary of your Visit");
        summaryLabel.setFont(Font.font("Arial", 20));
        summaryLabel.setTextFill(Color.WHITE);
        summaryLabel.setAlignment(Pos.CENTER);
        
        grid.add(contactLabel, 0, 0);
        grid.add(messageLabel, 1, 0);
        grid.add(summaryLabel, 2, 0);
        
        TextArea contacts = new TextArea();
        contacts.setPrefWidth(100);
        contacts.setPrefHeight(300);
        contacts.setPrefRowCount(10);
        contacts.setWrapText(true);
        grid.add(contacts, 0, 1, 1, 1);
        
        TextArea textMessage = new TextArea();
        textMessage.setPrefWidth(100);
        textMessage.setPrefHeight(300);
        textMessage.setPrefRowCount(10);
        textMessage.setWrapText(true);
        grid.add(textMessage, 1, 1, 1, 1);
        
       // Message read
        
        File message = new File("./message.txt");
        try(Scanner scan = new Scanner(message);)
        {
        	 while( scan.hasNextLine()) {
             	String line = scan.nextLine();
             	if(line.contains(user.getName()))
             	{
             		textMessage.setText(line);
             	}
             }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        
       
        TextArea summary = new TextArea();
        summary.setPrefWidth(100);
        summary.setPrefHeight(300);
        summary.setPrefRowCount(10);
        summary.setWrapText(true);
        grid.add(summary, 2, 1, 1, 1);
        
       // visit summary read
        File visits = new File("./visits.txt");
        try(Scanner scan = new Scanner(visits);)
        {
        	 while( scan.hasNextLine()) {
             	String line = scan.nextLine();
             	if(line.contains(user.getName()))
             	{
             		summary.setText(line);
             	}
             }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        send = new Button("Send Message");
        grid.add(send, 1, 2);

        generate = new Button("Generate Summary");
        grid.add(generate, 2, 2);

        changeInfo = new Button("Change Contacts");
        grid.add(changeInfo, 0, 2);

       // Label nameLabel = new Label("Welcome, " + user.getName());
       // nameLabel.setFont(Font.font("Arial", 16));
       // nameLabel.setTextFill(Color.WHITE);
       //   borderPane.setTop(nameLabel);

        // You can access other user information using the user object here
        borderPane.setLeft(grid);
        borderPane.setTop(welcome);
        borderPane.setAlignment(welcome, javafx.geometry.Pos.CENTER);
        Scene scene = new Scene(borderPane, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}