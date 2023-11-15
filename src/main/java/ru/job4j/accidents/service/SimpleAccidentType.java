package ru.job4j.accidents.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.MemoryAccidentType;

import java.util.Collection;
@Service
@AllArgsConstructor
public class SimpleAccidentType implements AccidentTypeService {

    private final MemoryAccidentType memoryAccidentType;

    @Override
    public Collection<AccidentType> findAll() {
        return memoryAccidentType.findAll();
    }
}
