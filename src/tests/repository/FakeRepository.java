package tests.repository;

import repository.IRepository;
import domain.Patient;

import java.util.Optional;

public class FakeRepository implements IRepository<Integer, Patient<Integer>> {
    public boolean findAllShouldThrowException = false;

    public void findAllFiltered() {
        if(findAllShouldThrowException) {
            throw new RuntimeException("The repository is empty!");
        }
    }

    @Override
    public Integer add(Patient<Integer> cake) { return null; }

    @Override
    public Optional<Patient<Integer>> findById(Integer id){ return Optional.ofNullable(null);}

    @Override
    public void modify(Patient<Integer> updatedCake){}

    @Override
    public void delete(Integer id){}

    @Override
    public Iterable<Patient<Integer>> findAll(){ return null; }



}
