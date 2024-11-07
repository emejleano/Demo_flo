package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.Product;
import com.example.demo.Service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editProductForm(@PathVariable Long id, Model model) {
        try {
            Product product = productService.getProductById(id);
            model.addAttribute("product", product);
            return "product-edit";
        } catch (RuntimeException e) {
            model.addAttribute("error", "Product not found for id :: " + id);
            return "product-list"; // Arahkan kembali ke daftar produk jika produk tidak ditemukan
        }
    }

    @PostMapping("/edit/{id}") 
    @PreAuthorize("hasRole('ADMIN')") 
    public String editProduct(@PathVariable Long id, @ModelAttribute("product") Product product) { 
        product.setId(id); 
        productService.saveProduct(product); 
        return "redirect:/product/list"; 
    
    }
    // Menghapus produk berdasarkan ID, hanya dapat diakses oleh ADMIN 
    @GetMapping("/delete/{id}") 
    @PreAuthorize("hasRole('ADMIN')") 
    public String deleteProduct(@PathVariable Long id) { 
        productService.deleteProduct(id); 
        return "redirect:/product/list"; 
    }
}
