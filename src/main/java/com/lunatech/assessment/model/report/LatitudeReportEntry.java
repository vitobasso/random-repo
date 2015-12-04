package com.lunatech.assessment.model.report;

import java.util.Map;

/**
 * Created by Victor on 04/12/2015.
 */
public class LatitudeReportEntry {

    private String latitudeId;
    private Long count;

    public LatitudeReportEntry(Map.Entry<String, Long> entry) {
        this.latitudeId = entry.getKey();
        this.count = entry.getValue();
    }

    public String getLatitudeId() {
        return latitudeId;
    }

    public void setLatitudeId(String latitudeId) {
        this.latitudeId = latitudeId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
