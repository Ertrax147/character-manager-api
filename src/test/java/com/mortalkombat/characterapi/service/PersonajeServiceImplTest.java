package com.mortalkombat.characterapi.service;
import com.mortalkombat.characterapi.model.NivelPoder;
import com.mortalkombat.characterapi.model.Personaje;
import com.mortalkombat.characterapi.repository.PersonajeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonajeServiceImplTest {

    @Mock
    private PersonajeRepository personajeRepository;

    @InjectMocks
    private PersonajeServiceImpl personajeService;

    @Test
    void debeCrearUnNuevoPersonaje(){
        Personaje personajeAcrear = new Personaje(null, "Scorpion", 100, NivelPoder.ALTO, List.of("Lanzar cadena", "Teletransportarse"));
        Personaje personajeEsperado = new Personaje(1L, "Scorpion", 100, NivelPoder.ALTO, List.of("Lanzar cadena", "Teletransportarse"));

        when(personajeRepository.save(any(Personaje.class))).thenReturn(personajeEsperado);

        Personaje resultado = personajeService.crearPersonaje(personajeAcrear);

        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(1L);
        assertThat(resultado.getNombre()).isEqualTo("Scorpion");

    }

    @Test
    void debeEliminarUnPersonajeExistente(){
        Personaje scorpion = new Personaje(1L, "Scorpion", 100, NivelPoder.ALTO, List.of("Lanzar cadena", "Teletransportarse"));
        Long idParaEliminar = scorpion.getId();

        when(personajeRepository.deleteById(idParaEliminar)).thenReturn(true);

        boolean resultado = personajeService.eliminarPersonaje(idParaEliminar);

        assertThat(resultado).isTrue();
        verify(personajeRepository, times(1)).deleteById(idParaEliminar);
    }

    @Test
    void debeEditarUnPersonaje(){
        Personaje scorpion = new Personaje(1L, "Scorpion", 100, NivelPoder.ALTO, List.of("Lanzar cadena", "Teletransportarse"));
        Personaje datosNuevos = new Personaje(null, "Inferno Scorpion", 150, NivelPoder.ALTO, List.of("Lanzar cadena", "Teletransportarse"));

        Long idParaEditar = scorpion.getId();

        when(personajeRepository.existById(idParaEditar)).thenReturn(true);

        when(personajeRepository.save(any(Personaje.class))).thenAnswer(invocation -> {
            Personaje p = invocation.getArgument(0);
            p.setId(idParaEditar);
            return p;
        });

        Optional<Personaje> resultado = personajeService.editarPersonaje(idParaEditar, datosNuevos);

        assertThat(resultado).isPresent();
        assertThat(resultado.get().getNombre()).isEqualTo("Inferno Scorpion");
        assertThat(resultado.get().getSaludMaxima()).isEqualTo(150);
        assertThat(resultado.get().getId()).isEqualTo(idParaEditar);
    }

    @Test
    void debeListarTodosLosPersonajes(){
        Personaje scorpion = new Personaje(1L, "Scorpion", 100, NivelPoder.ALTO, List.of("Lanzar cadena", "Teletransportarse"));
        Personaje scorpionInferno = new Personaje(null, "Inferno Scorpion", 150, NivelPoder.ALTO, List.of("Lanzar cadena", "Teletransportarse"));

        List<Personaje> listaDePersonajes = List.of(scorpion, scorpionInferno);

        when(personajeRepository.findAll()).thenReturn(listaDePersonajes);

        List<Personaje> resultado = personajeService.listarTodos();

        assertThat(resultado).isNotNull();
        assertThat(resultado).hasSize(2);
    }

    @Test
    @DisplayName("Debe obtener personajes con nivel de poder MEDIO o ALTO")
    void debeObtenerPersonajesConPoderMedioOSuperior() {
        Personaje scorpion = new Personaje(1L, "Scorpion", 105, NivelPoder.ALTO, null);
        Personaje subZero = new Personaje(2L, "Sub Zero", 105, NivelPoder.BAJO, null);
        Personaje liuKang = new Personaje(3L, "Liu Kang", 105, NivelPoder.MEDIO, null);
        when(personajeRepository.findAll()).thenReturn(List.of(scorpion, subZero, liuKang));

        List<Personaje> resultado = personajeService.obtenerPersonajesConPoderMedioOSuperior();

        assertThat(resultado).hasSize(2);
        assertThat(resultado).extracting(Personaje::getNombre).containsExactlyInAnyOrder("Scorpion", "Liu Kang");
    }

    @Test
    @DisplayName("Debe buscar personajes por subcadena en el nombre (ignorando mayúsculas)")
    void debeBuscarPorNombre() {
        Personaje scorpion = new Personaje(1L, "Scorpion", 105, NivelPoder.ALTO, null);
        Personaje subZero = new Personaje(2L, "Sub Zero", 105, NivelPoder.BAJO, null);

        when(personajeRepository.findAll()).thenReturn(List.of(scorpion, subZero));

        List<Personaje> resultado = personajeService.buscarPorNombre("sCo");

        assertThat(resultado).hasSize(1);
        assertThat(resultado.get(0).getNombre()).isEqualTo("Scorpion");
    }

    @Test
    @DisplayName("Debe listar personajes ordenados por salud máxima descendente")
    void debeListarOrdenadosPorSaludDesc() {
        Personaje scorpion = new Personaje(1L, "Scorpion", 110, NivelPoder.ALTO, null);
        Personaje subZero = new Personaje(2L, "Sub Zero", 105, NivelPoder.BAJO, null);
        Personaje liuKang = new Personaje(3L, "Liu Kang", 100, NivelPoder.MEDIO, null);

        when(personajeRepository.findAll()).thenReturn(List.of(subZero, scorpion, liuKang));

        List<Personaje> resultado = personajeService.listarOrdenadosPorSaludDesc();

        assertThat(resultado).hasSize(3);
        assertThat(resultado).extracting(Personaje::getSaludMaxima).containsExactly(110, 105, 100);
    }




}