package com.lunatech.assessment.model.report;

import java.util.List;

/**
 * Created by Victor on 02/12/2015.
 */
public class Report {

    private List<CountryReportEntry> topCoutries;
    private List<CountryReportEntry> bottomCountries;
    private List<LatitudeReportEntry> commonLatitudes;

    public List<CountryReportEntry> getTopCoutries() {
        return topCoutries;
    }

    public void setTopCoutries(List<CountryReportEntry> topCoutries) {
        this.topCoutries = topCoutries;
    }

    public List<CountryReportEntry> getBottomCountries() {
        return bottomCountries;
    }

    public void setBottomCountries(List<CountryReportEntry> bottomCountries) {
        this.bottomCountries = bottomCountries;
    }

    public List<LatitudeReportEntry> getCommonLatitudes() {
        return commonLatitudes;
    }

    public void setCommonLatitudes(List<LatitudeReportEntry> commonLatitudes) {
        this.commonLatitudes = commonLatitudes;
    }
}
