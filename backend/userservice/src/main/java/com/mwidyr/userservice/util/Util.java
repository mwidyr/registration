package com.mwidyr.userservice.util;

import java.util.regex.Pattern;

public class Util {
    public static String DATE_OF_BIRTH_FORMAT = "dd-mm-yyyy";

    // see this regex compiler : https://regex101.com/r/mSP6kY/5
    public static String INDONESIA_MOBILE_NUMBER_REGEX = "(\\+62((\\d{3}([ -]\\d{3,})([- ]\\d{4,})?)|(\\d+)))|(\\(\\d+\\) \\d+)|\\d{3}( \\d+)+|(\\d+[ -]\\d+)|\\d+";

    // RFC822 and accepts IP address and server names (for intranet purposes)
    public static String REGEX_VALID_EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";

    public static boolean isValidIndonesiaMobileNumber(String mobileNumber){
        mobileNumber.replaceAll("\\s+","");
        final Pattern p = Pattern.compile(INDONESIA_MOBILE_NUMBER_REGEX);
        return p.matcher(mobileNumber).matches();
    }

    public static boolean isEmailValid(String email) {
        // RFC822 and accepts IP address and server names (for intranet purposes)
        final Pattern EMAIL_REGEX = Pattern.compile(REGEX_VALID_EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        return EMAIL_REGEX.matcher(email).matches();
    }
}
