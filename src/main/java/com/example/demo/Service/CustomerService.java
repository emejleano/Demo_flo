package com.example.demo.Service;

import com.example.demo.Model.Customer;
import com.example.demo.Repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    public List<Customer> getAllCustomers(){
        return customerRepository.findAll();
    }
    public Customer saveCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer getCustomerById(Long id) { 
        Optional<Customer> optionalCustomer = customerRepository.findById(id); 
        return optionalCustomer.orElse(null); 
}

// Menghapus customer berdasarkan ID
    public void deleteCustomer(Long id) { 
        customerRepository.deleteById(id); 
    }
}

