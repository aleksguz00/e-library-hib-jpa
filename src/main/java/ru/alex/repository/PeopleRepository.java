package ru.alex.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alex.model.Person;

import java.util.Optional;

public interface PeopleRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByName(String name);
    Optional<Person> findByEmail(String email);
}
