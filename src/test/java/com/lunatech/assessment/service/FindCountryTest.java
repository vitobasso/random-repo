package com.lunatech.assessment.service;

import com.google.common.collect.Lists;
import com.lunatech.assessment.model.Country;
import com.lunatech.assessment.reader.CountryReader;
import com.lunatech.assessment.service.entity.CountryService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static com.lunatech.assessment.TestEntityFactory.createCountry;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FindCountryTest {

    @InjectMocks
    private CountryService service;

    @Mock
    private CountryReader reader;

    private Country country1, country2;

    @Before
    public void setUp() throws Exception {
        country1 = createCountry("Brazil", "BR");
        country2 = createCountry("Netherlands", "NL");

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
        Optional<Country> country = service.find("BR");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindByExactName() throws Exception {
        Optional<Country> country = service.find("Brazil");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindByLowerCaseName() throws Exception {
        Optional<Country> country = service.find("brazil");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindNL() throws Exception {
        Optional<Country> country = service.find("NL");
        assertEquals(country2, country.get());
    }

    @Test
    public void testFindNetherlands() throws Exception {
        Optional<Country> country = service.find("netherlands");
        assertEquals(country2, country.get());
    }

    @Test
    public void testCantFind() throws Exception {
        Optional<Country> country = service.find("US");
        assertFalse(country.isPresent());
    }

}
