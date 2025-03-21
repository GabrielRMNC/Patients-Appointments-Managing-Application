package repository;

import domain.Identifiable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

public class MemoryRepository<ID, T extends Identifiable<ID>> implements IRepository<ID, T> {
    private HashMap<ID, T> items = new HashMap<>();

    @Override
    public ID add(T element) {
        items.put(element.getId(), element);
        return element.getId();
    }

    @Override
    public Iterable<T> findAll() {
        return new ArrayList<>(items.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(items.get(id));
    }

    @Override
    public void modify(T updatedItem) {
        items.put(updatedItem.getId(), updatedItem);
    }

    @Override
    public void delete(ID id) {
        items.remove(id);
    }
}