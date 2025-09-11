package com.epam.service.impl;

import com.epam.dto.response.TrainingsTypeResponseDto;
import com.epam.exception.NotFoundException;
import com.epam.mapper.TrainingTypeMapper;
import com.epam.model.TrainingType;
import com.epam.repository.TrainingTypeRepository;
import com.epam.service.AuthService;
import com.epam.service.TrainingTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jdmon on 16/08/2025
 * @project springbasegymcrm
 */
@Service
public class TrainingTypeServiceImpl implements TrainingTypeService {

    private final TrainingTypeRepository trainingTypeRepository;
    private final TrainingTypeMapper trainingTypeMapper;
    private final AuthService authService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TrainingTypeServiceImpl.class);

    public TrainingTypeServiceImpl(TrainingTypeRepository trainingTypeRepository, TrainingTypeMapper trainingTypeMapper, AuthService authService) {
        this.trainingTypeRepository = trainingTypeRepository;
        this.trainingTypeMapper = trainingTypeMapper;
        this.authService = authService;
    }

    @Override
    @Transactional(readOnly = true)
    public TrainingType selectTrainingTypeById(Long id) {
        TrainingType trainingTypeSelected = trainingTypeRepository.findById(id)
                .orElseThrow(() ->
                        new NotFoundException("TrainingType was not found"));
        LOGGER.debug("The TrainingType {} was select", trainingTypeSelected.getName());
        return trainingTypeSelected;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainingsTypeResponseDto> getAllTrainingTypes(String token) {
        authService.validateAuthentication(token);
        List <TrainingType> trainingTypes = trainingTypeRepository.findAll();
        LOGGER.debug("The TrainingTypes list has {} elements ",trainingTypes.size());
        return trainingTypeMapper.trainingTypesToTrainingTypesResponse(trainingTypeRepository.findAll());
    }
}
