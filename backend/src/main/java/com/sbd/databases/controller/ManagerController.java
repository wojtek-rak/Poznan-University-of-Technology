package com.sbd.databases.controller;

import com.sbd.databases.model.DTO.ManagerLoginDTO;
import com.sbd.databases.model.DTO.ManagerShopOrderDTO;
import com.sbd.databases.model.Manager;
import com.sbd.databases.service.ManagerService;
import com.sbd.databases.service.ShopOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        if (managerService.existsByUsername(managerLoginDTO.getUsername()))
        {
            return managerService.loginManager(managerLoginDTO);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Manager with such username does not exist in our database.");
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/secure/shop-orders/{id}")
    @ResponseBody
    public ManagerShopOrderDTO assignShopOrder(HttpServletRequest request, @PathVariable Integer id)
    {
        Manager manager = managerService.getManagerFromRequest(request);
        return shopOrderService.assignManager(manager, id);
    }

    @PostMapping("/secure/shop-orders/{id}")
    @ResponseBody
    public ManagerShopOrderDTO confirmShopOrder(HttpServletRequest request, @PathVariable Integer id)
    {
        Manager manager = managerService.getManagerFromRequest(request);
        return shopOrderService.confirmShopOrder(manager, id);
    }
}
