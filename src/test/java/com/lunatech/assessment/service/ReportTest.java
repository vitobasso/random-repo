package com.lunatech.assessment.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.model.report.CountryReportEntry;
import com.lunatech.assessment.model.report.Report;
import com.lunatech.assessment.service.entity.AirportService;
import com.lunatech.assessment.service.entity.CountryService;
import com.lunatech.assessment.service.entity.RunwayService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lunatech.assessment.TestEntityFactory.createAirport;
import static com.lunatech.assessment.TestEntityFactory.createCountry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportTest {

    @InjectMocks
    private ReportService reportService =  new ReportService(1);

    @Mock private CountryService countryService;
    @Mock private AirportService airportService;
    @Mock private RunwayService runwayService;

    private Country brazil, netherlands, england;
    private Airport pintoMartins, schiphol, guarulhos, heathrow, gatwick, stansted;

    @Before
    public void setUp() throws Exception {
        netherlands = createCountry("Netherlands", "NL");
        brazil = createCountry("Brazil", "BR");
        england = createCountry("United Kingdom", "UK");

        schiphol = createAirport("Schiphol", "NL");
        pintoMartins = createAirport("Pinto Martins", "BR");
        guarulhos = createAirport("Guarulhos", "BR");
        heathrow = createAirport("Heathrow", "UK");
        gatwick = createAirport("Gatwick", "UK");
        stansted = createAirport("Stansted", "UK");

        List<Airport> nlAirports = Lists.newArrayList(schiphol);
        List<Airport> brAirports = Lists.newArrayList(pintoMartins, guarulhos);
        List<Airport> ukAirports = Lists.newArrayList(heathrow, gatwick, stansted);
        when(airportService.findByCountry(netherlands)).thenReturn(nlAirports);
        when(airportService.findByCountry(brazil)).thenReturn(brAirports);
        when(airportService.findByCountry(england)).thenReturn(ukAirports);

        Map<String, Long> counts = new HashMap<>();
        counts.put(netherlands.getCode(), (long) nlAirports.size());
        counts.put(brazil.getCode(), (long) brAirports.size());
        counts.put(england.getCode(), (long) ukAirports.size());
        when(airportService.countByCountryCode()).thenReturn(counts);

        when(countryService.getById("NL")).thenReturn(netherlands);
        when(countryService.getById("BR")).thenReturn(brazil);
        when(countryService.getById("UK")).thenReturn(england);

        when(runwayService.countByLatitudeCircle()).thenReturn(Maps.newHashMap());
        when(runwayService.getRunwaySurfaceTypes(Mockito.any(Airport.class))).thenReturn(Lists.newArrayList());
    }

    @Test
    public void testTopCountries() throws Exception {
        Report report = reportService.createReport();

        List<CountryReportEntry> topCoutries = report.getTopCoutries();
        assertEquals(1, topCoutries.size());
        assertTrue(topCoutries.stream()
                .filter(entry -> entry.getCountry().equals(england))
                .filter(entry -> entry.getAirportCount().equals(3L))
                .findAny().isPresent());
    }

    @Test
    public void testBottomCountries() throws Exception {
        Report report = reportService.createReport();

        List<CountryReportEntry> bottomCoutries = report.getBottomCountries();
        assertEquals(1, bottomCoutries.size());
        assertTrue(bottomCoutries.stream()
                .filter(entry -> entry.getCountry().equals(netherlands))
                .filter(entry -> entry.getAirportCount().equals(1L))
                .findAny().isPresent());
    }

}
