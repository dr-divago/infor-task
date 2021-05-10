package com.company.infor.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class DateUtils {

    private DateUtils(){}
    public static LocalDateTime convertToUtc(LocalDateTime time) {
        return time.atZone(ZoneId.systemDefault()).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
    }

    public static String updateToCurrentDate() {
        var formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd'T'k:mm");
        return convertToUtc(LocalDateTime.now()).format(formatter);
    }

    public static String updateToUTC(String dataCreated) {
        var formatter = DateTimeFormatter.ofPattern( "dd/MM/yyyy k:mm");
        var customDate = LocalDateTime.parse(dataCreated, formatter);
        return convertToUtc(customDate).toString();
    }
}
