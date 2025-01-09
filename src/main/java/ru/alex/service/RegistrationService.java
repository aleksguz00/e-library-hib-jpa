package ru.alex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.model.Person;
import ru.alex.model.Roles;
import ru.alex.repository.PeopleRepository;

import java.util.Optional;

@Service
@Transactional
public class RegistrationService {

    private final PeopleRepository peopleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationService(PeopleRepository peopleRepository, PasswordEncoder passwordEncoder) {
        this.peopleRepository = peopleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    public Optional<Person> findByUsername(String username) {
        return peopleRepository.findByEmail(username);
    }

    public int register(Person person) {
        String password = passwordEncoder.encode(person.getPassword());
        person.setPassword(password);
        person.setRole(Roles.ROLE_USER);

        Person savedPerson = peopleRepository.save(person);

        return savedPerson.getId();
    }
}