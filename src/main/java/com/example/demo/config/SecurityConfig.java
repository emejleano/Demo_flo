package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/h2-console/**").permitAll() // Mengizinkan akses umum ke halaman tertentu
                .requestMatchers("/product/add", "/product/edit/**", "/product/delete/**").hasRole("ADMIN") // Hak akses admin
                .requestMatchers("/product/list", "/product/filter", "/order/list", "/customer/list").authenticated() // Hak akses untuk user login
                .anyRequest().permitAll() // Permisi ke halaman lain yang tidak terdaftar
            )
            .formLogin(form -> form
                .loginPage("/login") // Halaman login custom
                .defaultSuccessUrl("/menu", true) // Halaman setelah login sukses
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Konfigurasi logout
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**")) // Mengabaikan CSRF untuk H2 Console
            .headers((headers) -> headers.disable()); // Disable frame options untuk H2 Console

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails admin = User.builder()
            .username("admin")
            .password(passwordEncoder().encode("admin123"))
            .roles("ADMIN")
            .build();
        UserDetails user = User.builder()
            .username("user")
            .password(passwordEncoder().encode("user123"))
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(admin, user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
