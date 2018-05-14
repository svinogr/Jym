package info.upump.jym.activity;

import info.upump.jym.entity.Workout;

public interface IItemFragment<T> {
//    void addChosenItem(T object);
    void clear();
    void addItem(T object);

    void delete(long id);

    void insertDeletedItem(long id);

    void update(T object);
}
