package repository;

import domain.Patient;
import domain.Appointment;
import repository.file.binary.BinaryFilePatientRepository;
import repository.file.binary.BinaryFileAppointmentRepository;
import repository.file.text.TextFilePatientRepository;
import repository.file.text.TextFileAppointmentRepository;
import repository.file.db.AppointmentRepositoryDB;
import repository.file.db.PatientRepositoryDB;

public class CreateRepository {
    public static IRepository<Long, Patient<Long>> createPatientRepository(String repositoryType, String filename) {
        return switch (repositoryType.toLowerCase()) {
            case "binary" -> new BinaryFilePatientRepository(filename);
            case "text" -> new TextFilePatientRepository(filename);
            case "db" -> new PatientRepositoryDB();
            default -> new PatientRepository();
        };
    }

    public static IRepository<Long, Appointment<Long>> createAppointmentRepository(String repositoryType, String filename) {
        return switch (repositoryType.toLowerCase()) {
            case "binary" -> new BinaryFileAppointmentRepository(filename);
            case "text" -> new TextFileAppointmentRepository(filename);
            case "db" -> new AppointmentRepositoryDB();
            default -> new AppointmentRepository();
        }
                ;
    }
}
