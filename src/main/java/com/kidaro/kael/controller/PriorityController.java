package com.kidaro.kael.controller;

import com.kidaro.kael.model.Priority;
import com.kidaro.kael.service.PriorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/priorities")
public class PriorityController {

    @Autowired
    private PriorityService priorityService;

    @GetMapping
    public List<Priority> getAllPriorities() {
        return priorityService.getAllPriorities();
    }

    @GetMapping("/{id}")
    public Optional<Priority> getPriorityById(@PathVariable Long id) {
        return priorityService.getPriorityById(id);
    }

    @PostMapping
    public Priority createPriority(@RequestBody Priority priority) {
        return priorityService.createPriority(priority);
    }

    @PutMapping("/{id}")
    public Priority updatePriority(@PathVariable Long id, @RequestBody Priority priorityDetails) {
        return priorityService.updatePriority(id, priorityDetails);
    }

    @DeleteMapping("/{id}")
    public void deletePriority(@PathVariable Long id) {
        priorityService.deletePriority(id);
    }
}
