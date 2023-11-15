package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.model.AccidentType;
import ru.job4j.accidents.model.Rule;
import ru.job4j.accidents.service.AccidentRuleService;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@ThreadSafe
@Controller
@AllArgsConstructor
@RequestMapping("/accident")
public class AccidentController {

    private final AccidentService accidentService;

    private final AccidentTypeService accidentTypeService;

    private final AccidentRuleService accidentRuleService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", accidentService.findAll());
        return "accident/list";
    }

    @GetMapping("/createAccident")
    public String viewCreateAccident(Model model) {
        List<AccidentType> types = (List<AccidentType>) accidentTypeService.findAll();
        List<Rule> rules = (List<Rule>) accidentRuleService.findAll();
        model.addAttribute("rules", rules);
        model.addAttribute("types", types);
        return "accident/createAccident";
    }

    @PostMapping("/saveAccident")
    public String save(@ModelAttribute Accident accident, HttpServletRequest req) {
        String[] ids = req.getParameterValues("rIds");
        accidentService.save(accident);
        return "redirect:/accident";
    }

    @GetMapping("/updateAccident")
    public String update(@RequestParam("id") int id, Model model) {
        Optional<Accident> optionalAccident = accidentService.findById(id);
        if (optionalAccident.isEmpty()) {
            model.addAttribute("message", "Не найден Id прошествия");
            return "errors/404";
        }
        model.addAttribute("accident", optionalAccident.get());
        return "accident/updateAccident";
    }

    @PostMapping("/updateAccident")
    public String update(@ModelAttribute Accident accident) {
        if (!accidentService.update(accident)) {
            return "errors/404";
        }
        return "redirect:/accident";
    }
}