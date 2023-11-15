package ru.job4j.accidents.repository;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface AccidentRuleRepository {
    Collection<Rule> findAll();
}