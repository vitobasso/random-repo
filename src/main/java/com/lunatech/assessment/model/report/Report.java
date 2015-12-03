package com.lunatech.assessment.model.report;

import java.util.List;

/**
 * Created by Victor on 02/12/2015.
 */
public class Report {

    private List<ReportEntry> topCoutries;
    private List<ReportEntry> bottomCountries;

    public List<ReportEntry> getTopCoutries() {
        return topCoutries;
    }

    public void setTopCoutries(List<ReportEntry> topCoutries) {
        this.topCoutries = topCoutries;
    }

    public List<ReportEntry> getBottomCountries() {
        return bottomCountries;
    }

    public void setBottomCountries(List<ReportEntry> bottomCountries) {
        this.bottomCountries = bottomCountries;
    }

}
