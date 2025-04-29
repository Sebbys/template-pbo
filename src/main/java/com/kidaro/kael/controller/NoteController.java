package com.kidaro.kael.controller;

import com.kidaro.kael.model.Note; // Assuming Note is in this package
import com.kidaro.kael.repository.NoteRepository; // Assuming NoteRepository is in this package
import java.util.List;
import org.springframework.web.bind.annotation.*;
@CrossOrigin("*")
@RestController
@RequestMapping("/notes")
public class NoteController {

    private final NoteRepository noteRepository;

    public NoteController(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @PostMapping("/add")
    public Note addNote(@RequestBody Note note) {
        return noteRepository.save(note);
    }

    @GetMapping("/all")
    public List<Note> getAllNotes() {
        return noteRepository.findAll();
    }
}

