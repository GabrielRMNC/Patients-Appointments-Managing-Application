package repository;

import domain.Identifiable;

public class AppointmentRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T> {
}