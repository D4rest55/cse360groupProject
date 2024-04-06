package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
                    user = new Patient(name, id, userType);
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
        private int height;
        private int weight;
        private int age;
        private String allergies;

        public Patient(String name, String id, UserType userType) {
            super(name, id, userType);

        }

        public int getHeight() {
            return height;
        }

        public int getWeight() {
            return weight;
        }

        public int getAge() {
            return age;
        }

        public String getAllergies() {
            return allergies;
        }
    }
    
    public void addPatientToReadyList(Patient patient) {
        readyPatients.add(patient);
    }
    
    public List<Patient> getReadyPatients() {
        return readyPatients;
    }

    // Enum for user types
    public enum UserType {
        PATIENT, RECEPTIONIST, DOCTOR
    }
    
    public enum patientStatus {
    	READY, NOTREADY
    }
}
