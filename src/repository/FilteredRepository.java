package repository;

import filters.Filter;
import domain.Identifiable;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

public class FilteredRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    private IRepository<ID, T> repository;
    private Filter<T> filter;

    public FilteredRepository(IRepository<ID, T> repository, Filter<T> filter) {
        this.repository = repository;
        this.filter = filter;
    }

    @Override
    public ID add(T item) {
        return repository.add(item);
    }

    @Override
    public Iterable<T> findAll() {
        List<T> filteredItems = new ArrayList<>();
        for (T item : repository.findAll()) {
            if (filter.accept(item)) {
                filteredItems.add(item);
            }
        }
        return filteredItems;
    }

    @Override
    public Optional<T> findById(ID id) {
        Optional<T> item = repository.findById(id);
        return item.filter(filter::accept);
    }

    @Override
    public void modify(T updatedItem) {
        repository.modify(updatedItem);
    }

    @Override
    public void delete(ID id) {
        repository.delete(id);
    }
}