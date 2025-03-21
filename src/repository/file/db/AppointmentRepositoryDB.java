package repository.file.db;

import domain.Appointment;
import repository.AppointmentRepository;

import java.sql.*;
import java.util.Optional;

public class AppointmentRepositoryDB extends AppointmentRepository<Long, Appointment<Long>> {
    public static final String JDBC_URL = "jdbc:sqlite:patientsAndAppointmentsDB.sqlite";

    public AppointmentRepositoryDB() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM appointments");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long idPatient = resultSet.getLong("patient_id");
                String date = resultSet.getString("date");
                String status = resultSet.getString("status");

                Appointment<Long> appointment = new Appointment<>(id,idPatient, date, status);
                super.add(appointment);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing PatientRepositoryDB", e);
        }
    }

    @Override
    public Long add(Appointment<Long> entity) {
        super.add(entity);

        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO appointments (id,patient_id,date,status) VALUES (?, ?, ?, ?)");
            statement.setLong(1, entity.getId());
            statement.setLong(2, entity.getId());
            statement.setString(3, entity.getDate());
            statement.setString(4, entity.getStatus());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding appointment to database", e);
        }

        return entity.getId();
    }

    @Override
    public void modify(Appointment<Long> updatedItem) {
        super.modify(updatedItem);

        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE appointments SET patient_id = ?, date = ?, status = ? WHERE id = ?");
            statement.setLong(1, updatedItem.getPatientId());
            statement.setString(2, updatedItem.getDate());
            statement.setString(3, updatedItem.getStatus());
            statement.setLong(4, updatedItem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating appointment in database", e);
        }
    }

    @Override
    public void delete(Long id) {
        super.delete(id);

        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM appointments WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting appointment from database", e);
        }
    }

    @Override
    public Optional<Appointment<Long>> findById(Long id) {
        return super.findById(id);
    }
}
