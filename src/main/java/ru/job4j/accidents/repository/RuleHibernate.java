package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.*;

@AllArgsConstructor
@Repository
public class RuleHibernate implements AccidentRuleRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Rule> findAll() {
       return crudRepository.query("FROM Rule", Rule.class);
    }

    @Override
    public Set<Rule> findAllById(Set<Integer> id) {
        return new HashSet<>(crudRepository.query(
                "from Rule where id in :fRulesId", Rule.class,
                Map.of("fRulesId", id)
        ));
    }
}