package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.MemoryAccidentRuleRepository;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleAccidentRuleService implements AccidentRuleService {

   private final MemoryAccidentRuleRepository memoryAccidentRuleRepository;

    @Override
    public List<Rule> findAll() {
        return memoryAccidentRuleRepository.findAll();
    }

    @Override
    public Set<Rule> findAllById(Set<Integer> id) {
       return memoryAccidentRuleRepository.findAllById(id);
    }

    @Override
    public void setRuleController(Accident accident, Set<Integer> id) {
        Set<Rule> rules = memoryAccidentRuleRepository.findAllById(id);
        accident.setRule(rules);
    }
}