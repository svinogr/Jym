package info.upump.jym.activity;

public interface IItemFragment<T> {

    void clear();

    void addItem(T object);

    void delete(long id);

    void insertDeletedItem(long id);

    void update(T object);
}
