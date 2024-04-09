package application;

import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;


public class Patient extends Application {


	private Stage primaryStage;
	private GridPane grid;
	private Users.User user;
    private Label messageLabel, contactLabel, insuranceLabel, pharmacyLabel, summaryLabel, welcome,
    		phoneLabel, emailLabel, insuranceProviderLabel, insurancePolicyNumLabel, pharmacyNameLabel, 
    		pharmacyAddressLabel, emptyLabel;
    Button send, changeInfo;

	
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
        
        phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(Font.font("Arial", 12));
        phoneLabel.setTextFill(Color.WHITE);
        phoneLabel.setAlignment(Pos.CENTER);
        
        emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("Arial", 12));
        emailLabel.setTextFill(Color.WHITE);
        emailLabel.setAlignment(Pos.CENTER);
        
        insuranceLabel = new Label("Insurance Information");
        insuranceLabel.setFont(Font.font("Arial", 20));
        insuranceLabel.setTextFill(Color.WHITE);
        insuranceLabel.setAlignment(Pos.CENTER);
        
        insuranceProviderLabel = new Label("Insurance Provider:");
        insuranceProviderLabel.setFont(Font.font("Arial", 12));
        insuranceProviderLabel.setTextFill(Color.WHITE);
        insuranceProviderLabel.setAlignment(Pos.CENTER);
        
        insurancePolicyNumLabel = new Label("Policy Number:");
        insurancePolicyNumLabel.setFont(Font.font("Arial", 12));
        insurancePolicyNumLabel.setTextFill(Color.WHITE);
        insurancePolicyNumLabel.setAlignment(Pos.CENTER);
        
        pharmacyLabel = new Label("Pharmacy Information");
        pharmacyLabel.setFont(Font.font("Arial", 20));
        pharmacyLabel.setTextFill(Color.WHITE);
        pharmacyLabel.setAlignment(Pos.CENTER);
        
        pharmacyNameLabel = new Label("Pharmacy Name:");
        pharmacyNameLabel.setFont(Font.font("Arial", 12));
        pharmacyNameLabel.setTextFill(Color.WHITE);
        pharmacyNameLabel.setAlignment(Pos.CENTER);
        
        pharmacyAddressLabel = new Label("Address:");
        pharmacyAddressLabel.setFont(Font.font("Arial", 12));
        pharmacyAddressLabel.setTextFill(Color.WHITE);
        pharmacyAddressLabel.setAlignment(Pos.CENTER);
        
        messageLabel = new Label("Message Your Provider Here");
        messageLabel.setFont(Font.font("Arial", 20));
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setAlignment(Pos.CENTER);
        
        summaryLabel = new Label("Summary of your Visit");
        summaryLabel.setFont(Font.font("Arial", 20));
        summaryLabel.setTextFill(Color.WHITE);
        summaryLabel.setAlignment(Pos.CENTER);
        
        emptyLabel = new Label("One or more fields is empty");
        emptyLabel.setFont(Font.font("Arial", 12));
        
        grid.add(contactLabel, 0, 0);
        grid.add(messageLabel, 1, 0);
        
        TextField phone = new TextField();
        TextField email = new TextField();
        TextField insuranceProvider = new TextField();
        TextField insurancePolicyNum = new TextField();
        TextField pharmacyName = new TextField();
        TextField pharmacyAddress = new TextField();
        pharmacyAddress.setPromptText("Address Line 1");
        TextField pharmacyCity = new TextField();
        pharmacyCity.setPromptText("City");
        pharmacyCity.setMaxWidth(95);
        TextField pharmacyState = new TextField();
        pharmacyState.setPromptText("State");
        pharmacyState.setMaxWidth(45);
        TextField pharmacyZip = new TextField();
        pharmacyZip.setPromptText("ZIP");
        pharmacyZip.setMaxWidth(50);
        
        HBox cityStateZip = new HBox(5);
        cityStateZip.getChildren().addAll(pharmacyCity, pharmacyState, pharmacyZip);
        
        VBox patientInfo = new VBox(5);
        patientInfo.getChildren().addAll(phoneLabel, phone, emailLabel, email, insuranceLabel, 
        		insuranceProviderLabel, insuranceProvider, insurancePolicyNumLabel, insurancePolicyNum, 
        		pharmacyLabel, pharmacyNameLabel, pharmacyName, pharmacyAddressLabel, pharmacyAddress,
        		cityStateZip);
        grid.add(patientInfo, 0, 1, 1, 1);
        
        TextArea textMessage = new TextArea();
        textMessage.setPrefWidth(400);
        textMessage.setPrefHeight(170);
        textMessage.setPrefRowCount(10);
        textMessage.setWrapText(true);
        grid.add(textMessage, 1, 1, 1, 1);
        
       // Message read
        
        File message = new File("./message.txt");
        try(Scanner scan = new Scanner(message);)
        {
        	 while( scan.hasNextLine()) {
             	String line = scan.nextLine();
             	String[] splitLine = line.split(",");
       		 	if(splitLine[0].equals(user.getName()))
  //i know this looks dumb but its so we dont forget to talk about it.   			
             			//&& line.contains(user.getId())
             			
             	{
             		textMessage.appendText("\n" + line + "\n");
             	}
             }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        
       
        TextArea summary = new TextArea();
        summary.setPrefWidth(400);
        summary.setPrefHeight(170);
        summary.setPrefRowCount(10);
        summary.setWrapText(true);
        summary.setEditable(false);
        
       // visit summary read
        File visits = new File("./visits.txt");
        try(Scanner scan = new Scanner(visits);)
        {
        	
        	 while( scan.hasNextLine()) 
        	 {
        		 String line = scan.nextLine();
        		 String[] splitLine = line.split(",");
        		 if(splitLine[0].equals(user.getName()))
             	 {
             		 summary.appendText("\n" + line + "\n");
             	 }
        		 
             }
        }catch (IOException e) {
            e.printStackTrace();
        }
        
        send = new Button("Send Message");
        changeInfo = new Button("Update Info");
        patientInfo.getChildren().add(changeInfo);
        
        VBox secondColumn = new VBox(5);
        secondColumn.getChildren().addAll(textMessage, send, summaryLabel, summary);
        grid.add(secondColumn, 1, 1);
        
        try {
    		BufferedReader br = new BufferedReader(new FileReader("patientInfo.txt"));
    		String line;
    		while((line = br.readLine()) != null) {
    			String[] splitLine = line.split("\\|");
    			if (splitLine[0].equals(user.getName())) {
    				phone.setText(splitLine[1]);
    				email.setText(splitLine[2]);
    				insuranceProvider.setText(splitLine[3]);
    				insurancePolicyNum.setText(splitLine[4]);
    				pharmacyName.setText(splitLine[5]);
    				pharmacyAddress.setText(splitLine[6]);
    				pharmacyCity.setText(splitLine[7]);
    				pharmacyState.setText(splitLine[8]);
    				pharmacyZip.setText(splitLine[9]);
    			}
    		}
    		br.close();
    	}
    	catch (IOException ioe) {
    		System.out.println("Failed to fetch patient info");
    	}
        
        changeInfo.setOnAction(e -> {
        	boolean hasEmpty = false;
        	TextField[] fields = {phone, email, insuranceProvider, insurancePolicyNum, pharmacyName,
        			pharmacyAddress, pharmacyCity, pharmacyState, pharmacyZip};
        	for (TextField tf : fields) {
        		if (tf.getText().isBlank()) {
        			hasEmpty = true;
        			break;
        		}
        	}
        	if (!hasEmpty) {
	        	try {
	        		BufferedWriter bw = new BufferedWriter(new FileWriter("patientInfo.txt"));
	        		BufferedReader br = new BufferedReader(new FileReader("patientInfo.txt"));
	        		String lines = "";
	        		String line;
	        		boolean hasEntry = false;
	        		while((line = br.readLine()) != null) {
	        			String[] splitLine = line.split("\\|");
	        			if (splitLine[0] == user.getName()) {
	        				splitLine[1] = phone.getText();
	        				splitLine[2] = email.getText();
	        				splitLine[3] = insuranceProvider.getText();
	        				splitLine[4] = insurancePolicyNum.getText();
	        				splitLine[5] = pharmacyName.getText();
	        				splitLine[6] = pharmacyAddress.getText();
	        				splitLine[7] = pharmacyCity.getText();
	        				splitLine[8] = pharmacyState.getText();
	        				splitLine[9] = pharmacyZip.getText();
	        				hasEntry = true;
	        			}
	        			else {
	        				lines += line;
	        			}
	        		}
	        		if (!hasEntry) {
	        			lines += user.getName() + "|" + phone.getText() + "|" + email.getText() + "|"
	        					+ insuranceProvider.getText() + "|" + insurancePolicyNum.getText() + "|" 
	        					+ pharmacyName.getText() + "|" + pharmacyAddress.getText() + "|"
	        					+ pharmacyCity.getText() + "|" + pharmacyState.getText() + "|"
	        					+ pharmacyZip.getText();
	        		}
	        		bw.write(lines);
	        		br.close();
	        		bw.close();
	        		emptyLabel.setText("Patient info updated successfully");
	        		emptyLabel.setTextFill(Color.BLACK);
	           		if (GridPane.getColumnIndex(emptyLabel) == null) {
	        			grid.add(emptyLabel, 0, 2);
	        		}
	        	}
	        	catch (IOException ioe) {
	        		System.out.println("Failed to update patient info");
	        	}
        	}
        	else {
        		emptyLabel.setText("One or more fields is empty");
    			emptyLabel.setTextFill(Color.RED);
        		if (GridPane.getColumnIndex(emptyLabel) == null) {
        			grid.add(emptyLabel, 0, 2);
        		}
        	}
        });

       // Label nameLabel = new Label("Welcome, " + user.getName());
       // nameLabel.setFont(Font.font("Arial", 16));
       // nameLabel.setTextFill(Color.WHITE);
       //   borderPane.setTop(nameLabel);

        // You can access other user information using the user object here
        borderPane.setLeft(grid);
        borderPane.setTop(welcome);
        BorderPane.setAlignment(welcome, javafx.geometry.Pos.CENTER);
        Scene scene = new Scene(borderPane, 700, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
