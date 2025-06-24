package com.mortalkombat.characterapi.service;

import com.mortalkombat.characterapi.model.Personaje;
import com.mortalkombat.characterapi.repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository repository;

    @Autowired
    public PersonajeServiceImpl(PersonajeRepository repository) {
        this.repository = repository;
    }




}