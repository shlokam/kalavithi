package com.tw.prograd.user;

import com.tw.prograd.user.exception.InValidEmailException;
import com.tw.prograd.user.exception.InValidMobileNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumMap;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailValidationTest {
    EmailValidation emailValidation;

    @BeforeEach
    void beforeEach() {
        emailValidation = new EmailValidation();

    }

    @Test
    public void shouldValidateEmailWhenItIsInProperFormat() {
        assertThat(emailValidation.isEmailValid("himanshi@gmail.com"), is(equalTo(true)));
    }


    @Test
    public void shouldThrowExceptionWhenEmailIsNotInCorrectFormat() {
        assertThrows(InValidEmailException.class, () -> emailValidation.isEmailValid("hima@gmailco"));
    }
}
