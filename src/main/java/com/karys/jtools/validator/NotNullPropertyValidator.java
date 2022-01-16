package com.karys.jtools.validator;

import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotNullPropertyValidator implements ConstraintValidator<NotNullProperty, Property<?>> {

    @Override
    public boolean isValid(Property<?> property, ConstraintValidatorContext constraintValidatorContext) {

        if (null == property || null == property.getValue()) {

            if (property instanceof StringProperty) {
                return StringUtils.isNotBlank((String) property.getValue());
            }
            return false;
        }
        return true;
    }
}
