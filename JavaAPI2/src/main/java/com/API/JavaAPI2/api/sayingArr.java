package com.API.JavaAPI2.api;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonProperty;

public class sayingArr {

    
private String[] arr;

    public sayingArr() {
        // Jackson deserialization
    }

    public sayingArr(String[] arr) {
        this.arr = arr;
    }
    

    @JsonProperty
    public String[] getArr() {
        return this.arr;
    }
    
    

}