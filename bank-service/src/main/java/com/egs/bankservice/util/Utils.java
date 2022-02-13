package com.egs.bankservice.util;

import java.util.Random;

public class Utils {
    public static String getRandomNumberStringSixChar() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    public static String getRandomNumberStringFourChar() {
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        return String.format("%04d", number);
    }
}
