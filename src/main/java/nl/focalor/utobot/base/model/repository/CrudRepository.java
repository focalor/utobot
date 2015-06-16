package nl.focalor.utobot.base.model.repository;

import java.io.Serializable;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Created by luigibanzato on 11/04/2015.
 */
@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {

	public <S extends T> S save(S entity);

	public T findOne(ID primaryKey);

	public Iterable<T> findAll();

	public Long count();

	public void delete(T entity);

	public void delete(ID primaryKey);

	public boolean exists(ID primaryKey);
}
