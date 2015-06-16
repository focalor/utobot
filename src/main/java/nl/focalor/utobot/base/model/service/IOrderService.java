package nl.focalor.utobot.base.model.service;

import java.util.stream.Stream;

import nl.focalor.utobot.base.model.entity.Order;

/**
 * @author focalor
 */
public interface IOrderService {
	public void delete(long id);

	public Order add(String order);

	public Stream<Order> getAll();
}