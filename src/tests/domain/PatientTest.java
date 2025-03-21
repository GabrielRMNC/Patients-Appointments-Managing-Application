package tests.domain;

import domain.Patient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class PatientTest {

    @Test
    public void testPatientConstructor_Name(){
        Patient<Long> patient = new Patient<>(1L, "Red", 65);
        assertEquals("Red", patient.getName());
    }

    @Test
    public void testPatientConstructor_Age(){
        Patient<Long> patient = new Patient<>(1L, "Red", 65);
        assertEquals(65, patient.getAge());
    }

    @Test
    public void testPatientConstructor_Id(){
        Patient<Long> patient = new Patient<>(1L, "Red", 65);
        assertEquals(1, patient.getId());
    }


}
