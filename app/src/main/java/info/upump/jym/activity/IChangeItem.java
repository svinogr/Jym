package info.upump.jym.activity;

/**
 * Created by explo on 24.03.2018.
 */

public interface IChangeItem<T> {
    void updateDescription();
    void delete(long id);
    void setInterfaceForDescription(IDescriptionFragment interfaceForDescription);
    void setInterfaceForItem(IItemFragment interfaceForItem);
}
