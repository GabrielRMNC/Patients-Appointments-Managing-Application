package tests.repository.file;

import domain.Patient;
import repository.file.text.TextFilePatientRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;
public class TextFilePatientRepositoryTest {
    private static final String filename = "test_cakes.txt";
    private TextFilePatientRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new TextFilePatientRepository(filename);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(filename);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAdd_addANewPatient_true(){
        Patient<Long> patient = new Patient<>(1L, "Red", 65);
        repository.add(patient);
        List<Patient<Long>> patients = (List<Patient<Long>>) repository.findAll();
        assertEquals(1,patients.size());
    }

    @Test
    public void testDelete_deleteAPatient_true(){
        Patient<Long> patient = new Patient<>(1L, "Red", 65);
        repository.add(patient);
        repository.delete(patient.getId());
        List<Patient<Long>> patients = (List<Patient<Long>>) repository.findAll();
        assertTrue(patients.isEmpty(),"Repo should be empty!");
    }

    @Test
    public void testModify_updateAPatientInRepository_true(){
        Patient<Long> patient = new Patient<>(1L, "Red", 65);
        repository.add(patient);
        patient.setName("Dream");
        patient.setAge(34);

        repository.modify(patient);

        List<Patient<Long>> cakes = (List<Patient<Long>>) repository.findAll();
        assertEquals("Dream",cakes.getFirst().getName());
    }


    @Test
    public void testEnsureFileExists_fileExists_fileCreated(){
        File file = new File(filename);
        file.delete();

        repository = new TextFilePatientRepository(filename);
        assertTrue(file.exists(), "File should be created!");
    }

    @Test
    public void testEnsureFileExists_FileDoesNotExist_FileCreated() {
        File file = new File(filename);
        file.delete();

        repository = new TextFilePatientRepository(filename);

        assertTrue(file.exists(), "The file should be created if it doesn't exist.");
    }

}
