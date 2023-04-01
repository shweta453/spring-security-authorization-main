package net.codejava.util;

import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
public class DateHelper {

    public static Date convertStringToDate(String dateString) {
        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(dateString);
            return date;
        } catch (Exception ex) {
            log.error("Exception while parsing date : {}", ex.getMessage());
        }
        return null;
    }

    public static String convertDateToString(LocalDateTime date) {
//        Date date = Calendar.getInstance().getTime();
//        LocalDateTime dateTime = LocalDateTime.parse(date.toLocaleString());
        return date.toString();
    }
}
