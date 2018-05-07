package com.myth.test;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Data
public class UserEntity {
    @NotEmpty
    private String name;
    @Min(value = 18,message = "年龄最小18")
    private int age;


}
