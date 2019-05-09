package com.ge.digital.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a DTO class to store address and links linked to that address
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 4L;
    private String address;
    private List<String> links;

    public Page(){
        this.address = "";
        this.links = new ArrayList<>();
    }

    public Page(String address, List<String> links){
        this.address = address;
        this.links = links;
    }

    public String getAddress() {
        return address;
    }

    public List<String> getLinks() {
        return links;
    }

}
