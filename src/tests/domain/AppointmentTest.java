package tests.domain;

import domain.Appointment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.format.DateTimeFormatter;


public class AppointmentTest {

    private Appointment<Long> appointment;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @BeforeEach
    public void setUp() {
        appointment = new Appointment<>(1L, 1L, "2023-10-01", "scheduled");
    }

    @Test
    public void testGetId_positiveId_returnsId(){

        assertEquals(1, appointment.getId());
    }

    @Test
    public void testToString_expectedString_returnsStringRepresentation(){
        String expectedString = "1,1,2023-10-01,scheduled";
        assertEquals(expectedString, appointment.toString());
    }

    @Test
    public void testConstructor_date_returnsAppointmentDate(){
        Appointment<Long> appointment3 = new Appointment<>(3L, 1L, "2023-10-01", "cancelled");
        assertEquals("2023-10-01", appointment3.getDate());
    }

    @Test
    public void testConstructor_appointmentStatus_returnsStatus(){
        Appointment<Long> appointment2 = new Appointment<>(2L, 1L, "2023-10-01", "cancelled");
        assertEquals("cancelled", appointment2.getStatus());
    }

    @Test
    public void testSetAppointmentStatus_differentStatus_shouldChange(){
        Appointment<Long> appointment4 = new Appointment<>(4L, 1L, "2023-10-01", "finished");
        appointment4.setStatus("scheduled");
        assertEquals("scheduled", appointment4.getStatus());
    }


}
