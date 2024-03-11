package org.sbsa.deco.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {
    public static boolean validateNotEmpty(String text) {
        if (text == null) return false;
        return !StringUtils.isEmpty(text);
    }

    public static boolean isEmpty(String text) {
        if (text == null) return true;
        return StringUtils.isEmpty(text);
    }

    public static boolean valideEmail(String email) {
        if (email == null) return false;
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    public static boolean valideTel(String tel) {
        if (tel == null) return false;
        String regex = "^\\+(?:[0-9] ?){6,14}[0-9]$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(tel);

        return matcher.matches();
    }

    public static boolean valideDate(String date) {
        if (date == null) return false;
        String regex = "^[0-3]?[0-9]/[0-3]?[0-9]/(?:[0-9]{2})?[0-9]{2}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(date);

        return matcher.matches();
    }

    public static boolean valideNumbers(String num) {
        if (num == null) return false;
        Pattern pattern = Pattern.compile("^(0|[1-9][0-9]*|[1-9][0-9]{0,2}(,[0-9]{3,3})*)$");
        Matcher matcher = pattern.matcher(num);

        return matcher.matches();
    }
}
