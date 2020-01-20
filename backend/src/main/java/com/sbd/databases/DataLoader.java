package com.sbd.databases;

import com.sbd.databases.model.*;
import com.sbd.databases.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Profile("development")
@Component
public class DataLoader implements ApplicationRunner
{

    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private SupplierRepository supplierRepository;
    private CategoryRepository categoryRepository;
    private WarehouseProductRepository warehouseProductRepository;
    private ProductRepository productRepository;
    private CartProductRepository cartProductRepository;
    private ManagerRepository managerRepository;
    private SaleRepository saleRepository;

    @Autowired
    public DataLoader(CustomerRepository customerRepository, CartRepository cartRepository, SupplierRepository supplierRepository, CategoryRepository categoryRepository, WarehouseProductRepository warehouseProductRepository, ProductRepository productRepository, CartProductRepository cartProductRepository, ManagerRepository managerRepository, SaleRepository saleRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
        this.warehouseProductRepository = warehouseProductRepository;
        this.productRepository = productRepository;
        this.cartProductRepository = cartProductRepository;
        this.managerRepository = managerRepository;
        this.saleRepository = saleRepository;
    }

    public void run(ApplicationArguments args) {
        Customer wojtek = new Customer();
        wojtek.setName("Wojtek Rak");
        wojtek.setAddress("1232 Poznań");
        wojtek.setPhone("777 777 777");
        customerRepository.save(wojtek);

        Customer blazej = new Customer();
        blazej.setName("Błażej Krzyżanek");
        blazej.setAddress("134 Poznań");
        blazej.setPhone("099 909 900");
        customerRepository.save(blazej);

        Supplier supplier = new Supplier();
        supplier.setName("Nowa Era");
        supplierRepository.save(supplier);

        Category category = new Category();
        category.setName("book");
        category.setSupplier(supplier);
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setEan(new BigDecimal("213232212123"));
        product1.setCategory(category);
        product1.setName("Mały Książe");
        product1.setVat(new BigDecimal(23));
        product1.setPrice(new BigDecimal("19.99"));
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setEan(new BigDecimal("212332212123"));
        product2.setCategory(category);
        product2.setName("Duży Głód");
        product2.setVat(new BigDecimal(10));
        product2.setPrice(new BigDecimal("27.99"));
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setEan(new BigDecimal("568353247657"));
        product3.setCategory(category);
        product3.setName("Dżuma");
        product3.setVat(new BigDecimal(11));
        product3.setPrice(new BigDecimal("17.49"));
        productRepository.save(product3);

        WarehouseProduct warehouseProduct1 = new WarehouseProduct();
        warehouseProduct1.setCount(20);
        warehouseProduct1.setWarehouseCode(12312312);
        warehouseProduct1.setProduct(product1);
        warehouseProductRepository.save(warehouseProduct1);

        WarehouseProduct warehouseProduct2 = new WarehouseProduct();
        warehouseProduct2.setCount(12);
        warehouseProduct2.setWarehouseCode(234123);
        warehouseProduct2.setProduct(product2);
        warehouseProductRepository.save(warehouseProduct2);

        WarehouseProduct warehouseProduct3 = new WarehouseProduct();
        warehouseProduct3.setCount(46);
        warehouseProduct3.setWarehouseCode(777);
        warehouseProduct3.setProduct(product3);
        warehouseProductRepository.save(warehouseProduct3);

        Cart cart1 = new Cart();
        cart1.setConfirmed(false);
        cart1.setCustomer(wojtek);
        cartRepository.save(cart1);

        Cart cart2 = new Cart();
        cart2.setConfirmed(false);
        cart2.setCustomer(blazej);
        cartRepository.save(cart2);

        CartProduct cartProduct1 = new CartProduct();
        cartProduct1.setProduct(product1);
        cartProduct1.setCart(cart1);
        cartProduct1.setCount(4);
        cartProductRepository.save(cartProduct1);

        CartProduct cartProduct2 = new CartProduct();
        cartProduct2.setProduct(product3);
        cartProduct2.setCart(cart1);
        cartProduct2.setCount(3);
        cartProductRepository.save(cartProduct2);

        Sale sale = new Sale();
        sale.setPercentDiscount(12);
        sale.setProduct(product2);
        saleRepository.save(sale);

        Manager manager = new Manager();
        manager.setName("Administrator");
        manager.setPassword("123456");
        manager.setUsername("prezes123");
        managerRepository.save(manager);
    }
}
