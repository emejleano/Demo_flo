package com.example.demo.controller;

import com.example.demo.Model.Customer;
import com.example.demo.Service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    // Menampilkan daftar pelanggan (untuk USER dan ADMIN)
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "customer-list";
    }

    // Form untuk menambah pelanggan baru (untuk ADMIN)
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addCustomerForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customer-add";
    }

    // Proses penambahan pelanggan (untuk ADMIN)
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        customerService.saveCustomer(customer);
        return "redirect:/customer/list";
    }

    // Form untuk mengedit pelanggan berdasarkan ID (untuk ADMIN)
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        Customer customer = customerService.getCustomerById(id);
        if (customer != null) {
            model.addAttribute("customer", customer);
            return "customer-edit";
        } else {
            return "redirect:/customer/list";
        }
    }

    // Proses update pelanggan (untuk ADMIN)
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editCustomer(@PathVariable Long id, @ModelAttribute("customer") Customer customer) {
        customer.setId(id);
        customerService.saveCustomer(customer);
        return "redirect:/customer/list";
    }

    // Menghapus pelanggan berdasarkan ID (untuk ADMIN)
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "redirect:/customer/list";
    }
}
