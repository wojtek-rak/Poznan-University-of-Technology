package com.sbd.databases.controller;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.model.DTO.AssignShopOrderDTO;
import com.sbd.databases.model.DTO.ManagerLoginDTO;
import com.sbd.databases.model.DTO.ManagerShopOrderDTO;
import com.sbd.databases.model.DTO.ManagerSignupDTO;
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
    private final JwtTokenUtil jwtTokenUtil;
    private final ManagerService managerService;
    private final ShopOrderService shopOrderService;

    @Autowired
    public ManagerController(JwtTokenUtil jwtTokenUtil, ManagerService managerService, ShopOrderService shopOrderService)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.managerService = managerService;
        this.shopOrderService = shopOrderService;
    }

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestBody ManagerLoginDTO managerLoginDTO)
    {
        return jwtTokenUtil.generateToken(managerLoginDTO);
    }

    @PostMapping("/secure/signup")
    @ResponseBody
    public Manager signup(@RequestBody ManagerSignupDTO managerSignupDTO)
    {
        if (!managerService.existsByUsername(managerSignupDTO.getUsername()))
        {
            Manager manager = new Manager();
            manager.setName(managerSignupDTO.getName());
            manager.setUsername(managerSignupDTO.getUsername());
            manager.setPassword(managerSignupDTO.getPassword());
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
        List<ManagerShopOrderDTO> shopOrderServiceAll = shopOrderService.findAll();
        return shopOrderServiceAll;
    }

    @PutMapping("/secure/shop-orders")
    @ResponseBody
    public ManagerShopOrderDTO assignShopOrder(HttpServletRequest request, @RequestBody AssignShopOrderDTO assignShopOrderDTO)
    {
        Manager manager = jwtTokenUtil.getManagerFromRequest(request, assignShopOrderDTO.getManagerId());
        return shopOrderService.assignManager(manager, assignShopOrderDTO.getShopOrderId());
    }

}
