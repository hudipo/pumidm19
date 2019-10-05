package com.hudipo.pum_indomaret.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

}
