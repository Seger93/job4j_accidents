package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.repository.AccidentRepository;
import ru.job4j.accidents.repository.MemoryAccidentRepository;

@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {

    private final AccidentRepository accidentRepository = MemoryAccidentRepository.getIns();

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidentRepository.findAll());
        return "accident/list";
    }
}