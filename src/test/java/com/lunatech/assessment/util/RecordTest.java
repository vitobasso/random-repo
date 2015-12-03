package com.lunatech.assessment.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class RecordTest{

    @Spy
    private Record record;

    @Test
    public void testString() throws Exception {
        doReturn("ASP").when(record).get("surface");
        String value = record.getString("surface");
        assertEquals("ASP", value);
    }

    @Test
    public void testInt() throws Exception {
        doReturn("5525").when(record).get("length");
        int value = record.getInt("length");
        assertEquals(5525, value);
    }

    @Test
    public void testDouble() throws Exception {
        doReturn("-20.8").when(record).get("latitude");
        double value = record.getDouble("latitude");
        assertEquals(-20.8, value, 0.001);
    }

    @Test
    public void testEmptyInt() throws Exception {
        doReturn("").when(record).get("length");
        Integer value = record.getInt("length");
        assertNull(value);
    }

}
