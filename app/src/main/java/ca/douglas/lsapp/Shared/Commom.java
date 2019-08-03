package ca.douglas.lsapp.Shared;

import java.util.ArrayList;

import ca.douglas.lsapp.DB.Product;
import ca.douglas.lsapp.DB.StoreProduct;
import ca.douglas.lsapp.DB.User;

public class Commom {
    public static User currentUser = new User();//Get from firebase when login is succesfull
    public static ArrayList<Product> products = new ArrayList<>();
    public static ArrayList<StoreProduct> storeProducts = new ArrayList<>();
    public static final String AWS_URL = "ec2-3-84-15-86.compute-1.amazonaws.com";
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

}
