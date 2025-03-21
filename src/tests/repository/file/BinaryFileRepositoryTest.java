package tests.repository.file;

import repository.file.binary.BinaryFileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import tests.repository.TestEntity;


import java.io.File;
import java.util.List;

public class BinaryFileRepositoryTest {
    private static final String filename = "test_binary_file.bin";
    private BinaryFileRepository<Integer, TestEntity> repository;

    @BeforeEach
    public void setUp() {
        repository = new BinaryFileRepository<>(filename);
    }

    @AfterEach
    public void tearDown() {
        File file = new File(filename);
        if(file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testAdd_newEntity_true(){
        TestEntity entity = new TestEntity(1,"Name");

        repository.add(entity);
        List<TestEntity> entities = (List<TestEntity>) repository.findAll();
        assertEquals(1, entities.size(), "Repo should have 1 entity! ");
    }

    @Test
    public void testDelete_entity_true(){
        TestEntity entity = new TestEntity(1,"Name");

        repository.add(entity);
        repository.delete(1);
        List<TestEntity> entities = (List<TestEntity>) repository.findAll();
        assertTrue(entities.isEmpty(), "Repo should be empty! ");
    }
}
