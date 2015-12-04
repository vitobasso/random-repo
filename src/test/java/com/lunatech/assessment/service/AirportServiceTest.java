package com.lunatech.assessment.service;

import com.google.common.collect.Lists;
import com.lunatech.assessment.model.Airport;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.AirportReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Map;

import static com.lunatech.assessment.TestEntityFactory.createAirport;
import static com.lunatech.assessment.TestEntityFactory.createCountry;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AirportServiceTest {

    @InjectMocks
    private AirportService airportService;

    @Mock
    private AirportReader reader;

    private Country brazil, netherlands;
    private Airport pintoMartins, schiphol, guarulhos;

    @Before
    public void setUp() throws Exception {
        brazil = createCountry("Brazil", "BR");
        netherlands = createCountry("Netherlands", "NL");
        pintoMartins = createAirport("Pinto Martins", "BR");
        schiphol = createAirport("Schiphol", "NL");
        guarulhos = createAirport("Guarulhos", "BR");

        List<Airport> airportList = Lists.newArrayList(pintoMartins, schiphol, guarulhos);
        when(reader.read()).thenReturn(airportList);
    }

    @Test
    public void testListAll() throws Exception {
        List<Airport> airports = airportService.listAll();
        assertNotNull(airports);
        assertEquals(3, airports.size());
        assertTrue(airports.contains(pintoMartins));
        assertTrue(airports.contains(schiphol));
        assertTrue(airports.contains(guarulhos));
    }

    @Test
    public void testFindByCountryBR() throws Exception {
        List<Airport> airports = airportService.findByCountry(brazil);
        assertNotNull(airports);
        assertEquals(2, airports.size());
        assertTrue(airports.contains(pintoMartins));
        assertTrue(airports.contains(guarulhos));
    }

    @Test
    public void testFindByCountryNL() throws Exception {
        List<Airport> airports = airportService.findByCountry(netherlands);
        assertNotNull(airports);
        assertEquals(1, airports.size());
        assertTrue(airports.contains(schiphol));
    }

    @Test
    public void testCountByCountryBR() throws Exception {
        Map<String, Long> count = airportService.countByCountryCode();
        assertNotNull(count);
        assertTrue(count.containsKey("BR"));
        assertTrue(count.containsKey("NL"));
        assertFalse(count.containsKey("US"));
        assertEquals(2, (long) count.get("BR"));
        assertEquals(1, (long) count.get("NL"));
    }


}
