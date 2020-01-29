package com.sbd.databases.service;

import com.sbd.databases.model.Category;
import com.sbd.databases.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public void save(Category category)
    {
        categoryRepository.save(category);
    }

    public Optional<Category> findByName(String name)
    {
        return categoryRepository.findByName(name);
    }

    public List<Category> findAll()
    {
        return categoryRepository.findAll();
    }
}
