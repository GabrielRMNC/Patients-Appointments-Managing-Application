package mainApp;

import ui.PatientApp;

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

public class PatientsApp {
    public static void main(String[] args) {

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

        PatientService<Long> patientService = new PatientService<>(patientRepository);
        AppointmentService<Long> appointmentService = new AppointmentService<>(appointmentRepository, patientRepository);

        if (repositoryType.equalsIgnoreCase("inmemory")){
            patientService.createPatient(1L, "Safta Noel", 30);
            patientService.createPatient(2L, "Tudor Sabau", 21);
            patientService.createPatient(3L, "Muresan Tudor", 40);
            patientService.createPatient(4L, "Pavel Skips", 27);
            patientService.createPatient(5L, "Romanica Gabriel", 20);

            appointmentService.createAppointment(1L, 1L, "2023-10-01", "scheduled");
            appointmentService.createAppointment(2L, 2L, "2023-10-02", "cancelled");
            appointmentService.createAppointment(3L, 3L, "2023-10-03", "finished");
            appointmentService.createAppointment(4L, 4L, "2023-10-04", "scheduled");
            appointmentService.createAppointment(5L, 5L, "2023-10-05", "finished");
        }



        Function<String, Long> idConverter = Long::valueOf;
        PatientApp<Long> patientApp = new PatientApp<>(patientService, appointmentService, idConverter);
        patientApp.start();
    }
}