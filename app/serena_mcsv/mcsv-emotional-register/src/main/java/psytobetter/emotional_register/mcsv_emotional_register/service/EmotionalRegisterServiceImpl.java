package psytobetter.emotional_register.mcsv_emotional_register.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import psytobetter.emotional_register.mcsv_emotional_register.client.EmotionRegisterClient;
import psytobetter.emotional_register.mcsv_emotional_register.client.EmotionalUserClient;
import psytobetter.emotional_register.mcsv_emotional_register.dto.clients.EmotionDTO;
import psytobetter.emotional_register.mcsv_emotional_register.dto.clients.UserDTO;
import psytobetter.emotional_register.mcsv_emotional_register.dto.emotionalRegister.EmotionalRegisterRequestDTO;
import psytobetter.emotional_register.mcsv_emotional_register.dto.emotionalRegister.EmotionalRegisterResponseDTO;
import psytobetter.emotional_register.mcsv_emotional_register.exceptions.ResourceNotFoundException;
import psytobetter.emotional_register.mcsv_emotional_register.model.EmotionalRegister;
import psytobetter.emotional_register.mcsv_emotional_register.repository.EmotionalRegisterRepository;


import java.util.List;

@Service
@RequiredArgsConstructor
public class EmotionalRegisterServiceImpl implements EmotionalRegisterService {

    private final EmotionalRegisterRepository repository;
    private final EmotionalUserClient userClient;
    private final EmotionRegisterClient emotionClient;

    @Override
    public EmotionalRegisterResponseDTO create(EmotionalRegisterRequestDTO dto) {

        UserDTO user = userClient.getUserById(dto.getUserId());
        EmotionDTO emotion = emotionClient.getEmotionById(dto.getEmotionId());

        EmotionalRegister entity = new EmotionalRegister();
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        entity.setUserId(dto.getUserId());
        entity.setEmotionId(dto.getEmotionId());

        repository.save(entity);

        return mapToResponse(entity, user, emotion);
    }

    @Override
    public EmotionalRegisterResponseDTO findById(Long id) {

        EmotionalRegister entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Registro emocional no encontrado con ID: " + id)
                );

        UserDTO user = userClient.getUserById(entity.getUserId());
        EmotionDTO emotion = emotionClient.getEmotionById(entity.getEmotionId());

        return mapToResponse(entity, user, emotion);
    }

    @Override
    public List<EmotionalRegisterResponseDTO> findAll() {

        return repository.findAll().stream().map(entity -> {

            UserDTO user = userClient.getUserById(entity.getUserId());
            EmotionDTO emotion = emotionClient.getEmotionById(entity.getEmotionId());

            return mapToResponse(entity, user, emotion);

        }).toList();
    }

    @Override
    public EmotionalRegisterResponseDTO update(Long id, EmotionalRegisterRequestDTO dto) {

        EmotionalRegister entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Registro emocional no encontrado con ID: " + id)
                );

        UserDTO user = userClient.getUserById(dto.getUserId());
        EmotionDTO emotion = emotionClient.getEmotionById(dto.getEmotionId());

        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        entity.setUserId(dto.getUserId());
        entity.setEmotionId(dto.getEmotionId());

        repository.save(entity);

        return mapToResponse(entity, user, emotion);
    }

    @Override
    public void delete(Long id) {

        EmotionalRegister entity = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Registro emocional no encontrado con ID: " + id)
                );

        repository.delete(entity);
    }

    private EmotionalRegisterResponseDTO mapToResponse(
            EmotionalRegister entity,
            UserDTO user,
            EmotionDTO emotion
    ) {
        EmotionalRegisterResponseDTO dto = new EmotionalRegisterResponseDTO();
        dto.setId(entity.getId());
        dto.setDate(entity.getDate());
        dto.setDescription(entity.getDescription());
        dto.getUserId();
        dto.setEmotionId(entity.getEmotionId());
        return dto;
    }
}
