package com.example.demo.Model;

import jakarta.persistence.*;
import java.time.LocalDate;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;

@Entity
@Table(name ="customer_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
     @ManyToOne
    private Customer customer;

    @ManyToOne
    private Product product;
    private LocalDate orderDate;

     // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct (Product product) {
        this.product = product;
    }
    public LocalDate getOrderDate(){
        return orderDate;
    }
    public void setOrderDate(LocalDate orderDate){
        this.orderDate=orderDate;
    }

}



