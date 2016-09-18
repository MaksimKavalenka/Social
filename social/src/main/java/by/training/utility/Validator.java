package by.training.utility;

import static by.training.constants.MessageConstants.FORM_ERROR;

import by.training.exception.ValidationException;

public abstract class Validator {

    public static void allNotNull(final Object... args) throws ValidationException {
        for (Object arg : args) {
            if (arg == null) {
                throw new ValidationException(FORM_ERROR);
            }
        }
    }

}
