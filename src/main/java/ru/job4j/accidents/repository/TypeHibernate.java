package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Repository
public class TypeHibernate implements AccidentTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<AccidentType> findAll() {
        return crudRepository.query("FROM AccidentType", AccidentType.class);
    }

    @Override
    public AccidentType findById(int id) {
        return crudRepository.getSingleExample(
                "from AccidentType where id in :fRulesId", AccidentType.class,
                Map.of("fRulesId", id)
        );
    }
}