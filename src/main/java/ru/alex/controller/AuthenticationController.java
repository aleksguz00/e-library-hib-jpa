package ru.alex.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.alex.dto.AuthDto;
import ru.alex.dto.PersonDto;
import ru.alex.model.Person;
import ru.alex.service.PeopleService;
import ru.alex.service.RegistrationService;
import ru.alex.util.DtoConverter;
import ru.alex.util.RegistrationValidator;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private final PeopleService peopleService;
    private final RegistrationService registrationService;

    private final RegistrationValidator registrationValidator;
    private final DtoConverter dtoConverter;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationController(RegistrationService registrationService,
                                    RegistrationValidator registrationValidator,
                                    AuthenticationManager authenticationManager,
                                    PeopleService peopleService, DtoConverter dtoConverter) {
        this.registrationService = registrationService;
        this.registrationValidator = registrationValidator;
        this.authenticationManager = authenticationManager;
        this.peopleService = peopleService;
        this.dtoConverter = dtoConverter;
    }

    @GetMapping("/login")
    public String login(@ModelAttribute("person") PersonDto personDto) {
        return "/auth/login";
    }

    @GetMapping("/register")
    public String register(@ModelAttribute("person") PersonDto personDto) {
        return "/auth/register";
    }

    @PostMapping("/register")
    public String registrationPerform(@ModelAttribute("person") @Valid PersonDto personDto, BindingResult bindingResult) {
        Person person = dtoConverter.dtoToPerson(personDto);

        registrationValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/auth/register";
        }

        int id = registrationService.register(person);

        return "redirect:/people/" + id;
    }

    @PostMapping("/login")
    public String loginPerform(@ModelAttribute("person") AuthDto authDto) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authDto.getEmail(), authDto.getPassword());

        try {
            authenticationManager.authenticate(authenticationToken);
        } catch (BadCredentialsException exception) {
            return "/auth/login?error";
        }

        int id = peopleService.findByEmail(authDto.getEmail()).get().getId();

        return "redirect:/people/" + id;
    }
}
