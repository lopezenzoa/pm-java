package model.interfaces;

public interface CRUDable<T> {
    void create(T model);
    void read(T model);
    void update(T newModel);
    void delete(T model);
}
