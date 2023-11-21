package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface AccidentRuleRepository {
    Collection<Rule> findAll();

    Collection<Rule> findAllById(Set<Integer> id);
}