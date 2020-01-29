package com.sbd.databases.service;

import com.sbd.databases.model.Cart;
import com.sbd.databases.model.CartProduct;
import com.sbd.databases.model.Customer;
import com.sbd.databases.model.DTO.*;
import com.sbd.databases.model.ShopOrder;
import com.sbd.databases.repository.CartRepository;
import com.sbd.databases.repository.ShopOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService
{
    private final CartRepository cartRepository;
    private final ManagerService managerService;
    private final ShopOrderRepository shopOrderRepository;
    private final ProductService productService;
    private final CartProductService cartProductService;
    private final WarehouseProductService warehouseProductService;
    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    public CartService(CartRepository cartRepository, ManagerService managerService, ShopOrderRepository shopOrderRepository, ProductService productService, CartProductService cartProductService, WarehouseProductService warehouseProductService)
    {
        this.cartRepository = cartRepository;
        this.managerService = managerService;
        this.shopOrderRepository = shopOrderRepository;
        this.productService = productService;
        this.cartProductService = cartProductService;
        this.warehouseProductService = warehouseProductService;
    }

    public CartWithProductsDTO getNotConfirmedCartOfCustomer(Customer customer)
    {
        Cart cart = cartRepository.getFirstByCustomerAndConfirmed(customer, false);

        return new CartWithProductsDTO(cart);
    }

    @Transactional
    public CartWithShopOrderDTO checkoutCartOfCustomer(Customer customer, AddressDTO addressDTO) throws ResponseStatusException
    {
        Cart cart = cartRepository.getFirstByCustomerAndConfirmed(customer, false);
        if (!cart.getCartProducts().isEmpty())
        {
            cart.setConfirmed(true);
            cartRepository.save(cart);

            Cart cartNew = new Cart();
            cartNew.setConfirmed(false);
            cartNew.setCustomer(customer);
            cartRepository.save(cartNew);

            ShopOrder shopOrder = new ShopOrder();
            shopOrder.setAddress(addressDTO.toString());
            shopOrder.setManager(managerService.getAvailableManager());
            shopOrder.setCustomer(customer);
            shopOrder.setCart(cart);
            shopOrder.setConfirmed(false);
            shopOrderRepository.save(shopOrder);

            cart.getCartProducts()
                    .forEach(warehouseProductService::updateProduct);

            CartWithProductsDTO cartWithProductsDTO = new CartWithProductsDTO(cart);
            ShopOrderDTO shopOrderDTO = new ShopOrderDTO(shopOrder);

            return new CartWithShopOrderDTO(cartWithProductsDTO, shopOrderDTO);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.METHOD_NOT_ALLOWED, "Cart is empty.");
        }
    }

    public List<CartWithShopOrderDTO> getCartsOfCustomer(Customer customer)
    {
        return cartRepository
                .getAllByCustomer(customer)
                .stream()
                .map(CartWithShopOrderDTO::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public CartWithProductsDTO addProductToCart(Integer id, AddProductDTO addProductDTO, Customer customer)
    {
        Cart cart = cartRepository.getFirstByCustomerAndConfirmed(customer, false);
        List<CartProduct> cartProducts = cart.getCartProducts();

        for (CartProduct cartProduct : cartProducts)
        {
            if (cartProduct.getProduct().getId().equals(id))
            {
                cartProduct.setCount(cartProduct.getCount() + addProductDTO.getCount());
                cartProductService.save(cartProduct);

                return new CartWithProductsDTO(cart);
            }
        }

        CartProduct cartProduct = new CartProduct();
        cartProduct.setCount(addProductDTO.getCount());
        cartProduct.setProduct(productService.findById(id));
        cartProduct.setCart(cart);
        cartProductService.save(cartProduct);
        cartProducts.add(cartProduct);
        cart.setCartProducts(cartProducts);

        return new CartWithProductsDTO(cart);
    }

    @Transactional
    public CartWithProductsDTO deleteProductFromCartOfCustomer(Customer customer, Integer cartProductId)
    {
        Cart cart = cartRepository.getFirstByCustomerAndConfirmed(customer, false);
        List<CartProduct> cartProducts = cart.getCartProducts();

        Iterator<CartProduct> iterator = cartProducts.iterator();
        while (iterator.hasNext())
        {
            CartProduct cartProduct = iterator.next();
            if (cartProduct.getId().equals(cartProductId))
            {
                cartProductService.delete(cartProduct);
                iterator.remove();
                break;
            }
        }

        cart.setCartProducts(cartProducts);

        return new CartWithProductsDTO(cart);
    }

    public BigDecimal calculateCart(Integer id)
    {
        Query query = entityManager.createNativeQuery("SELECT dbo.CalculateCart(?)");
        query.setParameter(1, id);

        try
        {
            BigDecimal res = (BigDecimal) query.getSingleResult();
            return res;
        }
        catch (NoResultException e)
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot calculate value for cart with id = " + id.toString());
        }
    }
}
