package com.tw.prograd.user;

import com.tw.prograd.user.exception.InValidPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidation {
    private final String regex = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–:;',?/*~$^+=<>])(?=\\S+$).{8,15}$";
    private final Pattern pattern = Pattern.compile(regex);

    public boolean checkPassword(String password) throws InValidPasswordException {
        Matcher matcher = pattern.matcher(password);
        if (!matcher.find()) {
            throw new InValidPasswordException("Invalid Password format");
        }
        return true;
    }
}
