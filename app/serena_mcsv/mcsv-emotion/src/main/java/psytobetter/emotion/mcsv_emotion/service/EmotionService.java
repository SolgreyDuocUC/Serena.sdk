package psytobetter.emotion.mcsv_emotion.service;

import psytobetter.emotion.mcsv_emotion.dto.EmotionRequestDTO;
import psytobetter.emotion.mcsv_emotion.dto.EmotionResponseDTO;

import java.util.List;

public interface EmotionService {

    EmotionResponseDTO create(EmotionRequestDTO dto);

    EmotionResponseDTO findById(Long id);

    List<EmotionResponseDTO> findAll();

    EmotionResponseDTO update(Long id, EmotionRequestDTO dto);

    void delete(Long id);
}
