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
    private Integer code;
    private String meg;
    private Object data;

    public Result(Boolean result, Integer code, String meg) {
        this.result = result;
        this.code = code;
        this.meg = meg;
    }

    public Result(Boolean result, Integer code, String meg, Object data) {
        this.result = result;
        this.code = code;
        this.meg = meg;
        this.data = data;
    }

    public Result(Boolean result, Integer code) {
        this.result = result;
        this.code = code;
    }
}
