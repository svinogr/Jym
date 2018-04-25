package info.upump.jym.activity;

/**
 * Created by explo on 26.03.2018.
 */

public interface IDescriptionFragment<T> {
    T getChangeableItem();
    void updateItem(T object);
}
