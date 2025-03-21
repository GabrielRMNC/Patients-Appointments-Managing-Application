package repository.file.text;

import domain.Patient;

public class TextFilePatientRepository extends TextFileRepository<Long, Patient<Long>> {
    public TextFilePatientRepository(String filename) {
        super(filename);
        super.readFromFile();
    }

    @Override
    protected Patient<Long> parseEntity(String line) {
        String[] tokens = line.split(",");

        long patientId = Long.parseLong(tokens[0].trim());
        String patientName = tokens[1].trim();
        int patientAge = Integer.parseInt(tokens[2].trim());

        return new Patient<>(patientId, patientName, patientAge);
    }

}
