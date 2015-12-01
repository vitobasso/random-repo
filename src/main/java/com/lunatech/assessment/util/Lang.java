package com.lunatech.assessment.util;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Victor on 01/12/2015.
 */
public class Lang {

    public static <T> Stream<T> getStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

}
