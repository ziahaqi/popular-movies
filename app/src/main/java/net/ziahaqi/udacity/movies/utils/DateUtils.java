package net.ziahaqi.udacity.movies.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ziahaqi on 6/29/17.
 */

public class DateUtils {

    public static String convertMovieReleaseDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM yyy", Locale.getDefault());
        return dateFormat.format(date);
    }
}
