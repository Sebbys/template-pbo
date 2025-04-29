package com.kidaro.kael.service;

import com.kidaro.kael.model.Priority;
import com.kidaro.kael.repository.PriorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PriorityService {

    @Autowired
    private PriorityRepository priorityRepository;

    public List<Priority> getAllPriorities() {
        return priorityRepository.findAll();
    }

    public Optional<Priority> getPriorityById(Long id) {
        return priorityRepository.findById(id);
    }

    public Priority createPriority(Priority priority) {
        // Add validation if needed (e.g., check for duplicate levels)
        return priorityRepository.save(priority);
    }

    public Priority updatePriority(Long id, Priority priorityDetails) {
        return priorityRepository.findById(id).map(priority -> {
            priority.setLevel(priorityDetails.getLevel());
            priority.setColor(priorityDetails.getColor());
            return priorityRepository.save(priority);
        }).orElse(null); // Or throw an exception
    }

    public void deletePriority(Long id) {
        // Consider handling cases where the priority is still assigned to tasks
        priorityRepository.deleteById(id);
    }
}
