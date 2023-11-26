package by.bsuir.wt.fourth.service.validator.impl;

import by.bsuir.wt.fourth.service.validator.AbstractValidator;

public class NaturalNumberValidator extends AbstractValidator {
    private static final String NATURAL_NUMBER_REGEX = "^(([1-9][0-9]*)|([1-9]))$";

    @Override
    protected String getRegex() {
        return NATURAL_NUMBER_REGEX;
    }
}