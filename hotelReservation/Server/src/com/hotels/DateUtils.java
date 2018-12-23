package com.hotels;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

// a helper class that's deals with validating and calculating dates in a format of "dd/MM/yyyy"

public class DateUtils {

    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static LocalDate today = LocalDate.now(ZoneId.systemDefault());
//    private static final String dateBeforeString = null;
//    private static final String dateAfterString = null;

// this methods calculates the number days between two given dates
    public static int calculateDays(LocalDate dateBefore, LocalDate dateAfter) {

        long noOfDaysBetween = 0;
        try {
            noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
        } catch(DateTimeParseException exc){
            System.out.printf("is not parsable!%n");
            throw exc;      // Rethrow the exception.
        }
        return (int) noOfDaysBetween;

    }


// this method takes a date string and parse it and return a LocalDate object
    public static LocalDate parseDate(String date){


        LocalDate parsedString = null;
        try {
            parsedString = LocalDate.parse(date, df);

        } catch(DateTimeParseException exc){
            System.out.printf("is not parsable!%n");
            throw exc;      // Rethrow the exception.
        }
        return parsedString;

    }

//this methods return the current day
    public static LocalDate getToday() {
        return today;
    }

    public static String getTodayString() {

        return today.format(df);
    }

//this method validate a given date...
    public static boolean isDateValid(String date) {

        try {
            return parseDate(date).isSupported(ChronoField.ERA);
        } catch (DateTimeParseException e) {
            System.out.println("The date you entered is invalid, please provide a valid format(dd/MM/YYYY)");
        }

        return false;

    }


    public static boolean isDateBefore(LocalDate date, LocalDate otherDate) {

        if (date != null && otherDate != null)
            return date.isBefore(otherDate);
        else
            return false;


    }

    public static boolean isDateAfter(LocalDate date, LocalDate otherDate) {

        if (date != null && otherDate != null)
            return date.isAfter(otherDate);
        else
            return false;


    }

    public static boolean areDatesEquals(LocalDate date, LocalDate otherDate) {

        if (date != null && otherDate != null)
            return date.isEqual(otherDate);
        else
            return false;


    }





}