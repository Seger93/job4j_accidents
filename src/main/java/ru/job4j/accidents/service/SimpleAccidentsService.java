package ru.job4j.accidents.service;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.repository.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@ThreadSafe
@AllArgsConstructor
public class SimpleAccidentsService implements AccidentService {

    private final JPAAccidentRepository accidentRepository;

    private final JPATypeRepository memoryAccidentType;

    private final JPARuleRepository memoryAccidentRuleRepository;

    @Override
    public Accident save(Accident accident, Set<Integer> id) {
        accident.setType(memoryAccidentType.findById(accident.getType().getId()).get());
        accident.setRule(memoryAccidentRuleRepository.findAllById(id));
        return accidentRepository.save(accident);
    }

    @Override
    public void deleteById(int id) {
        accidentRepository.deleteById(id);
    }

    @Override
    public Accident update(Accident accident, Set<Integer> id) {
        accident.setType(memoryAccidentType.findById(accident.getType().getId()).get());
        accident.setRule(memoryAccidentRuleRepository.findAllById(id));
        return accidentRepository.save(accident);
    }

    @Override
    public Optional<Accident> findById(int id) {
        return accidentRepository.findById(id);
    }

    @Override
    public Collection<Accident> findAll() {
        return (Collection<Accident>) accidentRepository.findAll();
    }
}