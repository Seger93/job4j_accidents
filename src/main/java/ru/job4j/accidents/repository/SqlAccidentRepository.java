package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.Rule;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
@AllArgsConstructor
public class SqlAccidentRepository implements AccidentRepository {

    private final JdbcTemplate jdbc;

    private final SqlTypeRepository sqlTypeRepository;

    private final SqlRuleRepository sqlRuleRepository;

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO accident (name, text, address, type_id) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, accident.getName());
            statement.setString(2, accident.getText());
            statement.setString(3, accident.getAddress());
            statement.setInt(4, accident.getType().getId());
            return statement;
        }, keyHolder);
        Map<String, Object> keys = keyHolder.getKeys();
        int id = (int) keys.get("id");
        accident.setId(id);
        insertAccidentRules(accident);
        return accident;
    }

    private void insertAccidentRules(Accident accident) {
        for (Rule rule : accident.getRule()) {
            jdbc.update("INSERT INTO accident_rule (accident_id, rule_id) VALUES (?, ?)",
                    accident.getId(), rule.getId());
        }
    }

    @Override
    public boolean deleteById(int id) {
        int rowsDeleted = jdbc.update("DELETE FROM accident WHERE id = ?", id);
        return rowsDeleted > 0;
    }

    @Override
    public boolean update(Accident accident) {
        int rowsUpdated = jdbc.update("UPDATE accident "
                        + "SET name = ?, text = ?, address = ?, type_id = ? "
                        + "WHERE id = ?",
                accident.getName(), accident.getText(), accident.getAddress(),
                accident.getType().getId(), accident.getId());
        if (rowsUpdated > 0) {
            jdbc.update("DELETE FROM accident_rule WHERE accident_id = ?", accident.getId());
            insertAccidentRules(accident);
            return true;
        }
        return false;
    }

    @Override
    public Optional<Accident> findById(int id) {
        Accident accident = new Accident();
        jdbc.query(
                "SELECT a.id, a.name, a.text, a.address, t.id as type_id, t.name as type_name, r.id as rule_id, r.name as rule_name "
                        + "FROM accident a "
                        + "JOIN type t ON a.type_id = t.id "
                        + "JOIN accident_rule ar ON a.id = ar.accident_id "
                        + "JOIN rules r ON ar.rule_id = r.id "
                        + "WHERE a.id = ?",
                (rs, row) -> {
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("name"));
                    return Optional.of(accident);
                });
        return Optional.of(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return jdbc.query("SELECT DISTINCT a.id, a.text, a.address, t.name AS type_name, r.name AS rule_name FROM accident a "
                       + "JOIN accident_rule ar ON a.id = ar.accident_id "
                       + "JOIN rules r ON ar.rule_id = r.id "
                       + "JOIN type t ON a.type_id = t.id;",
                (rs, row) -> {
                    Accident accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(sqlTypeRepository.findById(rs.getInt("id")));
                    Set<Integer> set = new HashSet<>();
                    set.add(rs.getInt("id"));
                    Set<Rule> rulSet = new HashSet<>(sqlRuleRepository.findAllById(set));
                    accident.setRule(rulSet);
                    return accident;
                });
    }
}