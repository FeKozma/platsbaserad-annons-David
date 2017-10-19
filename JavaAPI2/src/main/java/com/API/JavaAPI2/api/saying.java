package com.API.JavaAPI2.api;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonProperty;

public class saying {
    private long id;

    @Length(max = 3)
    private String content;

    public saying() {
        // Jackson deserialization
    }

    public saying(long id, String content) {
        this.id = id;
        this.content = content;
    }
    

    @JsonProperty
    public long getId() {
        return id;
    }

    @JsonProperty
    public String getContent() {
        return content;
    }
}