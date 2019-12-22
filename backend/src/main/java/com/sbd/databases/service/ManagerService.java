package com.sbd.databases.service;

import com.sbd.databases.model.Manager;
import com.sbd.databases.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerService
{
    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository)
    {
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

    public Manager findById(Integer id)
    {
        return managerRepository.findById(id).orElse(null);
    }

    public Manager findByUsername(String username)
    {
        return managerRepository.findByUsername(username).orElse(null);
    }
}
