package repository.file.binary;

import domain.Patient;

public class BinaryFilePatientRepository<Integer> extends BinaryFileRepository<Integer, Patient<Integer>> {
    public BinaryFilePatientRepository(String filename) {
        super(filename);
    }
}
