package com.tw.prograd.user;

import com.tw.prograd.user.exception.InValidPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordValidationTest {
    PasswordValidation passwordValidation;

    @BeforeEach
    void beforeEach() {
        passwordValidation = new PasswordValidation();

    }

    @Test
    public void shouldValidatePasswordWhenThePasswordIsInProperFormat() throws Exception {
        assertThat(passwordValidation.checkPassword("Himanshi@22"), is(equalTo(true)));
    }


    @Test
    void shouldThrowExceptionWhenNoUpperCaseCharacterInPassword() {
        assertThrows(InValidPasswordException.class, () -> passwordValidation.checkPassword("himanshi@2"));
    }

    @Test
    void shouldThrowExceptionWhenNoLowerCaseCharacterInPassword() {
        assertThrows(InValidPasswordException.class, () -> passwordValidation.checkPassword("HIMANSHI@2"));
    }

    @Test
    void shouldThrowExceptionWhenNoNumbersInAPassword() {
        assertThrows(InValidPasswordException.class, () -> passwordValidation.checkPassword("Himanshi@"));
    }

    @Test
    void shouldThrowExceptionWhenNoSpecialCharacterInPassword() {
        assertThrows(InValidPasswordException.class, () -> passwordValidation.checkPassword("Himanshi22"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordLengthIsGreaterThanFifteen() {
        assertThrows(InValidPasswordException.class, () -> passwordValidation.checkPassword("HimanshiHimanshi@2"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordLengthIsLessThanEight() {
        assertThrows(InValidPasswordException.class, () -> passwordValidation.checkPassword("Him@2"));
    }

    @Test
    void shouldThrowExceptionWhenPasswordHasWhiteSpaces() {
        assertThrows(InValidPasswordException.class, () -> passwordValidation.checkPassword("Himanshi @2"));
    }
}

