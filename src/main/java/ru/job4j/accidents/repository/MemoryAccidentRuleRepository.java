package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class MemoryAccidentRuleRepository implements AccidentRuleRepository {

    private final Map<Integer, Rule> rules = new ConcurrentHashMap<>();

    public MemoryAccidentRuleRepository() {
        rules.put(1, new Rule(1, "Статья. 1"));
        rules.put(2, new Rule(2, "Статья. 2"));
        rules.put(3, new Rule(3, "Статья. 3"));
    }

    @Override
    public List<Rule> findAll() {
        return rules.values().stream().toList();
    }

    @Override
    public Set<Rule> findAllById(Set<Integer> id) {
        return rules.values().stream().filter(rule -> id.contains(rule.getId())).collect(Collectors.toSet());
    }
}