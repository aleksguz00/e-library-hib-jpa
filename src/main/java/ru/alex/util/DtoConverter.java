package ru.alex.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.dto.PersonDto;
import ru.alex.model.Person;

@Component
public class DtoConverter {

    private final ModelMapper modelMapper;

    @Autowired
    public DtoConverter(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Person dtoToPerson(PersonDto dto) {
        return modelMapper.map(dto, Person.class);
    }

    public PersonDto personToDto(Person person) {
        return modelMapper.map(person, PersonDto.class);
    }
}
