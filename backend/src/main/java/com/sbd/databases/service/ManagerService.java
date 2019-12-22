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
}
