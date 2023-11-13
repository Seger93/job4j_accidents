package ru.job4j.accidents.repository;

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
public class MemoryAccidentRepository implements AccidentRepository {

    private static final  MemoryAccidentRepository INS = new MemoryAccidentRepository();

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap();

    private final AtomicInteger nextId = new AtomicInteger(0);

    private MemoryAccidentRepository() {
        save(new Accident(0, "Авария1", "Столкновение двух авто", "Улица Пушкина"));
        save(new Accident(0, "Авария2", "Столкновение двух авто", "Улица Колотушкина"));
    }

    public static MemoryAccidentRepository getIns() {
        return INS;
    }

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
                        accident.getAddress())) != null;
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