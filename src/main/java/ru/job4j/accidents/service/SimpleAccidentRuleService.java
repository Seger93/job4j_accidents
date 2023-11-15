package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.MemoryAccidentRuleRepository;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimpleAccidentRuleService implements AccidentRuleService {

    MemoryAccidentRuleRepository memoryAccidentRuleRepository;

    @Override
    public Collection<Rule> findAll() {
        return memoryAccidentRuleRepository.findAll();
    }
}
