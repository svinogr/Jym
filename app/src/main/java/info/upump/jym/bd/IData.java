package info.upump.jym.bd;


import java.util.List;

public interface IData<T> {
    List<T> getAll();
    long create(T object);
}
