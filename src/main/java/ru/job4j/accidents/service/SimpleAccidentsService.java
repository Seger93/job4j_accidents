package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.repository.*;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@ThreadSafe
@AllArgsConstructor
public class SimpleAccidentsService implements AccidentService {

    private final SqlAccidentRepository accidentRepository;

    private final SqlTypeRepository memoryAccidentType;

    private final SqlRuleRepository memoryAccidentRuleRepository;

    @Override
    public Accident save(Accident accident, Set<Integer> id) {
        accident.setType(memoryAccidentType.findById(accident.getType().getId()));
        accident.setRule(memoryAccidentRuleRepository.findAllById(id));
        return accidentRepository.save(accident);
    }

    @Override
    public boolean deleteById(int id) {
        return accidentRepository.deleteById(id);
    }

    @Override
    public boolean update(Accident accident, Set<Integer> id) {
        accident.setType(memoryAccidentType.findById(accident.getType().getId()));
        accident.setRule(memoryAccidentRuleRepository.findAllById(id));
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