package nl.focalor.utobot.base.model.repository;

import java.io.Serializable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * Created by luigibanzato on 11/04/2015.
 */
@NoRepositoryBean
public interface CrudRepository<T, ID extends Serializable> extends Repository<T, ID> {

	<S extends T> S save(S entity);

	T findOne(ID primaryKey);

	Iterable<T> findAll();

	Long count();

	void delete(T entity);

	void delete(ID primaryKey);

	boolean exists(ID primaryKey);
}
