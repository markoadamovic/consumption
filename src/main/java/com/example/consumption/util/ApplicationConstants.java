package com.example.consumption.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public class ApplicationConstants {

    private ApplicationConstants() {
        // no instances are allowed for the constants class
    }

    public static final Map<String, LocalDateTime> monthAbbreviationToLastDayOfMonth = new HashMap<>();

    static {
        LocalDateTime now = LocalDateTime.now();

        // Create a mapping from month abbreviations to the last day of the month
        monthAbbreviationToLastDayOfMonth.put("JAN", now.withMonth(1).withDayOfMonth(31).withHour(12).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("FEB", getLastDayOfFebruary());
        monthAbbreviationToLastDayOfMonth.put("MAR", now.withMonth(3).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("APR", now.withMonth(4).withDayOfMonth(30).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("MAY", now.withMonth(5).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("JUN", now.withMonth(6).withDayOfMonth(30).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("JUL", now.withMonth(7).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("AUG", now.withMonth(8).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("SEP", now.withMonth(9).withDayOfMonth(30).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("OCT", now.withMonth(10).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("NOV", now.withMonth(11).withDayOfMonth(30).withHour(23).withMinute(59).withSecond(59));
        monthAbbreviationToLastDayOfMonth.put("DEC", now.withMonth(12).withDayOfMonth(31).withHour(23).withMinute(59).withSecond(59));
    }

    private static LocalDateTime getLastDayOfFebruary() {
        int currentYear = LocalDate.now().getYear();
        if (Year.isLeap(currentYear)) {
            return LocalDateTime.of(currentYear, Month.FEBRUARY, 29, 23, 59, 59);
        } else {
            return LocalDateTime.of(currentYear, Month.FEBRUARY, 28, 23, 59, 59);
        }
    }

}
