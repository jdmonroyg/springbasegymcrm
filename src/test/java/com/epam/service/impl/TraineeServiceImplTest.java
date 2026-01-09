package com.epam.service.impl;

import com.epam.dto.request.CreateTraineeRequestDto;
import com.epam.dto.response.CreateUserResponseDto;
import com.epam.dto.response.TraineeResponseDto;
import com.epam.exception.EmailAlreadyExistsException;
import com.epam.exception.NotFoundException;
import com.epam.mapper.TraineeMapper;
import com.epam.mapper.TrainingMapper;
import com.epam.model.Trainee;
import com.epam.repository.TraineeRepository;
import com.epam.repository.UserRepository;
import com.epam.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class TraineeServiceImplTest {

    private TraineeRepository traineeRepository;
    private UserRepository userRepository;
    private UserUtil userUtil;
    private PasswordEncoder encoder;
    private TraineeMapper traineeMapper;
    private TrainingMapper trainingMapper;
    private TraineeServiceImpl service;

    @BeforeEach
    void setUp() {
        traineeRepository = mock(TraineeRepository.class);
        userRepository = mock(UserRepository.class);
        userUtil = mock(UserUtil.class);
        encoder = mock(PasswordEncoder.class);
        traineeMapper = mock(TraineeMapper.class);
        trainingMapper = mock(TrainingMapper.class);

        service = new TraineeServiceImpl(traineeRepository, userRepository, userUtil, encoder, traineeMapper, trainingMapper);
    }

    @Test
    void createTraineeShouldReturnResponseDto() {
        CreateTraineeRequestDto dto = new CreateTraineeRequestDto("John", "Doe", "john@email.com", LocalDate.of(1990,1,1), "123 Street");
        Trainee trainee = new Trainee("John","Doe","john@email.com","jdoe","encodedPass",true,LocalDate.of(1990,1,1),"123 Street");

        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.empty());
        when(userUtil.generateUsername("John","Doe", Collections.emptySet())).thenReturn("jdoe");
        when(userUtil.generateRandomPassword()).thenReturn("rawPass");
        when(encoder.encode("rawPass")).thenReturn("encodedPass");
        when(traineeMapper.createTraineeRequestDtoToTrainee(dto)).thenReturn(trainee);

        CreateUserResponseDto response = service.createTrainee(dto);

        assertEquals("jdoe", response.username());
        assertEquals("rawPass", response.password());
        verify(traineeRepository).save(trainee);
    }

    @Test
    void createTraineeShouldThrowWhenEmailExists() {
        CreateTraineeRequestDto dto = new CreateTraineeRequestDto("John", "Doe", "john@email.com", LocalDate.of(1990,1,1), "123 Street");
        when(userRepository.findByEmail(dto.email())).thenReturn(Optional.of(new Trainee()));

        assertThrows(EmailAlreadyExistsException.class, () -> service.createTrainee(dto));
    }


    @Test
    void selectTraineeByUsernameShouldThrowWhenNotFound() {
        when(traineeRepository.findByUsername("jdoe")).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.selectTraineeByUsername("jdoe"));
    }

}