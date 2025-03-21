package repository;

import domain.Identifiable;

public class PatientRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T> {
}