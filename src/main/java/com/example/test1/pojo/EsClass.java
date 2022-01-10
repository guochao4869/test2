package com.example.test1.pojo;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

@Data
public class EsClass implements Serializable {
    @Id
    private Integer id;
    private String name;
    private String no;
}
