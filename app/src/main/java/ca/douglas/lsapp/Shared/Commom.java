package ca.douglas.lsapp.Shared;

import ca.douglas.lsapp.DB.User;

public class Commom {
    public static User currentUser = new User();//Get from firebase when login is succesfull
    public static String Products[][] = new String[2][7];
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
