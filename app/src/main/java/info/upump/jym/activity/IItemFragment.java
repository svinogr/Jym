package info.upump.jym.activity;

public interface IItemFragment<T> {
    void addChosenItem(long idItem);
    boolean clear();
    void addItem(long longExtra);
    void updateItem(long longExtra);
}
