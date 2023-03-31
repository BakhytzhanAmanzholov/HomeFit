package kz.fitness.homefit.services;

import java.util.List;

public interface CrudService <T, ID>{
    T save(T entity);
    void delete(ID id);
    T update(T entity);
    T findById(ID id);

}
