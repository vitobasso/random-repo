package com.lunatech.assessment.util;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Lists.reverse;

/**
 * Created by Victor on 01/12/2015.
 */
public class Lang {

    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static String prompt(String question) {
        System.out.print(question);
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public static String format(Object value, String format) {
        return value != null ? String.format(format, value) : "";
    }

    public static String format(String value) {
        return format(value, "%s");
    }

    public static String format(Integer value) {
        return format(value, "%d");
    }

    public static <T> List<T> getFirstElements(List<T> list, int n) {
        return list.subList(0, n);
    }

    public static <T> List<T> getLastElements(List<T> list, int n) {
        return reverse(list).subList(0, n);
    }

}
