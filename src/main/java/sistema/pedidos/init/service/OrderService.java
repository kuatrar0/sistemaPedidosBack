package sistema.pedidos.init.service;

import sistema.pedidos.init.model.Order;
import sistema.pedidos.init.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order updateOrder(String id, Order orderDetails) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido no encontrado"));
        order.setBuyerId(orderDetails.getBuyerId());
        order.setSellerId(orderDetails.getSellerId());
        order.setProducts(orderDetails.getProducts());
        order.setTotalPrice(orderDetails.getTotalPrice());
        return orderRepository.save(order);
    }

    public void deleteOrder(String id) {
        orderRepository.deleteById(id);
    }
}