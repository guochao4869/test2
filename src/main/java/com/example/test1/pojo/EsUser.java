package com.example.test1.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class EsUser implements Serializable {

    @Id
    private String id;
    private String name;
    private Integer age;
}
