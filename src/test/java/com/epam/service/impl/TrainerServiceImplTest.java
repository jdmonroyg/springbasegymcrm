package com.epam.service.impl;

import com.epam.dto.request.CreateTrainerRequestDto;
import com.epam.dto.response.CreateUserResponseDto;
import com.epam.dto.response.TrainerResponseDto;
import com.epam.exception.EmailAlreadyExistsException;
import com.epam.mapper.TrainerMapper;
import com.epam.mapper.TrainingMapper;
import com.epam.model.Trainer;
import com.epam.model.TrainingType;
import com.epam.repository.TrainerRepository;
import com.epam.repository.UserRepository;
import com.epam.service.TrainingTypeService;
import com.epam.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class TrainerServiceImplTest {

    private TrainerRepository trainerRepository;
    private TrainingTypeService trainingTypeService;
    private UserRepository userRepository;
    private UserUtil userUtil;
    private PasswordEncoder encoder;
    private TrainerMapper trainerMapper;
    private TrainingMapper trainingMapper;
    private TrainerServiceImpl service;

    @BeforeEach
    void setUp() {
        trainerRepository = mock(TrainerRepository.class);
        trainingTypeService = mock(TrainingTypeService.class);
        userRepository = mock(UserRepository.class);
        userUtil = mock(UserUtil.class);
        encoder = mock(PasswordEncoder.class);
        trainerMapper = mock(TrainerMapper.class);
        trainingMapper = mock(TrainingMapper.class);

        service = new TrainerServiceImpl(trainerRepository, trainingTypeService, userRepository,
                userUtil, encoder, trainerMapper, trainingMapper);
    }

    @Test
    void createTrainerShouldReturnResponseDto() {
        CreateTrainerRequestDto dto = new CreateTrainerRequestDto("John", "Doe", "john@email.com", 1L);
        Trainer trainer = new Trainer("John","Doe","john@email.com","jdoe","encodedPass",true,new TrainingType());

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(userUtil.generateUsername("John","Doe", Collections.emptySet())).thenReturn("jdoe");
        when(userUtil.generateRandomPassword()).thenReturn("rawPass");
        when(encoder.encode("rawPass")).thenReturn("encodedPass");
        when(trainingTypeService.selectTrainingTypeById(1L)).thenReturn(new TrainingType());
        when(trainerMapper.createTrainerRequestDtoToTrainer(dto)).thenReturn(trainer);

        CreateUserResponseDto response = service.createTrainer(dto);

        assertEquals("jdoe", response.username());
        assertEquals("rawPass", response.password());
        verify(trainerRepository).save(trainer);
    }

    @Test
    void createTrainerShouldThrowWhenEmailExists() {
        CreateTrainerRequestDto dto = new CreateTrainerRequestDto("John", "Doe", "john@email.com", 1L);
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(new Trainer()));

        assertThrows(EmailAlreadyExistsException.class, () -> service.createTrainer(dto));
    }


    @Test
    void selectTrainerByUsernameShouldThrowWhenNotFound() {
        when(trainerRepository.findByUsername("jdoe")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> service.selectTrainerByUsername("jdoe"));
    }
}