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

    public void save(Manager manager)
    {
        managerRepository.save(manager);
    }

    public Manager getManagerFromRequest(HttpServletRequest request)
    {
        String name;

        String token = jwtTokenUtil.getTokenFromRequest(request);

        try
        {
            name = jwtTokenUtil.getNameFromToken(token);
            Manager manager = managerRepository.getByUsername(name);

            if (manager.getToken().equals(token))
            {
                return manager;
            }
            else
            {
                throw new Exception();
            }
        }
        catch (Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You can not view this page.");
        }
    }

    public String loginManager(ManagerLoginDTO managerLoginDTO) throws ResponseStatusException
    {
        Manager manager;

        if (existsByUsername(managerLoginDTO.getUsername()))
        {
            manager = managerRepository.getByUsername(managerLoginDTO.getUsername());
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid username or password.");
        }

        if (manager != null && manager.getPassword().equals(managerLoginDTO.getPassword()))
        {
            return jwtTokenUtil.generateToken(manager);
        }
        else
        {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid username or password.");
        }
    }

    public Boolean existsByUsername(String username)
    {
        return managerRepository.existsByUsername(username);
    }

    public String logoutManager(ManagerLoginDTO managerLoginDTO)
    {
        Manager manager = managerRepository.getByUsername(managerLoginDTO.getUsername());
        manager.setToken(null);

        return managerLoginDTO.getUsername();
    }
}
