package ru.job4j.accidents.service;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.repository.MemoryAccidentType;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleAccidentType implements AccidentTypeService {

    private final MemoryAccidentType memoryAccidentType;

    @Override
    public List<AccidentType> findAll() {
        return memoryAccidentType.findAll();
    }

    @Override
    public void setTypeController(Accident accident) {
        List<AccidentType> list = memoryAccidentType.findAll();
        AccidentType accidentType = list.get(accident.getType().getId());
        accident.setType(accidentType);
    }
}