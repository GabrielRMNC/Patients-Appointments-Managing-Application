package ui;

import service.PatientService;
import domain.Patient;
import filters.FilterPatientsByName;
import filters.FilterPatientsByAge;

import java.util.Scanner;
import java.util.function.Function;

public class PatientUI<ID> {
    private PatientService<ID> patientService;
    private Scanner scanner = new Scanner(System.in);
    private Function<String, ID> idConverter;

    private static final int ADD_PATIENT = 1;
    private static final int VIEW_ALL_PATIENTS = 2;
    private static final int UPDATE_PATIENT = 3;
    private static final int FILTER_PATIENT_NAME = 4;
    private static final int FILTER_PATIENT_AGE = 5;
    private static final int DELETE_PATIENT = 6;
    private static final int BACK_MAIN_MENU = 7;

    public PatientUI(PatientService<ID> patientService, Function<String, ID> idConverter) {
        this.patientService = patientService;
        this.idConverter = idConverter;
    }

    public void managePatients() {
        boolean exit = false;

        while (!exit) {
            System.out.println("\nPatient menu: ");
            System.out.println("1. Add patient");
            System.out.println("2. View all patients");
            System.out.println("3. Update patient");
            System.out.println("4. Filter patients by name");
            System.out.println("5. Filter patients by age");
            System.out.println("6. Delete patient");
            System.out.println("7. Back to main menu");
            System.out.println(" ");

            System.out.println("Choose an option:");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case ADD_PATIENT:
                    addPatient();
                    break;
                case VIEW_ALL_PATIENTS:
                    viewAllPatients();
                    break;
                case UPDATE_PATIENT:
                    updatePatient();
                    break;
                case FILTER_PATIENT_NAME:
                    filterPatientsByName();
                    break;
                case FILTER_PATIENT_AGE:
                    filterPatientsByAge();
                    break;
                case DELETE_PATIENT:
                    deletePatient();
                    break;
                case BACK_MAIN_MENU:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    public void addPatient() {
        System.out.println("Enter patient ID: ");
        String patientIDInput = scanner.nextLine();
        ID patientID = idConverter.apply(patientIDInput);
        System.out.println("Enter patient name: ");
        String patientName = scanner.nextLine();
        System.out.println("Enter patient age: ");
        int patientAge = scanner.nextInt();

        try {
            patientService.createPatient(patientID, patientName, patientAge);
            System.out.println("Patient added!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error adding patient: " + e.getMessage());
        }
    }

    private void viewAllPatients() {
        patientService.getAllPatients().forEach(System.out::println);
    }

    private void updatePatient() {
        System.out.println("Enter patient ID to update: ");
        String patientIDInput = scanner.nextLine();
        ID patientID = idConverter.apply(patientIDInput);
        System.out.println("Enter patient name: ");
        String patientName = scanner.nextLine();
        System.out.println("Enter patient age: ");
        int patientAge = scanner.nextInt();

        try {
            patientService.updatePatient(new Patient<>(patientID, patientName, patientAge));
            System.out.println("Patient updated!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error updating patient: " + e.getMessage());
        }
    }

    private void filterPatientsByName() {
        System.out.println("Enter patient name: ");
        String nameToFilterBy = scanner.nextLine();
        patientService.filterPatientsByName(nameToFilterBy).forEach(System.out::println);
    }

    private void filterPatientsByAge() {
        System.out.println("Enter patient age: ");
        int ageToFilterBy = scanner.nextInt();
        patientService.filterPatientsByAge(ageToFilterBy).forEach(System.out::println);
    }

    private void deletePatient() {
        System.out.println("Enter patient ID to delete: ");
        String patientIDInput = scanner.nextLine();
        ID patientID = idConverter.apply(patientIDInput);

        try {
            patientService.deletePatient(patientID);
            System.out.println("Patient deleted!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting patient: " + e.getMessage());
        }
    }
}