package nl.focalor.utobot.base.model.service;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import nl.focalor.utobot.base.model.entity.Order;
import nl.focalor.utobot.base.model.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author focalor
 */
@Service
public class OrderService implements IOrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Override
	public Stream<Order> getAll() {
		return StreamSupport.stream(orderRepo.findAll().spliterator(), false);
	}

	@Override
	public void delete(long id) {
		orderRepo.delete(id);

	}

	@Override
	public Order add(String orderText) {
		Order order = new Order();
		order.setText(orderText);
		orderRepo.save(order);

		return order;
	}

}