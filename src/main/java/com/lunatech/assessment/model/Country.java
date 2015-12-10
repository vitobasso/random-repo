package com.lunatech.assessment.model;

/**
 * Created by Victor on 01/12/2015.
 */
public class Country implements Entity {

    private String code;
    private String name;

    @Override
    public String getId() {
        return getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
