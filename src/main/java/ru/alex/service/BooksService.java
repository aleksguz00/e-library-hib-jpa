package ru.alex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.alex.model.Book;
import ru.alex.model.BookStatus;
import ru.alex.model.Person;
import ru.alex.repository.BooksRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BooksService {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll(int perPage, int page, boolean isSorted) {
        if (!isSorted) {
            return booksRepository.findAll(PageRequest.of(page, perPage)).getContent();
        } else {
            return booksRepository.findAll(PageRequest.of(page, perPage, Sort.by("year"))).getContent();
        }
    }

    @Transactional(readOnly = true)
    public Optional<Book> findById(int id) {
        return booksRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Book> findByTitleContaining(String title, boolean isSorted) {
        if (isSorted) {
            return booksRepository.findByTitleContainingIgnoreCase(title, Sort.by("year"));
        } else {
            return booksRepository.findByTitleContainingIgnoreCase(title);
        }

    }

    public void save(Book book) {
        book.setStatus(BookStatus.AVAILABLE);

        booksRepository.save(book);
    }

    public void update(int id, Book newBook) {
        Optional<Book> book = findById(id);

        if (book.isPresent()) {
            newBook.setId(id);
            newBook.setStatus(book.get().getStatus());

            booksRepository.save(newBook);
        }
    }

    public void delete(int id) {
        booksRepository.deleteById(id);
    }

    public void assign(int id, Person person) {
        Optional<Book> book = findById(id);

        if (book.isPresent()) {
            book.get().setOwner(person);
            book.get().setStatus(BookStatus.TAKEN);
            book.get().setTakenDate(LocalDateTime.now());

            booksRepository.save(book.get());
        }
    }

    public void release(int id) {
        Optional<Book> book = findById(id);

        if (book.isPresent()) {
            book.get().setOwner(null);
            book.get().setStatus(BookStatus.AVAILABLE);
            book.get().setTakenDate(null);

            booksRepository.save(book.get());
        }
    }
}
