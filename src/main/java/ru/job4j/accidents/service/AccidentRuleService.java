package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AccidentRuleService {
    List<Rule> findAll();

    Set<Rule> findAllById(Set<Integer> id);
}