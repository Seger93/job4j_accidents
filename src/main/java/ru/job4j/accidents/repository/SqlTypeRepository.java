package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;

@AllArgsConstructor
@Repository
public class SqlTypeRepository implements AccidentTypeRepository {
    private final JdbcTemplate jdbc;

    @Override
    public List<AccidentType> findAll() {
        String sql = "SELECT * FROM type";
        return jdbc.query(sql, (rs, rowNum) -> new AccidentType(rs.getInt("id"), rs.getString("name")));
    }
}