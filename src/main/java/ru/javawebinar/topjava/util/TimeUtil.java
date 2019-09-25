package ru.javawebinar.topjava.util;

import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) &gt;= 0 &amp;&amp; lt.compareTo(endTime) &lt;= 0;
    }
}
