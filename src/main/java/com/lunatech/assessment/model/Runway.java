package com.lunatech.assessment.model;

/**
 * Created by Victor on 01/12/2015.
 */
public class Runway {

    private String id;
    private String airportRef;
    private Integer length;
    private Integer width;
    private String surface;
    private String latitude;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAirportRef() {
        return airportRef;
    }

    public void setAirportRef(String airportRef) {
        this.airportRef = airportRef;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getSurface() {
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

}
