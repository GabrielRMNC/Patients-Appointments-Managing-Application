package repository.file.text;

import domain.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TextFileAppointmentRepository extends TextFileRepository<Long, Appointment<Long>> {
    private static final DateTimeFormatter  DATE_TIME_FORMATTER
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TextFileAppointmentRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected Appointment<Long> parseEntity(String line) {
        String[] tokens = line.split(",");

        long appointmentId = Long.parseLong(tokens[0].trim());
        long patientId = Long.parseLong(tokens[1].trim());
        String orderDate = tokens[2].trim();
        String orderStatus = tokens[3].trim();

        Appointment<Long> appointment = new Appointment<>(appointmentId,patientId, orderDate,orderStatus);

        return appointment;
    }
}
