package repository.file;

import domain.Identifiable;
import repository.MemoryRepository;

public abstract class FileRepository<ID, T extends Identifiable<ID>> extends MemoryRepository<ID, T> {
    protected String filename;

    public FileRepository(String filename) {
        this.filename = filename;
    }

    protected abstract void readFromFile();
    protected abstract void writeToFile();

    @Override
    public ID add(T entity){
        ID id = super.add(entity);
        writeToFile();
        return id;
    }

    @Override
    public void delete(ID id) {
        super.delete(id);
        writeToFile();
    }

    @Override
    public void modify(T entity) {
        super.modify(entity);
        writeToFile();
    }
}
