package com.task.management.Utillity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {

    public static LocalDate parseDate(DateTimeFormatter formatter,String date) {
        return LocalDate.parse(date, formatter);
    }

    public static String formateDate(DateTimeFormatter formatter,LocalDate dueDate) {
        return dueDate.format(formatter);
    }
}
