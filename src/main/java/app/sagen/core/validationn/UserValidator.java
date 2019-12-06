package app.sagen.core.validationn;

import app.sagen.core.model.User;
import app.sagen.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;

@Component
public class UserValidator implements Validator {

    private final UserService userService;
    private Pattern passwordPattern;

    @Autowired
    public UserValidator(UserService userService) {
        this.userService = userService;
        this.passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) { // todo: Fix the messages here....
        User user = (User) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "NotEmpty", "This field is required.");
        if (user.getUsername().length() < 5 || user.getUsername().length() > 32) {
            errors.rejectValue("username", "Size", "Please use between 5 and 32 characters.");
        }
        if (userService.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "Duplicate", "Someone already has that username.");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "NotEmpty", "This field is required.");
        if (!passwordPattern.matcher(user.getPassword()).find() || user.getPassword().length() > 32) {
            errors.rejectValue("password", "Size", "Password should be at least 8 characters and contain at least one upper and lowecase letter, one digit and one symbol.");
        }

        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff", "These passwords don't match.");
        }
    }
}
