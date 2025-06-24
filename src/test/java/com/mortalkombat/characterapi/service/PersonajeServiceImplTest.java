package com.mortalkombat.characterapi.service;
import com.mortalkombat.characterapi.model.NivelPoder;
import com.mortalkombat.characterapi.model.Personaje;
import com.mortalkombat.characterapi.repository.PersonajeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonajeServiceImplTest {

    @Mock
    private PersonajeRepository personajeRepository;

    @InjectMocks
    private PersonajeServiceImpl personajeService;

}