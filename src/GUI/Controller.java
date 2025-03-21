// MainController.java
package GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import repository.file.db.AppointmentRepositoryDB;
import repository.file.db.PatientRepositoryDB;
import service.PatientService;
import service.AppointmentService;


import domain.Patient;
import domain.Appointment;
import repository.IRepository;
import service.PatientService;
import service.AppointmentService;
import repository.CreateRepository;

import java.util.function.Function;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Controller {

    private PatientService<Long> patientService;
    private AppointmentService<Long> appointmentService;



    public Controller() {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("settings.properties")) {
            properties.load(input);
        } catch (IOException e) {
            System.err.println("Error loading properties file: " + e.getMessage());
            return;
        }

        String repositoryType = properties.getProperty("Repository", "inmemory");
        String patientsFile = properties.getProperty("Patient", "patients.txt");
        String appointmentsFile = properties.getProperty("Appointment", "appointments.txt");

        IRepository<Long, Patient<Long>> patientRepository = CreateRepository.createPatientRepository(repositoryType, patientsFile);
        IRepository<Long, Appointment<Long>> appointmentRepository = CreateRepository.createAppointmentRepository(repositoryType, appointmentsFile);

        if (repositoryType.equalsIgnoreCase("inmemory")) {
            Patient<Long> SaftaNoel = new Patient<Long>(1L, "Safta Noel", 30);
            patientRepository.add(SaftaNoel);
            Patient<Long> Tud = new Patient<Long>(2L, "Tudor Sabau", 21);
            patientRepository.add(Tud);
            Patient<Long> Mur = new Patient<Long>(3L, "Muresan Tudor", 40);
            patientRepository.add(Mur);
            Patient<Long> Gab = new Patient<Long>(5L, "Romanica Gabriel", 20);
            patientRepository.add(Gab);
        }
        this.patientService = new PatientService<Long>(patientRepository);
        this.appointmentService = new AppointmentService<Long>(appointmentRepository,patientRepository);
    }

    @FXML
    private void addPatient(ActionEvent event) {
        showAlert("Add Patient", "This feature will allow you to add a patient.");
    }

    @FXML
    private void viewAllPatients(ActionEvent event) {
        StringBuilder patientsList = new StringBuilder("Patients:\n");
        patientService.getAllPatients().forEach(patient -> patientsList.append(patient.toString()).append("\n"));
        showTextAlert("View All Patients", patientsList.toString());
    }

    @FXML
    private void updatePatient(ActionEvent event) {
        showAlert("Update Patient", "This feature will allow you to update patient details.");
    }

    @FXML
    private void filterByName(ActionEvent event) {
        showAlert("Filter Patients by Name", "This feature will allow you to filter patients by name.");
    }

    @FXML
    private void filterByAge(ActionEvent event) {
        showAlert("Filter Patients by Age", "This feature will allow you to filter patients by age.");
    }

    @FXML
    private void deletePatient(ActionEvent event) {
        showAlert("Delete Patient", "This feature will allow you to delete a patient.");
    }

    @FXML
    private void createAppointment(ActionEvent event) {
        showAlert("Create Appointment", "This feature will allow you to create an appointment.");
    }

    @FXML
    private void viewAllAppointments(ActionEvent event) {
        StringBuilder appointmentsList = new StringBuilder("Appointments:\n");
        appointmentService.getAllAppointments().forEach(appointment -> appointmentsList.append(appointment.toString()).append("\n"));
        showTextAlert("View All Appointments", appointmentsList.toString());
    }

    @FXML
    private void cancelAppointment(ActionEvent event) {
        showAlert("Cancel Appointment", "This feature will allow you to cancel an appointment.");
    }

    @FXML
    private void finishAppointment(ActionEvent event) {
        showAlert("Finish Appointment", "This feature will allow you to mark an appointment as finished.");
    }

    @FXML
    private void deleteAppointment(ActionEvent event) {
        showAlert("Delete Appointment", "This feature will allow you to delete an appointment.");
    }

    @FXML
    private void filterByStatus(ActionEvent event) {
        showAlert("Filter Appointments by Status", "This feature will allow you to filter appointments by status.");
    }

    @FXML
    private void filterByPatientName(ActionEvent event) {
        showAlert("Filter Appointments by Patient Name", "This feature will allow you to filter appointments by patient name.");
    }

    @FXML
    private void filterByDate(ActionEvent event) {
        showAlert("Filter Appointments by Date", "This feature will allow you to filter appointments by date.");
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        System.exit(0);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showTextAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        TextArea textArea = new TextArea(content);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }
}
