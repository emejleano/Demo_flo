package com.example.demo.Service;


import com.example.demo.Model.Order;
import com.example.demo.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    // Mengambil semua data order
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    // Menyimpan atau memperbarui order
    public Order saveOrder(Order order) {
        if(order == null){
            throw new IllegalArgumentException("Order tidak boleh kosong");
        }
        if (order.getCustomer() == null || order.getProduct() == null){
            throw new IllegalArgumentException("Order harus merupakan customer dan produk yang valid");
        }
        return orderRepository.save(order);
    }

    // Mengambil order berdasarkan ID
    public Order getOrderById(Long id) { 
        Optional<Order> optionalOrder = orderRepository.findById(id); 
        return optionalOrder.orElse(null); 
}

    // Menghapus order berdasarkan ID
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
