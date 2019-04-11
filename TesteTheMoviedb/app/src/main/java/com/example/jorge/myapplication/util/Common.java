package com.example.jorge.myapplication.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.example.jorge.myapplication.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


/**
 * This class for support all application
 */

public class Common {

    /**
     * Checks if internet is ok .
     */
    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String formatDate(String date){
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        try {
            Date data = formato.parse(date);
            DateFormat df2 = DateFormat.getDateInstance(DateFormat.MEDIUM, new Locale("pt", "BR"));
            return df2.format(data);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return "";
    }

    public static String getFormatVoteAverage(Context context, String getVoteAverage){
        return context.getResources().getString(R.string.string_voteAverage) + " " + getVoteAverage + context.getResources().getString(R.string.string_voteAverage_complement);
    }

    public static String getFormatOriginalLanguage(Context context, String getFormatOriginalLanguage){
        return context.getResources().getString(R.string.string_original_language) + " " + getFormatOriginalLanguage;
    }



}
