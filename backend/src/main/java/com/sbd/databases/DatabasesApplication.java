package com.sbd.databases;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableEncryptableProperties
public class DatabasesApplication
{
    public static void main(String[] args)
    {
        SpringApplication.run(DatabasesApplication.class, args);
    }
}
