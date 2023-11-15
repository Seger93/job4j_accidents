package ru.job4j.accidents.service;

import ru.job4j.accidents.model.Rule;

import java.util.Collection;

public interface AccidentRuleService {
    Collection<Rule> findAll();
}