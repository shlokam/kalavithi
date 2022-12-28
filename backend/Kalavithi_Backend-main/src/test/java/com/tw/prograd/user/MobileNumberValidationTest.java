package com.tw.prograd.user;

import com.tw.prograd.user.exception.InValidMobileNumberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MobileNumberValidationTest {

    MobileNumberValidation mobileNumberValidation;

    @BeforeEach
    void beforeEach() {
        mobileNumberValidation = new MobileNumberValidation();

    }

    @Test
    public void shouldValidateMobileNumberWhenItIsInProperFormat() {
        assertThat(mobileNumberValidation.isMobileNumberValid("9867812231"), is(equalTo(true)));
    }

    @Test
    public void shouldThrowExceptionWhenMobileNumberIsNotTenDigit() {
        assertThrows(InValidMobileNumberException.class, () -> mobileNumberValidation.isMobileNumberValid("7865433"));
    }

    @Test
    public void shouldThrowExceptionWhenMobileNumberFirstDigitIsNotInTheRangeOfSixToNine() {
        assertThrows(InValidMobileNumberException.class, () -> mobileNumberValidation.isMobileNumberValid("0821644320"));
    }


}
