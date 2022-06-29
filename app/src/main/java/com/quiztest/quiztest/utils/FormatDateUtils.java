package com.quiztest.quiztest.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDateUtils {

    public static String formatDateSaver(String date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            Date past = format.parse(date);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
            return newFormat.format(past);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
