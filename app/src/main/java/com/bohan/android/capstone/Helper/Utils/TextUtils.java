package com.bohan.android.capstone.Helper.Utils;

import android.text.Html;
import android.text.Spanned;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import com.google.firebase.crash.FirebaseCrash;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Created by Bo Han.
 * Firebase will be deprecated
 */
@SuppressWarnings("deprecation")
public class TextUtils {

    public static String issueNameFromVolume(String issue, String volume, int number) {
        String issueName;
        if (issue != null)
            issueName = String.format(Locale.US, "%s #%d - %s", volume, number, issue);
        else
            issueName = String.format(Locale.US, "%s #%d", volume, number);

        return issueName;
    }

    public static String issueTitleFromVolume(String volume, int number) {
        return String.format(Locale.US, "%s #%d", volume, number);
    }



    public static String dateString(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(date);
    }

    public static String dateStringForToday() {
        return dateString(new Date());
    }

    public static String formattedDate(String origin, String format) {

        SimpleDateFormat oldDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Date storeDate = new Date();

        try {
            storeDate = oldDate.parse(origin);
        } catch (ParseException e) {
            FirebaseCrash.log("Failed in parsing date! Original date : " + origin);
            FirebaseCrash.report(e);
            e.printStackTrace();
        }

        SimpleDateFormat newDate = new SimpleDateFormat(format, Locale.US);

        return newDate.format(storeDate);
    }

    public static String formattedDateForToday() {
        return formattedDate(dateStringForToday(), "MMM d, yyyy");
    }

    public static Spanned spannedHtmlText(String htmlText) {

        Spanned spannedHtmlText;
        String textWithoutCover = htmlText.replaceAll("<h4.*?/table>", "");

        String newText = Jsoup.clean(textWithoutCover, Whitelist.basic());

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
            spannedHtmlText = Html.fromHtml(newText, Html.FROM_HTML_MODE_LEGACY);
         else
            spannedHtmlText = Html.fromHtml(newText);
        return spannedHtmlText;
    }
}
