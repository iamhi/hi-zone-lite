package com.github.iamhi.hizone.lite.messagebox.database;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface LiteMessageRepository<T, I> extends Repository<T, I> {

    List<T> findAll();

    List<T> findByBox(String box);
}