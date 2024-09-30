package com.ecommerce.productservice.util;


import com.ecommerce.productservice.model.Product;
import com.ecommerce.productservice.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoadedClass{
    CommandLineRunner commandLineRunner(ProductRepository productRepository){
        return args -> {
            List<Product> products = Arrays.asList(
                    Product.builder().name("Apple_iPhone_14").description("Apple iPhone 14").price(new BigDecimal(999)).build(),
                    Product.builder().name("Samsung_Galaxy_S22").description("Samsung Galaxy S22").price(new BigDecimal(899)).build(),
                    Product.builder().name("OnePlus_Nord_2").description("OnePlus Nord 2").price(new BigDecimal(499)).build(),
                    Product.builder().name("Sony_Bravia_TV").description("Sony Bravia TV").price(new BigDecimal(799)).build(),
                    Product.builder().name("LG_Refrigerator").description("LG Refrigerator").price(new BigDecimal(1199)).build(),
                    Product.builder().name("Nike_Air_Max_Shoes").description("Nike Air Max Shoes").price(new BigDecimal(150)).build(),
                    Product.builder().name("Adidas_Superstar_Shoes").description("Adidas Superstar Shoes").price(new BigDecimal(120)).build(),
                    Product.builder().name("MacBook_Pro_16").description("MacBook Pro 16-inch").price(new BigDecimal(2499)).build(),
                    Product.builder().name("Dell_XPS_13").description("Dell XPS 13").price(new BigDecimal(1799)).build(),
                    Product.builder().name("HP_Spectre_x360").description("HP Spectre x360").price(new BigDecimal(1699)).build(),
                    Product.builder().name("Bose_Headphones_700").description("Bose Headphones 700").price(new BigDecimal(379)).build(),
                    Product.builder().name("Sony_WH-1000XM4").description("Sony WH-1000XM4").price(new BigDecimal(349)).build(),
                    Product.builder().name("My_item").description("Custom item").price(new BigDecimal(40)).build()
            );

            products.forEach(product -> {
                if (!productRepository.existsByName(product.getName())) {
                    productRepository.save(product);
                }
                }
            );
        };
    }
}
