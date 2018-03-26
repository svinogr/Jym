package info.upump.jym.activity.cycle;

import info.upump.jym.entity.Cycle;

/**
 * Created by explo on 24.03.2018.
 */

public interface IChangeItem<T> {
    void update(T object);
    void save(T object);
    void delete(long id);
    void setInterfaceForDescription(IDescriptionFragment interfaceForDescription);
}
