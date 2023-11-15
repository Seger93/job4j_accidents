package ru.job4j.accidents.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.apache.logging.log4j.CloseableThreadContext;
import org.springframework.stereotype.Repository;
import ru.job4j.accidents.model.Accident;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
@ThreadSafe
@AllArgsConstructor
public class MemoryAccidentRepository implements AccidentRepository {

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap();

    private final AtomicInteger nextId = new AtomicInteger(0);

    @Override
    public Accident save(Accident accident) {
        accident.setId(nextId.incrementAndGet());
        return accidents.put(accident.getId(), accident);
    }

    @Override
    public boolean deleteById(int id) {
        return accidents.remove(id) != null;
    }

    @Override
    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(), (id, oldAccident) ->
                new Accident(oldAccident.getId(), accident.getName(), accident.getText(),
                        accident.getAddress(), accident.getType(), accident.getRule())) != null;
    }

    @Override
    public Optional<Accident> findById(int id) {
        return Optional.ofNullable(accidents.get(id));
    }

    @Override
    public Collection<Accident> findAll() {
        return accidents.values();
    }
}