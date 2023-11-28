package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;

import java.sql.*;
import java.util.*;

@Repository
@AllArgsConstructor
public class SqlAccidentRepository implements AccidentRepository {

    private final JdbcTemplate jdbc;

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
                accident.getType(), accident.getId());
        if (rowsUpdated > 0) {
            jdbc.update("DELETE FROM accident_rule WHERE accident_id = ?", accident.getId());
            insertAccidentRules(accident);
        }
        return false;
    }

    @Override
    public Optional<Accident> findById(int id) {
        String sql = "SELECT a.id, a.text, a.address, t.id AS type_id, t.name AS type_name, r.id AS rule_id, r.name AS rule_name "
                + "FROM accident a "
                + "JOIN accident_rule ar ON a.id = ar.accident_id "
                + "JOIN rules r ON ar.rule_id = r.id "
                + "JOIN type t ON a.type_id = t.id "
                + "WHERE a.id = ?";
        Accident accident = null;
        try (Connection connection = Objects.requireNonNull(jdbc.getDataSource()).getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    accident = new Accident();
                    accident.setId(rs.getInt("id"));
                    accident.setName(rs.getString("text"));
                    accident.setAddress(rs.getString("address"));
                    accident.setType(new AccidentType(rs.getInt("type_id"),
                            rs.getString("type_name")));
                    accident.setRule(new HashSet<>());
                    Rule rule = new Rule();
                    rule.setId(rs.getInt("rule_id"));
                    rule.setName(rs.getString("rule_name"));
                    accident.getRule().add(rule);
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return Optional.of(accident);
    }

    @Override
    public Collection<Accident> findAll() {
        return jdbc.query("SELECT DISTINCT a.id, a.name, a.text, a.address, t.id AS type_id, t.name AS type_name, "
                        + "r.id AS rule_id, r.name AS rule_name FROM accident a "
                        + "JOIN accident_rule ar ON a.id = ar.accident_id "
                        + "JOIN rules r ON ar.rule_id = r.id "
                        + "JOIN type t ON a.type_id = t.id;",
                rs -> {
                    Map<Integer, Accident> accidentMap = new HashMap<>();
                    while (rs.next()) {
                        int id = rs.getInt("id");
                        Accident accident = accidentMap.get(id);
                        if (accident == null) {
                            accident = new Accident();
                            accident.setId(id);
                            accident.setName(rs.getString("name"));
                            accident.setText(rs.getString("text"));
                            accident.setAddress(rs.getString("address"));
                            accident.setType(new AccidentType(rs.getInt("type_id"),
                                    rs.getString("type_name")));
                            accident.setRule(new HashSet<>());
                            accidentMap.put(id, accident);
                        }
                        String ruleName = rs.getString("rule_name");
                        if (ruleName != null) {
                            Rule rule = new Rule();
                            rule.setId(rs.getInt("rule_id"));
                            rule.setName(ruleName);
                            accident.getRule().add(rule);
                        }
                    }
                    return accidentMap.values();
                });
    }
}