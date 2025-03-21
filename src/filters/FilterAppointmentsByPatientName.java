package filters;

import domain.Appointment;
import domain.Patient;
import repository.IRepository;

public class FilterAppointmentsByPatientName<ID> implements Filter<Appointment<ID>> {
    private String patientName;
    private IRepository<ID, Patient<ID>> patientRepository;

    public FilterAppointmentsByPatientName(String patientName, IRepository<ID, Patient<ID>> patientRepository) {
        this.patientName = patientName;
        this.patientRepository = patientRepository;
    }

    @Override
    public boolean accept(Appointment<ID> item) {
        Patient<ID> patient = patientRepository.findById(item.getPatientId()).orElse(null);
        return patient != null && patient.getName().equalsIgnoreCase(patientName);
    }
}