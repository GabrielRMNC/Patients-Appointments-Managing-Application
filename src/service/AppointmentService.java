package service;

import domain.Appointment;
import domain.Patient;
import filters.FilterAppointmentsByStatus;
import filters.FilterAppointmentsByPatientName;
import filters.FilterAppointmentsByDate;
import repository.IRepository;
import repository.FilteredRepository;

import java.util.Optional;

public class AppointmentService<ID> {
    private IRepository<ID, Appointment<ID>> appointmentRepository;
    private IRepository<ID, Patient<ID>> patientRepository;

    public AppointmentService(IRepository<ID, Appointment<ID>> appointmentRepository, IRepository<ID, Patient<ID>> patientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
    }

    public ID createAppointment(ID appointmentId, ID patientId, String date, String status) {
        if(appointmentRepository.findById(appointmentId).isPresent()) {
            throw new IllegalArgumentException("Appointment already exists");
        }

        if(patientRepository.findById(patientId).isEmpty()) {
            throw new IllegalArgumentException("Patient ID " + patientId + " not found");
        }
        Appointment<ID> newAppointment = new Appointment<>(appointmentId, patientId, date, status);
        return appointmentRepository.add(newAppointment);
    }

    public Iterable<Appointment<ID>> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment<ID>> getAppointmentById(ID idToViewBy) {
        return appointmentRepository.findById(idToViewBy);
    }

    public void cancelAppointment(ID idToBeCancelled) {
        Optional<Appointment<ID>> appointmentToBeCancelled = appointmentRepository.findById(idToBeCancelled);
        if(appointmentToBeCancelled.isEmpty()){
            throw new IllegalArgumentException("Appointment with ID " + idToBeCancelled + " not found");
        }
        Appointment<ID> appointment = appointmentToBeCancelled.get();
        appointment.setStatus("cancelled");
        appointmentRepository.modify(appointment);
    }

    public void finishAppointment(ID idToBeFinished) {
        Optional<Appointment<ID>> appointmentToBeFinished = appointmentRepository.findById(idToBeFinished);
        if(appointmentToBeFinished.isEmpty()){
            throw new IllegalArgumentException("Appointment with ID " + idToBeFinished + " not found");
        }
        Appointment<ID> appointment = appointmentToBeFinished.get();
        appointment.setStatus("finished");
        appointmentRepository.modify(appointment);
    }

    public void deleteAppointment(ID idToBeDeleted) {
        if(appointmentRepository.findById(idToBeDeleted).isEmpty()){
            throw new IllegalArgumentException("Appointment with ID " + idToBeDeleted + " not found");
        }
        appointmentRepository.delete(idToBeDeleted);
    }

    public Iterable<Appointment<ID>> filterByStatus(String statusToFilterBy) {
        FilterAppointmentsByStatus<ID> statusFilter = new FilterAppointmentsByStatus<>(statusToFilterBy);

        FilteredRepository<ID, Appointment<ID>> filteredRepository = new FilteredRepository<>(appointmentRepository, statusFilter);
        return filteredRepository.findAll();
    }

    public Iterable<Appointment<ID>> filterByPatientName(String patientNameToFilterBy) {
        FilterAppointmentsByPatientName<ID> patientFilter = new FilterAppointmentsByPatientName<>(patientNameToFilterBy, patientRepository);
        FilteredRepository<ID, Appointment<ID>> filteredRepository = new FilteredRepository<>(appointmentRepository, patientFilter);
        return filteredRepository.findAll();
    }

    public Iterable<Appointment<ID>> filterByDate(String dateToFilterBy) {
        FilterAppointmentsByDate<ID> dateFilter = new FilterAppointmentsByDate<>(dateToFilterBy);
        FilteredRepository<ID, Appointment<ID>> filteredRepository = new FilteredRepository<>(appointmentRepository, dateFilter);
        return filteredRepository.findAll();
    }

    public void updateAppointment(Appointment<ID> newAppointment){
        appointmentRepository.modify(newAppointment);
    }
}