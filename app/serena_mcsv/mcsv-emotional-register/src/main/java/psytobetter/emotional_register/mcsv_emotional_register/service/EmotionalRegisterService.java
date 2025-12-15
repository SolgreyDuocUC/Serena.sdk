package psytobetter.emotional_register.mcsv_emotional_register.service;

import psytobetter.emotional_register.mcsv_emotional_register.dto.emotionalRegister.EmotionalRegisterRequestDTO;
import psytobetter.emotional_register.mcsv_emotional_register.dto.emotionalRegister.EmotionalRegisterResponseDTO;

import java.util.List;

public interface EmotionalRegisterService {

    EmotionalRegisterResponseDTO create(EmotionalRegisterRequestDTO dto);

    EmotionalRegisterResponseDTO findById(Long id);

    List<EmotionalRegisterResponseDTO> findAll();

    EmotionalRegisterResponseDTO update(Long id, EmotionalRegisterRequestDTO dto);

    void delete(Long id);
}

