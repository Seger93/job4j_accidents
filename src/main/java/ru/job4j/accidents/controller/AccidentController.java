package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;

import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {

    private final AccidentService accidentService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "accident/list";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident() {
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident) {
        accidentService.save(accident);
        return "redirect:/accident";
    }

    @GetMapping("/updateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        model.addAttribute("accident", accidentService.findById(id).get());
        return "accident/updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        accidentService.update(accident);
        return "redirect:/accident";
    }
}