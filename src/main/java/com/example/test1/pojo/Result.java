package com.example.test1.pojo;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author OuLa-test
 */
@Data
@NoArgsConstructor
public class Result implements Serializable {
    private Boolean result;
    private String meg;
    private Object data;

    public Result(Boolean result, String meg) {
        this.result = result;
        this.meg = meg;
    }

    public Result(Boolean result, String meg, Object data) {
        this.result = result;
        this.meg = meg;
        this.data = data;
    }
}
