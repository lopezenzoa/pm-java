package model.interfaces;

public interface CRUDable<T> {
    void create();
    void read();
    void update(T newModel);
    void delete();
}
