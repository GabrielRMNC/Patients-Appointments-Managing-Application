package tests.repository.memory;


import tests.repository.FakeRepository;
import repository.FilteredRepository;
import org.junit.jupiter.api.Test;
import filters.FilterPatientsByName;

public class InMemoryFilteredRepositoryTest {
    public FilterPatientsByName<Integer> filterByFlavour = new FilterPatientsByName<>("ananas");


    @Test
    public void test_findAll_emptyRepository_throwsException(){
        FakeRepository fakeRepository = new FakeRepository();
        fakeRepository.findAllShouldThrowException = true;
        FilteredRepository filteredRepository = new FilteredRepository<>(fakeRepository, filterByFlavour);
        try{
            filteredRepository.findAll();
            assert false;
        }catch (Exception e){}
    }
}
