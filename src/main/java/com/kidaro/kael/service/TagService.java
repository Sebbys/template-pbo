package com.kidaro.kael.service;

import com.kidaro.kael.model.Tag;
import com.kidaro.kael.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }

    public Optional<Tag> getTagById(Long id) {
        return tagRepository.findById(id);
    }

    public Tag createTag(Tag tag) {
        // Add validation if needed (e.g., check for duplicate names)
        return tagRepository.save(tag);
    }

    public Tag updateTag(Long id, Tag tagDetails) {
        return tagRepository.findById(id).map(tag -> {
            tag.setName(tagDetails.getName());
            tag.setColor(tagDetails.getColor());
            return tagRepository.save(tag);
        }).orElse(null); // Or throw an exception
    }

    public void deleteTag(Long id) {
        // Consider handling cases where the tag is still assigned to tasks
        tagRepository.deleteById(id);
    }
}
