package tests.repository.file;

import domain.Appointment;
import repository.file.text.TextFileAppointmentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

public class TextFileAppointmentRepositoryTest {
    private static final String filename = "test_orders.txt";
    private TextFileAppointmentRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new TextFileAppointmentRepository(filename);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAdd_newAppointment_true(){
        Appointment<Long> appointment4 = new Appointment<>(4L, 1L, "2023-10-01", "finished");

        appointment4.setStatus("cancelled");
        repository.add(appointment4);
        List<Appointment<Long>> orders = (List<Appointment<Long>>) repository.findAll();
        assertEquals(appointment4.getId(),orders.getFirst().getId());
    }

    @Test
    public void testDelete_appointment_true(){
        Appointment<Long> appointment3 = new Appointment<>(3L, 1L, "2023-10-01", "finished");

        appointment3.setStatus("cancelled");

        repository.add(appointment3);
        repository.delete(appointment3.getId());
        List<Appointment<Long>> appointments = (List<Appointment<Long>>) repository.findAll();
        assertTrue(appointments.isEmpty(), "Repo should be empty!");
    }

    @Test
    public void testModify_modifyingTheAppointment_true(){
        Appointment<Long> appointment2 = new Appointment<>(2L, 1L, "2023-10-01", "finished");

        appointment2.setStatus("cancelled");

        repository.add(appointment2);

        appointment2.setStatus("scheduled");
        repository.modify(appointment2);

        List<Appointment<Long>> appointments = (List<Appointment<Long>>) repository.findAll();
        assertEquals("scheduled", appointments.getFirst().getStatus());
    }


}
