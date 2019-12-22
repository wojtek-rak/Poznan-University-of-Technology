package com.sbd.databases.service;

import com.sbd.databases.filter.JwtTokenUtil;
import com.sbd.databases.model.DTO.ManagerLoginDTO;
import com.sbd.databases.model.Manager;
import com.sbd.databases.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;

@Service
public class ManagerService
{
    private final JwtTokenUtil jwtTokenUtil;
    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(JwtTokenUtil jwtTokenUtil, ManagerRepository managerRepository)
    {
        this.jwtTokenUtil = jwtTokenUtil;
        this.managerRepository = managerRepository;
    }

    public Manager getAvailableManager()
    {
        return managerRepository.getOne(1);
    }

    public Boolean existsByUsername(String username)
    {
        return managerRepository.existsByUsername(username);
    }

    public void save(Manager manager)
    {
        managerRepository.save(manager);
    }

    public Manager getManagerFromRequest(HttpServletRequest request)
    {
        Integer managerId = jwtTokenUtil.getIdFromRequest(request);
        Manager manager = managerRepository.getOne(managerId);

        return manager;
    }

    public String loginManager(ManagerLoginDTO managerLoginDTO)
    {
        Manager manager = managerRepository.getByUsername(managerLoginDTO.getUsername());

        if (manager != null && manager.getPassword().equals(managerLoginDTO.getPassword()))
        {
            return jwtTokenUtil.generateToken(manager);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid username or password.");
        }
    }
}
