package com.lunatech.assessment.service;

import com.google.common.collect.Lists;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.CountryReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {

    @InjectMocks
    private CountryService service;

    @Mock
    private CountryReader reader;

    private Country country1, country2;

    @Before
    public void setUp() throws Exception {
        country1 = new Country();
        country1.setName("Brazil");
        country1.setCode("BR");

        country2 = new Country();
        country2.setName("Netherlands");
        country2.setCode("NL");

        List<Country> countryList = Lists.newArrayList(country1, country2);
        when(reader.read()).thenReturn(countryList);
    }

    @Test
    public void testListAll() throws Exception {
        List<Country> countries = service.listAll();
        assertTrue(countries.contains(country1));
        assertTrue(countries.contains(country2));
    }

    @Test
    public void testFindByCode() throws Exception {
        Optional<Country> country = service.findMatch("BR");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindByExactName() throws Exception {
        Optional<Country> country = service.findMatch("Brazil");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindByLowerCaseName() throws Exception {
        Optional<Country> country = service.findMatch("brazil");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindNL() throws Exception {
        Optional<Country> country = service.findMatch("NL");
        assertEquals(country2, country.get());
    }

    @Test
    public void testFindNetherlands() throws Exception {
        Optional<Country> country = service.findMatch("netherlands");
        assertEquals(country2, country.get());
    }

    @Test
    public void testCantFind() throws Exception {
        Optional<Country> country = service.findMatch("US");
        assertFalse(country.isPresent());
    }

}
