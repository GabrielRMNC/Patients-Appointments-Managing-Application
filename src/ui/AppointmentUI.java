package ui;

import service.AppointmentService;
import domain.Appointment;

import java.util.Scanner;
import java.util.function.Function;

public class AppointmentUI<ID> {
    private AppointmentService<ID> appointmentService;
    private final Scanner scanner = new Scanner(System.in);
    private Function<String, ID> idConverter;

    private static final int CREATE_APPOINTMENT = 1;
    private static final int VIEW_ALL_APPOINTMENTS = 2;
    private static final int CANCEL_APPOINTMENT = 3;
    private static final int FINISH_APPOINTMENT = 4;
    private static final int DELETE_APPOINTMENT = 5;
    private static final int FILTER_BY_STATUS = 6;
    private static final int FILTER_BY_PATIENT_NAME = 7;
    private static final int FILTER_BY_DATE = 8;
    private static final int BACK_MAIN_MENU = 9;

    public AppointmentUI(AppointmentService<ID> appointmentService, Function<String, ID> idConverter) {
        this.appointmentService = appointmentService;
        this.idConverter = idConverter;
    }

    public void manageAppointments() {
        boolean exit = false;
        while (!exit) {
            System.out.println("\nAppointment menu:");
            System.out.println("1. Create an appointment");
            System.out.println("2. View all appointments");
            System.out.println("3. Cancel an appointment");
            System.out.println("4. Finish an appointment");
            System.out.println("5. Delete an appointment");
            System.out.println("6. Filter appointments by status");
            System.out.println("7. Filter appointments by patient name");
            System.out.println("8. Filter appointments by date");
            System.out.println("9. Back to main menu");
            System.out.println(" ");

            System.out.println("Choose an option: ");
            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case CREATE_APPOINTMENT:
                    createAppointment();
                    break;
                case VIEW_ALL_APPOINTMENTS:
                    viewAllAppointments();
                    break;
                case CANCEL_APPOINTMENT:
                    cancelAppointment();
                    break;
                case FINISH_APPOINTMENT:
                    finishAppointment();
                    break;
                case DELETE_APPOINTMENT:
                    deleteAppointment();
                    break;
                case FILTER_BY_STATUS:
                    filterAppointmentsByStatus();
                    break;
                case FILTER_BY_PATIENT_NAME:
                    filterAppointmentsByPatientName();
                    break;
                case FILTER_BY_DATE:
                    filterAppointmentsByDate();
                    break;
                case BACK_MAIN_MENU:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void createAppointment() {
        System.out.println("Enter appointment ID: ");
        String appointmentIDInput = scanner.nextLine();
        ID appointmentID = idConverter.apply(appointmentIDInput);
        System.out.println("Enter patient ID: ");
        String patientIDInput = scanner.nextLine();
        ID patientID = idConverter.apply(patientIDInput);
        System.out.println("Enter appointment date: ");
        String date = scanner.nextLine();
        System.out.println("Enter appointment status (scheduled/cancelled/finished): ");
        String status = scanner.nextLine();

        try {
            appointmentService.createAppointment(appointmentID, patientID, date, status);
            System.out.println("Appointment created successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error creating appointment: " + e.getMessage());
        }
    }

    private void viewAllAppointments() {
        appointmentService.getAllAppointments().forEach(System.out::println);
    }

    private void cancelAppointment() {
        System.out.println("Enter the ID of the appointment you want to cancel: ");
        String appointmentIDInput = scanner.nextLine();
        ID appointmentID = idConverter.apply(appointmentIDInput);
        try {
            appointmentService.cancelAppointment(appointmentID);
            System.out.println("Appointment cancelled successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error cancelling appointment: " + e.getMessage());
        }
    }

    private void finishAppointment() {
        System.out.println("Enter the ID of the appointment you want to finish: ");
        String appointmentIDInput = scanner.nextLine();
        ID appointmentID = idConverter.apply(appointmentIDInput);
        try {
            appointmentService.finishAppointment(appointmentID);
            System.out.println("Appointment finished successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error finishing appointment: " + e.getMessage());
        }
    }

    private void deleteAppointment() {
        System.out.println("Enter the ID of the appointment you want to delete: ");
        String appointmentIDInput = scanner.nextLine();
        ID appointmentID = idConverter.apply(appointmentIDInput);
        try {
            appointmentService.deleteAppointment(appointmentID);
            System.out.println("Appointment deleted successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error deleting appointment: " + e.getMessage());
        }
    }

    private void filterAppointmentsByStatus() {
        System.out.println("Enter appointment status (scheduled/cancelled/finished): ");
        String status = scanner.nextLine();
        appointmentService.filterByStatus(status).forEach(System.out::println);
    }

    private void filterAppointmentsByPatientName() {
        System.out.println("Enter patient name: ");
        String patientName = scanner.nextLine();
        appointmentService.filterByPatientName(patientName).forEach(System.out::println);
    }

    private void filterAppointmentsByDate() {
        System.out.println("Enter appointment date: ");
        String date = scanner.nextLine();
        appointmentService.filterByDate(date).forEach(System.out::println);
    }
}