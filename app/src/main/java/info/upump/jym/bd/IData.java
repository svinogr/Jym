package info.upump.jym.bd;


import java.util.List;

import info.upump.jym.entity.Cycle;
import info.upump.jym.entity.Sets;

public interface IData<T> {
    List<T> getAll();

    long create(T object);

    boolean delete(T object);

    boolean update(T object);

    T getById(long id);

    List<T> getByParentId(long id);

    boolean clear( long id);
    long copyFromTemplate(long idItem, long idPrent);

}
