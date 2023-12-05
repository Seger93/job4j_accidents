package ru.job4j.accidents.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.Set;

@Repository
public interface JPARuleRepository extends CrudRepository<Rule, Integer> {
    Set<Rule> findAllById(Iterable<Integer> ids);

    Set<Rule> findAll();
}