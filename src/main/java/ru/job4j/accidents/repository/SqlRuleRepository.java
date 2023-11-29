package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class SqlRuleRepository implements AccidentRuleRepository {

    private final JdbcTemplate jdbc;

    @Override
    public List<Rule> findAll() {
        return jdbc.query("SELECT * FROM rules",
                (rs, rowNum) -> new Rule(rs.getInt("id"), rs.getString("name")));
    }

    @Override
    public Set<Rule> findAllById(Set<Integer> id) {
        String query = "SELECT * FROM rules WHERE id IN (" + id.stream().map(Object::toString).
                collect(Collectors.joining(", ")) + ")";
        return new HashSet<>(jdbc.query(query, (rs, rowNum) -> new Rule(rs.getInt("id"), rs.getString("name"))));
    }
}