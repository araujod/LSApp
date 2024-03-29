package ca.douglas.lsapp.Shared;

import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import ca.douglas.lsapp.DB.OrderHistory;
import ca.douglas.lsapp.DB.OrderStatus;
import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DB.User;

public class Commom {
    public static User currentUser = new User();//Get from firebase when login is succesfull
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<StoreProduct> storeProducts = new ArrayList<>();
    public static ArrayList<OrderStatus> orderStatus = new ArrayList<>();
    public static final String AWS_URL = "ec2-54-152-117-15.compute-1.amazonaws.com";
    private static final DecimalFormat CURRENCY_FORMATTER = new DecimalFormat("$ #,###.##");
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMMM dd, yyyy HH:mm");
    public static final int DELIVERY_FEE = 5;
    public static ArrayList<Product> productListFromSelectedStore = new ArrayList<>();
    public static ArrayList<OrderHistory> orderHistories = new ArrayList<>();

    /**
     * Round a float to a given number of digits
     *
     * @param numToRound The number to be rounded
     * @param digits     The desired digits
     * @return The float rounded
     */
    public static float roundToDigits(float numToRound, int digits) {
        int tenPow = (int) Math.pow(10, digits);
        return ((float) Math.round(numToRound * tenPow)) / tenPow;
    }

    /**
     * Format a float with an specific currency format
     *
     * @param currency The float to be formatted
     * @return The currency formatted
     */
    public static String getCurrencyFormatted(float currency) {
        return CURRENCY_FORMATTER.format(currency);
    }

    public static String getDateFormatted(Date date) {
        return DATE_FORMAT.format(date);
    }

    public static Date getDate(int year, int month, int day) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * Hide a view which is inside a constraint layout
     *
     * @param view The view to be hidden
     */
    public static void hideComponentInConstraintLayout(View view) {
        view.setVisibility(View.INVISIBLE);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
        params.topMargin = 0;
        if (view instanceof TextView)
            ((TextView) view).setHeight(0);
        else if (view instanceof ConstraintLayout)
            ((ConstraintLayout) view).setMaxHeight(0);
        else if (view instanceof ImageView)
            params.height = 0;
        view.setLayoutParams(params);
    }

    /**
     * Show component in constraint layout by specifying the height in pixels
     *
     * @param view   The view to be shown
     * @param height The height in pixels of the view
     */
    public static void showComponentInConstraintLayout(View view, int height) {
        view.setVisibility(View.VISIBLE);
        if (view instanceof TextView)
            ((TextView) view).setHeight(height);
        else if (view instanceof ConstraintLayout)
            ((ConstraintLayout) view).setMaxHeight(height);
        else if (view instanceof ImageView) {
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            params.height = height;
            view.setLayoutParams(params);
        }
    }
}
