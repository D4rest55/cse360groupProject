package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class Users {
    private List<User> userList;
    private List<Patient> readyPatients;

    public Users() {
        userList = new ArrayList<>();
        readyPatients = new ArrayList<>();
        // Load users from file
        loadUsersFromFile("user.txt");
    }

    private void loadUsersFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                String name = parts[0].trim();
                String id = parts[1].trim();
                UserType userType = UserType.valueOf(parts[2].trim());
                User user;
                switch (userType) {
                case PATIENT:
                	 String pasthistory = parts[3].trim();
                     String prevMeds = parts[4].trim();
                     String immunization = parts[5].trim();
                     user = new Patient(name, id, userType, pasthistory, prevMeds, immunization);
                     break;
                default:
                    user = new User(name, id, userType);
                    break;
            }
            userList.add(user);
        
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid user type in file: " + e.getMessage());
        }
    }

    public void addUser(User user) {
        userList.add(user);
    }

    public User findUserByNameAndId(String fullName, String id) {
        for (User user : userList) {
            if (user.getName().equalsIgnoreCase(fullName) && user.getId().equals(id)) {
                return user;
            }
        }
        return null; // User not found
    }

    public List<User> getUserList() {
        return userList;
    }

    public static class User {
        private String name;
        private String id;
        private UserType userType;

        public User(String name, String id, UserType userType) {
            this.name = name;
            this.id = id;
            this.userType = userType;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        public UserType getUserType() {
            return userType;
        }
    }

    // Subclass for Patient with additional fields
    public static class Patient extends User {
        private String pasthistory;
        private String prevMeds;
        private String immunization;

        public Patient(String name, String id, UserType userType, String pasthistory,String prevMeds, String immunization) {
            super(name, id, userType);
            this.pasthistory = pasthistory;
            this.prevMeds = prevMeds;
            this.immunization = immunization;
        }

        public String getpastHistory() {
            return pasthistory;
        }

        public String getprevMeds() {
            return prevMeds;
        }

        public String getImmunization() {
            return immunization;
        }

    }
    
    public void addPatientToReadyList(Patient patient) {
        readyPatients.add(patient);
    }
    
    public List<Patient> getReadyPatients() {
        return readyPatients;
    }
    
    void displayPatientInformation(String name, GridPane grid) {
        for (User user : userList) {
            if (user.getName().equals(name) && user instanceof Patient) {
                Patient patient = (Patient) user;
                addLabelToGrid(grid, "Name:", 2, 0);
                addLabelToGrid(grid, patient.getName(), 3, 0);
                addLabelToGrid(grid, "Birthday:", 2, 1);
                addLabelToGrid(grid, patient.getId(), 3, 1);
                addLabelToGrid(grid, "Past History:", 2, 2);
                addLabelToGrid(grid, patient.getpastHistory(), 3, 2);
                addLabelToGrid(grid, "Previous Medications:", 2, 3);
                addLabelToGrid(grid, patient.getprevMeds(), 3, 3);
                addLabelToGrid(grid, "Immunization:", 2, 4);
                addLabelToGrid(grid, patient.getImmunization(), 3, 4);
                // Add other properties as needed
                return; // Stop after finding the user
            }
        }
        addLabelToGrid(grid, "Patient with name " + name + " not found.", 2, 0);
    }

    private void addLabelToGrid(GridPane grid, String text, int column, int row) {
        Label label = new Label(text);
        grid.add(label, column, row);
    }

    // Enum for user types
    public enum UserType {
        PATIENT, RECEPTIONIST, DOCTOR
    }
    
    public enum patientStatus {
    	READY, NOTREADY
    }
}
