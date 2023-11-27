package ru.job4j.accidents.repository;

import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.AccidentType;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MemoryAccidentType implements AccidentTypeRepository {

    private final Map<Integer, AccidentType> types = new ConcurrentHashMap<>();

    public MemoryAccidentType() {
        types.put(0, new AccidentType(0, "Две машины"));
        types.put(1, new AccidentType(1, "Машина и человек"));
        types.put(2, new AccidentType(2, "Машина и велосипед"));
    }

    @Override
    public List<AccidentType> findAll() {
        return types.values().stream().toList();
    }

    @Override
    public AccidentType findById(int id) {
        return types.get(id);
    }
}