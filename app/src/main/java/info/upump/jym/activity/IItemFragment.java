package info.upump.jym.activity;

public interface IItemFragment<T> {
//    void addChosenItem(T object);
    void clear();
    void addItem(T object);
}
