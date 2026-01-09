package com.epam.service.impl;

import com.epam.dto.response.TrainingTypeResponseDto;
import com.epam.exception.NotFoundException;
import com.epam.mapper.TrainingTypeMapper;
import com.epam.model.TrainingType;
import com.epam.repository.TrainingTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author jdmon on 8/01/2026
 * @project springbasegymcrm
 */
class TrainingTypeServiceImplTest {

    private TrainingTypeRepository repository;
    private TrainingTypeMapper mapper;
    private TrainingTypeServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(TrainingTypeRepository.class);
        mapper = mock(TrainingTypeMapper.class);
        service = new TrainingTypeServiceImpl(repository, mapper);
    }

    @Test
    void selectTrainingTypeByIdShouldReturnEntityWhenFound() {
        TrainingType type = new TrainingType();
        type.setName("Yoga");

        when(repository.findById(1L)).thenReturn(Optional.of(type));

        TrainingType result = service.selectTrainingTypeById(1L);

        assertEquals("Yoga", result.getName());
    }

    @Test
    void selectTrainingTypeByIdShouldThrowWhenNotFound() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.selectTrainingTypeById(1L));
    }

    @Test
    void getAllTrainingTypesShouldReturnMappedDtos() {
        TrainingType type = new TrainingType();
        type.setName("Pilates");

        List<TrainingType> entities = List.of(type);
        List<TrainingTypeResponseDto> dtos = List.of(new TrainingTypeResponseDto(4L,"Pilates"));

        when(repository.findAll()).thenReturn(entities);
        when(mapper.trainingTypesToTrainingTypesResponse(entities)).thenReturn(dtos);

        List<TrainingTypeResponseDto> result = service.getAllTrainingTypes("dummyToken");

        assertEquals(1, result.size());
        assertEquals("Pilates", result.get(0).name());
    }

}