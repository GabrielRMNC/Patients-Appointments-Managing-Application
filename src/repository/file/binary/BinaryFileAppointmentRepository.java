package repository.file.binary;

import domain.Appointment;

public class BinaryFileAppointmentRepository<Integer> extends BinaryFileRepository<Integer,Appointment<Integer>>{
    public BinaryFileAppointmentRepository(String filename) {
        super(filename);
    }
}
