package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Rule;

import java.util.List;
import java.util.Set;

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
    public List<Rule> findAllById(Set<Integer> id) {
        Object[] params = id.toArray();
        StringBuilder query = new StringBuilder("SELECT * FROM rules WHERE id IN (");
        for (int i = 0; i < params.length; i++) {
            if (i > 0) {
                query.append(", ");
            }
            query.append("?");
        }
        query.append(")");

        return jdbc.query(query.toString(), params,
                (rs, rowNum) -> new Rule(rs.getInt("id"), rs.getString("name")));
    }
}