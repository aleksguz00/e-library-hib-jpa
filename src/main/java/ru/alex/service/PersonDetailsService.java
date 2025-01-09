package ru.alex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.alex.model.Person;
import ru.alex.repository.PeopleRepository;
import ru.alex.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonDetailsService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Person> person = peopleRepository.findByEmail(username);

        if (person.isPresent()) {
            return new PersonDetails(person.get());
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
