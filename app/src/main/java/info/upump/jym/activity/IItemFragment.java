package info.upump.jym.activity;

import java.util.List;

/**
 * Created by explo on 28.03.2018.
 */

public interface IItemFragment<T> {
    void addChosenItem(long idItem);
    boolean clear();
    void addItem(long longExtra);
}
