package ru.job4j.accidents.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.JPATypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleAccidentType implements AccidentTypeService {

    private final JPATypeRepository memoryAccidentType;

    @Override
    public List<AccidentType> findAll() {
        return (List<AccidentType>) memoryAccidentType.findAll();
    }

    @Override
    public Optional<AccidentType> findById(int id) {
        return memoryAccidentType.findById(id);
    }
}