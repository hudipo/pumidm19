package com.hudipo.pum_indomaret.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PumDateFormat {

    public static String rawToDateFormat(String raw) {
        String results = null;
        String OLD_FORMAT = "dd/MM/yyyy";
        String NEW_FORMAT = "yyyy-MM-dd";

        SimpleDateFormat formatInput = new SimpleDateFormat(OLD_FORMAT, Locale.getDefault());
        SimpleDateFormat formatOutput = new SimpleDateFormat(NEW_FORMAT, Locale.getDefault());

        Date dateInput = null;

        try {
            dateInput = formatInput.parse(raw);
        }catch (Exception ignored){

        }
        if (dateInput != null){
            try {
                results = formatOutput.format(dateInput);
            }catch (Exception ignored){

            }
        }

        return results;
    }

    public static String dateFormatServer(String raw){
        String results = null;
        String OLD_FORMAT = "dd/MM/yyyy";
        String NEW_FORMAT = "yyyy-MM-dd";

        SimpleDateFormat formatInput = new SimpleDateFormat(OLD_FORMAT, Locale.getDefault());
        SimpleDateFormat formatOutput = new SimpleDateFormat(NEW_FORMAT, Locale.getDefault());

        Date dateInput = null;

        try {
            dateInput = formatInput.parse(raw);
        }catch (Exception ignored){

        }
        if (dateInput != null){
            try {
                results = formatOutput.format(dateInput);
            }catch (Exception ignored){

            }
        }

        return results;
    }

    public static String dateFormatView(int year, int month, int dayOfMonth){
        String result;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        result = dateFormat.format(calendar.getTime());
        return result;
    }

    public static boolean comparisionDateBefore(String useDate, String respDate) throws ParseException {
        String DATE_FORMAT = "dd/MM/yyyy";
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        Date dateUse = dateFormat.parse(useDate);
        Date dateResp = dateFormat.parse(respDate);

        if (dateUse != null){
            return dateUse.before(dateResp) || dateUse.equals(dateResp);
        }
        return false;
    }
}
