package ru.alex.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.model.Book;
import ru.alex.model.Person;
import ru.alex.repository.PeopleRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Transactional(readOnly = true)
    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Person> findById(int id) {
        return peopleRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Optional<Person> findByEmail(String email) {
        return peopleRepository.findByEmail(email);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person newPerson) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            newPerson.setId(id);
            peopleRepository.save(newPerson);
        }
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> getBooksByPersonId(int id) {
        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()) {
            Hibernate.initialize(person.get().getBooks());
            List<Book> books = person.get().getBooks();

            for (Book book : books) {
                checkExpired(book);
            }

            return books;
        } else {
            return Collections.emptyList();
        }
    }

    @Transactional(readOnly = true)
    public boolean isOldEmail(Person newPerson, Person oldPerson) {
        return newPerson.getEmail().equals(oldPerson.getEmail());
    }

    private void checkExpired(Book book) {
        LocalDateTime takenDate = book.getTakenDate();

        if (takenDate != null) {
            if (takenDate.plusSeconds(10).isBefore(LocalDateTime.now())) {
                book.setExpired(true);
                System.out.println("Expired");
            } else {
                System.out.println("Not expired");
            }
        } else {
            System.out.println("NULL");
        }
    }
}
