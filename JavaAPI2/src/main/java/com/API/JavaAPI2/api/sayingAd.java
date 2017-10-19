package com.API.JavaAPI2.api;

import org.hibernate.validator.constraints.Length;
import com.fasterxml.jackson.annotation.JsonProperty;

public class sayingAd {

    
    private String business;
    private String summary;
    private String url;
    private String expiratorydate;

    public sayingAd() {
        // Jackson deserialization
    }

    public sayingAd(String business, String summary,  String expiratorydate, String url) {
        this.business = business;
        this.summary = summary;
        this.url = url;
        this.expiratorydate = expiratorydate;
    }
    

    @JsonProperty
    public String getBusiness() {
        return this.business;
    }
    
    @JsonProperty
    public String getSummary() {
        return this.summary;
    }
    
    @JsonProperty
    public String getUrl() {
        return this.url;
    }
    
    @JsonProperty
    public String getExpiratorydate() {
        return this.expiratorydate;
    }

}