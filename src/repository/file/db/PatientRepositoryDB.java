package repository.file.db;

import domain.Patient;
import repository.PatientRepository;

import java.sql.*;
import java.util.Optional;

public class PatientRepositoryDB extends PatientRepository<Long, Patient<Long>> {
    public static final String JDBC_URL = "jdbc:sqlite:patientsAndAppointmentsDB.sqlite";

    public PatientRepositoryDB() {
        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement("SELECT * FROM patients");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String name = resultSet.getString("name");
                Integer age = resultSet.getInt("age");

                Patient<Long> patient = new Patient<>(id, name, age);
                super.add(patient);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error initializing PatientRepositoryDB", e);
        }
    }

    @Override
    public Long add(Patient<Long> entity) {
        super.add(entity); // Add to in-memory repository

        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement(
                    "INSERT INTO patients (id, name, age) VALUES (?, ?, ?)");
            statement.setLong(1, entity.getId());
            statement.setString(2, entity.getName());
            statement.setInt(3, entity.getAge());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error adding patient to database", e);
        }

        return entity.getId();
    }

    @Override
    public void modify(Patient<Long> updatedItem) {
        super.modify(updatedItem); // Update in-memory repository

        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement(
                    "UPDATE patients SET name = ?, age = ? WHERE id = ?");
            statement.setString(1, updatedItem.getName());
            statement.setInt(2, updatedItem.getAge());
            statement.setLong(3, updatedItem.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating patient in database", e);
        }
    }

    @Override
    public void delete(Long id) {
        super.delete(id); // Remove from in-memory repository

        try (Connection conn = DriverManager.getConnection(JDBC_URL)) {
            PreparedStatement statement = conn.prepareStatement("DELETE FROM patients WHERE id = ?");
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting patient from database", e);
        }
    }

    @Override
    public Optional<Patient<Long>> findById(Long id) {
        return super.findById(id); // Use in-memory repository for retrieval
    }
}
