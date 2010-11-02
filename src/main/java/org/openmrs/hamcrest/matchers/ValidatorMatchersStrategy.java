package org.openmrs.hamcrest.matchers;

import org.springframework.validation.Validator;

/**
 * User: JHABLUTZEL
 * Date: 29/10/2010
 * Time: 04:29:30 PM
 */
public class ValidatorMatchersStrategy {
    private Validator validator;
    private String objectName;


    /**
     * Constructs and returns a Validator Matcher Factory,
     * class used to return the hamcrest fluid syntax methods
     *
     * @param validator
     * @param objectName
     */
    public ValidatorMatchersStrategy(Validator validator, String objectName) {
        // TODO validate args
        this.validator = validator;
        this.objectName = objectName;
    }

    public <T> FieldBindingMatcher<T> hasError(FieldBindingMatcher<T> matcher) {
        return matcher;
    }

    public <T> FieldBindingMatcher<T> onField(String field) {
        FieldBindingMatcher<T> tBindingMatcher = new FieldBindingMatcher<T>(validator, objectName, field);
        return tBindingMatcher;
    }

    public <T> FieldBindingMatcher<T> onObject() {
        FieldBindingMatcher<T> tBindingMatcher = new FieldBindingMatcher<T>(validator, objectName, null);
        return tBindingMatcher;
    }

}
