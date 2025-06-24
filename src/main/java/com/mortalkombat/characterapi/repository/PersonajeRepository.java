package com.mortalkombat.characterapi.repository;

import com.mortalkombat.characterapi.model.Personaje;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;

@Repository
public class PersonajeRepository {

    private final Map<Long, Personaje> personajes = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

}