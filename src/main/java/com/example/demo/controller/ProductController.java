package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Service.ProductService;
import com.example.demo.Model.Product;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Menampilkan daftar produk, bisa diakses oleh ROLE USER atau ADMIN
    @GetMapping("/list")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String getAllProducts(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    }

    // Menampilkan form tambah produk, hanya untuk ROLE ADMIN
    @GetMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String addProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-add";
    }

    // Menyimpan produk yang baru ditambahkan, hanya untuk ROLE ADMIN
    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMIN')")
    public String saveProduct(@ModelAttribute("product") Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be positive.");
        }
        productService.saveProduct(product);
        return "redirect:/product/list";
    }

    // Menampilkan form edit produk, hanya untuk ROLE ADMIN
    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        if (product != null) {
            model.addAttribute("product", product);
            return "product-edit";
        } else {
            return "redirect:/product/list";
        }
    }

    // Menyimpan perubahan produk, hanya untuk ROLE ADMIN
    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String editProduct(@PathVariable Long id, @ModelAttribute("product") Product product) {
        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/product/list";
    }

    // Menghapus produk, hanya untuk ROLE ADMIN
    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/product/list";
    }

    // Filter produk berdasarkan harga minimum
    @GetMapping("/filter")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String filterProducts(@RequestParam double minPrice, Model model) {
        model.addAttribute("products", productService.getAllProducts().stream()
                .filter(product -> product.getPrice() >= minPrice)
                .collect(Collectors.toList()));
        return "product-list";
    }
}
