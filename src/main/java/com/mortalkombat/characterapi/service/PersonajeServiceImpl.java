package com.mortalkombat.characterapi.service;

import com.mortalkombat.characterapi.model.NivelPoder;
import com.mortalkombat.characterapi.model.Personaje;
import com.mortalkombat.characterapi.repository.PersonajeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PersonajeServiceImpl implements PersonajeService {

    private final PersonajeRepository repository;

    @Autowired
    public PersonajeServiceImpl(PersonajeRepository repository) {
        this.repository = repository;
    }

    public Personaje crearPersonaje (Personaje personaje) {
        return repository.save(personaje);
    }

    public boolean eliminarPersonaje(Long id) {
        return repository.deleteById(id);
    }

    public Optional<Personaje> editarPersonaje (Long id, Personaje personajeActualizado) {
        if (!repository.existById(id)) {
            return Optional.empty();
        }

        personajeActualizado.setId(id);

        return Optional.of(repository.save(personajeActualizado));
    }

    public List<Personaje> listarTodos() {
        return repository.findAll();
    }

    public List<Personaje> obtenerPersonajesConPoderMedioOSuperior() {
        return repository.findAll().stream()
                .filter(p -> p.getNivelDePoder().ordinal() >= NivelPoder.MEDIO.ordinal())
                .collect(Collectors.toList());
    }

    public List<Personaje> buscarPorNombre(String subcadena) {
        String lowerCaseSubcadena = subcadena.toLowerCase();
        return repository.findAll().stream()
                .filter(p -> p.getNombre().toLowerCase().contains(lowerCaseSubcadena))
                .collect(Collectors.toList());
    }

    public List<Personaje> listarOrdenadosPorSaludDesc() {
        return repository.findAll().stream()
                .sorted(Comparator.comparingInt(Personaje::getSaludMaxima).reversed())
                .collect(Collectors.toList());
    }







}