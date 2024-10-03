package com.sgp.qa.service;

import com.sgp.qa.model.Tags;
import com.sgp.qa.repository.TagsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomeService {

    @Autowired
    private TagsRepository tagsRepository;

    public List<Tags> getAllTags() {

        return tagsRepository.findAll();
    }
}
