
package com.lunatech.assessment;

import com.lunatech.assessment.model.Runway;
import com.lunatech.assessment.reader.RunwayReader;

import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String[] args) throws IOException {

//        AirportReader reader = new AirportReader();
//        List<Airport> airports = reader.read();
//        for (Airport airport : airports) {
//            String str = String.format("%s %s", airport.getName(), airport.getCountryCode());
//            System.out.println(str);
//        }

//        CountryReader reader = new CountryReader();
//        List<Country> countries = reader.read();
//        for (Country country : countries) {
//            String str = String.format("%s %s", country.getName(), country.getCode());
//            System.out.println(str);
//        }

        RunwayReader reader = new RunwayReader();
        List<Runway> runways = reader.read();
        for (Runway runway : runways) {
            String str = String.format("%s %s %s %f %f", runway.getId(), runway.getAirportRef(), runway.getSurface(),
                    runway.getLatitude(), runway.getLongitude());
            System.out.println(str);
        }
    }

}
