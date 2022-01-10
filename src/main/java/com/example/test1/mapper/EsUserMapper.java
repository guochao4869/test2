package com.example.test1.mapper;

import com.example.test1.pojo.EsUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * 继承crud
 */
public interface EsUserMapper extends CrudRepository<EsUser, String> {

    List<EsUser> findAllByName(String name);

    List<EsUser> findByNameOrderByAge();

    @Override
    Iterable<EsUser> findAll();
}
