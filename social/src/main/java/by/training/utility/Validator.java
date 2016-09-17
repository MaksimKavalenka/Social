package by.training.utility;

import static by.training.constants.MessageConstants.FORM_ERROR;

import javax.validation.ValidationException;

public abstract class Validator {

    public static void allNotNull(final Object... args) {
        for (Object arg : args) {
            if (arg == null) {
                throw new ValidationException(FORM_ERROR);
            }
        }
    }

}
