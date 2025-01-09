package ru.alex.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alex.model.Person;
import ru.alex.service.RegistrationService;

@Component
public class RegistrationValidator implements Validator {

    private final RegistrationService registrationService;

    @Autowired
    public RegistrationValidator(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        if (registrationService.findByUsername(person.getEmail()).isPresent()) {
            errors.rejectValue("email", null, "Email already exists");
        }
    }
}
