package ru.alex.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.alex.model.Person;
import ru.alex.service.PeopleService;

import java.util.Optional;

@Component
public class PersonEditValidator implements Validator {

    private final PeopleService peopleService;

    @Autowired
    public PersonEditValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person newPerson = (Person) target;
        Optional<Person> person = peopleService.findById(newPerson.getId());

        if (person.isPresent()) {
            if (!peopleService.isOldEmail(newPerson, person.get()) && person.get().getId() != newPerson.getId()) {
                errors.rejectValue("email", null, "Email already exists");
            }
        }
    }
}
