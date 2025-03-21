package service;

import domain.Patient;
import filters.FilterPatientsByName;
import filters.FilterPatientsByAge;
import repository.IRepository;
import repository.FilteredRepository;

import java.util.Optional;

public class PatientService<ID> {
    private IRepository<ID, Patient<ID>> patientRepository;

    public PatientService(IRepository<ID, Patient<ID>> patientRepository) {
        this.patientRepository = patientRepository;
    }

    public ID createPatient(ID patientId, String name, int age) {
        if(patientRepository.findById(patientId).isPresent()) {
            throw new IllegalArgumentException("Patient already exists");
        }
        Patient<ID> newPatient = new Patient<>(patientId, name, age);
        return patientRepository.add(newPatient);
    }

    public Iterable<Patient<ID>> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient<ID>> getPatientById(ID idToViewBy) {
        return patientRepository.findById(idToViewBy);
    }

    public void updatePatient(Patient<ID> newPatient){
        patientRepository.modify(newPatient);
    }

    public void deletePatient(ID idToBeDeleted) {
        if(patientRepository.findById(idToBeDeleted).isEmpty()){
            throw new IllegalArgumentException("Patient with ID " + idToBeDeleted + " not found");
        }
        patientRepository.delete(idToBeDeleted);
    }

    public Iterable<Patient<ID>> filterPatientsByName(String nameToFilterBy) {
        FilterPatientsByName<ID> nameFilter = new FilterPatientsByName<>(nameToFilterBy);
        FilteredRepository<ID, Patient<ID>> filteredRepository = new FilteredRepository<>(patientRepository, nameFilter);
        return filteredRepository.findAll();
    }

    public Iterable<Patient<ID>> filterPatientsByAge(int ageToFilterBy) {
        FilterPatientsByAge<ID> ageFilter = new FilterPatientsByAge<>(ageToFilterBy);
        FilteredRepository<ID, Patient<ID>> filteredRepository = new FilteredRepository<>(patientRepository, ageFilter);
        return filteredRepository.findAll();
    }
}