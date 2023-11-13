package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.MemoryAccidentRepository;

import java.util.Collection;
import java.util.Optional;

@Service
@ThreadSafe
@AllArgsConstructor
public class SimpleAccidentsService implements AccidentService {

    private final MemoryAccidentRepository accidentRepository;

    @Override
    public Accident save(Accident accident) {
        return accidentRepository.save(accident);
    }

    @Override
    public boolean deleteById(int id) {
        return accidentRepository.deleteById(id);
    }

    @Override
    public boolean update(Accident accident) {
        return accidentRepository.update(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return accidentRepository.findAll();
    }
}