package com.mortalkombat.characterapi.repository;

import com.mortalkombat.characterapi.model.Personaje;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;

@Repository
public class PersonajeRepository {

    private final Map<Long, Personaje> personajes = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(0);

    public Personaje save(Personaje personaje) {
        if (personaje.getId() == null) {
            personaje.setId(idGenerator.getAndIncrement());
        }
        personajes.put(personaje.getId(), personaje);
        return personaje;
    }

    public boolean deleteById(Long id){
        return personajes.remove(id) != null;
    }

    public boolean existById(Long id){
        return personajes.containsKey(id);
    }

    public List<Personaje> findAll(){
        return new ArrayList<>(personajes.values());
    }






}