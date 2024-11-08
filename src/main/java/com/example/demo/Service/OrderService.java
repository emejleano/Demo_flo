package com.example.demo.Service;

import com.example.demo.Model.Order;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Product;
import com.example.demo.Repository.OrderRepository;
import com.example.demo.Repository.CustomerRepository;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    // Mengambil semua data order
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    // Menyimpan atau memperbarui order dengan validasi Customer dan Product
    public Order saveOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Order tidak boleh kosong");
        }
        if (order.getCustomer() == null || order.getProduct() == null) {
            throw new IllegalArgumentException("Order harus memiliki customer dan produk yang valid");
        }

        Optional<Customer> customer = customerRepository.findById(order.getCustomer().getId());
        Optional<Product> product = productRepository.findById(order.getProduct().getId());

        if (customer.isEmpty() || product.isEmpty()) {
            throw new IllegalArgumentException("Customer atau Product tidak valid");
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
