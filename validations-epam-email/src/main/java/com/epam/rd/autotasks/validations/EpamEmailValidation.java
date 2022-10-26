package com.epam.rd.autotasks.validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EpamEmailValidation {

    public static boolean validateEpamEmail(String email) {
        if(email == null || email.isBlank())
            return false;
        Pattern p = Pattern.compile("^[a-zA-Z]+_[a-zA-Z]+[0-9]{0,}@epam\\.com$");
        Matcher m = p.matcher(email);
        return m.matches();

    }
}





