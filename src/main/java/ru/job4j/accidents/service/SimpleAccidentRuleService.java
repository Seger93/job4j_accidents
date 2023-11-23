package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.MemoryAccidentRuleRepository;
import ru.job4j.accidents.repository.SqlRuleRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class SimpleAccidentRuleService implements AccidentRuleService {

   private final SqlRuleRepository memoryAccidentRuleRepository;

    @Override
    public List<Rule> findAll() {
        return memoryAccidentRuleRepository.findAll();
    }
}