package ru.alex.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alex.model.Person;
import ru.alex.service.PeopleService;

import java.util.Optional;

@Component
public class PersonCreateValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonCreateValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person newPerson = (Person) target;
        Optional<Person> existingPerson = peopleService.findByEmail(newPerson.getEmail());

        if (existingPerson.isPresent()) {
            errors.rejectValue("email", null, "This email address is already in use");
        }
    }
}
