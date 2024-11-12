package com.example.demo.Service;

import com.example.demo.Model.Product;
import com.example.demo.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Mendapatkan semua produk
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Menyimpan produk dengan validasi harga
    public Product saveProduct(Product product) {
        if (product.getPrice() < 0) {
            throw new IllegalArgumentException("Product price must be positive.");
        }
        return productRepository.save(product);
    }

    // Mendapatkan produk berdasarkan ID, dengan exception handling
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    // Menghapus produk berdasarkan ID
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
