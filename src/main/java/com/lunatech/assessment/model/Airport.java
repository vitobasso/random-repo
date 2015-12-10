package com.lunatech.assessment.model;

/**
 * Created by Victor on 01/12/2015.
 */
public class Airport implements ChildEntity {

    private String id;
    private String name;
    private String countryCode;

    @Override
    public String getParentId() {
        return getCountryCode();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
