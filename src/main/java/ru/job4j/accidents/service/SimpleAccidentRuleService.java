package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.JPARuleRepository;

import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleAccidentRuleService implements AccidentRuleService {

   private final JPARuleRepository memoryAccidentRuleRepository;

    @Override
    public Set<Rule> findAll() {
        return  memoryAccidentRuleRepository.findAll();
    }
}