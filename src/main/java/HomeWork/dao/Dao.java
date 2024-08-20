package HomeWork.dao;

import HomeWork.entity.Invoice;

import java.util.List;
import java.util.Optional;

public interface Dao<K,E> {

    List<E> findAll();

    void save(E entity);

    void update(E entity);

    void deleteById(K id);

    Optional<E> findById(K id);

}
