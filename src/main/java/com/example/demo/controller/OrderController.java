package com.example.demo.Controller;

import com.example.demo.Model.Order;
import com.example.demo.Model.Customer;
import com.example.demo.Model.Product;
import com.example.demo.Service.OrderService;
import com.example.demo.Service.CustomerService;
import com.example.demo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    // Menampilkan daftar order
    @GetMapping("/list")
    public String listOrders(Model model) {
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute("orders", orders);
        return "order-list";
    }

    // Form untuk menambah order baru
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addOrderForm(Model model) {
        model.addAttribute("order", new Order());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("products", productService.getAllProducts());
        return "order-add";
    }

    // Proses penambahan order
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveOrder(@ModelAttribute("order") Order order) {
        orderService.saveOrder(order);
        return "redirect:/order/list";
    }

    // Form untuk mengedit order berdasarkan ID
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editOrderForm(@PathVariable Long id, Model model) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            model.addAttribute("order", order);
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("products", productService.getAllProducts());
            return "order-edit";
        } else {
            return "redirect:/order/list";
        }
    }

    // Proses update order
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editOrder(@PathVariable Long id, @ModelAttribute("order") Order order) {
        order.setId(id);  // Set ID untuk memastikan update
        orderService.saveOrder(order);
        return "redirect:/order/list";
    }

    // Menghapus order berdasarkan ID
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return "redirect:/order/list";
    }
}
