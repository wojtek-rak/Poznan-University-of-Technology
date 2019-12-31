package com.sbd.databases.controller;

import com.sbd.databases.model.DTO.ManagerLoginDTO;
import com.sbd.databases.model.DTO.ManagerShopOrderDTO;
import com.sbd.databases.model.DTO.SignManagerUpDTO;
import com.sbd.databases.model.Manager;
import com.sbd.databases.service.ManagerService;
import com.sbd.databases.service.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/manager")
public class ManagerController
{
    private final ManagerService managerService;
    private final ShopOrderService shopOrderService;

    @Autowired
    public ManagerController(ManagerService managerService, ShopOrderService shopOrderService)
    {
        this.managerService = managerService;
        this.shopOrderService = shopOrderService;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody ManagerLoginDTO managerLoginDTO)
    {
        return managerService.loginManager(managerLoginDTO);
    }

    @PostMapping("/secure/sign-up")
    @ResponseBody
    public Manager signUp(@RequestBody @Validated SignManagerUpDTO signUpDTO)
    {
        if (!managerService.existsByUsername(signUpDTO.getUsername()))
        {
            Manager manager = new Manager();
            manager.setName(signUpDTO.getName());
            manager.setUsername(signUpDTO.getUsername());
            manager.setPassword(signUpDTO.getPassword());
            managerService.save(manager);

            return manager;
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT, "Manager with such name exists.");
        }
    }

    @GetMapping("/secure/shop-orders")
    @ResponseBody
    public List<ManagerShopOrderDTO> getShopOrders()
    {
        try
        {
            return shopOrderService.findAll();
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @GetMapping("/secure/shop-orders/{id}")
    @ResponseBody
    public ManagerShopOrderDTO getShopOrder(@PathVariable Integer id)
    {
        try
        {
            return shopOrderService.getManagerShopOrderById(id);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @PutMapping("/secure/shop-orders/{id}")
    @ResponseBody
    public ManagerShopOrderDTO assignShopOrder(HttpServletRequest request, @PathVariable Integer id)
    {
        try
        {
            Manager manager = managerService.getManagerFromRequest(request);
            return shopOrderService.assignManager(manager, id);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }

    @PostMapping("/secure/shop-orders/{id}")
    @ResponseBody
    public ManagerShopOrderDTO confirmShopOrder(HttpServletRequest request, @PathVariable Integer id)
    {
        try
        {
            Manager manager = managerService.getManagerFromRequest(request);
            return shopOrderService.confirmShopOrder(manager, id);
        }
        catch (Exception e)
        {
            if (e.getClass().equals(ResponseStatusException.class))
            {
                throw e;
            }

            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something happened, but it's not your fault.");
        }
    }
}
