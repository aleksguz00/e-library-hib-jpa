package ru.alex.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.model.Book;
import ru.alex.model.Person;
import ru.alex.service.BooksService;
import ru.alex.service.PeopleService;

import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BooksController {

    private final BooksService booksService;
    private final PeopleService peopleService;

    @Autowired
    public BooksController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }

    @GetMapping
    public String index(Model model,
                        @RequestParam(name = "per_page", defaultValue = "10") int perPage,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        @RequestParam(name = "isSorted", defaultValue = "false") boolean isSorted) {
        model.addAttribute("books", booksService.findAll(perPage, page, isSorted));

        return "books/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, @ModelAttribute("person") Person person,Model model) {
        Optional<Book> book = booksService.findById(id);

        if (book.isPresent()) {
            model.addAttribute("book", book.get());

            if (book.get().getOwner() != null) {
                model.addAttribute("owner", book.get().getOwner());
            } else {
                model.addAttribute("people", peopleService.findAll());
            }
        }

        return "books/show";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(name = "search", defaultValue = "") String search,
                         @RequestParam(name = "sort", defaultValue = "false") boolean isSorted) {
        if (!search.isEmpty()) {
            model.addAttribute("search", search);
            model.addAttribute("resultBooks", booksService.findByTitleContaining(search, isSorted));
        }

        return "books/search";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/new";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/new";
        }

        booksService.save(book);

        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        Optional<Book> book = booksService.findById(id);

        book.ifPresent(value -> model.addAttribute("book", value));

        return "books/edit";
    }

    // TODO: Forbid to edit book if it is in use
    @PatchMapping("/{id}")
    public String update(@PathVariable("id") int id, @ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "books/edit";
        }

        booksService.update(id, book);

        return "redirect:/books/{id}";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        booksService.delete(id);

        return "redirect:/books";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        booksService.assign(id, person);

        return "redirect:/books/{id}";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        booksService.release(id);

        return "redirect:/books/{id}";
    }
}
