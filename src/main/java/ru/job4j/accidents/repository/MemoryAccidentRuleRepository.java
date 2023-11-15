package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Repository
public class MemoryAccidentRuleRepository implements AccidentRuleRepository {

    private final List<Rule> rules = new ArrayList<>();

    public MemoryAccidentRuleRepository() {
        rules.add(new Rule(1, "Статья. 1"));
        rules.add(new Rule(2, "Статья. 2"));
        rules.add(new Rule(3, "Статья. 3"));
    }

    @Override
    public Collection<Rule> findAll() {
        return rules;
    }
}