package com.sbd.databases.repository;

import com.sbd.databases.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Integer>
{
    boolean existsByUsername(String username);

    Optional<Manager> findByUsername(String username);
}
