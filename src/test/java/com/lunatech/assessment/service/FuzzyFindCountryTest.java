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

import static com.lunatech.assessment.TestEntityFactory.createCountry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FuzzyFindCountryTest {

    @InjectMocks
    private CountryService service;

    @Mock
    private CountryReader reader;

    private Country country1, country2, country3;

    @Before
    public void setUp() throws Exception {
        country1 = createCountry("Brazil", "BR");
        country2 = createCountry("Netherlands", "NL");
        country3 = createCountry("Zimbabwe", "ZW");

        List<Country> countryList = Lists.newArrayList(country1, country2, country3);
        when(reader.read()).thenReturn(countryList);
    }

    @Test
    public void testFindByCode() throws Exception {
        Optional<Country> country = service.findFuzzy("BR");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindBrazilExact() throws Exception {
        Optional<Country> country = service.findFuzzy("Brazil");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindBrazilLowerCase() throws Exception {
        Optional<Country> country = service.findFuzzy("brazil");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindBrazilIncomplete() throws Exception {
        Optional<Country> country = service.findFuzzy("bra");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindBrazilMisspelled() throws Exception {
        Optional<Country> country = service.findFuzzy("brasil");
        assertEquals(country1, country.get());
    }

    @Test
    public void testFindNL() throws Exception {
        Optional<Country> country = service.findFuzzy("NL");
        assertEquals(country2, country.get());
    }

    @Test
    public void testFindNetherlands() throws Exception {
        Optional<Country> country = service.findFuzzy("netherlands");
        assertEquals(country2, country.get());
    }

    @Test
    public void testFindNetherlandsIncomplete() throws Exception {
        Optional<Country> country = service.findFuzzy("nether");
        assertEquals(country2, country.get());
    }

    @Test
    public void testFindNetherlandsMisspelled() throws Exception {
        Optional<Country> country = service.findFuzzy("neterlands");
        assertEquals(country2, country.get());
    }

    @Test
    public void testFindZimbabueIncomplete() throws Exception {
        Optional<Country> country = service.findFuzzy("zimb");
        assertEquals(country3, country.get());
    }

    @Test
    public void testCantFind() throws Exception {
        Optional<Country> country = service.findFuzzy("US");
        assertFalse(country.isPresent());
    }

}
